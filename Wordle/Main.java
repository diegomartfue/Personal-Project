import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    // Method to start a new WordleGame by prompting the user to enter a puzzle number.
    public static WordleGame startGame(Scanner scanner) throws FileNotFoundException {
        // Prompt the user to enter a puzzle number
        System.out.print("Enter the puzzle number (between 0 and 2315): ");
        int puzzleNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Instantiate a new WordleGame instance using the puzzle number
        WordleGame game = new WordleGame(puzzleNumber);
        return game;
    }

    // Method to play the WordleGame by prompting the user for guesses until the game is over.
    public static void playGame(Scanner scanner, WordleGame game) throws FileNotFoundException {
        while (!game.isGameOver()) {
            // Prompt the user to enter a 5-letter guess
            System.out.print("Enter a 5-letter guess: ");
            String guess = scanner.nextLine();

            // Check if the guess is in the dictionary
            while (!WordBank.checkInDictionary(guess)) {
                System.out.println("Invalid guess. Please enter a valid word from the dictionary.");
                System.out.print("Enter a 5-letter guess: ");
                guess = scanner.nextLine();
            }

            // Make the guess on the game
            game.guess(guess);

            // Print out the game state
            System.out.println(game.toString());
        }
    }

    // Method to report the outcome of the WordleGame.
    public static void reportGameOutcome(WordleGame game) {
        if (game.isGameWin()) {
            System.out.println("You won!");
        } else {
            System.out.println("The answer was " + game.getAnswer());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Only use this Scanner for user input, do not create new ones via `new Scanner(System.in)`.
        Scanner scanner = new Scanner(System.in);
        WordleGame game = startGame(scanner);
        playGame(scanner, game);
        reportGameOutcome(game);
    }
}
