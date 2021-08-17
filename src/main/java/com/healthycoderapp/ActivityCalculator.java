package com.healthycoderapp;

public class ActivityCalculator {
    private static final int WORKOUT_SESSION_MIN = 45;

    public String rateActivityLevel(int weeklyCardioMin, int weeklyWorkoutSession) {
        if(weeklyCardioMin < 0 || weeklyWorkoutSession < 0) {
            throw new ArithmeticException();
        }

        int totalWorkoutMins = weeklyCardioMin + WORKOUT_SESSION_MIN * weeklyWorkoutSession;
        double avgWorkoutMins = totalWorkoutMins / 7.0;

        if(avgWorkoutMins < 20) {
            return "bad";
        }
        else if(avgWorkoutMins >= 20 && avgWorkoutMins < 40) {
            return "average";
        }

        return "good";
    }
}
