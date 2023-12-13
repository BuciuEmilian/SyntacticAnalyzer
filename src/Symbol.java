class Symbol {
    private String name;
    private SymbolType type;


    public Symbol(String name, SymbolType type) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isTerminal() {
        return type == SymbolType.TERMINAL;
    }

    public boolean isNonterminal() {
        return type == SymbolType.NONTERMINAL;
    }

    public boolean isProduction() {
        return type == SymbolType.PRODUCTION;
    }
}