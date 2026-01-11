package com.app.data.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.data.entity.SessionKey;
import com.app.data.entity.User;

@Repository
public interface SessionKeyRepo extends JpaRepository<SessionKey, UUID>, JpaSpecificationExecutor<SessionKey> {
	@Query("""
		    SELECT s FROM SessionKey s
		    JOIN FETCH s.user u
		    LEFT JOIN FETCH u.role ur
		    LEFT JOIN FETCH ur.role r
		    WHERE s.id = :id
		""")
	SessionKey findByIdWithUserRolesAndRoles(UUID id);
	
	@Query("select s.user from SessionKey s where s.id = :id")
	Optional<User> findUserBySessId(UUID id);
	
	@Query("select s.user.id from SessionKey s where s.id = :id")
	Optional<UUID> findUserIdBySessId(UUID id);
	
	@Query("select s.user.login from SessionKey s where s.id = :id")
	Optional<String> findUserLoginBySessId(UUID id);
	
	@Modifying
	@Query("delete from SessionKey s where s.expires <= current_timestamp")
	void deleteExpiredSessions();
	
	@Modifying
	@Query("delete from SessionKey s where s.user.id = :id")
	void deleteByUserId(UUID id);
}
