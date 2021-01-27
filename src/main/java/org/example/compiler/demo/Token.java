package org.example.compiler.demo;

/**
 * @author Shane Wei
 * @date 2021/1/25 10:24
 * Description:
 */
public class Token {
    enum Type {
        // begin关键字
        BEGIN,
        // end 关键字
        END,
        // 分号 ';'
        SEM,
        // 赋值 ':='
        ASSIGN,
        // 点号 '.'
        DOT,
        // 标识符ID
        ID,
        INTEGER,
        PLUS,
        MINUS,
        TIMES,
        DIV,
        LEFT_PART,
        RIGHT_PART,
        EOF
    }

    private final Type type;
    private final Object value;

    public Token(Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("token(%s,%s)", type, value);
    }
}
