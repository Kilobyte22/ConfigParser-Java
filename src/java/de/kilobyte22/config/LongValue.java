package de.kilobyte22.config;

public class LongValue extends ConfigValue {
    private final long data;

    public LongValue(long data) {
        super();
        this.data = data;
    }

    @Override
    public String toString() {
        return "long:" + data;
    }

    public int toInt() {
        return (int) data;
    }
}
