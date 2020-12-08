package com.hcl.prac;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.hcl.prac.csvexception.MalformedCSVException;
import com.hcl.prac.student.Student;

public final class FileIOTest {
	public static void main(String[] args) {

		final String MALFORMED_CSV = "Each row in CSV file must contain 3 values.";
		List<Student> students = new ArrayList<>();
		// Read input from students.csv and parse values
		try (BufferedInputStream inStream = new BufferedInputStream(new FileInputStream("students.csv"))) {

			System.out.println("Reading CSV File Input...");
			// Read in lines from CSV splitting on each line so that each element of the
			// lines array contains one complete row from the CSV
			String[] lines = new String(inStream.readAllBytes(), StandardCharsets.UTF_8).split("\r");

			// First line of CSV file is the header for the columns. Extract that and use it
			// as a label for console output
			String[] csvHeader = lines[0].replace("\r", "").replace("\n", "").split(",");

			// Each record must contain 3 values, which is the number of attributes for
			// Student Constructor
			if (csvHeader.length != 3) {
				// If not 3 columns throw custom Exception
				throw new MalformedCSVException(MALFORMED_CSV);
			}

			// Print CSV as table : This is the Header
			System.out.printf("%71s\n", "|---------------------------------------|");
			System.out.printf("%31s", "|");
			System.out.printf("%10s %10s %11s %6s\n", csvHeader[0].toUpperCase(), csvHeader[1].toUpperCase(),
					csvHeader[2].toUpperCase(), "|");
			System.out.printf("%71s\n", "|---------------------------------------|");

			// Assign parsed CSV values to attributes of Student type, and push each student
			// onto an ArrayList
			for (int i = 1; i < lines.length; i++) {
				String[] record = lines[i].replace("\r", "").replace("\n", "").split(",");
				// Each record must contain 3 values, which is the number of attributes for
				// Student Constructor
				if (record.length != 3) {
					// If not 3 columns throw custom Exception
					throw new MalformedCSVException(MALFORMED_CSV);
				}
				// Print the contents of the CSV file to a table on the console
				System.out.printf("%30s| %-15s| %-5s| %-14s|\n", " ", record[0], record[1], record[2]);
				students.add(new Student(record[0], Integer.parseInt(record[1]), record[2]));
			}
			System.out.printf("%70s\n", "---------------------------------------");

			// If there is any exception thrown, catch it and exit the program. This is done
			// because the code that follows this
			// will Serialize the objects created from the CSV. If an exception occurs here
			// we don't want to overwrite any
			// existing valid Serialized data with invalid or empty data, which is what
			// would occur if the execution were
			// allowed to continue beyond these exception traps in the case of an exception.
		} catch (FileNotFoundException e) {
			System.out.println(e);
			toList(e.getStackTrace()).forEach(System.out::println);
			System.exit(1);
		} catch (IOException e) {
			System.out.println(e);
			toList(e.getStackTrace()).forEach(System.out::println);
			System.exit(1);
		} catch (MalformedCSVException e) {
			System.out.println(e);
			toList(e.getStackTrace()).forEach(System.out::println);
			System.exit(1);
		}

		System.out.println("\nList of Students Objects Created From CSV Input File");
		System.out.println("-----------------------------------------------------");
		students.forEach(System.out::println);
		System.out.println("-----------------------------------------------------");

		// Sort List<Student> by Student id
		Collections.sort(students);

		System.out.println("\nSame List of Student Objects Sorted by ID Attribute");
		System.out.println("----------------------------------------------------");
		students.forEach(System.out::println);
		System.out.println("----------------------------------------------------");

		System.out.println("\nSerialize List of Students and Write to File `students.dat`");
		System.out.printf("%75s\n", "(Run main method in ConsumeSerial.java to demonstrate deserialization)");
		// Serialize the ArrayList of Students into a data file to be consumed later (in
		// ConsumeSerial.java)
		try (BufferedOutputStream bufferedOutStream = new BufferedOutputStream(new FileOutputStream("students.dat"))) {
			ObjectOutputStream objectOutStream = new ObjectOutputStream(bufferedOutStream);
			objectOutStream.writeObject(students);
		} catch (FileNotFoundException e) {
			System.out.println(e);
			toList(e.getStackTrace()).forEach(System.out::println);
		} catch (IOException e) {
			System.out.println(e);
			toList(e.getStackTrace()).forEach(System.out::println);
		}
	}

	// Alias method for returning any type of array as a list
	public static <T> List<T> toList(T[] arr) {
		return Arrays.asList(arr);
	}
}