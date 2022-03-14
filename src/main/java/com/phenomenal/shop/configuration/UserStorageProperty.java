package com.phenomenal.shop.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="useruploads")
public class UserStorageProperty {
	private String uploadDirectory;
	public String getUploadDirectory() {
		return uploadDirectory;
	}
	public void setUploadDirectory(String uploadDirectory) {
		this.uploadDirectory = uploadDirectory;
	}
}
