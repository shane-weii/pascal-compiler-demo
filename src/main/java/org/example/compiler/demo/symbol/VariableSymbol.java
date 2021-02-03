package org.example.compiler.demo.symbol;


/**
 * @author Shane Wei
 * @date 2021/1/28 11:14
 * Description:
 */
public class VariableSymbol extends AbstractSymbol {
    public VariableSymbol(String name, BuiltinTypeSymbol type) {
        super(name, type.getName());
    }

    @Override
    public String toString() {
        return String.format("<%s(name='%s', type='%s')>", this.getClass().getSimpleName(), getName(), getType());
    }
}
