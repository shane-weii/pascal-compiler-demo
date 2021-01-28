package org.example.compiler.demo;

/**
 * @author Shane Wei
 * @date 2021/1/28 11:36
 * Description:
 */
public interface SymbolTable {
    /***
     * 定义符号
     * @param symbol
     */
    void define(Symbol symbol);

    /***
     * 查找符号
     * @param name
     * @return
     */
    Symbol lookup(String name);
}
