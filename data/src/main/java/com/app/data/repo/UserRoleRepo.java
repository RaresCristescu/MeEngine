package com.app.data.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.app.data.entity.User;
import com.app.data.entity.UserRole;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, UUID>, JpaSpecificationExecutor<UserRole> {
	Optional<UserRole> findByUser(User user);
}
