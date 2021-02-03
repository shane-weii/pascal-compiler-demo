package org.example.compiler.demo;

/**
 * @author Shane Wei
 * @date 2021/1/25 10:24
 * Description:
 */
public class Token {
    enum Type {
        // program 关键字
        PROGRAM,
        // var 关键字
        VAR,
        // INTEGER 关键字，整数类型
        INTEGER,
        // REAL 关键字，实数类型
        REAL,
        // begin 关键字
        BEGIN,
        // end 关键字
        END,
        // div 关键字
        INTEGER_DIV,
        // PROCEDURE 关键字
        PROCEDURE,
        // 分号 ';'
        SEM,
        // 赋值 ':='
        ASSIGN,
        // 点号 '.'
        DOT,
        // 冒号 ':'
        COLON,
        // 逗号 ','
        COMMA,
        // 标识符ID
        ID,
        // 整数常数
        INTEGER_CONST,
        // 浮点数常数
        FLOAT_CONST,
        // '+'
        PLUS,
        // '-'
        MINUS,
        // '*'
        TIMES,
        // '/'
        FLOAT_DIV,
        // '('
        LEFT_PART,
        // ')'
        RIGHT_PART,
        // 结束
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
        return String.format("Token(%s, %s)", type, value);
    }
}
