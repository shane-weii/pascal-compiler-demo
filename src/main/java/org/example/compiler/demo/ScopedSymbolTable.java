package org.example.compiler.demo;

import org.example.compiler.demo.symbol.BuiltinTypeSymbol;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shane Wei
 * @date 2021/2/3 14:46
 * Description:
 */
public class ScopedSymbolTable {
    private final Map<String, Symbol> symbolMap = new HashMap<>();
    private final String scopeName;
    private final int scopeLevel;
    private final ScopedSymbolTable parentScope;

    public ScopedSymbolTable(String scopeName, int scopeLevel) {
        this(scopeName, scopeLevel, null);
    }

    public ScopedSymbolTable(String scopeName, int scopeLevel, ScopedSymbolTable parentScope) {
        this.scopeName = scopeName;
        this.scopeLevel = scopeLevel;
        this.parentScope = parentScope;
    }

    public void initBuiltinSymbols() {
        define(new BuiltinTypeSymbol("INTEGER"));
        define(new BuiltinTypeSymbol("REAL"));
    }

    public String getScopeName() {
        return scopeName;
    }

    public int getScopeLevel() {
        return scopeLevel;
    }

    public ScopedSymbolTable getParentScope() {
        return parentScope;
    }

    public void define(Symbol symbol) {
        symbolMap.put(symbol.getName(), symbol);
    }

    public Symbol lookup(String name) {
        return lookup(name, false);
    }

    public Symbol lookup(String name, boolean currentScopeOnly) {
        Symbol symbol = symbolMap.get(name);
        if (currentScopeOnly || symbol != null) {
            return symbol;
        }
        return parentScope == null ? null : parentScope.lookup(name, false);
    }

    @Override
    public String toString() {
        String head = "SCOPE (SCOPED SYMBOL TABLE)";
        StringBuilder sb  = new StringBuilder();
        sb.append(head).append("\n");
        for (int i = 0; i < head.length(); i++) {
            sb.append("=");
        }
        sb.append("\nScope name : ").append(scopeName).append("\n");
        sb.append("Scope level : ").append(scopeLevel).append("\n");
        sb.append("Parent scope : ").append(parentScope).append("\n");
        String tabHead = "Scope (Scoped symbol table) contents";
        sb.append(tabHead).append("\n");
        for (int i = 0; i < tabHead.length(); i++) {
            sb.append("-");
        }
        sb.append("\n");
        symbolMap.forEach((k, v) -> sb.append(k).append(": ").append(v).append("\n"));
        for (int i = 0; i < tabHead.length(); i++) {
            sb.append("-");
        }
        sb.append('\n');
        return sb.toString();
    }
}
