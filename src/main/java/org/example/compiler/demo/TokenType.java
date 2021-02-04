package org.example.compiler.demo;

/**
 * @author Shane Wei
 * @date 2021/2/3 17:55
 * Description:
 */
public enum TokenType {
    /********************************************** keyword ************************************************************/
    PROGRAM("PROGRAM"),
    VAR("VAR"),
    INTEGER("INTEGER"),
    REAL("REAL"),
    BEGIN("BEGIN"),
    INTEGER_DIV("DIV"),
    PROCEDURE("PROCEDURE"),
    END("END"),
    /*******************************************************************************************************************/

    /********************************************* single character ****************************************************/
    SEM(";"),
    DOT("."),
    COLON(":"),
    COMMA(","),
    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    FLOAT_DIV("/"),
    LEFT_PART("("),
    RIGHT_PART(")"),
    /*******************************************************************************************************************/

    /**************************************************** misc *********************************************************/
    // 标识符ID
    ID("ID"),
    // 整数常数
    INTEGER_CONST("INTEGER_CONST"),
    // 浮点数常数
    FLOAT_CONST("FLOAT_CONST"),
    // 赋值 ':='
    ASSIGN(":="),
    // 结束
    EOF("EOF");
    /*******************************************************************************************************************/

    public String value;

    TokenType(String value) {
        this.value = value;
    }

    /***
     * get type enum by value string.
     * @param value
     * @return
     */
    public static TokenType getByValue(String value) {
        for (TokenType tokenType : TokenType.values()) {
            if (tokenType.value.equalsIgnoreCase(value)) {
                return tokenType;
            }
        }
        return null;
    }
}
