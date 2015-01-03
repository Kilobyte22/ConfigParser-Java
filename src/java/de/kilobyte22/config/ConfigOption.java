package de.kilobyte22.config;

import java.util.LinkedList;
import java.util.List;

public class ConfigOption {
    private final String name;
    private ConfigElement sub;
    private List<ConfigValue> args = new LinkedList<ConfigValue>();

    public ConfigOption(String name) {

        this.name = name;
    }

    public ConfigElement getSub() {
        return sub;
    }

    public void setSub(ConfigElement sub) {
        this.sub = sub;
    }

    public void addArg(ConfigValue configValue) {
        args.add(configValue);
    }

    @Override
    public String toString() {
        return toString("");
    }

    public String toString(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("Name: ").append(name).append("\n");
        sb.append(indent).append("Args: ");
        for (ConfigValue arg : args) {
            sb.append(arg.toString()).append(", ");
        }
        sb.append("\n").append(indent).append("Sub: ");
        if (sub == null) {
            sb.append("<null>");
        } else {
            sb.append("\n").append(sub.toString(indent + "  "));
        }
        sb.append("\n\n");
        return sb.toString();
    }

    public int argCount() {
        return args.size();
    }

    public String getString(int idx) {
        return ((StringValue) args.get(idx)).getValue();
    }

    public int getInt(int idx) {
        return ((LongValue) args.get(idx)).toInt();
    }

    public String getName() {
        return name;
    }
}
