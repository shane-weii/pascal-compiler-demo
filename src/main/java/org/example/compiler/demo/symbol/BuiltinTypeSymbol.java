package org.example.compiler.demo.symbol;


/**
 * @author Shane Wei
 * @date 2021/1/28 11:13
 * Description:
 */
public class BuiltinTypeSymbol extends AbstractSymbol {
    public BuiltinTypeSymbol(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return String.format("<%s>", getName());
    }
}
