package com.hardik.lockhart.keycloak.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.hardik.lockhart")
public class KeycloakConfiguration {

	private Configuration keyCloak = new Configuration();

	@Data
	public class Configuration {
		private String realmName;
		private String clientId;
		private String clientSecret;
		private String redirectUri;
		private String domain;
		private String grantType;
	}

}
