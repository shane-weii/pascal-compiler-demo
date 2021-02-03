package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Visitor;

import java.util.List;

/**
 * @author Shane Wei
 * @date 2021/1/29 9:43
 * Description:
 */
public class ProcedureDclNode implements AstNode {
    private final String name;
    private final List<ParamNode> params;
    private final BlockNode blockNode;

    public ProcedureDclNode(String name, List<ParamNode> params, BlockNode blockNode) {
        this.name = name;
        this.params = params;
        this.blockNode = blockNode;
    }

    public String getName() {
        return name;
    }

    public BlockNode getBlockNode() {
        return blockNode;
    }

    public List<ParamNode> getParams() {
        return params;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
