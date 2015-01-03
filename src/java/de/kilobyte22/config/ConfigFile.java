package de.kilobyte22.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class ConfigFile extends ConfigElement {
    private final File file;
    private ConfigElement root;

    public ConfigFile(String name) {
        this(new File(name));
    }

    public ConfigFile(File file) {
        this.file = file;
    }

    public void load() {
        try {
            Scanner s = new Scanner(new FileInputStream(file));
            s.useDelimiter("\\Z");
            String code = s.next();
            root = new Parser(new Tokenizer(code).run()).parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<ConfigOption> getOptions() {
        return root.getOptions();
    }
    public List<ConfigOption> getOptions(String name) {
        return root.getOptions(name);
    }
    public boolean has(String name) {
        return root.has(name);
    }
    public ConfigOption first(String name) {
        return root.first(name);
    }
}
