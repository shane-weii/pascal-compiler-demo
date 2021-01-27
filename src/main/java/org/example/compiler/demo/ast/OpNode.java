package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Visitor;
import org.example.compiler.demo.Token;

/**
 * @author Shane Wei
 * @date 2021/1/25 10:58
 * Description:
 */
public class OpNode implements AstNode {
    private final AstNode left;
    private final AstNode right;
    private final Token op;

    public OpNode(AstNode left, Token op, AstNode right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public AstNode getLeft() {
        return left;
    }

    public AstNode getRight() {
        return right;
    }

    public Token getOp() {
        return op;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
