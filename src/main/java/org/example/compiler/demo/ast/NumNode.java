package org.example.compiler.demo.ast;

import org.example.compiler.demo.AstNode;
import org.example.compiler.demo.Visitor;
import org.example.compiler.demo.Token;

/**
 * @author Shane Wei
 * @date 2021/1/25 10:53
 * Description: 数字节点
 */
public class NumNode implements AstNode {
    private final int value;

    public NumNode(Token token) {
        this.value = (int) token.getValue();
    }

    public int getValue() {
        return value;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
