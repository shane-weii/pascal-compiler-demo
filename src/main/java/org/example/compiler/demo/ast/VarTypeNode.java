package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Token;
import org.example.compiler.demo.Visitor;

/**
 * @author Shane Wei
 * @date 2021/1/27 14:56
 * Description:
 */
public class VarTypeNode implements AstNode {
    private final String name;

    public VarTypeNode(Token token) {
        this.name = (String) token.getValue();
    }

    public String getName() {
        return name;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
