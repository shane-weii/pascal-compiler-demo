package org.example.compiler.demo.exception;

import org.example.compiler.demo.Token;

/**
 * @author Shane Wei
 * @date 2021/2/3 17:59
 * Description:
 */
public class ParserException extends CompilerException {
    public ParserException(String message, ErrorCode errorCode, Token token) {
        super(message, errorCode, token);
    }
}
