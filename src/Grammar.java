import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Grammar {
    private Symbol startSymbol;
    private HashMap<String, List<List<Symbol>>> rules;

    public Grammar(List<String> lines) {
        this.rules = parseGrammar(lines);
        //System.out.println(rules);
    }

    private HashMap<String, List<List<Symbol>>> parseGrammar(List<String> lines) {
        HashMap<String, List<List<Symbol>>> rules = new HashMap<>();
        for (String line : lines) {
            String[] components = line.split("::=");
            String aux = components[0].strip();
            String symbol = aux.substring(1, aux.length() - 1);
            String result = components[1].strip();
            if (startSymbol == null) {
                this.startSymbol = new Symbol(symbol, SymbolType.NONTERMINAL);
            }

            char separator = '@';
            boolean inside = false;
            StringBuilder sb = new StringBuilder();
            List<Symbol> symbols = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                char curr = result.charAt(i);
                // we're either at the start of a nonTerminal or at the end
                if (curr == separator) {
                    // if we're at the end
                    if (inside) {
                        symbols.add(new Symbol(sb.toString(), SymbolType.NONTERMINAL));
                        sb.setLength(0);
                    }
                    inside = !inside;
                    continue;
                }
                if (inside) {
                    sb.append(curr);
                } else {
                    symbols.add(new Symbol(curr));
                }
            }
            if (!rules.containsKey(symbol)) {
                rules.put(symbol, new ArrayList<>());
            }

            rules.get(symbol).add(symbols);
        }

        return rules;
    }

    public Symbol getStartSymbol() {
        return startSymbol;
    }

    public List<Symbol> getProductionResult(String symbol, int index) {
        try {
            return rules.get(symbol).get(index);
        }
        catch (IndexOutOfBoundsException ex){
            return new ArrayList<>();
        }
    }

    public List<Symbol> getProductionResult(Symbol symbol, int index) {
        try {
            return rules.get(symbol.getName()).get(index);
        }
        catch (IndexOutOfBoundsException ex){
            return new ArrayList<>();
        }
    }
}
