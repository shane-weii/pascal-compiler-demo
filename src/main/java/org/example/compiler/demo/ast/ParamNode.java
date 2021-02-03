package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Visitor;

/**
 * @author Shane Wei
 * @date 2021/2/3 14:05
 * Description:
 */
public class ParamNode implements AstNode {
    private final VariableNode variable;
    private final VarTypeNode type;

    public ParamNode(VariableNode variable, VarTypeNode type) {
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
