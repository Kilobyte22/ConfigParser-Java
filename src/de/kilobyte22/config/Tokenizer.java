package de.kilobyte22.config;

import java.util.LinkedList;
import java.util.List;

public class Tokenizer {

    private final String input;
    private List<Token> ret;
    private String current;
    private ParseScope scope = ParseScope.NONE;
    private int pos = 0;
    private char last = 0;
    private int commentLevel = 0;
    private int line = 1;
    private boolean hadR = false;

    private char next() {
        return input.charAt(pos++);
    }

    public Tokenizer(String input) {

        this.input = input;
    }

    public synchronized List<Token> run() {
        if (ret != null) return ret;
        ret = new LinkedList<Token>();
        while (pos < input.length()) {
            char c = next();
            handleRN(c);
            if (scope == ParseScope.STRING) {
                switch (c) {
                    case '"':
                        finishToken();
                        break;
                    case '\\':
                        scope = ParseScope.ESCAPED_STRING;
                        break;
                    default:
                        current += c;
                }
            } else if (scope == ParseScope.ESCAPED_STRING) {
                switch (c) {
                    case 'n':
                        current += "\n";
                        break;
                    case 't':
                        current += "\t";
                        break;
                    default:
                        current += c;
                        break;
                }
                scope = ParseScope.STRING;
            } else if (scope == ParseScope.COMMENT) {
                switch (c) {
                    case '\n':case '\r':
                        scope = ParseScope.NONE;
                        break;
                    default:
                        break;
                }
            } else if (scope == ParseScope.MULTI_COMMENT) {
                switch (c) {
                    case '/':
                        if (last == '*') {
                            if (--commentLevel <= 0) {
                                assert commentLevel == 0; // juuuuuuuuust to be safe
                                scope = ParseScope.NONE;
                            }
                        }
                        break;
                    case '*':
                        if (last == '/') {
                            commentLevel++;
                        }
                        break;
                    default:
                        break;
                }
            } else {
                switch (c) {
                    case ' ':case '\n':case '\r':case '\t':
                        finishToken();
                        break;
                    case '{':case '}':case ';':
                        finishToken();
                        scope = ParseScope.TOKEN;
                        current = "" + c;
                        finishToken();
                        break;
                    case '"':
                        finishToken();
                        current = "";
                        scope = ParseScope.STRING;
                        break;
                    case '#':
                        scope = ParseScope.COMMENT;
                        break;
                    case '*':case '/':
                        if (last == '/') {
                            if (c == '/') {
                                scope = ParseScope.COMMENT;
                            } else {
                                scope = ParseScope.MULTI_COMMENT;
                                commentLevel = 1;
                            }
                            break;
                        }
                    default:
                        if (scope == ParseScope.NONE) {
                            scope = ParseScope.TOKEN;
                            current = c + "";
                        } else {
                            current += c;
                        }
                }
            }
            last = c;
        }
        return ret;
    }

    private void handleRN(char c) {
        switch (c) {
            case '\r':
                hadR = true;
                line++;
                break;
            case '\n':
                if (hadR)
                    hadR = false;
                else
                    line++;
                break;
            default:
                hadR = false;
        }
    }

    private void finishToken() {
        if (scope == ParseScope.NONE) return;
        Token t = new Token(current);
        switch (scope) {
            case TOKEN:
                if (current.length() == 1 && ";{}".contains(current))
                    t.setSyntaxElement(true);
                try {
                    Double.parseDouble(current);
                    t.setNumber(true);
                } catch (NumberFormatException e) {}
                break;
            case STRING:
                t.setString(true);
                break;
        }
        current = null;
        scope = ParseScope.NONE;
        t.setLine(line);
        ret.add(t);
    }

    private enum ParseScope {
        NONE,
        TOKEN,
        ESCAPED_STRING,
        STRING,
        COMMENT,
        MULTI_COMMENT
    }
}
