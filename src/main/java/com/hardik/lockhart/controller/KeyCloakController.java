package com.hardik.lockhart.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.lockhart.keycloak.TokenService;
import com.hardik.lockhart.keycloak.dto.KeycloakTokenDto;
import com.hardik.lockhart.keycloak.request.LogoutUserRequest;
import com.hardik.lockhart.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class KeyCloakController {

	private final TokenService tokenService;

	private final UserService userService;

	@GetMapping("v1/get-url")
	public String keycloakUrlHandler() {
		return tokenService.getKeyCloakUrl();
	}

	@GetMapping("/v1/get-token/{code}")
	public KeycloakTokenDto fetchTokenHandler(@PathVariable(required = true, name = "code") final String code)
			throws UnsupportedEncodingException {
		var tokenResponse = tokenService.getAccessToken(code);
		userService.handleSignIn(tokenResponse.getAccess_token());
		return tokenResponse;
	}

	@GetMapping("v1/refresh-token/{refreshToken}")
	public KeycloakTokenDto refreshTokenHandler(
			@PathVariable(name = "refreshToken", required = true) final String refreshToken) {
		return tokenService.refreshToken(refreshToken);
	}

	@PostMapping("v1/logout")
	public void logoutHandler(@RequestBody(required = true) final LogoutUserRequest logoutUserRequest) {
		tokenService.logout(logoutUserRequest);
	}

}
