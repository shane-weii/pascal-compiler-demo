package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Visitor;

/**
 * @author Shane Wei
 * @date 2021/1/27 14:46
 * Description:
 */
public class ProgramNode implements AstNode {
    private final String name;
    private final BlockNode blockNode;

    public ProgramNode(String name, BlockNode blockNode) {
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
