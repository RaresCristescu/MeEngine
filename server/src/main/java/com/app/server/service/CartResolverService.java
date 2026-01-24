package com.app.server.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.data.entity.Cart;
import com.app.data.entity.User;
import com.app.server.utils.CartCookieUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartResolverService {
	private final CartService cartService;
	private final SecurityService securityService;

	public CartResolverService(CartService cartService, SecurityService securityService) {
		this.cartService = cartService;
		this.securityService = securityService;
	}

	public Cart resolveCart(HttpServletRequest request, HttpServletResponse response) {
		Optional<User> user = securityService.getCurrentUser();
		if (user.isPresent()) {
			return cartService.getOrCreateUserCart(user.get());
		}

		UUID cartToken = CartCookieUtils.getCartToken(request);
		Cart cart = cartService.getOrCreateGuestCart(cartToken);

		if (cartToken == null) {
			CartCookieUtils.setCartCookie(response, cart.getCartToken());
		}

		return cart;
	}
}
