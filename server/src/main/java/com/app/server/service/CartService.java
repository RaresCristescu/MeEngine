package com.app.server.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.data.dto.MessageDto;
import com.app.data.entity.Cart;
import com.app.data.entity.Message;
import com.app.data.entity.User;
import com.app.data.mapper.MessageMapper;
import com.app.data.repo.CartRepo;
import com.app.data.repo.MessageRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartService {

	private final CartRepo cartRepo;

	public CartService(CartRepo cartRepo) {
		this.cartRepo = cartRepo;
	}
	
	public Cart getOrCreateGuestCart(UUID cartToken) {
        if (cartToken != null) {
            return cartRepo.findByCartToken(cartToken)
                    .orElseGet(this::createGuestCart);
        }
        return createGuestCart();
    }

    public Cart getOrCreateUserCart(User user) {
        return cartRepo.findCartByUserId(user.getId())
                .orElseGet(() -> createUserCart(user));
    }
	
	public Cart getCurrentUserCart(User user) {
	    return cartRepo.findCartByUserId(user.getId())
	            .orElseGet(() -> createUserCart(user));
	}

    private Cart createGuestCart() {
        Cart cart = new Cart();
        cart.setCartToken(UUID.randomUUID());
        cart.setStatus("ACTIVE");
        return cartRepo.save(cart);
    }

    private Cart createUserCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setStatus("ACTIVE");
        return cartRepo.save(cart);
    }
    
    public void mergeCarts(Cart guestCart, Cart userCart) {

        guestCart.getCartProduct().forEach(guestItem -> {

            userCart.getCartProduct().stream()
                .filter(u -> u.getProduct().getId()
                    .equals(guestItem.getProduct().getId()))
                .findFirst()
                .ifPresentOrElse(
                    userItem -> userItem.setQuantity(
                        userItem.getQuantity() + guestItem.getQuantity()
                    ),
                    () -> {
                        guestItem.setCart(userCart);
                        userCart.getCartProduct().add(guestItem);
                    }
                );
        });

        cartRepo.delete(guestCart);
        cartRepo.save(userCart);
    }

	

}
