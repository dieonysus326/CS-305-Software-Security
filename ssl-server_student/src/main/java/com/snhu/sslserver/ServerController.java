package com.snhu.sslserver;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

/**
 * REST route that returns a checksum for a static data string so a client can
 * verify that the data it received was not altered in transit.
 */
@RestController
public class ServerController {

	/** Unique data string to be verified. No user input is accepted. */
	private static final String DATA =
			"Daniel Sullivan - CS 305 Project Two - Artemis Financial checksum verification";

	/**
	 * Handles GET https://localhost:8443/hash. Only GET is mapped, so other
	 * HTTP methods are rejected with 405 Method Not Allowed.
	 */
	@GetMapping(value = "/hash", produces = MediaType.TEXT_HTML_VALUE)
	public String myHash() {
		String checksum = ChecksumGenerator.sha256Hex(DATA);

		// Values are HTML escaped before they are written to the page. The
		// data is static today, but escaping keeps the route safe from
		// cross-site scripting if the source of the data ever changes.
		return "<p>Data: " + HtmlUtils.htmlEscape(DATA) + "</p>"
				+ "<p>Name of Cipher Algorithm Used: " + HtmlUtils.htmlEscape(ChecksumGenerator.ALGORITHM) + "</p>"
				+ "<p>Checksum Value: " + HtmlUtils.htmlEscape(checksum) + "</p>";
	}
}
