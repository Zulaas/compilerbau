class TestScanner {
    static public void main(String args[]) {
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