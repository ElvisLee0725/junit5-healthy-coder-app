package com.healthycoderapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ActivityCalculatorTest {
    ActivityCalculator activityCalculator;

    @BeforeEach
    void setup() {
        activityCalculator = new ActivityCalculator();
    }

    @Test
    void should_ReturnBad_When_AvgBelow20() {
        int weeklyCardioMin = 30;
        int weeklyWorkoutSession = 1;
        String expected = "bad";

        String actual = activityCalculator.rateActivityLevel(weeklyCardioMin, weeklyWorkoutSession);

        assertEquals(expected, actual);
    }

    @Test
    void should_ReturnAverage_When_AvgBetween20And40() {
        int weeklyCardioMin = 120;
        int weeklyWorkoutSession = 1;
        String expected = "average";

        String actual = activityCalculator.rateActivityLevel(weeklyCardioMin, weeklyWorkoutSession);

        assertEquals(expected, actual);
    }

    @Test
    void should_ReturnGood_When_AvgAbove40() {
        int weeklyCardioMin = 200;
        int weeklyWorkoutSession = 2;
        String expected = "good";

        String actual = activityCalculator.rateActivityLevel(weeklyCardioMin, weeklyWorkoutSession);

        assertEquals(expected, actual);
    }

    @Test
    void should_ThrowException_When_InputsBelowZero() {
        int weeklyCardioMin = -2;
        int weeklyWorkoutSession = -1;

        Executable executable = () -> activityCalculator.rateActivityLevel(weeklyCardioMin, weeklyWorkoutSession);
        assertThrows(ArithmeticException.class, executable);
    }
}
