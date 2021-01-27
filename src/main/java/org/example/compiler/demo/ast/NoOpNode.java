package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Visitor;

/**
 * @author Shane Wei
 * @date 2021/1/26 13:57
 * Description:
 */
public class NoOpNode implements AstNode {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
