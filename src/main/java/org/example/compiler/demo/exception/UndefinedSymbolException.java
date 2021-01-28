package org.example.compiler.demo.exception;

/**
 * @author Shane Wei
 * @date 2021/1/19 10:43
 * Description:
 */
public class UndefinedSymbolException extends RuntimeException {
    private static final String ERROR_FORMAT = "undefined symbol '%s'";

    public UndefinedSymbolException() {
    }

    public UndefinedSymbolException(String symbol) {
        super(String.format(ERROR_FORMAT, symbol));
    }

    public UndefinedSymbolException(String message, Throwable cause) {
        super(message, cause);
    }

    public UndefinedSymbolException(Throwable cause) {
        super(cause);
    }
}
