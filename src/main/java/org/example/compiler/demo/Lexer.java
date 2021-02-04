package org.example.compiler.demo;

import org.example.compiler.demo.exception.LexerException;

import java.util.HashMap;
import java.util.Map;

import static org.example.compiler.demo.TokenType.*;

/**
 * @author Shane Wei
 * @date 2021/1/25 10:25
 * Description: 词法分析器
 */
public class Lexer {
    private final String text;
    private int currentPos;
    private char currentChar;
    private int lineNo;
    private int colNo;

    private static final Map<String, Token> KEYWORD_TOKEN_MAP = new HashMap<>();

    static {
        final TokenType[] values = values();
        // 关键字处理
        for (int i = PROGRAM.ordinal(); i <= END.ordinal(); i++) {
            TokenType type = values[i];
            KEYWORD_TOKEN_MAP.put(type.value, new Token(type, type.value));
        }
    }

    public Lexer(String text) {
        this.text = text;
        currentPos = 0;
        lineNo = 1;
        colNo = 1;
        currentChar = text.charAt(currentPos);
    }

    public Token getNextToken() {
        while (currentChar != 0) {
            if (isSpaceOrNewline(currentChar)) {
                skipSpaceAndNewline();
                continue;
            }
            if (isCommentStart(currentChar)) {
                skipComment();
                continue;
            }
            if (Character.isLetter(currentChar)) {
                return getIdentifierToken();
            }
            if (Character.isDigit(currentChar)) {
                return number();
            }
            // := 处理
            if (currentChar == ':' && peek() == '=') {
                advance();
                advance();
                return new Token(ASSIGN, ":=", lineNo, colNo);
            }
            // 单字符处理
            final TokenType type = getByValue("" + currentChar);
            if (type == null) {
                error();
            }
            Token token = new Token(type, type.value, lineNo, colNo);
            advance();
            return token;
        }
        return new Token(EOF, null);
    }

    private boolean isCommentStart(char currentChar) {
        return currentChar == '{';
    }

    private boolean isCommentEnd(char currentChar) {
        return currentChar == '}';
    }

    private void skipComment() {
        while (currentChar != 0 && !isCommentEnd(currentChar)) {
            advance();
        }
        advance();
    }

    private Token number() {
        StringBuilder sb = new StringBuilder();
        while (currentChar != 0 && Character.isDigit(currentChar)) {
            sb.append(currentChar);
            advance();
        }
        if (currentChar == '.') {
            sb.append(currentChar);
            advance();
            while (currentChar != 0 && Character.isDigit(currentChar)) {
                sb.append(currentChar);
                advance();
            }
            return new Token(FLOAT_CONST, Double.valueOf(sb.toString()), lineNo, colNo);
        }
        return new Token(INTEGER_CONST, Integer.valueOf(sb.toString()), lineNo, colNo);
    }

    private Token getIdentifierToken() {
        StringBuilder sb = new StringBuilder();
        while (currentChar != 0 && Character.isLetterOrDigit(currentChar)) {
            sb.append(currentChar);
            advance();
        }
        final String s = sb.toString();
        return KEYWORD_TOKEN_MAP.getOrDefault(s.toUpperCase(), new Token(ID, s, lineNo, colNo));
    }

    private void skipSpaceAndNewline() {
        while (currentChar != 0 && isSpaceOrNewline(currentChar)) {
            advance();
        }
    }

    private void advance() {
        if (currentChar == '\n') {
            lineNo++;
            colNo = 0;
        }

        currentPos++;
        if (currentPos < text.length()) {
            colNo++;
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

    /***
     * throws LexerException
     * @throws LexerException
     */
    private void error() throws LexerException {
        String message = String.format("Lexer error on '%s' line: %d column: %d", currentChar, lineNo, colNo);
        throw new LexerException(message);
    }

    private boolean isSpaceOrNewline(char c) {
        return Character.isSpaceChar(c) || c == '\n';
    }
}
