package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class BMICalculatorTest {
    private String environment = "prod";

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

    @Nested
    @DisplayName("This Is Sample Inner Class")
    class IsDietRecommendedTests {
        @ParameterizedTest(name = "weight = {0}, height = {1}")
        //@CsvSource(value = {"89.0, 1.72", "95.0, 1.75", "110.0, 1.78"})
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1) // skip 1st line header
        void should_ReturnTrue_When_DietRecommanded(Double coderWeight, Double coderHeight) {
            // given
            double weight = coderWeight;
            double height = coderHeight;

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
    }

    @Nested
    class FindCoderWithWorstBMITests {
        @Test
        @DisplayName("Sample Name To Display")
        @Disabled
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
        void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements() {
            // given
            assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
            List<Coder> coders = new ArrayList<>();
            for(int i = 0; i < 10000; i++) {
                coders.add(new Coder(1.0 + i, 10.0 + i));
            }

            // when
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

            // then
            assertTimeout(Duration.ofMillis(15), executable);
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
    }

    @Nested
    class GetBMIScoresTests {
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
}
