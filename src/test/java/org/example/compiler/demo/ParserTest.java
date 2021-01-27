package org.example.compiler.demo;

import org.example.compiler.demo.ast.AssignStmNode;
import org.example.compiler.demo.ast.CompoundStmNode;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Shane Wei
 * @date 2021/1/26 14:58
 * Description:
 */
public class ParserTest {
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
        CompoundStmNode node = parser.program();
        assertEquals(3, node.getChildren().size());
        node = (CompoundStmNode) node.getChildren().get(0);
        assertEquals(4, node.getChildren().size());
        AssignStmNode assignStmNode = (AssignStmNode) node.getChildren().get(0);
        assertEquals("number", assignStmNode.getLeft().getValue());
    }
}