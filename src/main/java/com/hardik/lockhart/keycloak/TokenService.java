package com.hardik.lockhart.keycloak;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.hardik.lockhart.keycloak.configuration.KeycloakConfiguration;
import com.hardik.lockhart.keycloak.dto.KeycloakTokenDto;
import com.hardik.lockhart.keycloak.request.LogoutUserRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(KeycloakConfiguration.class)
public class TokenService {

	private final KeycloakConfiguration keycloakConfiguration;

	private final RestTemplate restTemplate;

	public KeycloakTokenDto getAccessToken(String code) {
		final var configuration = keycloakConfiguration.getKeyCloak();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", configuration.getClientId());
		map.add("grant_type", configuration.getGrantType());
		map.add("code", code);
		map.add("redirect_uri", configuration.getRedirectUri());
		map.add("client_secret", configuration.getClientSecret());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		KeycloakTokenDto keycloakTokenDto = null;
		try {
			keycloakTokenDto = restTemplate.postForObject(configuration.getDomain() + "/auth/realms/"
					+ configuration.getRealmName() + "/protocol/openid-connect/token", request, KeycloakTokenDto.class);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return keycloakTokenDto;
	}

	public KeycloakTokenDto refreshToken(String refreshToken) {
		final var configuration = keycloakConfiguration.getKeyCloak();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", configuration.getClientId());
		map.add("grant_type", "refresh_token");
		map.add("refresh_token", refreshToken);
		map.add("client_secret", configuration.getClientSecret());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		KeycloakTokenDto keycloakTokenDto = null;
		try {
			keycloakTokenDto = restTemplate.postForObject(configuration.getDomain() + "/auth/realms/"
					+ configuration.getRealmName() + "/protocol/openid-connect/token", request, KeycloakTokenDto.class);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return keycloakTokenDto;
	}

	public String getKeyCloakUrl() {
		final var configuration = keycloakConfiguration.getKeyCloak();
		return configuration.getDomain() + "/auth/realms/" + configuration.getRealmName()
				+ "/protocol/openid-connect/auth?client_id=" + configuration.getClientId() + "&redirect_uri="
				+ configuration.getRedirectUri() + "&response_type=code";
	}

	public void logout(LogoutUserRequest logoutUserRequest) {
		final var configuration = keycloakConfiguration.getKeyCloak();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("Authorization", "Bearer " + logoutUserRequest.getAccessToken());
		map.add("refresh_token", logoutUserRequest.getRefreshToken());
		map.add("client_id", configuration.getClientId());
		map.add("client_secret", configuration.getClientSecret());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		try {
			restTemplate.postForLocation(configuration.getDomain() + "/auth/realms/" + configuration.getRealmName()
					+ "/protocol/openid-connect/logout", request);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

}
