package com.hardik.lockhart.keycloak.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class KeycloakTokenDto {
	
	private final String access_token;
	private final Integer expires_in;
	private final Integer refresh_expires_in;
	private final String refresh_token;
	private final String token_type;
	private final Integer not_before_policy;
	private final String session_state;
	private final String scope;

}
