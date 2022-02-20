import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.lang.*;

public class main {

    public static void main(String[] args) throws FileNotFoundException {

        // Variable list
        int guessesLeft = 6;
        ArrayList<String> previousGuesses = new ArrayList<String>();
        ArrayList<String> wordList = prepareListFromFile("src/main/resources/word_list.txt");
        String targetWord = generateTargetWord(wordList);

        // Game loop
        System.out.println("! means that letter is correct and in the right place.");
        System.out.println("? means the letter is correct but in the wrong place");
        System.out.println();

        while(true) {
            System.out.println("Guesses left = " + guessesLeft);
            System.out.println();
            System.out.println("Enter your guess:");
            Scanner sc = new Scanner(System.in);
            String guess = sc.nextLine();

            if (!isWordValid(wordList, previousGuesses, guess)) {
                continue;
            }
            previousGuesses.add(guess);

            if (guess.equalsIgnoreCase(targetWord)) {
                System.out.println("You got it! Congratulations!");
                break;
            }

            giveFeedbackOnGuess(targetWord, guess);
            guessesLeft = guessesLeft - 1;

            if (guessesLeft == 0) {
                System.out.println("Out of guesses...");
                break;
            }
        }

        System.out.println("Thanks for playing!");


    }

    public static boolean isWordValid(ArrayList<String> wordList, ArrayList<String> previousGuesses, String word) {
        if (!isWordOnlyLetters(word)) {
            System.out.println("Word contains characters that are not allowed");
            return false;
        }
        if (!isWordFiveLetters(word)) {
            System.out.println("Word is not 5 letters long");
            return false;
        }
        if (!isWordOnList(wordList, word)) {
            System.out.println("Word is not on list");
            return false;
        }
        if (hasWordBeenGuessedAlready(previousGuesses, word)) {
            System.out.println("Word has already been guessed");
            return false;
        }
        return true;
    }

    public static boolean isWordOnList(ArrayList<String> wordList, String word) {
        for (String str : wordList) {
            if (word.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWordOnlyLetters(String word) {
        if (word.matches("[a-zA-Z]+"))  {
            return true;
        }
        return false;
    }

    public static boolean isWordFiveLetters(String word) {
        if (word.length() == 5) {
            return true;
        }
        return false;
    }

    public static boolean hasWordBeenGuessedAlready(ArrayList<String> previousGuesses, String word) {
        for (String str : previousGuesses) {
            if (word.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> prepareListFromFile(String filePath) throws FileNotFoundException {
        Scanner sc1 = new Scanner(new File(filePath));
        ArrayList<String> wordList = new ArrayList<String>();
        while(sc1.hasNextLine()) {
            wordList.add(sc1.nextLine());
        }
        return wordList;
    }

    public static String generateTargetWord(ArrayList<String> wordList) {
        Random rand = new Random();
        int upperbound = wordList.size() - 1;
        int generatedNumber = rand.nextInt(upperbound);
        return wordList.get(generatedNumber);
    }

    public static void giveFeedbackOnGuess(String targetWord, String guess) {
        char[] targetCharArray = targetWord.toCharArray();
        char[] guessCharArray = guess.toCharArray();
        char[] feedbackCharArray = {'_', '_', '_', '_', '_'};

        for (int i=0; i<5; i++) {
            if (guessCharArray[i] == targetCharArray[i]) {
                feedbackCharArray[i] = '!';
            }
            else if(targetWord.contains(Character.toString(guessCharArray[i]))) {
                feedbackCharArray[i] = '?';
            }
        }

        for (char c : guessCharArray) {
            System.out.print(Character.toUpperCase(c));
            System.out.print("   ");
        }
        System.out.println();

        for (char c : feedbackCharArray) {
            System.out.print(c);
            System.out.print("   ");
        }
        System.out.println();
    }

}
