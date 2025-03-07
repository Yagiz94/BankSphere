// BankAppApplication.java
package com.example.bankSphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Base64;


@SpringBootApplication
public class BankAppApplication {
	public static void main(String[] args) {
        try {
            tokenizer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SpringApplication.run(BankAppApplication.class, args);
	}

	private static final String SECRET_KEY = "your-secret-key";

	private static void tokenizer()
			throws Exception {
		// 1. Create Header
		String header = Base64.getUrlEncoder().withoutPadding().encodeToString(
				"{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());

		// 2. Create Payload
		String payload = Base64.getUrlEncoder().withoutPadding().encodeToString(
				("{\"sub\":\"testUser\",\"role\":\"USER\",\"exp\":" + (System.currentTimeMillis() / 1000 + 600) + "}").getBytes()
		);

		// 3. Combine Header and Payload
		String unsignedToken = header + "." + payload;

		// 4. Generate Signature (Fake HMAC Implementation Without Dependencies)
		String signature = simpleHmacSha256(unsignedToken, SECRET_KEY);

		// 5. Combine All Parts of the JWT
		String jwtToken = unsignedToken + "." + signature;
		System.out.println("Generated Token: " + jwtToken);

	}

	private static String simpleHmacSha256(String data, String key) {
		StringBuilder sb = new StringBuilder();

		// Simplistic "hashing" algorithm: XOR each character of data with the key
		for (int i = 0; i < data.length(); i++) {
			char hashedChar = (char) (data.charAt(i) ^ key.charAt(i % key.length()));
			sb.append(hashedChar);
		}

		// Encode the result in Base64
		return Base64.getUrlEncoder().withoutPadding().encodeToString(sb.toString().getBytes());
	}
}
