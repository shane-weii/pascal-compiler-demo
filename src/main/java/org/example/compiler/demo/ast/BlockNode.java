package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Visitor;

import java.util.List;

/**
 * @author Shane Wei
 * @date 2021/1/27 14:46
 * Description:
 */
public class BlockNode implements AstNode {
    private final List<VarDeclNode> declarations;
    private final CompoundStmNode compoundStmNode;

    public BlockNode(List<VarDeclNode> declarations, CompoundStmNode compoundStmNode) {
        this.declarations = declarations;
        this.compoundStmNode = compoundStmNode;
    }

    public List<VarDeclNode> getDeclarations() {
        return declarations;
    }

    public CompoundStmNode getCompoundStmNode() {
        return compoundStmNode;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
