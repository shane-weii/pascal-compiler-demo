package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Visitor;
import org.example.compiler.demo.Token;

/**
 * @author Shane Wei
 * @date 2021/1/25 17:31
 * Description:
 */
public class UnaryOpNode implements AstNode {
    private final AstNode expr;
    private final Token op;

    public UnaryOpNode(Token op, AstNode expr) {
        this.op = op;
        this.expr = expr;
    }

    public AstNode getExpr() {
        return expr;
    }

    public Token getOp() {
        return op;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
