package model;

import java.util.ArrayList;

public class Scenario {
    private String mode;
    private String name;
    private ArrayList<Dimension> dimensions;

    public Scenario(String mode, String name) {
        this.mode = mode;
        this.name = name;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(Dimension dimension) {
        dimensions.add(dimension);
    }

    public String getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Dimension> getDimensions() {
        return dimensions;
    }

    @Override
    public String toString() {
        return name;
    }
}