package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Visitor;
import org.example.compiler.demo.Token;

/**
 * @author Shane Wei
 * @date 2021/1/26 13:56
 * Description:
 */
public class VariableNode implements AstNode {
    private final String value;

    public VariableNode(Token token) {
        this.value = (String) token.getValue();
    }

    public String getValue() {
        return value;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
