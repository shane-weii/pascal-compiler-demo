package org.example.compiler.demo.symbol;

import org.example.compiler.demo.Symbol;

/**
 * @author Shane Wei
 * @date 2021/1/28 11:26
 * Description:
 */
public abstract class AbstractSymbol implements Symbol {
    private final String name;
    private final String type;

    protected AbstractSymbol(String name) {
        this(name, null);
    }

    protected AbstractSymbol(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }
}
