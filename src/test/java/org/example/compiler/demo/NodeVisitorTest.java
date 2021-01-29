package org.example.compiler.demo;

import org.example.compiler.demo.ast.ProgramNode;
import org.example.compiler.demo.exception.UndefinedSymbolException;
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
        String code = "PROGRAM Prog;\n" +
                "VAR\n" +
                "   number     : INTEGER;\n" +
                "   a, b, c, x : INTEGER;\n" +
                "   y          : REAL;\n" +
                "\n" +
                "PROCEDURE p1;\n" +
                "VAR\n" +
                "   a : REAL;\n" +
                "   k : INTEGER;\n" +
                "\n" +
                "   PROCEDURE p2;\n" +
                "   VAR\n" +
                "      a, z : INTEGER;\n" +
                "   BEGIN {p2}\n" +
                "      z := 777;\n" +
                "   END;  {p2}\n" +
                "\n" +
                "BEGIN {p1}\n" +
                "END;  {p1}\n" +
                "BEGIN {Prog}\n" +
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
                "END.  {Prog}";
        Lexer lexer = new Lexer(code);
        Parser parser = new Parser(lexer);
        final ProgramNode program = parser.program();
        NodeVisitor nodeVisitor = new NodeVisitor();
        nodeVisitor.visit(program);
        final Map<String, Double> table = nodeVisitor.getVariableTable();
        assertEquals(2, table.get("number").intValue());
        assertEquals(2, table.get("a").intValue());
        assertEquals(25, table.get("b").intValue());
        assertEquals(27, table.get("c").intValue());
        assertEquals(11, table.get("x").intValue());
        assertEquals(5.9971, table.get("y"), 4);
    }

    @Test
    public void testUndefined() {
        String code = "PROGRAM prog;\n" +
                "VAR\n" +
                "   a : INTEGER;\n" +
                "\n" +
                "BEGIN\n" +
                "   a := 2 + b;\n" +
                "END.";
        Parser parser = new Parser(code);
        NodeVisitor nodeVisitor = new NodeVisitor();
        final ProgramNode program = parser.program();
        assertThrows("undefined symbol 'b'", UndefinedSymbolException.class, () -> nodeVisitor.visit(program));
    }
}