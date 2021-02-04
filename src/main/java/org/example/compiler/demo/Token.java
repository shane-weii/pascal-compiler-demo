package org.example.compiler.demo;

/**
 * @author Shane Wei
 * @date 2021/1/25 10:24
 * Description:
 */
public class Token {
    private final TokenType type;
    private final Object value;
    private final Integer lineNo;
    private final Integer column;

    public Token(TokenType type, Object value) {
        this(type, value, null, null);
    }

    public Token(TokenType type, Object value, Integer lineNo, Integer column) {
        this.type = type;
        this.value = value;
        this.lineNo = lineNo;
        this.column = column;
    }

    public TokenType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Token(%s, %s, position = %d:%d)", type, value, lineNo, column);
    }
}
