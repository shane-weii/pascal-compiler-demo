package org.example.compiler.demo;

import org.example.compiler.demo.ast.*;
import org.example.compiler.demo.exception.InvalidSyntaxException;

import java.util.ArrayList;
import java.util.List;

import static org.example.compiler.demo.Token.Type.*;

/**
 * @author Shane Wei
 * @date 2021/1/25 11:04
 * Description: 语法分析器
 */
public class Parser {
    private final Lexer lexer;
    private Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    public Parser(String text) {
        this.lexer = new Lexer(text);
        this.currentToken = lexer.getNextToken();
    }

    private void eat(Token.Type type) {
        if (currentToken.getType() == type) {
            currentToken = lexer.getNextToken();
            return;
        }
        throw new InvalidSyntaxException("type match error, current type is " + currentToken.getType().name() + ", passed is " + type.name());
    }

    /*******************************************************************************************************************/
    /**
     * Pascal 语法规则：[第一版]
     * program : compound_statement DOT
     * compound_statement : BEGIN statement_list END
     * statement_list : statement | statement (SEM statement)*
     * statement : compound_statement | assign_statement | empty
     * assign_statement : variable ASSIGN expr
     * empty :
     * expr : term((PLUS|MINUS)term)*
     * term : factor((TIMES|DIV)factor)*
     * factor : (PLUS|MINUS)factor | INTEGER | LEFT expr RIGHT | variable
     */
    /******************************************************************************************************************/

    // factor : INTEGER | (PLUS|MINUS)factor | LEFT expr RIGHT | variable
    private AstNode factor() {
        switch (currentToken.getType()) {
            case INTEGER:
                NumNode numNode = new NumNode(currentToken);
                eat(INTEGER);
                return numNode;
            case LEFT_PART:
                eat(LEFT_PART);
                AstNode node = expr();
                eat(RIGHT_PART);
                return node;
            case PLUS:
            case MINUS:
                Token token = currentToken;
                if (currentToken.getType() == PLUS) {
                    eat(PLUS);
                } else {
                    eat(MINUS);
                }
                return new UnaryOpNode(token, factor());
            case ID:
                // 单独变量
                return variable();
            default:
        }
        return null;
    }

    // term : factor((TIMES|DIV)factor)*
    private AstNode term() {
        AstNode node = factor();
        while (currentToken.getType() == TIMES || currentToken.getType() == DIV) {
            Token op = currentToken;
            if (op.getType() == TIMES) {
                eat(TIMES);
            } else {
                eat(DIV);
            }
            node = new OpNode(node, op, factor());
        }
        return node;
    }

    // expr : term((PLUS|MINUS)term)*
    @VisibleForTesting
    protected AstNode expr() {
        AstNode node = term();
        while (currentToken.getType() == PLUS || currentToken.getType() == MINUS) {
            Token op = currentToken;
            if (op.getType() == PLUS) {
                eat(PLUS);
            } else {
                eat(MINUS);
            }
            node = new OpNode(node, op, term());
        }
        return node;
    }

    // program : compound_statement DOT
    public CompoundStmNode program() {
        CompoundStmNode node = compoundStatement();
        eat(DOT);
        return node;
    }

    // compound_statement : BEGIN statement_list END
    private CompoundStmNode compoundStatement() {
        eat(BEGIN);
        final List<AstNode> nodes = statementList();
        eat(END);
        CompoundStmNode compoundStmNode = new CompoundStmNode();
        compoundStmNode.getChildren().addAll(nodes);
        return compoundStmNode;
    }

    // statement_list : statement | statement SEM statement_list
    private List<AstNode> statementList() {
        List<AstNode> nodes = new ArrayList<>();
        nodes.add(statement());
        while (currentToken.getType() == SEM) {
            eat(SEM);
            nodes.add(statement());
        }
        return nodes;
    }

    // statement : compound_statement | assign_statement | empty
    private AstNode statement() {
        AstNode node;
        if (currentToken.getType() == BEGIN) {
            node = compoundStatement();
        } else if (currentToken.getType() == ID) {
            node = assignStatement();
        } else {
            node = empty();
        }
        return node;
    }

    // empty :
    private AstNode empty() {
        return new NoOpNode();
    }

    // assign_statement : variable ASSIGN expr
    private AssignStmNode assignStatement() {
        final VariableNode variable = variable();
        Token token = currentToken;
        eat(ASSIGN);
        final AstNode expr = expr();
        return new AssignStmNode(variable, token, expr);
    }

    // variable : ID
    private VariableNode variable() {
        Token token = currentToken;
        eat(ID);
        return new VariableNode(token);
    }
}
