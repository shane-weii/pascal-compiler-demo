package org.example.compiler.demo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Shane Wei
 * @date 2021/1/26 11:36
 * Description:
 */
public class LexerTest {
    @Test
    public void test() {
        String code = "BEGIN\n" +
                "    BEGIN\n" +
                "        number := 2;\n" +
                "        a := number;\n" +
                "        b := 10 * a + 10 * number / 4;\n" +
                "        c := a - - b\n" +
                "    END;\n" +
                "    x := 11;\n" +
                "END.";
        Lexer lexer = new Lexer(code);
        Token token = lexer.getNextToken();
        assertEquals(Token.Type.BEGIN, token.getType());
        assertEquals(Token.Type.BEGIN, lexer.getNextToken().getType());
        token = lexer.getNextToken();
        assertEquals(Token.Type.ID, token.getType());
        assertEquals("number", token.getValue());
        assertEquals(Token.Type.ASSIGN, lexer.getNextToken().getType());
        assertEquals(Token.Type.INTEGER, lexer.getNextToken().getType());
        assertEquals(Token.Type.SEM, lexer.getNextToken().getType());
        // a := number;
        assertEquals(Token.Type.ID, lexer.getNextToken().getType());
        assertEquals(Token.Type.ASSIGN, lexer.getNextToken().getType());
        assertEquals(Token.Type.ID, lexer.getNextToken().getType());
        assertEquals(Token.Type.SEM, lexer.getNextToken().getType());
        // b := 10 * a + 10 * number / 4;
        assertEquals(Token.Type.ID, lexer.getNextToken().getType());
        assertEquals(Token.Type.ASSIGN, lexer.getNextToken().getType());
        assertEquals(Token.Type.INTEGER, lexer.getNextToken().getType());
        assertEquals(Token.Type.TIMES, lexer.getNextToken().getType());
        assertEquals(Token.Type.ID, lexer.getNextToken().getType());
        assertEquals(Token.Type.PLUS, lexer.getNextToken().getType());
        assertEquals(Token.Type.INTEGER, lexer.getNextToken().getType());
        assertEquals(Token.Type.TIMES, lexer.getNextToken().getType());
        assertEquals(Token.Type.ID, lexer.getNextToken().getType());
        assertEquals(Token.Type.DIV, lexer.getNextToken().getType());
        assertEquals(Token.Type.INTEGER, lexer.getNextToken().getType());
        assertEquals(Token.Type.SEM, lexer.getNextToken().getType());
    }

}