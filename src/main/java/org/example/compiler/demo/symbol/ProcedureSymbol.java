package org.example.compiler.demo.symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shane Wei
 * @date 2021/2/3 14:39
 * Description:
 */
public class ProcedureSymbol extends AbstractSymbol {
    private final List<VariableSymbol> params;

    public ProcedureSymbol(String name) {
        this(name, new ArrayList<>());
    }

    public ProcedureSymbol(String name, List<VariableSymbol> params) {
        super(name);
        this.params = params;
    }

    public List<VariableSymbol> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return String.format("<%s(name='%s', params='%s')>", this.getClass().getSimpleName(), getName(), getParams());
    }
}
