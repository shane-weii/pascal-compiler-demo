package org.example.compiler.demo.exception;

/**
 * @author Shane Wei
 * @date 2021/2/3 17:52
 * Description:
 */
public enum ErrorCode {
    /***
     * 非法词汇
     */
    UNEXPECTED_TOKEN("Unexpected token"),

    /***
     * 标签未定义
     */
    ID_NOT_FOUND("Identifier not found"),

    /***
     * 重复定义
     */
    DUPLICATE_ID("Duplicate id found");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
