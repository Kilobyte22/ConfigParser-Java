package de.kilobyte22.config;

public class StringValue extends ConfigValue {
    private final String data;

    public StringValue(String data) {
        super();
        this.data = data;
    }


    @Override
    public String toString() {
        return "string:" + data;
    }

    public String getValue() {
        return data;
    }
}
