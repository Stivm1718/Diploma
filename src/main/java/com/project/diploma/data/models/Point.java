package com.project.diploma.data.models;

public enum Point {
    GOLD(100), LEVEL(1), CURRENT_POINTS(0), MAX_POINTS(100);

    private final int value;

    Point(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
