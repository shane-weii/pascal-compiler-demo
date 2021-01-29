package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Visitor;

/**
 * @author Shane Wei
 * @date 2021/1/29 9:43
 * Description:
 */
public class ProcedureDclNode implements AstNode {
    private final String name;
    private final BlockNode blockNode;

    public ProcedureDclNode(String name, BlockNode blockNode) {
        this.name = name;
        this.blockNode = blockNode;
    }

    public String getName() {
        return name;
    }

    public BlockNode getBlockNode() {
        return blockNode;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
