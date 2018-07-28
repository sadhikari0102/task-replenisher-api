package com.walmart.replenisher.utils;

import java.nio.charset.Charset;
import java.util.Base64;

public class ApplicationUtilities {

	public static String getUsername(String authorization) {
		String base64Credentials = authorization.substring("Basic".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                Charset.forName("UTF-8"));
        return credentials.split(":")[0];
	}
}
