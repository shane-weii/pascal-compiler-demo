package org.example.compiler.demo;

import org.example.compiler.demo.exception.LexerException;
import org.example.compiler.demo.exception.SemanticException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Shane Wei
 * @date 2021/2/3 16:18
 * Description:
 */
public class InterpreterTest {
    @Test
    public void test() {
        String code = load("src/test/resources/main_test.pas");
        final Lexer lexer = new Lexer(code);
        final Parser parser = new Parser(lexer);
        final AstNode tree = parser.parse();
        // 语义分析
        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        tree.accept(analyzer);

        Interpreter intr = new Interpreter(tree);
        intr.interpreter();
        Assert.assertEquals(22, intr.getVariableTable().get("c"), 0);
    }

    @Test
    public void testDupErr() {
        String code = load("src/test/resources/dup_err.pas");
        final Lexer lexer = new Lexer(code);
        final Parser parser = new Parser(lexer);
        final AstNode tree = parser.parse();
        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        Assert.assertThrows(SemanticException.class, () -> tree.accept(analyzer));
    }

    @Test
    public void testLexerErr() {
        String code = load("src/test/resources/lexer_err.pas");
        final Lexer lexer = new Lexer(code);
        final Parser parser = new Parser(lexer);
        Assert.assertThrows(LexerException.class, parser::parse);
    }

    public String load(String path) {
        StringBuilder sb = new StringBuilder();
        String s;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            while ((s = reader.readLine()) != null) {
                sb.append(s).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
