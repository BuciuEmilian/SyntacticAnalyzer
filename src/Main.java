import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Path.of("inputs/g1.txt"));
            Grammar grammar = new Grammar(lines);
            ADR adr = new ADR();
            System.out.println(adr.analyse(grammar, "acbc"));
            System.out.println(adr.analyse(grammar, "acbd"));
            System.out.println(adr.analyse(grammar, "acbd"));
        } catch (IOException ex) {
            // handle exception...
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}