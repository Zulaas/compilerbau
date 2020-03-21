import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * The type Num scanner test.
 * @author jan
 */
class NumAbstractScannerTest extends NumAbstractScanner {


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    public void TestScanner() {
        NumAbstractScanner scanner;
        scanner = new NumAbstractScanner();
        if (scanner.readInput("testdatei_arithmetik.txt")) {
            scanner.printInputStream();
            if (scanner.lexicalAnalysis())
                scanner.printTokenStream();
        } else
            System.out.println("AbstractScanner beendet");
    }
}