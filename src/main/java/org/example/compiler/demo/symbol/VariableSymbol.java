package org.example.compiler.demo.symbol;


/**
 * @author Shane Wei
 * @date 2021/1/28 11:14
 * Description:
 */
public class VariableSymbol extends AbstractSymbol {
    private final BuiltinTypeSymbol type;

    public VariableSymbol(String name, BuiltinTypeSymbol type) {
        super(name);
        this.type = type;
    }

    public BuiltinTypeSymbol getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("<%s:%s>", getName(), getType());
    }
}
