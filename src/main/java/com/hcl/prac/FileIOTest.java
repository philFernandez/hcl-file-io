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
import java.util.List;
import com.hcl.prac.csvexception.MalformedCSVException;
import com.hcl.prac.student.Student;

public final class FileIOTest {
	public static void main(String[] args) {

		List<Student> students = new ArrayList<>();
		/**
		 * Read input from students.csv and parse values
		 */
		try (BufferedInputStream inStream = new BufferedInputStream(new FileInputStream("students.csv"))) {

			System.out.println("Reading CSV File Input...");
			// Read CSV file into a String array, removing line breaks and splitting file input on "," in the process
			String[] input = new String(inStream.readAllBytes(), StandardCharsets.UTF_8).replace("\r", "")
					.replace("\n", "").split(",");

			// Student objects have 3 attributes two their constructor, so the csv must
			// resolve to a list of length%3 = 0
			if (input.length % 3 != 0) {
				throw new MalformedCSVException("CSV length must be multiple of 3");
			}

			// Assign parsed CSV values to attributes of Student types, and push each
			// student onto an ArrayList
			// Each three tokens consists of one Student
				System.out.printf("%71s\n", "|---------------------------------------|");
				System.out.printf("%31s", "|");
				System.out.printf("%10s %10s %11s %6s\n", "NAME", "ID", "CITY", "|");
				System.out.printf("%71s\n", "|---------------------------------------|");
			for (int i = 0; i < input.length - 2; i += 3) {
				System.out.printf("%30s| %-15s| %-5s| %-14s|\n"," ", input[i], input[i + 1], input[i + 2]);
				students.add(new Student(input[i], Integer.parseInt(input[i + 1]), input[i + 2]));
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
		
		System.out.println("\nSerialize List of Students and Write to File `students.dat`");
		System.out.printf("%75s\n", "(Run main method in ConsumeSerial.java to demonstrate deserialization)");
		// Serialize the ArrayList of Students into a data file to be consumed later (in
		// ConsumeSerial.java)
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("students.dat"))) {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(students);
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
