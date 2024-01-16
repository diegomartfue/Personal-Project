import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class UtepTube {
    public static void main(String[] args) {
        Scanner scanner;
        File myObj;
        int totalSeconds = 0;
        int totalMinutes = 0;
        int totalHours = 0;
        String link = "https://youtu.be/";
        String videoId = "";
        String videoTitle = "";
        String creator = "";
        String minutes = "";
        String seconds = "";
        String playlist = "";
        String number = "";
        int playlistOrder = 0;
        boolean run = true;
       
        // Initialize input scanner
        Scanner input = new Scanner(System.in);
        myObj = new File("corpus.csv");

        try {
            // Attempt to open the file for reading
            scanner = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            System.err.println("Error: File 'corpus.csv' not found.");
            return; // Exit the program if the file is not found.
        }

        String choice;

        while (run) {
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

                    // Print the table data from the CSV file
                    try (Scanner fileScanner = new Scanner(myObj)) {
                        while (fileScanner.hasNextLine()) {
                            String line = fileScanner.nextLine();
                            Scanner lineReader = new Scanner(line);
                            lineReader.useDelimiter(",");

                            videoId = lineReader.next().trim();
                            videoTitle = lineReader.next().trim();
                            creator = lineReader.next().trim();
                            minutes = lineReader.next().trim();
                            seconds = lineReader.next().trim();

                            // Print a table row
                            System.out.printf("| %-11s | %-49s | %-19s | %5s |%n", videoId, videoTitle, creator, minutes + ":" + seconds);

                            lineReader.close();
                        }
                    } catch (FileNotFoundException e) {
                        System.err.println("Error: File 'corpus.csv' not found.");
                    }

                    // Print the footer
                    System.out.println("+-------------+---------------------------------------------------+---------------------+-------+");

                    break;
                case "2":
                    System.out.println("Please provide the video ID");
                    String id = input.next().trim();
                    boolean found = false;
                    scanner.close();

                    try {
                        // Reopen the file for reading
                        scanner = new Scanner(myObj);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            Scanner lineReader = new Scanner(line);
                            lineReader.useDelimiter(",");

                            videoId = lineReader.next().trim();
                            videoTitle = lineReader.next().trim();
                            creator = lineReader.next().trim();
                            minutes = lineReader.next().trim();
                            seconds = lineReader.next().trim();
                            String preRollAd = lineReader.next().trim();
                            String midRollAd = lineReader.next().trim();
                            String postRollAd = lineReader.next().trim();

                            if (id.equals(videoId)) {
                            	playlistOrder ++;
                            	number = String.valueOf(playlistOrder);
                                found = true;

                                playlist += number + "." + " " + link + videoId + "\t| " + minutes + ":" + seconds +"(";


                                // Update total time with video and ad duration
                                totalSeconds += Integer.parseInt(seconds);
                                totalMinutes += Integer.parseInt(minutes);
                                System.out.println("Excellent choice!");
                                System.out.println("Video Title: " + videoTitle);
                                System.out.println("Creator: " + creator);
                                System.out.println("Minutes: " + minutes);
                                System.out.println("Seconds: " + seconds);

                                // Add ad durations to total time
                                if (preRollAd.equals("true")) {
                                	playlist += "+30s preroll\t";
                                    totalSeconds += 30;
                                }
                                
                                if (midRollAd.equals("true")) 
                                {
								    System.out.println("Would you like to skip the add? (true/false)");
								    Scanner newInput = new Scanner(System.in);
								    boolean skip = newInput.nextBoolean();
								    
								    if (skip) {
								        playlist += "+10s midroll\t";
								        totalSeconds += 10;
								    } else {
								        totalMinutes += 2;
								        playlist += "+2 min midroll\t";
								    }
								}
								
								if (postRollAd.equals("true")) 
								{
                                    totalSeconds += 5;
                                    playlist += "+5s postroll";
                                }
                                

                                if(preRollAd.equals("false") && midRollAd.equals("false") && postRollAd.equals("false"))
                                {
                                	playlist += "no ads";
                                }
                                
                              
                                // Adjust total time for minutes and hours if necessary
                                while (totalSeconds >= 60) {
                                    totalSeconds -= 60;
                                    totalMinutes += 1;
                                }
                                while (totalMinutes >= 60) {
                                    totalMinutes -= 60;
                                    totalHours += 1;
                                }
                                playlist+=")\n";
                                break;
                            }

                            lineReader.close();
                        }
                    } catch (FileNotFoundException e) {
                        System.err.println("Error: File 'corpus.csv' not found.");
                    }

                    if (!found)
                        System.out.println("Invalid ID, please provide a proper video ID.");
                    break;
                case "3":
                    if ((totalMinutes + totalHours + totalSeconds) == 0) {
                        System.out.println("------------- YOUR PLAYLIST ------------");
                        System.out.println("Total play time: 0:00:00");
                        System.out.println("");
                    } else {
                        System.out.println("------------- YOUR PLAYLIST ------------");
                        System.out.println(playlist);


                        // Display the total play time
                        System.out.println("\n\nTotal play time: " + totalHours + ":" + totalMinutes + ":" + totalSeconds+ "\n");
                    }
                    break;
                case "4":
                    // Clear the playlist
                    totalHours = 0;
                    totalMinutes = 0;
                    totalSeconds = 0;
                    playlist = "";
                    playlistOrder = 0;
                    System.out.println("Your playlist has been cleared.");
                    break;
                case "5":
                    scanner.close();
                    run = false; // Exit the program when the user chooses to close it.
                    System.out.println("Closing UtepTube");
                    break; // Added 'break' to exit the loop
                default:
                    System.out.println("Invalid number, please provide a number 1-5.");
            }
        }
    }
}
