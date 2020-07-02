package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TestRule {
    private static LocalDateTime startAllTestsInClass;
    private static LocalDateTime startOneTestInClass;

    private static List<String> conclusive = new ArrayList<>();

    @BeforeClass
    public static void startTest(){
        System.out.println("startTest");
        startAllTestsInClass = LocalDateTime.now();
    }

    @AfterClass
    public static void endTests(){
        System.out.println("-------------------------------------------------");
        System.out.println("End Tests in class. Common time = " + ChronoUnit.SECONDS.between(startAllTestsInClass, LocalDateTime.now()) + " sec.");
        conclusive.forEach(System.out::println);
        System.out.println("-------------------------------------------------");
    }

    @Rule
    public org.junit.rules.TestRule listner = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            System.out.println("succeeded " + description.getClassName() + " " + description.getMethodName());
        }

        @Override
        protected void failed(Throwable e, Description description) {
            System.out.println("failed " + description.getClassName() + " " + description.getMethodName());
        }

        @Override
        protected void starting(Description description) {
            System.out.println("starting " + description.getClassName() + " " + description.getMethodName());
            startOneTestInClass = LocalDateTime.now();
        }

        @Override
        protected void finished(Description description) {
            System.out.println("finished " + description.getClassName() + " " + description.getMethodName());
            System.out.println("End Test. " + description.getMethodName() + " Time = " + ChronoUnit.MILLIS.between(startOneTestInClass, LocalDateTime.now()) + " mills.");
            conclusive.add(description.getMethodName() + " ends. Time = " + ChronoUnit.MILLIS.between(startOneTestInClass, LocalDateTime.now()) + " mills.");
        }
    };
}
