package de.kilobyte22.config.test;

import de.kilobyte22.config.Parser;
import de.kilobyte22.config.Token;
import de.kilobyte22.config.Tokenizer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String... args) throws FileNotFoundException {
        Scanner s = new Scanner(new FileInputStream("input.cfg"));
        s.useDelimiter("\\Z");
        String code = s.next();
        List<Token> tokens = new Tokenizer(code).run();
        System.out.println(tokens.toString());
        System.out.print(new Parser(tokens).parse().toString());
    }
}
