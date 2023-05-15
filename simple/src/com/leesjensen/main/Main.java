package com.leesjensen.main;

import com.leesjensen.main.student.Student;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        if (args.length > 1) {
            System.out.println(args[0]);
        }

        Student std = new Student("joe");
        System.out.println(std);

        try {
            std.read("student.txt");
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

//        String s = "my string";
//        s = String.format("%s more and more", s);
//        System.out.println(s + " more");
//
//        if (s.startsWith("my")) {
//            System.out.println("This is my kind of string");
//        }
//
//        StringBuilder sb = new StringBuilder("initial");
//        sb.append(" more");
//
//        System.out.println(sb);
//
//        for (char c : sb.toString().toCharArray()) {
//            System.out.println(c);
//        }
//
//        String[] strs = {"hello", "fish"};
//        for (String sx : strs) {
//            System.out.println(sx);
//        }
    }
}