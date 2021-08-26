package com.spring.logstash.commons.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import static com.spring.logstash.commons.utils.SpringApplicationContext.getApplicationContext;

public class CryptoUtils {

	public static final String MASTER_KEY_PROPERTY = "crypto.masterkey";
	public static String MASTER_KEY;

	static {
		ApplicationContext ctx = getApplicationContext();

		if (ctx != null) {
			String propValue = ctx.getEnvironment().getProperty(CryptoUtils.MASTER_KEY_PROPERTY);
			MASTER_KEY = propValue;
		}
	}

	public static String decrypt(byte[] input, byte[] key) throws Exception {
		if (input.length == 0)
			return "";

		String encryptedText = new String(input);
		if (encryptedText.startsWith("encrypted_twilight_text_"))
			input = encryptedText.replace("encrypted_twilight_text_", "").getBytes();

		input = Base64.getDecoder().decode(input);

		// Check Minimum Length (IV (12) + TAG (16))
		if (input.length > 28) {
			byte[] iv = Arrays.copyOfRange(input, 0, 12);
			byte[] content = Arrays.copyOfRange(input, 12, input.length);

			SecretKeySpec sKey = new SecretKeySpec(key, "AES");
			Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
			c.init(Cipher.DECRYPT_MODE, sKey, new GCMParameterSpec(128, iv));

			return new String(c.doFinal(content), StandardCharsets.UTF_8);
		}
		throw new Exception();
	}

	public static String encrypt(String content, byte[] key) throws Exception {
		if (ObjectUtils.isEmpty(content))
			return "";
		if (content.startsWith("encrypted_twilight_text_"))
			return content;

		SecureRandom r = SecureRandom.getInstance("SHA1PRNG");
		// Generate 128 bit IV for Encryption
		byte[] iv = new byte[12];
		r.nextBytes(iv);

		SecretKeySpec sKey = new SecretKeySpec(key, "AES");
		Cipher c = Cipher.getInstance("AES/GCM/NoPadding");

		// Generated Authentication Tag should be 128 bits
		c.init(Cipher.ENCRYPT_MODE, sKey, new GCMParameterSpec(128, iv));
		byte[] es = c.doFinal(content.getBytes(StandardCharsets.UTF_8));

		// Construct Output as "IV + CIPHERTEXT"
		byte[] output = new byte[12 + es.length];
		System.arraycopy(iv, 0, output, 0, 12);
		System.arraycopy(es, 0, output, 12, es.length);

		String encrypted = Base64.getEncoder().encodeToString(output);
		return "encrypted_twilight_text_".concat(encrypted);
	}

}