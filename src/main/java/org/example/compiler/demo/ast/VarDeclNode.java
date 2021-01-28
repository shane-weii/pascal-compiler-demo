package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Visitor;

/**
 * @author Shane Wei
 * @date 2021/1/27 14:49
 * Description:
 */
public class VarDeclNode implements AstNode {
    private final VariableNode variable;
    private final VarTypeNode type;

    public VarDeclNode(VariableNode variable, VarTypeNode type) {
        this.variable = variable;
        this.type = type;
    }

    public VariableNode getVariable() {
        return variable;
    }

    public VarTypeNode getType() {
        return type;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
