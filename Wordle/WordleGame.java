import java.io.FileNotFoundException;

public class WordleGame {
    private int puzzleNumber;
    private String answer;
    private int numberGuessesSoFar = 0;
    public WordleLetter[][] board = new WordleLetter[6][5];

    // Constructor that initializes a new WordleGame with a specified puzzle number
    // and retrieves the answer for that puzzle number from the WordBank.
    public WordleGame(int puzzleNumberIn) throws FileNotFoundException {
        this.puzzleNumber = puzzleNumberIn;
        this.answer = WordBank.getAnswerForPuzzleNumber(puzzleNumberIn);
    }

    // Getter method to retrieve the answer of the current game.
    public String getAnswer() {
        return this.answer;
    }

    // Method to process a user's guess and update the game state accordingly.
    public void guess(String guessWordIn) {
        if (numberGuessesSoFar < 6) { // Check if the user has not exceeded the maximum attempts
            WordleLetter[] word = new WordleLetter[5];

            // Check each character of the guess against the corresponding character in the answer.
            for (int i = 0; i < guessWordIn.length(); i++) {
                WordleLetter character = new WordleLetter(guessWordIn.charAt(i));

                // If the character is in the correct position, set its color to green.
                if (guessWordIn.charAt(i) == this.answer.charAt(i)) {
                    character.setColor("green");
                }
                word[i] = character;
            }

            // Check each character of the guess for incorrect positions and set colors accordingly.
            for (int j = 0; j < guessWordIn.length(); j++) {
                if (!word[j].isGreen()) {
                    if (guessWordIn.charAt(j) == answer.charAt(0) || guessWordIn.charAt(j) == answer.charAt(1) ||
                            guessWordIn.charAt(j) == answer.charAt(2) || guessWordIn.charAt(j) == answer.charAt(3) ||
                            guessWordIn.charAt(j) == answer.charAt(4)) {
                        word[j].setColor("yellow");
                    } else {
                        word[j].setColor("red");
                    }
                }
            }

            // Update the game board with the current guess.
            board[numberGuessesSoFar] = word;
            numberGuessesSoFar++;
        }
    }

    // Getter method to retrieve the number of guesses made so far.
    public int getNumberGuessesSoFar() {
        return numberGuessesSoFar;
    }

    // Getter method to retrieve the guessed word at a specific guess number.
    public WordleLetter[] getGuess(int guessNumberIn) {
        return board[guessNumberIn];
    }

    // Method to check if the game is over (either won or the maximum attempts reached).
    public boolean isGameOver() {
        return isGameWin() || numberGuessesSoFar == 6;
    }

    // Method to check if the game has been won.
    public boolean isGameWin() {
        if (numberGuessesSoFar <= 0) {
            return false; // Game hasn't started yet
        }

        // Check if all letters in the last guess are green (correctly placed).
        for (int i = 0; i < 5; i++) {
            if (!board[numberGuessesSoFar - 1][i].isGreen()) {
                return false;
            }
        }
        return true;
    }

    // Method to generate a string representation of the game board.
    public String toString() {
        // result will be used to build the full answer String
        StringBuilder result = new StringBuilder();

        // for each word guessed so far
        for (int i = 0; i < getNumberGuessesSoFar(); i++) {
            // get each letter of each word
            for (int j = 0; j < 5; j++) {
                // concatenate it to the result
                // WordleLetter's toString() is automatically invoked here.
                result.append(getGuess(i)[j]);
            }
            // new line separator between each word
            result.append("\n");
        }
        return result.toString();
    }
}
