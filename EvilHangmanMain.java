/*
Name: Charlie LeWarne
Assignment: Lab 10
Course/Semester: CSCI 270 Fall 2019
Class Section: 1
Lab section: 1
Sources consulted: None
*/

import java.io.*;
import java.util.*;

public class EvilHangmanMain {
    public static void main(String [] args) throws IOException {
        boolean playAgain = true;
        boolean solved = false;
        while(playAgain) {
            playAgain = true;
            Scanner keyboard = new Scanner(System.in);
            boolean wordNum = false;
            String output = "";
            ArrayList<String> guessesPrev = new ArrayList<>();

            Map<Integer, ArrayList<String>> map = FileInput();

            System.out.println("What size word would you like?");
            int length = keyboard.nextInt();
            while (map.get(length) == null) {
                System.out.println("There is no word in the dictionary with this length. Please enter another length: ");
                length = keyboard.nextInt();
            }
            System.out.println("How many guesses would you like? ");
            int guesses = keyboard.nextInt();

            System.out.println("Would you like to see the number of words left? (Yes/No)");
            String yesNo = keyboard.next();

            if (!yesNo.equalsIgnoreCase("yes") && !yesNo.equalsIgnoreCase("no")) {
                while (!yesNo.equalsIgnoreCase("yes") && !yesNo.equalsIgnoreCase("no")) {
                    System.out.println("Please answer yes or no: ");
                    yesNo = keyboard.next();
                }
            }
            if (yesNo.equalsIgnoreCase("yes")) {
                wordNum = true;
            } else if (yesNo.equalsIgnoreCase("no")) {
                wordNum = false;
            }

            ArrayList<String> list = map.get(length);

            for (int i = 0; i < length; i++) {
                output = output + "_";
            }
            while (playAgain) {
                solved = true;
                System.out.println("Input your guess: ");
                String input = keyboard.next();
                while (input.length() != 1 || input.charAt(0) > 'z' || input.charAt(0) < 'a') {
                    System.out.println("Input a valid letter: ");
                    input = keyboard.next();
                }
                for (int i = 0; i < guessesPrev.size(); i++) {
                    if (input.equalsIgnoreCase(guessesPrev.get(i))) {
                        System.out.println("The letters you have guessed are: " + guessesPrev);
                        System.out.println("Please input a letter that has not been guessed yet: ");
                        input = keyboard.next();
                    }
                }
                guessesPrev.add(input);
                char guess = input.charAt(0);

                list = output(guess, list, output);
                output = list.get(list.size() - 1);
                list.remove(list.size() - 1);
                String includes = list.get(list.size() - 1);
                list.remove(list.size() - 1);

                boolean include = false;

                if(includes.equals("true")) {
                    include = true;
                }
                for (int i = 0; i < output.length(); i++) {
                    if (output.charAt(i) == '_') {
                        solved = false;
                    }
                }
                if(!include) {
                    guesses--;
                    System.out.println("Wrong, you have " + guesses + " guesses remaining");
                    System.out.println("You have guessed: " + guessesPrev);
                    if(wordNum) {
                        System.out.println("There are " + list.size() + " words still available");
                    }
                    System.out.println(output);
                }
                else if(include && !solved){

                    System.out.println("Correct, you have " + guesses + " guesses remaining");
                    System.out.println("You have guessed: " + guessesPrev);
                    if(wordNum) {
                        System.out.println("There are " + list.size() + " words still available");
                    }
                    System.out.println(output);

                }
                if (solved) {
                    System.out.println("Congratulations! You have won the game! The word was " + output);
                    playAgain = false;
                }
                if(!solved && guesses == 0) {
                    System.out.println("You are out of guesses! The word was " + list.get(0));
                    playAgain = false;
                }

            }
            System.out.println("Would you like to play again? ");
            String playAgainTemp = keyboard.next();
            if (!playAgainTemp.equalsIgnoreCase("yes") && !playAgainTemp.equalsIgnoreCase("no")) {
                while (!playAgainTemp.equalsIgnoreCase("yes") && !playAgainTemp.equalsIgnoreCase("no")) {
                    System.out.println("Please answer yes or no: ");
                    playAgainTemp = keyboard.next();
                }
            }
            if (playAgainTemp.equalsIgnoreCase("yes")) {
                playAgain = true;
            } else if (playAgainTemp.equalsIgnoreCase("no")) {
                playAgain = false;
            }
        }
    }

    /**
     * A method that reads a file input, in this case "dictionary.txt", and adds the values to a map depending on the length
     * @return a map with lists of Strings of words that are sorted by their length
     * @throws FileNotFoundException if there is no file with the given name
     */
    public static Map<Integer, ArrayList<String>> FileInput() throws FileNotFoundException {
        File input = new File("src/dictionary.txt");
        Scanner fileScan = new Scanner(input);

        Map<Integer, ArrayList<String>> map = new HashMap<>();

        while(fileScan.hasNext()) {
            String word = fileScan.next();
            int len = word.length();
            if(map.get(len) == null) {
                ArrayList<String> list = new ArrayList<>();
                list.add(word);
                map.put(len, list);
            }
            else {
                ArrayList<String> list = map.get(len);
                list.add(word);
                map.put(len, list);
            }
        }
        return map;
    }

    /**
     * A method that looks through a list of words and creates a map using different options for the words available
     * depending on the guess that the user enters
     * @param guess The character that the user has guessed
     * @param wordList The list of possible words left
     * @param outputTemp The current String that is shown to the user, usually contaings '_'
     * @return An arrayList of Strings that represent the current available words
     */
    public static ArrayList<String> output( char guess, ArrayList<String> wordList, String outputTemp) {
        Map<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> outputArray;
        boolean includes = false;


        for(int i = 0; i < wordList.size(); i++) {
            String word = wordList.get(i);
            String key = "";
            for(int j = 0; j < word.length(); j++) {
                if(word.charAt(j) == guess) {
                    key = key + guess;
                }
                else{
                    key = key + outputTemp.charAt(j);
                }
            }
            if(map.get(key) == null) {
                ArrayList<String> list = new ArrayList<>();
                list.add(word);
                map.put(key, list);
            }
            else {
                ArrayList<String> list = map.get(key);
                list.add(word);
                map.put(key, list);
            }
        }
        Iterator<Map.Entry<String, ArrayList<String>>> iter = map.entrySet().iterator();
        String output = "";
        int listLength = 0;
        while(iter.hasNext()) {
            Map.Entry<String, ArrayList<String>> entry = iter.next();
            if(entry.getValue().size() > listLength) {
                output = entry.getKey();
                listLength = entry.getValue().size();
            }
        }
        outputArray = map.get(output);

        for(int i = 0; i < outputArray.get(0).length(); i++) {
            if(outputArray.get(0).charAt(i) == guess) {
                includes = true;
            }
        }
        if(!includes) {
            outputArray.add("false");
        }
        else {
            outputArray.add("true");
        }
        outputArray.add(output);
        return outputArray;
    }
}
