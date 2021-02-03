package org.example.compiler.demo;

import org.example.compiler.demo.ast.*;
import org.example.compiler.demo.exception.InvalidSyntaxException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    /***
     * 解析出AST树
     * @return
     */
    public AstNode parse() {
        final ProgramNode program = program();
        if (currentToken.getType() != EOF) {
            throw new InvalidSyntaxException();
        }
        return program;
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
     * Pascal 语法规则：[第三版]
     * program : PROGRAM variable SEM BLOCK DOT
     * block : declarations compound_statement
     * declarations : (VAR (variable_declaration SEM)+)* | (PROCEDURE ID (LPAREN parameter_list RPAREN)? SEMI block SEMI)* | empty
     * variable_declaration : ID(COMMA ID)* COLON variable_type
     * variable_type : INTEGER | REAL
     * parameter_list : parameter | parameter SEM parameter_list
     * parameter : ID(COMMA ID)* COLON variable_type
     * compound_statement : BEGIN statement_list END
     * statement_list : statement | statement (SEM statement)*
     * statement : compound_statement | assign_statement | empty
     * assign_statement : variable ASSIGN expr
     * empty :
     * expr : term((PLUS | MINUS)term)*
     * term : factor((TIMES | INTEGER_DIV | FLOAT_DIV)factor)*
     * factor : (PLUS|MINUS)factor | INTEGER_CONST | FLOAT_CONST | LEFT expr RIGHT | variable
     * variable : ID
     */
    /******************************************************************************************************************/

    // factor : INTEGER | (PLUS|MINUS)factor | LEFT expr RIGHT | variable
    private AstNode factor() {
        switch (currentToken.getType()) {
            case INTEGER_CONST:
                NumNode intNode = new NumNode(currentToken);
                eat(INTEGER_CONST);
                return intNode;
            case FLOAT_CONST:
                NumNode floatNum = new NumNode(currentToken);
                eat(FLOAT_CONST);
                return floatNum;
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

    // program : PROGRAM variable SEM BLOCK DOT
    private ProgramNode program() {
        eat(PROGRAM);
        final VariableNode variable = variable();
        eat(SEM);
        final BlockNode block = block();
        eat(DOT);
        return new ProgramNode(variable.getName(), block);
    }

    // block : declarations compound_statement
    private BlockNode block() {
        final List<AstNode> declarations = declarations();
        final CompoundStmNode compoundStmNode = compoundStatement();
        return new BlockNode(declarations, compoundStmNode);
    }

    // (VAR (variable_declaration SEM)+)* | (PROCEDURE ID (LPAREN parameter_list RPAREN)? SEMI block SEMI)* | empty
    private List<AstNode> declarations() {
        List<AstNode> nodes = new ArrayList<>();
        while (true) {
            if (currentToken.getType() == VAR) {
                eat(VAR);
                while (currentToken.getType() == ID) {
                    final List<VarDeclNode> list = variableDeclaration();
                    nodes.addAll(list);
                    eat(SEM);
                }
            } else if (currentToken.getType() == PROCEDURE) {
                eat(PROCEDURE);
                String name = (String) currentToken.getValue();
                eat(ID);

                List<ParamNode> params = Collections.emptyList();
                if (currentToken.getType() == LEFT_PART) {
                    eat(LEFT_PART);
                    params = parameterList();
                    eat(RIGHT_PART);
                }

                eat(SEM);
                final BlockNode block = block();
                nodes.add(new ProcedureDclNode(name, params, block));
                eat(SEM);
            } else {
                break;
            }
        }
        return nodes;
    }

    // parameter_list : parameter | parameter SEM parameter_list
    private List<ParamNode> parameterList() {
        // PROCEDURE proc();
        if (currentToken.getType() != ID) {
            return Collections.emptyList();
        }
        final List<ParamNode> nodes = parameter();
        while (currentToken.getType() == SEM) {
            eat(SEM);
            nodes.addAll(parameter());
        }
        return nodes;
    }

    // parameter : ID(COMMA ID)* COLON variable_type
    private List<ParamNode> parameter() {
        List<VariableNode> variableNodes = new ArrayList<>();
        variableNodes.add(variable());
        while (currentToken.getType() == COMMA) {
            eat(COMMA);
            variableNodes.add(variable());
        }
        eat(COLON);
        final VarTypeNode type = variableType();
        return variableNodes.stream().map(vNode -> new ParamNode(vNode, type)).collect(Collectors.toList());
    }

    // variable_declaration : ID(COMMA ID)* COLON variable_type
    private List<VarDeclNode> variableDeclaration() {
        List<VariableNode> nodes = new ArrayList<>();
        nodes.add(variable());
        while (currentToken.getType() == COMMA) {
            eat(COMMA);
            nodes.add(variable());
        }
        eat(COLON);
        final VarTypeNode type = variableType();
        return nodes.stream().map(n -> new VarDeclNode(n, type)).collect(Collectors.toList());
    }

    // variable_type : INTEGER | REAL
    private VarTypeNode variableType() {
        final Token token = this.currentToken;
        if (token.getType() == INTEGER) {
            eat(INTEGER);
        } else {
            eat(REAL);
        }
        return new VarTypeNode(token);
    }

    // term : factor((TIMES | INTEGER_DIV | FLOAT_DIV)factor)*
    private AstNode term() {
        AstNode node = factor();
        while (currentToken.getType() == TIMES || currentToken.getType() == INTEGER_DIV || currentToken.getType() == FLOAT_DIV) {
            Token op = currentToken;
            if (op.getType() == TIMES) {
                eat(TIMES);
            } else if (op.getType() == INTEGER_DIV) {
                eat(INTEGER_DIV);
            } else {
                eat(FLOAT_DIV);
            }
            node = new OpNode(node, op, factor());
        }
        return node;
    }

    // expr : term((PLUS|MINUS)term)*
    private AstNode expr() {
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
