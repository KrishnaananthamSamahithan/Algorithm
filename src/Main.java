import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            String filePath = "C:\\Users\\ksama\\IdeaProjects\\Algorithm\\src\\Inputs\\maze10_1.txt";
            SlidingPuzzle puzzle = new SlidingPuzzle(filePath);
            puzzle.findPath();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
