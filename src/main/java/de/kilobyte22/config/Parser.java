package de.kilobyte22.config;

import java.util.List;

public class Parser {
    private final List<Token> tokens;

    private int pos = 0;

    public Parser(List<Token> tokens) {

        this.tokens = tokens;
    }

    public ConfigElement parse() {
        return parseScope(true);
    }

    private ConfigElement parseScope(boolean isRoot) {
        ConfigElement el = new ConfigElement();
        main:
        while (true) {
            if (isRoot && pos >= tokens.size())
                break;
            Token t = next();
            if (t.isSyntaxElement()) {
                switch (t.getData().charAt(0)) {
                    case ';': continue;
                    case '{': throw new ParserException("unexpected { in line %s", t.getLine());
                    case '}': break main;
                }
            }
            ConfigOption option = new ConfigOption(t.getData());
            args:
            while (true) {
                t = next();
                if (t.isSyntaxElement()) {
                    switch (t.getData().charAt(0)) {
                        case ';':
                            break args;
                        case '}':
                            throw new ParserException("Unexpected '}' in line %s", t.getLine());
                        case '{':
                            option.setSub(parseScope(false));
                            expect(";");
                            break args;
                    }
                } else {
                    option.addArg(makeArg(t));
                }
            }
            el.add(option);
        }
        return el;
    }

    private ConfigValue makeArg(Token t) {
        String data = t.getData();
        if (t.isNumber()) {
            if (data.contains(".")) {
                double d = Double.parseDouble(data);
                return new DoubleValue(d);
            } else {
                long l = Long.parseLong(data);
                return new LongValue(l);
            }
        } else {
            return new StringValue(data);
        }
    }

    private Token expect(String s) {
        Token t = next();
        if (!t.getData().equals(s)) {
            throw new ParserException("Unexpected %s, expected %s in line %s", t.getData(), s, t.getLine());
        }
        return t;
    }

    private Token next() {
        if (pos == tokens.size())
            throw new ParserException("Unexpected EOF");
        return tokens.get(pos++);
    }

    private Token expectRegularToken() {
        Token t = next();
        if (!t.isRegularToken()) {
            throw new ParserException("Expected token, got %s in line %s", t.getData(), t.getLine());
        }
        return t;
    }
}
