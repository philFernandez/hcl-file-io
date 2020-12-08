package com.hcl.prac;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class ConsumeSerial {
	public static void main(String[] args) {
    	// Use list with Wild Card Generic parameter for reading in Student Objects from File
        List<?> students = new ArrayList<>();
        // Open Serialized file with BufferedInputStream
        System.out.println("Reading in Serialized Data File...");
        try (BufferedInputStream bis =
                new BufferedInputStream(new FileInputStream("students.dat"))) {
            ObjectInputStream ois = new ObjectInputStream(bis);
            // Read deserialized data file
            students = (List<?>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("\nStudent Objects Read and Deserialized from Data File");
        System.out.println("------------------------------------------------------");
        students.forEach(student -> System.out.println(student));

    }
}
