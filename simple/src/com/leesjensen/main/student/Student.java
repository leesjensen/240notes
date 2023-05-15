package com.leesjensen.main.student;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String read(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        StringBuilder sb = new StringBuilder();

        while (scanner.hasNext()) {
            String n = scanner.next();
            System.out.println(n);
            sb.append(n);
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return name;
    }
}
