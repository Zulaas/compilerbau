import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class NumScannerTest extends NumScanner {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    public void TestScanner() {
        NumScanner scanner;
        scanner = new NumScanner();
        if (scanner.readInput("testdatei_arithmetik.txt")) {
            scanner.printInputStream();
            if (scanner.lexicalAnalysis())
                scanner.printTokenStream();
        } else
            System.out.println("Scanner beendet");
    }
}