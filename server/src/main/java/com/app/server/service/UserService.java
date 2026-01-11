package com.app.server.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.data.dto.UserDetailsDto;
import com.app.data.dto.UserDto;
import com.app.data.entity.Role;
import com.app.data.entity.User;
import com.app.data.entity.UserRole;
import com.app.data.enums.RoleEnum;
import com.app.data.repo.RoleRepo;
import com.app.data.repo.SessionKeyRepo;
import com.app.data.repo.UserRepo;
import com.app.security.utils.PasswordUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	private final UserRepo userRepo;
	private final RoleRepo roleEepo;
	private final SessionKeyRepo sessionKeyRepo;
	
	private final SecurityService securityService;

	public UserService(UserRepo userRepo, RoleRepo roleEepo, SecurityService securityService, SessionKeyRepo sessionKeyRepo) {
		this.userRepo = userRepo;
		this.roleEepo = roleEepo;
		this.securityService = securityService;
		this.sessionKeyRepo = sessionKeyRepo;
	}

	public UserDetailsDto getAccountDetails(final UUID id) {
		User u = userRepo.findById(id).orElseThrow(NoSuchElementException::new);
		UserDetailsDto ud = UserDetailsDto.builder().login(u.getLogin()).email(u.getEmail())
				.role(u.getRole().iterator().next().getRole().getCode().name()).build();
		return ud;
	}

	public void registerUser(UserDto userDto) {
		String hashPwd = PasswordUtils.encode(userDto.getPassword());

		User newUser = new User();
		newUser.setLogin(userDto.getLogin());
		newUser.setEmail(userDto.getEmail());
		newUser.setPassword(hashPwd);
		newUser.setDisabled(false);
		final User dbUser = userRepo.save(newUser);

		Set<UserRole> userRoles = dbUser.getRole();
		List<Role> rList = roleEepo.findByCodes(userDto.getRoles()!=null && !userDto.getRoles().isEmpty()//TODO remove thius check after UI sends the roles
				? userDto.getRoles().stream().map(rDto -> RoleEnum.valueOf(rDto)).collect(Collectors.toList())
				: List.of(RoleEnum.ROLE_USER));
		rList.forEach(r -> userRoles.add(new UserRole(dbUser, r)));
		dbUser.setRole(userRoles);

		userRepo.save(dbUser);
	}

	public void deleteUser(UUID id) {
		securityService.hasRole(RoleEnum.ROLE_ADMIN);
		final UUID currentUserId = securityService.getCurrentUserId();

		log.info("[ USER-ID: {} ] Deleted user with id {} ", currentUserId, id);
		
		User user = getUserById(id);
		
		if(user.getExpires() == null) {
			user.setExpires(ZonedDateTime.now());
			sessionKeyRepo.deleteByUserId(id);
		}
		userRepo.save(user);
	}
	
	public User getUserById(UUID id) {
		return  userRepo.findById(id).orElseThrow(() -> new RuntimeException("Could not find user by id: " + id));
	}
}
