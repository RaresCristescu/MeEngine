package com.app.server.service;

import java.security.KeyPair;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.data.entity.Cart;
import com.app.data.entity.SessionKey;
import com.app.data.entity.User;
import com.app.data.enums.RoleEnum;
import com.app.data.repo.SessionKeyRepo;
import com.app.data.repo.UserRepo;
import com.app.security.utils.JwtUtils;
import com.app.security.utils.KeyUtils;
import com.app.security.utils.PasswordUtils;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SecurityService {

	private final UserRepo userRepo;
	private final SessionKeyRepo sessionRepo;
//	private final CartService cartService;//TODO need better logic

	public SecurityService(UserRepo userRepo, SessionKeyRepo sessionRepo
//			, CartService cartService
			) {
		this.userRepo = userRepo;
		this.sessionRepo = sessionRepo;
//		this.cartService = cartService;
	}

	public String login(String username, String password) {
		return userRepo.findByLogin(username).filter(u -> u.getDisabled() != null && !u.getDisabled())
				.filter(u -> PasswordUtils.matches(password, u.getPassword())).map(u -> {

//					Cart userCart = cartService.getOrCreateUserCart(u);
//
//					if (guestCartToken != null) {
//						cartRepo.findByCartToken(guestCartToken)
//								.ifPresent(guestCart -> cartMergeService.mergeCarts(guestCart, userCart));
//
//						CartCookieUtils.clearCartCookie(response);
//					}

					final SessionKey sk = new SessionKey();
					final UUID sessionId = UUID.randomUUID();

					sk.setId(sessionId);
					sk.setExpires(ZonedDateTime.now().plusHours(1L));
					sk.setUser(u);

					String token;
					try {
						token = generateJwtTokenForSession(sk);
					} catch (Exception e) {
						log.error("Error authenticating  User:{} Error trace: {}", username, e);
						throw new RuntimeException("Failed to generate JWT", e);
					}

					sessionRepo.save(sk);

					return token;
				}).orElseGet(null);
	}

	private String generateJwtTokenForSession(SessionKey sessionKey) throws Exception {
		try {
			final KeyPair keyPair = KeyUtils.generateEs512KeyPair();
			final String publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
			final String privateKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

			sessionKey.setPublicKey(publicKeyBase64);

			final String jwt = JwtUtils.generateToken(sessionKey.getId().toString(), "me-shop-api-token",
					privateKeyBase64);
			return jwt;
		} catch (Exception e) {
			log.error("Error generating JWT for session: {} Error trace: {}", sessionKey.getId(), e);
			throw e;
		}
	}

	public Boolean hasRole(RoleEnum role) {
		return hasRole(List.of(role).stream());
	}

	public Boolean hasRole(Stream<RoleEnum> roles) {
		return getCurrentUser()
				.map(user -> user.getRole().stream().map(ur -> ur.getRole().getCode()).collect(Collectors.toList()))
				.map(userRoles -> roles.filter(r -> userRoles.contains(r)).findFirst().isPresent())
				.orElseGet(() -> false);
	}

	public UUID getCurrentUserId() {
		Optional<User> user = getCurrentUser();
		if (user.isPresent()) {
			return user.get().getId();
		} else {
			log.error("Unidentifeid session.");
			throw new RuntimeException("Unidentifeid session.");
		}
	}

	public Optional<User> getCurrentUser() {
		UUID sessionId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return sessionRepo.findUserBySessId(sessionId);
	}

	@Scheduled(fixedRate = 5000)
	public void deleteExpiredSessions() {
		sessionRepo.deleteExpiredSessions();
	}

	public void logout() {
		UUID sessionId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		sessionRepo.deleteById(sessionId);
	}

	public SessionKey getSessionKeyById(final UUID id) {
		return sessionRepo.findByIdWithUserRolesAndRoles(id);
	}

}
