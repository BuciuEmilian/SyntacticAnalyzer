import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    private static void test1() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("inputs/g1.txt"));
        Grammar grammar = new Grammar(lines);
        ADR2 adr = new ADR2();
        System.out.println(adr.analyse(grammar, "aaaaacbaca"));
    }
    private static void test2() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("inputs/g2.txt"));
        Grammar grammar = new Grammar(lines);
        ADR2 adr = new ADR2();
        System.out.println(adr.analyse(grammar, "+a-aa"));
    }
    private static void test3() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("inputs/g3.txt"));
        Grammar grammar = new Grammar(lines);
        ADR2 adr = new ADR2();
        System.out.println(adr.analyse(grammar, "beginbeginstmtendend"));

        // S -> begin SList end -> begin S end -> begin (begin SList end) end -> begin (begin S end) end -> begin (begin stmt end) end
    }

    private static void test4() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("inputs/g4.txt"));
        Grammar grammar = new Grammar(lines);
        ADR2 adr = new ADR2();
        System.out.println(adr.analyse(grammar, "abbcc"));
    }

    private static void test5() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("inputs/g2.txt"));
        Grammar grammar = new Grammar(lines);
        ADR2 adr = new ADR2();
        System.out.println(adr.analyse(grammar, "+-++-"));
    }

    public static void test_cpp1() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("inputs/cpp.txt"));
        Grammar grammar = new Grammar(lines);
        ADR2 adr = new ADR2();
        List<String> inp_lines = Files.readAllLines(Path.of("C:\\Proiecte SSD\\Python\\Lab5LFTC\\outputs\\output_file.txt"));
        System.out.println(adr.analyse(grammar, inp_lines.get(0)
                ));
    }

    public static void main(String[] args) {
        try {
//            test1();
//            test2();
//            test3();
//            test4();
//            test5();
            test_cpp1();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}