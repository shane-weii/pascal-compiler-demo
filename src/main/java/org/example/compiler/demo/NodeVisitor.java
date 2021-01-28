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
public class NodeVisitor implements Visitor<Double> {
    private final Map<String, Double> variableTable = new HashMap<>();

    @Override
    public Double visit(NumNode node) {
        return node.getValue().doubleValue();
    }

    @Override
    public Double visit(OpNode node) {
        final AstNode left = node.getLeft();
        final AstNode right = node.getRight();
        final Token.Type opType = node.getOp().getType();
        double res = 0;
        if (opType == Token.Type.PLUS) {
            res = left.accept(this) + right.accept(this);
        } else if (opType == Token.Type.MINUS) {
            res = left.accept(this) - right.accept(this);
        } else if (opType == Token.Type.TIMES) {
            res = left.accept(this) * right.accept(this);
        } else if (opType == Token.Type.INTEGER_DIV){
            res = left.accept(this).intValue() / right.accept(this).intValue();
        } else {
            res = left.accept(this) / right.accept(this);
        }
        return res;
    }

    @Override
    public Double visit(UnaryOpNode node) {
        final AstNode expr = node.getExpr();
        double value = expr.accept(this);
        if (node.getOp().getType() == Token.Type.MINUS) {
            value = -value;
        }
        return value;
    }

    @Override
    public Double visit(NoOpNode node) {
        return null;
    }

    @Override
    public Double visit(CompoundStmNode node) {
        final List<AstNode> nodes = node.getChildren();
        for (AstNode astNode : nodes) {
            astNode.accept(this);
        }
        return null;
    }

    @Override
    public Double visit(AssignStmNode node) {
        final VariableNode left = node.getLeft();
        final AstNode right = node.getRight();
        variableTable.put(left.getValue(), right.accept(this));
        return null;
    }

    @Override
    public Double visit(VariableNode node) {
        final String variableId = node.getValue();
        if (!variableTable.containsKey(variableId)) {
            throw new InvalidSyntaxException("'" + variableId + "' undeclared (first use in this function)");
        }
        return variableTable.get(variableId);
    }

    @Override
    public Double visit(BlockNode node) {
        final List<VarDeclNode> declarations = node.getDeclarations();
        final CompoundStmNode compoundStmNode = node.getCompoundStmNode();
        declarations.forEach(d -> d.accept(this));
        compoundStmNode.accept(this);
        return null;
    }

    @Override
    public Double visit(ProgramNode node) {
        return node.getBlockNode().accept(this);
    }

    @Override
    public Double visit(VarDeclNode node) {
        return null;
    }

    @Override
    public Double visit(VarTypeNode node) {
        return null;
    }

    // 测试使用
    protected Map<String, Double> getVariableTable() {
        return Collections.unmodifiableMap(variableTable);
    }
}
