package org.example.compiler.demo;

import org.example.compiler.demo.ast.*;
import org.example.compiler.demo.exception.ErrorCode;
import org.example.compiler.demo.exception.SemanticException;
import org.example.compiler.demo.symbol.BuiltinTypeSymbol;
import org.example.compiler.demo.symbol.ProcedureSymbol;
import org.example.compiler.demo.symbol.VariableSymbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shane Wei
 * @date 2021/2/2 18:31
 * Description: 语义分析, 检查变量是否定义、重复定义
 */
public class SemanticAnalyzer implements Visitor<Void> {
    private static final Logger log = LoggerFactory.getLogger(SemanticAnalyzer.class);

    /***
     * 当前语义分析符号表
     */
    private ScopedSymbolTable currentScope;

    @Override
    public Void visit(NumNode node) {
        return null;
    }

    @Override
    public Void visit(OpNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return null;
    }

    @Override
    public Void visit(UnaryOpNode node) {
        node.getExpr().accept(this);
        return null;
    }

    @Override
    public Void visit(NoOpNode node) {
        return null;
    }

    @Override
    public Void visit(CompoundStmNode node) {
        for (AstNode child : node.getChildren()) {
            child.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(AssignStmNode node) {
        // 赋值操作 a := b + a, 需要先右后左访问
        node.getRight().accept(this);
        node.getLeft().accept(this);
        return null;
    }

    @Override
    public Void visit(VariableNode node) {
        String varName = node.getName();
        if (currentScope.lookup(varName) == null) {
            error(ErrorCode.ID_NOT_FOUND, node.getToken());
        }
        return null;
    }

    @Override
    public Void visit(BlockNode node) {
        for (AstNode declaration : node.getDeclarations()) {
            declaration.accept(this);
        }
        node.getCompoundStmNode().accept(this);
        return null;
    }

    @Override
    public Void visit(ProgramNode node) {
        log.debug("ENTER scope : global");
        final ScopedSymbolTable scopeTable = new ScopedSymbolTable("global", 1, currentScope);
        scopeTable.initBuiltinSymbols();
        currentScope = scopeTable;

        // 访问树
        node.getBlockNode().accept(this);
        log.debug(scopeTable.toString());

        // 还原位置，类似于出栈
        currentScope = currentScope.getParentScope();
        log.debug("EXIT scope : global");
        return null;
    }

    @Override
    public Void visit(VarDeclNode node) {
        final String typeName = node.getType().getName();
        final Symbol type = currentScope.lookup(typeName);
        final String varName = node.getVariable().getName();
        if (currentScope.lookup(varName, true) != null) {
            error(ErrorCode.DUPLICATE_ID, node.getVariable().getToken());
        }
        final VariableSymbol variableSymbol = new VariableSymbol(varName, (BuiltinTypeSymbol) type);
        currentScope.define(variableSymbol);
        return null;
    }

    @Override
    public Void visit(VarTypeNode node) {
        return null;
    }

    @Override
    public Void visit(ProcedureDclNode node) {
        final String procName = node.getName();
        final ProcedureSymbol procedureSymbol = new ProcedureSymbol(procName);
        currentScope.define(procedureSymbol);
        log.debug("ENTER scope : {}", procName);
        final ScopedSymbolTable procScope = new ScopedSymbolTable(procName, currentScope.getScopeLevel() + 1, currentScope);
        currentScope = procScope;

        for (ParamNode param : node.getParams()) {
            Symbol typeSym = currentScope.lookup(param.getType().getName());
            final VariableSymbol varSym = new VariableSymbol(param.getVariable().getName(), (BuiltinTypeSymbol) typeSym);
            currentScope.define(varSym);
            procedureSymbol.getParams().add(varSym);
        }
        // visit block node
        node.getBlockNode().accept(this);
        log.debug(procScope.toString());

        // 还原现场
        currentScope = currentScope.getParentScope();

        log.debug("EXIT scope : {}", procName);
        return null;
    }

    @Override
    public Void visit(ParamNode node) {
        return null;
    }

    /***
     * throws SemanticException
     * @param code
     * @param token
     * @throws SemanticException
     */
    public void error(ErrorCode code, Token token) throws SemanticException {
        String message = String.format("%s -> %s", code.getMessage(), token);
        throw new SemanticException(message, code, token);
    }
}
