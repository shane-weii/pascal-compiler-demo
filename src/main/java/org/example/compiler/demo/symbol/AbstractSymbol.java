package org.example.compiler.demo.symbol;

import org.example.compiler.demo.Symbol;

/**
 * @author Shane Wei
 * @date 2021/1/28 11:26
 * Description:
 */
public abstract class AbstractSymbol implements Symbol {
    private final String name;

    public AbstractSymbol(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
