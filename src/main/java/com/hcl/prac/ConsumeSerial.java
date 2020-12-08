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
        List<?> students = new ArrayList<>();
        try (BufferedInputStream bis =
                new BufferedInputStream(new FileInputStream("students.dat"))) {
            ObjectInputStream ois = new ObjectInputStream(bis);
            students = (List<?>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        students.forEach(student -> System.out.println(student));

    }
}
