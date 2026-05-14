package model;

import java.util.ArrayList;

public class Dimension {
    private String name;
    private int coefficient;
    private ArrayList<Metric> metrics;

    public Dimension(String name, int coefficient) {
        this.name = name;
        this.coefficient = coefficient;
        this.metrics = new ArrayList<>();
    }

    public void addMetric(Metric metric) {
        metrics.add(metric);
    }

    public String getName() {
        return name;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public ArrayList<Metric> getMetrics() {
        return metrics;
    }
}