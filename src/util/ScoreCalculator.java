package util;

import model.Metric;

public class ScoreCalculator {

    public static double calculateScore(Metric metric) {
        double value = metric.getValue();
        double min = metric.getMin();
        double max = metric.getMax();

        double score;

        if (metric.getDirection().equals("Higher")) {
            score = 1 + ((value - min) / (max - min)) * 4;
        } else {
            score = 5 - ((value - min) / (max - min)) * 4;
        }

        if (score < 1) {
            score = 1;
        }

        if (score > 5) {
            score = 5;
        }

        return Math.round(score * 2) / 2.0;
    }
}