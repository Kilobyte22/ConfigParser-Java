package de.kilobyte22.config;

public class DoubleValue extends ConfigValue {
    private final double data;

    public DoubleValue(double data) {
        super();
        this.data = data;
    }

    @Override
    public String toString() {
        return "double:" + data;
    }
}
