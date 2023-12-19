import java.util.Objects;

class Symbol {
    private String name;
    private SymbolType type;


    public Symbol(String name, SymbolType type) {
        this.name = name;
        this.type = type;
    }
    public Symbol(char name) {
        this.name = String.valueOf(name);
        this.type = SymbolType.TERMINAL;
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

    public boolean isAux() {
        return type == SymbolType.AUX;
    }

    public Pair<String, Integer> getSymbolData() throws Exception {
        if (type != SymbolType.AUX)
            throw new Exception();
        String[] data = name.split(" ");

        return new Pair<String, Integer>(data[0], Integer.parseInt(data[1]));
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(name, symbol.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}