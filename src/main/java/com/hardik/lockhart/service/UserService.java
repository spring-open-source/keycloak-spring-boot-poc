package com.hardik.lockhart.service;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.hardik.lockhart.entity.User;
import com.hardik.lockhart.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;

	public void handleSignIn(String idToken) {
		final var decodedJwt = JWT.decode(idToken).getClaims();
		final var emailId = decodedJwt.get("email").asString();
		final var firstName = decodedJwt.get("given_name").asString();
		final var lastName = decodedJwt.get("family_name").asString();

		if (!userRepository.existsByEmailId(emailId)) {
			final var user = new User();
			user.setEmailId(emailId);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			userRepository.save(user);
			log.info("NEW USER CREATED WITH EMAIL ID: " + emailId);
		} else
			log.info("USER ALREALY EXISTS: " + emailId);
	}

}
