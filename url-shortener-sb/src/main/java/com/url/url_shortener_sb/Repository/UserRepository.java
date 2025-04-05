package com.url.url_shortener_sb.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.url.url_shortener_sb.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional <User> findByUsername(String username);
}
