package com.app.server.utils;

import java.util.UUID;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CartCookieUtils {
	public static final String CART_COOKIE = "CART_TOKEN";

    private CartCookieUtils() {}

    public static UUID getCartToken(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if (CART_COOKIE.equals(cookie.getName())) {
                return UUID.fromString(cookie.getValue());
            }
        }
        return null;
    }

    public static void setCartCookie(HttpServletResponse response, UUID cartToken) {
        Cookie cookie = new Cookie(CART_COOKIE, cartToken.toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
        response.addCookie(cookie);
    }

    public static void clearCartCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(CART_COOKIE, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
