import java.util.*;

public class ADR {
    enum State {
        NORMAL,  // q
        BACK, // r
        END,  // t
        ERROR  // e
    }

    private State state;
    private int index;
    private Stack<Symbol> stack;
    private Stack<Symbol> input;

    public ADR() {
        this.state = State.NORMAL;
        this.index = 0;
        stack = new Stack<>();
        input = new Stack<>();
    }

    private void pushList(List<Symbol> symbols) {
        input.push(new Symbol("@", SymbolType.AUX));
        ListIterator<Symbol> li = symbols.listIterator(symbols.size());
        while (li.hasPrevious()) {
            input.push(li.previous());
        }
    }

    private void popList() {
        while (!input.peek().getName().equals("@")) {
            input.pop();
        }
        input.pop();
    }

    public void reset() {
        index = 0;
        input.clear();
        stack.clear();
    }

    public String analyse(Grammar grammar, String sequence) throws Exception {
        input.push(grammar.getStartSymbol());
        //Cattimp ((s!=t) si (s!=e)) executa
        while (state != State.END && state != State.ERROR) {
            // daca (s=q)
            if (state == State.NORMAL) {
                // daca beta = epsilon
                if (input.isEmpty()) {
                    // daca i=n+1
                    if (index == sequence.length())         // SUCCESS
                        state = State.END;
                    else                                    // INSUCCES DE MOMENT (beta = epsilon, i != n + 1) - stiva e goala dar nu s a terminat secventa
                        state = State.BACK;
                }
                else {
                    // daca varf(beta) = un neterminal A
                    Symbol symbol = input.peek();
                    if (symbol.isNonterminal()) {     // EXPANDARE
                        input.pop();
                        stack.push(new Symbol(symbol.getName() + " 0", SymbolType.AUX));   // partea stanga a primei regulii de productie care l implica pe A
                        pushList(grammar.getProductionResult(symbol, 0));                // partea dreapta a primei regulii de productie care l implica pe A

                    }
                    else {
                        // daca varf(beta) = terminalul xi
                        if (symbol.isTerminal()) {    // AVANS
                            String curr = String.valueOf(sequence.charAt(index));
                            if (symbol.getName().equals(curr)) {
                                index++;
                                input.pop();
                                stack.push(symbol);
                            }
                            else {                              // INSUCCES DE MOMENT (a != aj) - mismatch in secventa
                                state = State.BACK;
                            }
                        }
                        else {
                            input.pop();
                        }
                    }
                }
            }
            else {
                // daca s=r
                if (state == State.BACK) {
                    // daca varf(alfa) = un terminal a
                    Symbol symbol = stack.peek();
                    if (symbol.isTerminal()) {            // REVENIRE
                        index--;
                        Symbol a = stack.pop();
                        input.push(a);
                    }
                    else {  // varf(alfa) un Aj oarecare (indicatie pt A -> Y(j+1))
                        Pair<String, Integer> data = symbol.getSymbolData();
                        // daca exista r.p. A -> Y(j+1)
                        List<Symbol> rules = grammar.getProductionResult(data.getFirst(), data.getSecond() + 1);
                        if (!rules.isEmpty()) {                            // ALTA INCERCARE 1
                            state = State.NORMAL;
                            stack.pop();
                            stack.push(new Symbol(data.getFirst() + " " + (data.getSecond() + 1), SymbolType.AUX)); // partea stanga a urmatoarei reguli de productie care l implica pe A
                            popList();
                            pushList(rules); // partea dreapta a urmatoarei reguli de productie care l implica pe A
                        }
                        else
                        {
                            // daca i=1 si A=S
                            if (index == 0 && Objects.equals(symbol.getSymbolData().getFirst(), grammar.getStartSymbol().getName())) { // EROARE
                                state = State.ERROR;
                            }
                            else {                              // ALTA INCERCARE 2
                                Symbol Aj = stack.pop();
                                input.pop();
                                input.push(new Symbol(Aj.getSymbolData().getFirst(), SymbolType.NONTERMINAL));
                            }
                        }
                    }
                }
            }
        }
        // daca s=e
        if (state == State.ERROR) {
            return "IMPOSIBIL";                          // tipareste Eroare
        }
        else {
            return getProductions();     // constructie_sir_prod(G, alfa)
        }
    }

    private String getProductions() throws Exception {
        List<String> lst = new ArrayList<>();
        while (!stack.isEmpty()) {
            Symbol symbol = stack.pop();
            if (symbol.isAux()) {
                Pair<String, Integer> data = symbol.getSymbolData();
                lst.add(data.getFirst() + data.getSecond());
            }
        }
        StringBuilder sb = new StringBuilder();
        ListIterator<String> li = lst.listIterator(lst.size());
        while (li.hasPrevious()) {
            sb.append(li.previous());
            sb.append(" ");
        }

        return sb.toString();
    }
}

