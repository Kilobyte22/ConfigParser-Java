package de.kilobyte22.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConfigElement {
    private List<ConfigOption> options = new LinkedList<ConfigOption>();
    private Map<String, List<ConfigOption>> optionsByKey = new HashMap<String, List<ConfigOption>>();

    public void add(ConfigOption option) {
        options.add(option);
        if (!optionsByKey.containsKey(option.getName())) {
            optionsByKey.put(option.getName(), new LinkedList<ConfigOption>());
        }
        optionsByKey.get(option.getName()).add(option);
    }

    @Override
    public String toString() {
        return toString("");
    }

    public String toString(String indent) {
        StringBuilder sb = new StringBuilder();
        for (ConfigOption option : options) {
            sb.append(option.toString(indent));
        }
        return sb.toString();
    }

    public List<ConfigOption> getOptions() {
        return options;
    }

    public List<ConfigOption> getOptions(String name) {
        if (optionsByKey.containsKey(name)) {
            return optionsByKey.get(name);
        } else {
            return new LinkedList<ConfigOption>();
        }
    }

    public boolean has(String name) {
        return optionsByKey.containsKey(name) && optionsByKey.get(name).size() > 0;
    }

    public ConfigOption first(String name) {
        return optionsByKey.get(name).get(0);
    }
}
