package org.example.compiler.demo.exception;

import org.example.compiler.demo.Token;

/**
 * @author Shane Wei
 * @date 2021/2/3 17:56
 * Description:
 */
public abstract class CompilerException extends RuntimeException {
    private ErrorCode errorCode;
    private Token token;

    protected CompilerException(String message, ErrorCode errorCode, Token token) {
        super(message);
        this.errorCode = errorCode;
        this.token = token;
    }
}
