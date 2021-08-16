package com.healthycoderapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BMICalculatorTest {
    @BeforeAll
    // Execute once before all tests, usually for those expensive setup such as database connections or server setup
    static void beforeAll() {
        System.out.println("Run it before all unit tests");
    }

    @AfterAll
    // Execute once after all tests, usually for disconnect database or stop server
    static void afterAll() {
        System.out.println("Run it after all unit tests");
    }

    @Test
    void should_ReturnTrue_When_DietRecommanded() {
        // given
        double weight = 89.0;
        double height = 1.72;

        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // Then
        assertTrue(recommended);
    }

    @Test
    void should_ReturnFalse_When_DietNotRecommanded() {
        // given
        double weight = 60.0;
        double height = 1.82;

        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // then
        assertFalse(recommended);
    }

    @Test
    void should_ThrowArithmeticException_When_HeightIsZero() {
        // given
        double weight = 60.0;
        double height = 0.0;

        // when
        // Since we want to test if the method throws exception, but if it does, we'll never reach the assertion
        // Therefore, we need to use executable and lambda expression to realize it
        // The executable will not be run immediately. We'll pass it to assertThrows and run it
        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

        // then
        assertThrows(ArithmeticException.class, executable);
    }

    @Test
    void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.7));

        // when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // then
        assertAll(
                () -> assertEquals(1.82, coderWorstBMI.getHeight()),
                () -> assertEquals(98.0, coderWorstBMI.getWeight())
        );
    }

    @Test
    void should_ReturnNull_When_CoderListIsEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();

        // when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // then
        assertNull(coderWorstBMI);
    }

    @Test
    void should_ReturnCorrectBMIScoreArray_When_CoderListIsNotEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.7));
        double [] expected = {18.52, 29.59, 19.53};

        // when
        double [] actualBMIScores = BMICalculator.getBMIScores(coders);

        // then
        assertArrayEquals(expected, actualBMIScores);
    }
}
