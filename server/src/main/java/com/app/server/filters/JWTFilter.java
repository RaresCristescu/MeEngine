package com.app.server.filters;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.data.entity.SessionKey;
import com.app.security.constants.SecurityConstants;
import com.app.security.utils.JwtUtils;
import com.app.server.service.SecurityService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{
	
	  private final SecurityService securityService; 

	    public JWTFilter(SecurityService securityService) {
	        this.securityService = securityService;
	    }
	    
	    @Override
	    protected void doFilterInternal(
	            HttpServletRequest request,
	            HttpServletResponse response,
	            FilterChain filterChain
	    ) throws ServletException, IOException {

	        String header = request.getHeader(SecurityConstants.JWT_HEADER);

	        if (header == null || !header.startsWith("Bearer ")) {
	            filterChain.doFilter(request, response);
	            return;
	        }

	        try {
	            String token = header.substring(7);

	            String sessionId = JwtUtils.extractSessionIdUnverified(token);
	            if (sessionId == null) {
	                throw new BadCredentialsException("JWT missing session claim");
	            }

	            SessionKey sessionKey =
	                    securityService.getSessionKeyById(UUID.fromString(sessionId));

	            if (sessionKey == null || sessionKey.getExpires()==null || sessionKey.getExpires()
	            		.isBefore(ZonedDateTime.now())) {
	                throw new BadCredentialsException("Invalid or revoked session");
	            }

	            JwtUtils.validateToken(token, sessionKey.getPublicKey());
	            
	            
	            String[] roles = sessionKey.getUser().getRole().stream()
	                    .map(r -> r.getRole().getCode().name())
	                    .toArray(String[]::new);

	            UsernamePasswordAuthenticationToken authentication =
	                    new UsernamePasswordAuthenticationToken(
	                    		sessionKey.getId(),
	                            null,
	                            AuthorityUtils.createAuthorityList(roles)
	                    );

	            SecurityContextHolder.getContext()
	                    .setAuthentication(authentication);

	        } catch (Exception ex) {
	            SecurityContextHolder.clearContext();
	            throw new BadCredentialsException("Invalid JWT token", ex);
	        }

	        filterChain.doFilter(request, response);
	    }

	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		 return request.getServletPath().equals("/api/auth/login")
				 	|| request.getServletPath().equals("/api/auth")
	                || request.getServletPath().equals("/api/user/register")
	                || request.getServletPath().startsWith("/swagger-ui/")
	                || request.getServletPath().startsWith("/v3/api-docs");
	}

}
