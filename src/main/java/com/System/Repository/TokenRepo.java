package com.System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.System.Entities.ResetToken;

public interface TokenRepo extends JpaRepository<ResetToken, Long> {
	ResetToken findByToken(String token);
}
