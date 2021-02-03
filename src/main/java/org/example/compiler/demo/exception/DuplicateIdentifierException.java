package org.example.compiler.demo.exception;

/**
 * @author Shane Wei
 * @date 2021/1/19 10:43
 * Description:
 */
public class DuplicateIdentifierException extends RuntimeException {
    private static final String ERROR_FORMAT = "duplicate identifier '%s'";

    public DuplicateIdentifierException() {
    }

    public DuplicateIdentifierException(String symbol) {
        super(String.format(ERROR_FORMAT, symbol));
    }

    public DuplicateIdentifierException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateIdentifierException(Throwable cause) {
        super(cause);
    }
}
