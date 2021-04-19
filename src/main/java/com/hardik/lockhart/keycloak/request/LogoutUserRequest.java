package com.hardik.lockhart.keycloak.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class LogoutUserRequest {
	
	private final String AccessToken;
	private final String refreshToken;

}
