package com.axis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axis.model.MTARole;
import com.axis.model.MTARoleName;

@Repository
public interface MTARoleRepository extends JpaRepository<MTARole, Long> {
	
	Optional<MTARole> findByName(MTARoleName roleName);

}
