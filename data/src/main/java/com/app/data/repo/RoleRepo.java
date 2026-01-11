package com.app.data.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.data.entity.Role;
import com.app.data.enums.RoleEnum;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID>, JpaSpecificationExecutor<Role> {
	@Query("select r from Role r where r.code in :code")
	List<Role> findByCodes(List<RoleEnum> code);
}
