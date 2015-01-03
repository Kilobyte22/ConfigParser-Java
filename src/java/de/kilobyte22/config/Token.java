package de.kilobyte22.config;

public class Token {
    private final String data;
    private boolean string;
    private boolean syntaxElement;
    private boolean number;
    private int line;

    public Token(String data) {

        this.data = data;
    }

    public void setString(boolean string) {
        this.string = string;
    }

    public boolean isString() {
        return string;
    }

    public void setSyntaxElement(boolean syntaxElement) {
        this.syntaxElement = syntaxElement;
    }

    public boolean isSyntaxElement() {
        return syntaxElement;
    }

    public void setNumber(boolean number) {
        this.number = number;
    }

    public boolean isNumber() {
        return number;
    }

    public boolean isRegularToken() {
        return !(string || number || syntaxElement);
    }

    @Override
    public String toString() {
        return data;
    }

    public String getData() {
        return data;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
