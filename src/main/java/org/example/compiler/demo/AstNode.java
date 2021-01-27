package org.example.compiler.demo;

/**
 * @author Shane Wei
 * @date 2021/1/25 10:51
 * Description: 抽象AST树节点
 */
public interface AstNode {
    /***
     * 访问者模式
     * 好处是把对每种节点的解释操作放到了一个统一的visitor类中。
     * 其实也可以使用解释器模式实现，解释器模式则是把解释操作放到了每个节点类中。
     * @param visitor
     * @return
     */
    <T> T accept(Visitor<T> visitor);
}
