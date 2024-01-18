import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordBank {

  // This method retrieves the answer for a specific puzzle number from a file.
  // The puzzle number corresponds to the line number in the "answers.txt" file.
  // Do not modify the method signature.
  public static String getAnswerForPuzzleNumber(int puzzleNumber) throws FileNotFoundException {
    // Create a file scanner to read answers.txt.
    Scanner scanner = new Scanner(new File("answers.txt"));
    Scanner input = new Scanner(System.in);

    // Skip the first puzzleNumber number of words in the file.
    for (int i = 0; i < puzzleNumber; i++) {
      scanner.next();
    }

    // Return the very next word.
    return scanner.next();
  }

  // This method checks if a proposed word is present in the dictionary file.
  // Do not modify the method signature.
  public static boolean checkInDictionary(String proposed) throws FileNotFoundException {
    // Create a file scanner to read the dictionary file.
    Scanner scanner = new Scanner(new File("dictionary.txt"));

    // Iterate through the dictionary file to check if the proposed word is present.
    while (scanner.hasNext()) {
      String word = scanner.next();
      if (word.equals(proposed)) {
        // Word found in the dictionary.
        return true;
      }
    }

    // Word not found in the dictionary.
    return false;
  }
}
