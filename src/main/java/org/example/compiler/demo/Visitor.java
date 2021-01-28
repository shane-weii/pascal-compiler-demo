package org.example.compiler.demo;

import org.example.compiler.demo.ast.*;

/**
 * @author Shane Wei
 * @date 2021/1/25 11:24
 * Description: 访问者模式
 */
public interface Visitor<T> {
    /***
     * 解释数字节点
     * @param node
     * @return
     */
    T visit(NumNode node);

    /***
     * 解释操作节点
     * @param node
     * @return
     */
    T visit(OpNode node);

    /***
     * 解释一元操作节点
     * @param node
     * @return
     */
    T visit(UnaryOpNode node);

    /***
     * 解释无操作节点
     * @param node
     * @return
     */
    T visit(NoOpNode node);

    /***
     * 解释组合声明节点
     * @param node
     * @return
     */
    T visit(CompoundStmNode node);

    /***
     * 解释变量声明节点
     * @param node
     * @return
     */
    T visit(AssignStmNode node);

    /***
     * 解释变量节点
     * @param node
     * @return
     */
    T visit(VariableNode node);

    /***
     * 解释 block
     * @param node
     * @return
     */
    T visit(BlockNode node);

    /***
     * 解释 program
     * @param node
     * @return
     */
    T visit(ProgramNode node);

    /***
     * 解释变量定义
     * @param node
     * @return
     */
    T visit(VarDeclNode node);

    /***
     * 解释变量类型
     * @param node
     * @return
     */
    T visit(VarTypeNode node);
}
