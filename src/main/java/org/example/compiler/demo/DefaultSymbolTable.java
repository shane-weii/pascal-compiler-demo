package org.example.compiler.demo;

import org.example.compiler.demo.symbol.BuiltinTypeSymbol;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shane Wei
 * @date 2021/1/28 11:28
 * Description: 符号表
 */
public class DefaultSymbolTable implements SymbolTable {
    private final Map<String, Symbol> symbolMap;

    public DefaultSymbolTable() {
        symbolMap = new HashMap<>();
        symbolMap.put("INTEGER", new BuiltinTypeSymbol("INTEGER"));
        symbolMap.put("REAL", new BuiltinTypeSymbol("REAL"));
    }

    /***
     * 定义符号
     * @param symbol
     */
    @Override
    public void define(Symbol symbol) {
        System.out.println("define: " + symbol.getName());
        symbolMap.put(symbol.getName(), symbol);
    }

    /***
     * 查找符号
     * @param name
     * @return
     */
    @Override
    public Symbol lookup(String name) {
        System.out.println("lookup：" + name);
        return symbolMap.get(name);
    }
}
