package com.app.data.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.data.entity.Cart;
import com.app.data.entity.User;

@Repository
public interface CartRepo extends JpaRepository<Cart, UUID>, JpaSpecificationExecutor<Cart> {
	Optional<Cart> findByCartToken(UUID cartToken);
	

	
	@Query("select c.user from Cart c where c.user.id = :id")
	Optional<Cart> findCartByUserId(UUID id);
}
