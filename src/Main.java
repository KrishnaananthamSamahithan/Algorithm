import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.FileDialog;
import java.awt.Frame;

public class Main {
    private static File inputFile;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Run the files inside the Input folder");
            System.out.println("2. Choose file from the file explorer");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1, 2, or 3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline after reading integer

            switch (choice) {
                case 1:
                    while (true) {  // Start of nested loop for repeated file processing
                        System.out.print("Enter the file name: ");
                        String fileName = scanner.nextLine();
                        String filePath = "C:\\Users\\ksama\\IdeaProjects\\Algorithm\\src\\Inputs\\" + fileName;
                        runPuzzle(filePath);

                        // Ask if the user wants to continue after processing option 1
                        System.out.print("Do you want to continue running files from the Input folder? (yes/no): ");
                        String continueChoice = scanner.nextLine().trim().toLowerCase();
                        if (!continueChoice.equals("yes")) {
                            System.out.println("Returning to main menu.");
                            System.out.println(); // Print a newline for better formatting before the next loop iteration
                            break;  // Exit the nested loop and return to the main menu
                        }
                    }
                    break;
                case 2:
                    while (true) {
                        readFile();
                        // Ask if the user wants to continue after processing option 1
                        System.out.print("Do you want to continue running files from the Input folder? (yes/no): ");
                        String continueChoice = scanner.nextLine().trim().toLowerCase();
                        if (!continueChoice.equals("yes")) {
                            System.out.println("Returning to main menu.");
                            System.out.println(); // Print a newline for better formatting before the next loop iteration
                            break;  // Exit the nested loop and return to the main menu
                        }
                    }
                    break;

                case 3:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                    return;
            }
            continueProgram();
        }

    }

    private static void readFile() throws Exception {
        FileDialog fileDialog = new FileDialog((Frame) null, "Select Input File");
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setDirectory(System.getProperty("user.dir"));
        fileDialog.setFile("*.txt");
        fileDialog.setVisible(true);
        String file = fileDialog.getFile();
        if (file == null) {
            System.out.println("File operation canceled.");
            return;
        }
        String fileType = file.substring(Math.max(0, file.length() - 4));

        if (!fileType.equalsIgnoreCase(".txt")) {
            throw new Exception("File extension of the input file should be .txt");
        }

        File inputFile = new File(fileDialog.getDirectory() + file);
        if (!inputFile.exists() || inputFile.length() == 0) {
            throw new Exception("The selected file is invalid or empty");
        }

        runPuzzle(inputFile.getAbsolutePath());
    }
    
    public static void continueProgram(){
        Scanner scanner = new Scanner(System.in);
        // Ask if the user wants to continue after processing an option
        System.out.print("Do you want to continue? (yes/Press Any Key to Close): ");
        String continueChoice = scanner.nextLine().trim().toLowerCase();
        if (!continueChoice.equals("yes")) {
            System.out.println("Exiting the program.");
            scanner.close();
            System.exit(0);
        }
        System.out.println(); // Print a newline for better formatting before the next loop iteration
    }

    private static void runPuzzle(String filePath) {
        try {
            SlidingPuzzle puzzle = new SlidingPuzzle(filePath);
            puzzle.findPath();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
