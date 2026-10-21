package com.snhu.sslserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies the checksum logic against the published SHA-256 test vectors
 * (NIST FIPS 180-4 examples).
 */
class ChecksumGeneratorTests {

	@Test
	void hashesKnownVectorAbc() {
		assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad",
				ChecksumGenerator.sha256Hex("abc"));
	}

	@Test
	void hashesEmptyString() {
		assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
				ChecksumGenerator.sha256Hex(""));
	}

	@Test
	void checksumIsAlways64HexCharacters() {
		assertEquals(64, ChecksumGenerator.sha256Hex("Artemis Financial").length());
	}

	@Test
	void smallChangeInDataChangesChecksum() {
		assertNotEquals(ChecksumGenerator.sha256Hex("Hello World Check Sum!"),
				ChecksumGenerator.sha256Hex("Hello World Check Sum?"));
	}

	@Test
	void rejectsNullData() {
		assertThrows(IllegalArgumentException.class, () -> ChecksumGenerator.sha256Hex(null));
	}
}
