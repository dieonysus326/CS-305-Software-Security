package com.snhu.sslserver;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Generates cryptographic checksums for data verification.
 *
 * SHA-256 (part of the SHA-2 family, NIST FIPS 180-4) is used because it is
 * collision resistant. MD5 and SHA-1 are deliberately not offered because
 * practical collisions exist for both.
 */
public final class ChecksumGenerator {

	/** Standard algorithm name from the Java Security Standard Algorithm Names. */
	public static final String ALGORITHM = "SHA-256";

	private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

	private ChecksumGenerator() {
		// Utility class, not meant to be instantiated.
	}

	/**
	 * Returns the SHA-256 checksum of the given text as a lowercase hex string.
	 *
	 * @param data the text to hash; must not be null
	 * @return 64 hexadecimal characters (256 bits)
	 */
	public static String sha256Hex(String data) {
		if (data == null) {
			throw new IllegalArgumentException("Data to hash must not be null");
		}
		try {
			MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
			// An explicit charset keeps the checksum identical on every platform.
			byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(hash);
		} catch (NoSuchAlgorithmException e) {
			// Every Java platform is required to support SHA-256, so this
			// would mean a broken runtime. Fail closed rather than fall back
			// to a weaker algorithm.
			throw new IllegalStateException(ALGORITHM + " is not available", e);
		}
	}

	/** Converts raw hash bytes to a lowercase hexadecimal string. */
	static String bytesToHex(byte[] bytes) {
		char[] hex = new char[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++) {
			int value = bytes[i] & 0xFF;
			hex[i * 2] = HEX_DIGITS[value >>> 4];
			hex[i * 2 + 1] = HEX_DIGITS[value & 0x0F];
		}
		return new String(hex);
	}
}
