import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ADR {
    enum State {
        NORMAL,
        BACK,
        END,
        ERROR
    }

    private State state;
    private int index;
    private Stack<Symbol> stack;
    private Stack<Symbol> input;

    public ADR() {
        this.state = State.NORMAL;
        this.index = 1;
        stack = new Stack<>();
        input = new Stack<>();
    }

    String getProductions(Grammar grammar) {
        return "";
    }

    String analyse(Grammar grammar, Symbol startSymbol, String sequence) {
        input.push(startSymbol);
        //Cattimp ((s!=t) si (s!=e)) executa
        while (state != State.END && state != State.ERROR) {
            // daca (s=q)
            if (state == State.NORMAL) {
                // daca beta = epsilon
                if (stack.isEmpty()) {
                    // daca i=n+1
                    if (index == sequence.length())         // SUCCESS
                        state = State.END;
                    else                                    // INSUCCES DE MOMENT (beta = epsilon, i != n + 1) - stiva e goala dar nu s a terminat secventa
                        state = State.BACK;
                }
                else {
                    // daca varf(beta) = un neterminal A
                    if (input.peek().isNonterminal()) {     // EXPANDARE
                        Symbol A = input.pop();
                        //stack.push(A1);   // partea stanga a primei regulii de productie care l implica pe A
                        //input.push(Y1));  // partea dreapta a primei regulii de productie care l implica pe A
                    }
                    else {
                        // daca varf(beta) = terminalul xi
                        if (input.peek().isTerminal()) {    // AVANS
                            index++;
                            Symbol xi = input.pop();
                            stack.push(xi);
                        }
                        else {                              // INSUCCES DE MOMENT (a != aj) - mismatch in secventa
                            state = State.BACK;
                        }
                    }
                }
            }
            else {
                // daca s=r
                if (state == State.BACK) {
                    // daca varf(alfa) = un terminal a
                    if (stack.peek().isTerminal()) {            // REVENIRE
                        index--;
                        Symbol a = stack.pop();
                        input.push(a);
                    }
                    else {  // varf(alfa) un Aj oarecare (indicatie pt A -> Y(j+1))
                        // daca exista r.p. A -> Y(j+1)
                        if(true)                            // ALTA INCERCARE
                        {
                            state = State.NORMAL;
                            Symbol Aj = stack.pop();
                            //stack.push(A(j+1)); // partea stanga a urmatoarei reguli de productie care l implica pe A
                            Symbol Yj = input.pop();
                            //input.push(Y(j+1)); // partea dreapta a urmatoarei reguli de productie care l implica pe A
                        }
                        else
                        {
                            // daca i=1 si A=S
                            if (index == 1 && stack.peek() == grammar.getStartSymbol()) { // EROARE
                                state = State.ERROR;
                            }
                            else {
                                Symbol Aj = stack.pop();
                                input.push(Aj);
                            }
                        }
                    }
                }
            }
        }
        // daca s=e
        if (state == State.ERROR) {
            return "";                          // tipareste Eroare
        }
        else {
            return getProductions(grammar);     // constructie_sir_prod(G, alfa)
        }
    }
}
