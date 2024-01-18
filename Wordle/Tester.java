import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(WordBank.checkInDictionary("hello"));  // true
        System.out.println(WordBank.checkInDictionary("asdfg"));  // false
        System.out.println("___________________________________________________________");
        System.out.println("");

        System.out.println(WordBank.getAnswerForPuzzleNumber(0)); // bused
        System.out.println(WordBank.getAnswerForPuzzleNumber(1)); // plumb
        System.out.println("___________________________________________________________");
        System.out.println("");

        // Tests for WordleLetter
        WordleLetter letter = new WordleLetter('a');
        System.out.println(letter.isColorSet()); // false
        letter.setColor("yellow");
        System.out.println(letter.isGreen()); // false
        letter.setColor("green");
        System.out.println(letter.isGreen()); // true

        System.out.println("___________________________________________________________");
        System.out.println("");

        // Tests for WordleGame
        WordleGame game = new WordleGame(0);
        System.out.println(game.getAnswer()); // bused
        System.out.println(game.getNumberGuessesSoFar()); // 0

        game.guess("apple");
        System.out.println(game.getNumberGuessesSoFar()); // 1
        System.out.println(game.isGameOver()); // false
        System.out.println(game.isGameWin()); // false

        game.guess("bused");
        System.out.println(game.getNumberGuessesSoFar()); // 2
        System.out.println(game.isGameOver()); // true
        System.out.println(game.isGameWin()); // true

        System.out.println(game.toString());

        System.out.println("___________________________________________________________");
        System.out.println("");

        // Tests for Main
        Scanner scanner = new Scanner(System.in);
        WordleGame gameFromMain = Main.startGame(scanner);
        Main.playGame(scanner, gameFromMain);
        Main.reportGameOutcome(gameFromMain);
    }
}
