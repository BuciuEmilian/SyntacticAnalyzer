import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    private static void test1() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("inputs/g1.txt"));
        Grammar grammar = new Grammar(lines);
        ADR adr = new ADR();
        System.out.println(adr.analyse(grammar, "acbc"));
    }
    private static void test2() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("inputs/g2.txt"));
        Grammar grammar = new Grammar(lines);
        ADR adr = new ADR();
        System.out.println(adr.analyse(grammar, "+a-aa"));
    }
    private static void test3() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("inputs/g3.txt"));
        Grammar grammar = new Grammar(lines);
        ADR adr = new ADR();
        System.out.println(adr.analyse(grammar, "beginbeginstmtendend"));

        // S -> begin SList end -> begin S end -> begin (begin SList end) end -> begin (begin S end) end -> begin (begin stmt end) end
    }
    public static void main(String[] args) {
        try {
            test1();
            test2();
            test3();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}