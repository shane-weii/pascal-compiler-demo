package org.example.compiler.demo;

import org.example.compiler.demo.ast.CompoundStmNode;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Shane Wei
 * @date 2021/1/26 15:30
 * Description:
 */
public class NodeVisitorTest {
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
        Parser parser = new Parser(lexer);
        final CompoundStmNode node = parser.program();
        NodeVisitor nodeVisitor = new NodeVisitor();
        nodeVisitor.visit(node);
        final Map<String, Integer> table = nodeVisitor.getVariableTable();
        assertEquals(2, table.get("number").intValue());
        assertEquals(2, table.get("a").intValue());
        assertEquals(25, table.get("b").intValue());
        assertEquals(27, table.get("c").intValue());
        assertEquals(11, table.get("x").intValue());
    }
}