package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shane Wei
 * @date 2021/1/26 13:48
 * Description:
 */
public class CompoundStmNode implements AstNode {
    private final List<AstNode> children = new ArrayList<>();

    public List<AstNode> getChildren() {
        return children;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
