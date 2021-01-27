package org.example.compiler.demo.exception;

/**
 * @author Shane Wei
 * @date 2021/1/19 10:43
 * Description:
 */
public class InvalidSyntaxException extends RuntimeException {
    public InvalidSyntaxException() {
    }

    public InvalidSyntaxException(String message) {
        super(message);
    }

    public InvalidSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSyntaxException(Throwable cause) {
        super(cause);
    }
}
