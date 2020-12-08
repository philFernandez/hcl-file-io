package com.hcl.prac;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Unit test for simple App.
 */
class AppTest {

	// Assert that the csv file contains a valid number of values for creating Student objects
	@Test
	void testFile() throws IOException {
		try (BufferedInputStream buffInputStream = new BufferedInputStream(new FileInputStream("students.csv"))) {
			String[] input = new String(buffInputStream.readAllBytes(), StandardCharsets.UTF_8).replace("\n", "")
					.replace("\r", "").split(",");
			assertEquals(0, input.length % 3);
		}
	}
}
