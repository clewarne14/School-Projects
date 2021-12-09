/* Name: Charlie LaWarne
Maddie Herrmann
Assignment: Lab #10
Title: Word search
Course: CS 144
Class section: Section 2
Lab Section: Section 1
Semester: Spring 2019
Instructor: Dr.Cao
Date: 5-17-19
Sources consulted: docs.oracle.com for the Dialog window for the instructions
Known Bugs: findMissedWords does not work
Program description: Creates a game using a GUI that takes player answers and
gives a score for the correct answers.
Creativity: Created instructions button where a new window pops up with the
instructions before the game begins.
Instructions: Press new game to be given a word and play to check the player
answers once everything is typed in. For specific instructions click the
instructions button.
*/

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class WordSearch extends JFrame
{
  /**
   * South panel.
   */
  private JPanel sPanel;
  /**
 * North Panel
 */
  private JPanel nPanel;
  /**
 * Play Button
 */
  private JButton play;
  /**
 * New Game Button.
 */
  private JButton newGame;
  private JButton instructionsDisp;
  /**
 * Word Given to Player by Dictionary.
 */
  private JTextField givenWord;

  /**
 * Title "Word Search" on GUI.
 */
  private JLabel title;
  /**
 * How to play the game shown under the title.
 */
  private JLabel instructions;
  /**
 * Given Word Box
 */
  private String wordGiven = "";
  /**
 * Word Given.
 */
  private static String[] dictionary;
  /**
 * Random Word instantiation
 */
  private Random rand = new Random();
  /**
 * Player Input text box
 */
  private JTextArea showText;
  /**
 * Answers player provides.
 */
  private String[] answers;


  public WordSearch() throws IOException
  {
    // instantiate the method
    super("Word Search");
    readDictionary();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //panel setup
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout( new BorderLayout() );

    nPanel = new JPanel();
    nPanel.setLayout( new BorderLayout() );
    sPanel = new JPanel();

    //formatting for GUI
    play = new JButton("Play");
    newGame = new JButton("New Game");
    instructionsDisp = new JButton("Instructions");
    title = new JLabel("Word Search");
    title.setHorizontalAlignment(SwingConstants.CENTER);
    title.setVerticalAlignment(SwingConstants.CENTER);
    title.setFont(new Font("Verdana", Font.BOLD, 48));
    title.setForeground(Color.RED);
    instructions = new JLabel("Enter as many words as you can find using only the letters in:");
    instructions.setHorizontalAlignment(SwingConstants.CENTER);
    instructions.setVerticalAlignment(SwingConstants.CENTER);
    showText = new JTextArea(20, 65);
    showText.setLineWrap(true);
    JScrollPane scroll = new JScrollPane ( showText );
    scroll.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
    givenWord = new JTextField(20);
    givenWord.setFont(new Font("Verdana", Font.BOLD, 24));
    givenWord.setForeground(Color.BLUE);
    givenWord.setBackground(Color.WHITE);

    answers = new String[10000];

    showText.setEditable(false);

    play.addActionListener(new playButtonListener());
    newGame.addActionListener(new gameButtonListener());
    instructionsDisp.addActionListener(new instructionsButtonListener());

    mainPanel.add(scroll);

    nPanel.add(title, BorderLayout.NORTH);
    nPanel.add(instructions, BorderLayout.CENTER);
    nPanel.add(givenWord, BorderLayout.SOUTH);
    mainPanel.add(scroll);
    sPanel.add(play);
    sPanel.add(newGame);
    sPanel.add(instructionsDisp);

    add( sPanel, BorderLayout.SOUTH );
    add( nPanel, BorderLayout.NORTH );
    add( mainPanel, BorderLayout.CENTER);

    this.setSize(750, 500);

    this.setVisible(true);

    givenWord.setEditable(false);


  }
  /**
   * This method reads the file .
   * <p>
   * File is scanned in using new scanner. This file
   * is the dictionary
   * </p>
   * Dictionary is read using while loop and the file is then closed
   * for the next game.
   * @param  inputFile .
   * @return fileScan
   */
  public void readDictionary () throws IOException
{
  //create scanner object
  Scanner keyboard = new Scanner(System.in);
  dictionary = new String[172824];

  int i = 0;

  //read file name
  File inputFile = new File ("words.txt");
  Scanner fileScan = new Scanner (inputFile);
  fileScan.nextLine();

  while( fileScan.hasNext() )
  {
    dictionary[i] = fileScan.next(); //Read word
    i++;
  }
  fileScan.close(); //close the file connection

  }
  /**
   * This method creates new game button.                           (1)
   * <p>
   * Action listnere used to set game button.
   * </p>
   * Text on game button reads new game.
   *
   * @param  givenWord the word that is given by the dictionary.
   * @return givenWord.
   */

  private class gameButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      showText.setEditable(true);
      wordGiven = dictionary[rand.nextInt(172824)];
      givenWord.setText("" + wordGiven);
      showText.setText("");
    }
  }
  /**
   * This method creates the play button.
   * <p>
   * Action listener implemented to scan in words given and given
   * appropriate feedback
   * </p>
   * Feedback given comes from the method checkPlayerWords.
   *
   * @param  answers words the player gives.
   * @return answers.
   */
  private class playButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      answers = getPlayerWords();
      checkPlayerWords(answers);
      showText.setEditable(false);
      findMissedWords(answers);

    }

  }
private class instructionsButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      JOptionPane.showMessageDialog(null, "The instructions are: The words must be four or more letters. Proper nouns are not used. Slang words are not used. The words must appear in the dictionary and may not be the entire original word.", "Instructions", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /**
   * This method returns player words to lower case.
   * <p>
   * Lower case words are checked by words in the dcitionary .
   * </p>
   * And even more explanations to follow in consecutive
   * paragraphs separated by HTML paragraph breaks.
   *
   * @param  fullText.
   * @return fullText.
   */
  private String[] getPlayerWords()
  {
    String fullText = showText.getText().toLowerCase();

    return fullText.split("\\s+");

  }

  /**
 * This method checks the player words to see if they are in dictionary.
 * <p>
 * Words not in dictionary
 * Words too short
 * Search word
 * Not in search word
 * </p>
 * Player feedback is given with score.
 *
 * @param  totalScore.          (3)
 * @return totalScore.
 */
private void checkPlayerWords(String[] answers)
  {
    int totalScore = answers.length;

    for (int i = 0; i < answers.length; i++)
    {
      if(searchDictionary(answers[i]) == false)
      {
        answers[i] = answers[i] + "        not in dictionary";
        totalScore--;
      }
      else if(answers[i].length() <= 3)
      {
        answers[i] = answers[i] + "        Too short";
        totalScore--;
      }
      else if(inSearchWord(answers[i]) == false)
      {
        answers[i] = answers[i] + "        Not in search word";
        totalScore--;
      }
      else if (sameWord(answers[i]) == true)
      {
        answers[i] = answers[i] + "        Search word";
        totalScore--;
      }
      for(int j = 0; j < i; j++)
      {
        if(answers[i].equals(answers[j]))
        {
          answers[i] = answers[i] + "        Answer has already been given";
          totalScore--;
        }
      }
      for(int k = 0; k < dictionary.length; k++)
      {
        if(answers[i].length() == 4 && answers[i].substring(0,2).equals(dictionary[k]) && answers[i].charAt(3) == 's')
        {
          answers[i] = answers[i] + "        Answer acquires 4 letters by adding an s to the end";
          totalScore--;
        }
      }


    }
    showText.setText("");
    for(int j = 0; j < answers.length; j++)
    {
      showText.append(answers[j] + "\n");
    }
    showText.append("You got " + totalScore + " correct");
  }


  /**
 * This method searches the dictionary.
 * <p>
 * Counts characters in dictionary
 * </p>
 * If statments given for individual letters.
 *
 * @param  charCountOutput output of characters in string .
 * @return
 */
  private boolean searchDictionary(String word)
  {
    boolean trueFalse = false;
    for(int i = 0; i < dictionary.length; i++)
    {
      if(word.equals(dictionary[i]))
        trueFalse = true;
    }
    return trueFalse;
  }
  private int[] charCount(String s)
  {
    int[] charCountOutput;
    charCountOutput = new int[26];
    charCountOutput[0] = 0;
    charCountOutput[1] = 0;
    charCountOutput[2] = 0;
    charCountOutput[3] = 0;
    charCountOutput[4] = 0;
    charCountOutput[5] = 0;
    charCountOutput[6] = 0;
    charCountOutput[7] = 0;
    charCountOutput[8] = 0;
    charCountOutput[9] = 0;
    charCountOutput[10] = 0;
    charCountOutput[11] = 0;
    charCountOutput[12] = 0;
    charCountOutput[13] = 0;
    charCountOutput[14] = 0;
    charCountOutput[15] = 0;
    charCountOutput[16] = 0;
    charCountOutput[17] = 0;
    charCountOutput[18] = 0;
    charCountOutput[19] = 0;
    charCountOutput[20] = 0;
    charCountOutput[21] = 0;
    charCountOutput[22] = 0;
    charCountOutput[23] = 0;
    charCountOutput[24] = 0;
    charCountOutput[25] = 0;

    for (int i = 0;i < s.length();i++)
    {
      if(s.charAt(i) == 'a' || s.charAt(i) == 'A')
        charCountOutput[0]++;
      if(s.charAt(i) == 'b' || s.charAt(i) == 'B')
        charCountOutput[1]++;
      if(s.charAt(i) == 'c' || s.charAt(i) == 'C')
        charCountOutput[2]++;
      if(s.charAt(i) == 'd' || s.charAt(i) == 'D')
        charCountOutput[3]++;
      if(s.charAt(i) == 'e' || s.charAt(i) == 'E')
        charCountOutput[4]++;
      if(s.charAt(i) == 'f' || s.charAt(i) == 'F')
        charCountOutput[5]++;
      if(s.charAt(i) == 'g' || s.charAt(i) == 'G')
        charCountOutput[6]++;
      if(s.charAt(i) == 'h' || s.charAt(i) == 'H')
        charCountOutput[7]++;
      if(s.charAt(i) == 'i' || s.charAt(i) == 'I')
        charCountOutput[8]++;
      if(s.charAt(i) == 'j' || s.charAt(i) == 'J')
        charCountOutput[9]++;
      if(s.charAt(i) == 'k' || s.charAt(i) == 'K')
        charCountOutput[10]++;
      if(s.charAt(i) == 'l' || s.charAt(i) == 'L')
        charCountOutput[11]++;
      if(s.charAt(i) == 'm' || s.charAt(i) == 'M')
        charCountOutput[12]++;
      if(s.charAt(i) == 'n' || s.charAt(i) == 'N')
        charCountOutput[13]++;
      if(s.charAt(i) == 'o' || s.charAt(i) == 'O')
        charCountOutput[14]++;
      if(s.charAt(i) == 'p' || s.charAt(i) == 'P')
        charCountOutput[15]++;
      if(s.charAt(i) == 'q' || s.charAt(i) == 'Q')
        charCountOutput[16]++;
      if(s.charAt(i) == 'r' || s.charAt(i) == 'R')
        charCountOutput[17]++;
      if(s.charAt(i) == 's' || s.charAt(i) == 'S')
        charCountOutput[18]++;
      if(s.charAt(i) == 't' || s.charAt(i) == 'T')
        charCountOutput[19]++;
      if(s.charAt(i) == 'u' || s.charAt(i) == 'U')
        charCountOutput[20]++;
      if(s.charAt(i) == 'v' || s.charAt(i) == 'V')
        charCountOutput[21]++;
      if(s.charAt(i) == 'w' || s.charAt(i) == 'W')
        charCountOutput[22]++;
      if(s.charAt(i) == 'x' || s.charAt(i) == 'X')
        charCountOutput[23]++;
      if(s.charAt(i) == 'y' || s.charAt(i) == 'Y')
        charCountOutput[24]++;
      if(s.charAt(i) == 'z' || s.charAt(i) == 'Z')
        charCountOutput[25]++;

    }
    return charCountOutput;
  }

  /**
 * This method compares search word to character count.
 * <p>
 * @param  searchWordTemp
 * @return
 */
  private boolean inSearchWord(String word)
  {
    int[] searchWordTemp;
    searchWordTemp = new int[26];
    searchWordTemp = charCount(word);
    int[] searchWordTemp2;
    searchWordTemp2 = new int[26];
    searchWordTemp2 = charCount(wordGiven);
    boolean trueFalse = true;
    for (int i = 0; i < 26; i++)
    {
      if (searchWordTemp[i] > searchWordTemp2[i] )
      {
        trueFalse = false;
      }
    }
    return trueFalse;
  }

  /**
 * This method searches for words that are the same as the search word .
 * <p>
 * @param  searchWordTemp2.
 * @return
 */
  private boolean sameWord(String word)
  {
    int[] searchWordTemp;
    searchWordTemp = new int[26];
    searchWordTemp = charCount(word);
    int[] searchWordTemp2;
    searchWordTemp2 = new int[26];
    searchWordTemp2 = charCount(wordGiven);
    boolean trueFalse = true;
    for (int i = 0; i < 26;i++ )
    {
      if (searchWordTemp[i] != searchWordTemp2[i] )
      {
        trueFalse = false;
      }
    }
    return trueFalse;
  }

  /**
 * This method find the words that are missed.
 * <p>
 * Missed words are displayed at the bottom of the screen.
 * </p>
 * @param  missedWords.
 * @return
 */
  private void findMissedWords(String [] words)
  {
    String missedWords = "";
    for(int i = 0; i < dictionary.length; i++)
    {
      for (int j = 0; j < words.length; j++)
      {
        if(words[j].equals(dictionary[i]))
          missedWords = missedWords + words[i] + ", ";
      }
    }
    showText.append("\n" + missedWords);
  }
// main method
  public static void main( String[] args ) throws IOException
  {
      WordSearch frame = new WordSearch();
  }

}