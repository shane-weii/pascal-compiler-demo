package org.example.compiler.demo.exception;

import org.example.compiler.demo.Token;

/**
 * @author Shane Wei
 * @date 2021/2/3 17:59
 * Description:
 */
public class LexerException extends CompilerException {
    public LexerException(String message) {
        super(message, null, null);
    }

    public LexerException(String message, ErrorCode errorCode, Token token) {
        super(message, errorCode, token);
    }
}
