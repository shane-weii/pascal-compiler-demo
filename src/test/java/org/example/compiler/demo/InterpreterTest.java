package org.example.compiler.demo;

import org.junit.Assert;
import org.junit.Test;

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
        String code = load();
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

    public String load() {
        System.out.println();
        String path = "src/test/resources/test.pas";
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
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
