package com.axis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axis.model.MTAUser;

@Repository
public interface MTAUserRepository extends JpaRepository<MTAUser,Long> {
	
	Optional<MTAUser> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);

}
