package org.example.compiler.demo;

import org.example.compiler.demo.ast.*;
import org.example.compiler.demo.exception.InvalidSyntaxException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shane Wei
 * @date 2021/1/25 11:28
 * Description: 简单解释运算表达式的访问者
 */
public class NodeVisitor implements Visitor<Integer> {
    private final Map<String, Integer> variableTable = new HashMap<>();

    @Override
    public Integer visit(NumNode node) {
        return node.getValue();
    }

    @Override
    public Integer visit(OpNode node) {
        final AstNode left = node.getLeft();
        final AstNode right = node.getRight();
        final Token.Type opType = node.getOp().getType();
        int res = 0;
        if (opType == Token.Type.PLUS) {
            res = left.accept(this) + right.accept(this);
        } else if (opType == Token.Type.MINUS) {
            res = left.accept(this) - right.accept(this);
        } else if (opType == Token.Type.TIMES) {
            res = left.accept(this) * right.accept(this);
        } else {
            res = left.accept(this) / right.accept(this);
        }
        return res;
    }

    @Override
    public Integer visit(UnaryOpNode node) {
        final AstNode expr = node.getExpr();
        int value = expr.accept(this);
        if (node.getOp().getType() == Token.Type.MINUS) {
            value = -value;
        }
        return value;
    }

    @Override
    public Integer visit(NoOpNode node) {
        return null;
    }

    @Override
    public Integer visit(CompoundStmNode node) {
        final List<AstNode> nodes = node.getChildren();
        for (AstNode astNode : nodes) {
            astNode.accept(this);
        }
        return null;
    }

    @Override
    public Integer visit(AssignStmNode node) {
        final VariableNode left = node.getLeft();
        final AstNode right = node.getRight();
        variableTable.put(left.getValue(), right.accept(this));
        return null;
    }

    @Override
    public Integer visit(VariableNode node) {
        final String variableId = node.getValue();
        if (!variableTable.containsKey(variableId)) {
            throw new InvalidSyntaxException("'" + variableId + "' undeclared (first use in this function)");
        }
        return variableTable.get(variableId);
    }

    // 测试使用
    protected Map<String, Integer> getVariableTable() {
        return Collections.unmodifiableMap(variableTable);
    }
}
