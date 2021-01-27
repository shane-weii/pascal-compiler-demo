package org.example.compiler.demo;

import org.example.compiler.demo.exception.InvalidSyntaxException;
import org.example.compiler.demo.Token;

import java.util.HashMap;
import java.util.Map;

import static org.example.compiler.demo.Token.Type.*;

/**
 * @author Shane Wei
 * @date 2021/1/25 10:25
 * Description: 词法分析器
 */
public class Lexer {
    private final String text;
    private int currentPos;
    private char currentChar;

    private static final Map<String, Token> KEYWORD_TOKEN_MAP = new HashMap<>();
    private static final Map<String, Token> SYMBOL_TOKEN_MAP = new HashMap<>();

    static {
        KEYWORD_TOKEN_MAP.put("BEGIN", new Token(BEGIN, "BEGIN"));
        KEYWORD_TOKEN_MAP.put("END", new Token(END, "END"));

        SYMBOL_TOKEN_MAP.put("+", new Token(PLUS, "+"));
        SYMBOL_TOKEN_MAP.put("-", new Token(MINUS, "-"));
        SYMBOL_TOKEN_MAP.put("*", new Token(TIMES, "*"));
        SYMBOL_TOKEN_MAP.put("/", new Token(DIV, "/"));
        SYMBOL_TOKEN_MAP.put("(", new Token(LEFT_PART, "("));
        SYMBOL_TOKEN_MAP.put(")", new Token(RIGHT_PART, ")"));
        SYMBOL_TOKEN_MAP.put(";", new Token(SEM, ";"));
        SYMBOL_TOKEN_MAP.put(".", new Token(DOT, "."));
        SYMBOL_TOKEN_MAP.put(":=", new Token(ASSIGN, ":="));
    }

    public Lexer(String text) {
        if (text == null || text.length() == 0) {
            throw new IllegalArgumentException("text cannot be empty");
        }
        this.text = text;
        currentPos = 0;
        currentChar = text.charAt(currentPos);
    }

    public Token getNextToken() {
        while (currentChar != 0) {
            if (isSpaceOrNewline(currentChar)) {
                skipSpaceAndNewline();
                continue;
            }
            if (isIdentifier(currentChar)) {
                return getAlphaToken();
            }
            if (Character.isDigit(currentChar)) {
                int value = getInt();
                return new Token(INTEGER, value);
            }
            // := 处理
            if (currentChar == ':' && peek() == '=') {
                advance();
                advance();
                return SYMBOL_TOKEN_MAP.get(":=");
            }
            // 单字符处理
            final Token token = SYMBOL_TOKEN_MAP.get("" + currentChar);
            if (token == null) {
                throw new InvalidSyntaxException("invalid character '" + currentChar + "'");
            }
            advance();
            return token;
        }
        return new Token(EOF, null);
    }

    private Token getAlphaToken() {
        StringBuilder sb = new StringBuilder();
        while (currentChar != 0 && isIdentifier(currentChar)) {
            sb.append(currentChar);
            advance();
        }
        final String s = sb.toString();
        return KEYWORD_TOKEN_MAP.getOrDefault(s.toUpperCase(), new Token(ID, s));
    }

    private int getInt() {
        int res = 0;
        while (currentChar != 0 && Character.isDigit(currentChar)) {
            res = res * 10 + currentChar - 0x30;
            advance();
        }
        return res;
    }

    private void skipSpaceAndNewline() {
        while (currentChar != 0 && isSpaceOrNewline(currentChar)) {
            advance();
        }
    }

    private void advance() {
        currentPos++;
        if (currentPos < text.length()) {
            currentChar = text.charAt(currentPos);
        } else {
            currentChar = 0;
        }
    }

    private char peek() {
        int pos = currentPos + 1;
        if (pos < text.length()) {
            return text.charAt(pos);
        }
        return 0;
    }

    private boolean isSpaceOrNewline(char c) {
        return Character.isSpaceChar(c) || c == '\n';
    }

    private boolean isIdentifier(char c) {
        return Character.isAlphabetic(c);
    }
}
