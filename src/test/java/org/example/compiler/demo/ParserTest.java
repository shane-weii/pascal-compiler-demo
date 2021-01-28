package org.example.compiler.demo;

import org.example.compiler.demo.ast.AssignStmNode;
import org.example.compiler.demo.ast.BlockNode;
import org.example.compiler.demo.ast.CompoundStmNode;
import org.example.compiler.demo.ast.ProgramNode;
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
        String code = "PROGRAM prog;\n" +
                "VAR\n" +
                "   number     : INTEGER;\n" +
                "   a, b, c, x : INTEGER;\n" +
                "   y          : REAL;\n" +
                "\n" +
                "BEGIN {prog}\n" +
                "   BEGIN\n" +
                "      number := 2;\n" +
                "      a := number;\n" +
                "      b := 10 * a + 10 * number DIV 4;\n" +
                "      c := a - - b\n" +
                "   END;\n" +
                "   x := 11;\n" +
                "   y := 20 / 7 + 3.14\n" +
                "   { writeln('a = ', a); }\n" +
                "   { writeln('b = ', b); }\n" +
                "   { writeln('c = ', c); }\n" +
                "   { writeln('number = ', number); }\n" +
                "   { writeln('x = ', x); }\n" +
                "   { writeln('y = ', y); }\n" +
                "END.  {Part10}";
        Lexer lexer = new Lexer(code);
        Parser parser = new Parser(lexer);
        final ProgramNode program = parser.program();
        assertEquals("prog", program.getName());
        final BlockNode blockNode = program.getBlockNode();
        assertEquals(6, blockNode.getDeclarations().size());
        CompoundStmNode node = blockNode.getCompoundStmNode();
        assertEquals(3, node.getChildren().size());
        node = (CompoundStmNode) node.getChildren().get(0);
        assertEquals(4, node.getChildren().size());
        AssignStmNode assignStmNode = (AssignStmNode) node.getChildren().get(0);
        assertEquals("number", assignStmNode.getLeft().getName());
    }
}