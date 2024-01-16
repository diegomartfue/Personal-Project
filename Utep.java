import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Utep {
    public static void main(String[] args) {
        Scanner scanner = null;
        File myObj;
        Scanner input = new Scanner(System.in);
        myObj = new File("corpus.csv");

        try {
            scanner = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            System.err.println("Error: File 'corpus.csv' not found.");
            return; // Exit the program if the file is not found.
        }

        String choice;

        while (true) {
            System.out.println("Welcome to UtepTube! Please select an option below to continue:");
            System.out.println("\t1. List videos in corpus");
            System.out.println("\t2. Add video to playlist");
            System.out.println("\t3. View playlist");
            System.out.println("\t4. Clear playlist");
            System.out.println("\t5. Close UtepTube");
            System.out.print("> ");
            choice = input.next();

            switch (choice) {
                case "1":
                    System.out.println("+-----------------------------------------------------------------------------------------------+");
                    System.out.println("|                                        UtepTube corpus                                        |");
                    System.out.println("+-------------+---------------------------------------------------+---------------------+-------+");

                    // Print the table data
                    try {
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            Scanner lineReader = new Scanner(line);
                            lineReader.useDelimiter(",");

                            String videoId = lineReader.next();
                            String videoTitle = lineReader.next();
                            String creator = lineReader.next();
                            String minutes = lineReader.next();
                            String seconds = lineReader.next();

                            // Print a table row
                            System.out.printf("| %-11s | %-49s | %-19s | %5s |%n", videoId, videoTitle, creator, minutes + ":" + seconds);

                            lineReader.close();
                        }
                        scanner.close();
                    } catch (FileNotFoundException e) {
                        System.err.println("Error: File 'corpus.csv' not found.");
                    }

                    // Print the footer
                    System.out.println("+-------------+---------------------------------------------------+---------------------+-------+");
                    break;

                case "2":
                    System.out.println("Please provide the video ID");
                    String id = input.next();
                    boolean found = false;
                    scanner.close();

                    try {
                        scanner = new Scanner(myObj);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            Scanner lineReader = new Scanner(line);
                            lineReader.useDelimiter(",");

                            String videoId = lineReader.next();
                            String videoTitle = lineReader.next();
                            String creator = lineReader.next();

                            if (id.equals(videoId)) {
                                found = true;
                                System.out.println("Excellent choice!");
                                System.out.println("Video Title: " + videoTitle);
                                System.out.println("Creator: " + creator);
                                break;
                            }

                            lineReader.close();
                        }
                    } catch (FileNotFoundException e) {
                        System.err.println("Error: File 'corpus.csv' not found.");
                    }

                    if (!found) {
                        System.out.println("Invalid ID, please provide a proper video ID.");
                    }
                    break;

                case "3":
                    System.out.println("Viewing playlist...");
                    // Implement playlist viewing logic here
                    break;

                case "4":
                    System.out.println("Clearing playlist...");
                    // Implement playlist clearing logic here
                    break;

                case "5":
                    System.out.println("Closing UtepTube.");
                    scanner.close();
                    return; // Exit the program when the user chooses to close it.

                default:
                    System.out.println("Invalid number, please provide a number 1-5.");
            }
        }
    }
}
