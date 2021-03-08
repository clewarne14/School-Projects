/* Name: Charlie LeWarne and Maddie Herrmann
Assignment: Lab #05
Title: Space Needle
Course: CS 144
Class section: 2
Lab Section: 1
Semester: Spring 2019
Instructor: Dr.Cao
Date: 3-21-19
Sources consulted:
Known Bugs:
Program description: This program allows the user to insert a number for a scale
to produce a space needle that fits the users scale.
Creativity: anything extra that you added to the lab
Instructions: Enter a number for scale to see space needle.
*/
import java.util.Scanner;

public class SpaceNeedle
{
  public static void main(String[] args)
  {

    Scanner keyboard = new Scanner(System.in);

    int scaleSize;

    System.out.println("Enter scale size: ");
    scaleSize = keyboard.nextInt();

    //tip of needle
      for (int i = 1; i <= scaleSize; i++)
      {
        for (int space = 1; space <= (2 * scaleSize) + 10; space++)
        {
          System.out.print(" ");
        }
        System.out.println("||");
      }


  //needle round top
      for (int line = 1; line <= scaleSize; line++)
      {
        for (int space = 1; space <= (((2 * scaleSize) + 9) - (line * 3)) + 1; space++)
        {
          System.out.print(" ");
        }
        System.out.print("__/");
        for (int j = 1; j <= line-1; j++) {
          System.out.print(":::");
        }
        System.out.print("||");
        for (int j = 1; j <= line-1; j++) {
          System.out.print(":::");
        }
        System.out.println("\\__");
      }


    //needle round middle
      for (int space = 1; space <= (((2 * scaleSize) + 9) - (scaleSize * 3)) + 1; space++)
        System.out.print(" ");
      System.out.print("|");
      for (int i = 1; i <= scaleSize; i++)
      {
        System.out.print("\"\"\"\"\"\"");
      }
      System.out.println("|");

    //needle round base
      for (int line = 1; line <= scaleSize; line++)
      {
        for (int space = 1; space <= (scaleSize + (line * 2) - (scaleSize * 2 - 7)) + 1; space++)
        {
          System.out.print(" ");
        }
        System.out.print("\\_");
        for (int j = 1; j <= (((scaleSize*3)+1) - (line*2)); j++)
        {
          System.out.print("/\\");
        }
        System.out.println("_/");
      }



      //needle middle A

      for (int i = 1; i <= scaleSize; i++)
      {
        for (int space = 1; space <= (2 * scaleSize) + 10; space++)
        {
          System.out.print(" ");
        }
        System.out.println("||");
      }


    //needle middle B
      for (int line = 1; line <= scaleSize * scaleSize; line++)
      {
        for (int space = 1; space <= (2 * scaleSize) + 7; space++)
        {
          System.out.print(" ");
        }
        System.out.println("|%%||%%|");
      }


    //needle base
      for (int line = 1; line <= scaleSize; line++)
      {
        for (int space = 1; space <= (((2 * scaleSize) + 9) - (line * 3)) + 1; space++)
        {
          System.out.print(" ");
        }
        System.out.print("__/");
        for (int j = 1; j <= line-1; j++) {
          System.out.print(":::");
        }
        System.out.print("||");
        for (int j = 1; j <= line-1; j++) {
          System.out.print(":::");
        }
        System.out.println("\\__");
      }

    //needle bottom
      for (int space = 1; space <= (((2 * scaleSize) + 9) - (scaleSize * 3)) + 1; space++)
        System.out.print(" ");
      System.out.print("|");
      for (int i = 1; i <= scaleSize; i++)
      {
        System.out.print("\"\"\"\"\"\"");
      }
      System.out.println("|");
  }
}
