package com.app.server.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.data.dto.MessageDto;
import com.app.data.entity.Cart;
import com.app.server.service.CartService;
import com.app.server.utils.CartCookieUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/cart")
public class CartRest {
	 private final CartService cartService;

	    public CartRest(CartService cartService) {
	        this.cartService = cartService;
	    }

	    @GetMapping
	    public Cart getCart(HttpServletRequest request, HttpServletResponse response) {

	        UUID cartToken = CartCookieUtils.getCartToken(request);
	        Cart cart = cartService.getOrCreateGuestCart(cartToken);

	        if (cartToken == null) {
	            CartCookieUtils.setCartCookie(response, cart.getCartToken());
	        }

	        return cart;
	    }

}
