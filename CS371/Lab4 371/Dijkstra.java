/**
 * Name: Charlie LeWarne
 * Assignment: Lab 4
 * Course: CSCI 371
 * Date: November 14, 2021
 * Sources consulted: Cody Uehara, javadocs, https://stackoverflow.com/questions/4208655/empty-an-array-in-java-processing
 * Program Instructions: Program is run entirely in the constructor. Call the constructor with the file name as a parameter and the results will be printed out.
 * Known Bugs: None
 * Running Time: O(n^3)
 */

import java.io.*;
import java.util.*;

public class Dijkstra{
    public int totalProblems;
    public int totalInt;
    public int totalEsc = 0;
    public int col;
    public int row;
    public char[][] grid;
    public int[][] valGrid;
    public int[] entStart = new int[2];
    public Map<Character,Integer> enemyShips = new TreeMap<Character, Integer>();
    public ArrayList<Integer[]> posQ = new ArrayList<Integer[]>();
    public ArrayList<Integer> valQ = new ArrayList<Integer>();
    public int totalMove;
    public ArrayList<Integer[]> comQ = new ArrayList<Integer[]>();
    public int[][] finalGrid;
    public int output;

    public void finalClear(){
        col = 0;
        row = 0;
        posQ.clear();
        valQ.clear();
        comQ.clear();
        enemyShips.clear();
        totalMove = 0;
    }

    public static void main(String[] args) throws IOException{
        Dijkstra d = new Dijkstra("data.txt");
    }
    public Dijkstra(String filename) throws IOException{
            File file = new File(filename);
            Scanner fileScan = new Scanner(file);
            this.totalProblems = fileScan.nextInt();
            for(int i = 0; i < this.totalProblems; i++){
                this.totalInt = fileScan.nextInt();
                this.col = fileScan.nextInt();
                this.row = fileScan.nextInt();
                this.grid = new char[this.row][this.col];
                for(int j = 0; j < this.totalInt; j++){
                    this.enemyShips.put(fileScan.next().charAt(0), fileScan.nextInt());
                }
                fileScan.nextLine();
                for(int j = 0; j < this.row; j++){
                    String s = fileScan.nextLine();
                    for(int k = 0; k < s.length(); k++){
                        this.grid[j][k] = s.charAt(k);
                    }
                }
                createValGrid();
                System.out.println(findPathOut(this.entStart[1], this.entStart[0]));
            }
            fileScan.close();
        
    }
    public void createValGrid(){
        this.valGrid = new int[this.row][this.col];
        this.finalGrid = new int[this.row][this.col];
        for(int i = 0; i < this.row; i++){
            for(int j = 0; j < this.col; j++){
                if(this.grid[i][j] == 'E'){
                    this.valGrid[i][j] = 0;
                    this.entStart[0] = i;
                    this.entStart[1] = j;
                    this.finalGrid[i][j] = 0;
                }else{
                    this.valGrid[i][j] = this.enemyShips.get(this.grid[i][j]);
                    this.finalGrid[i][j] = Integer.MAX_VALUE;
                }
            }
        }
    }
    public int findPathOut(int col, int row){
        boolean complete = false;
        int comp = -1;
        this.valQ.add(valGrid[row][col]);
        Integer[] tempArr = new Integer[2];
        tempArr[0] = row;
        tempArr[1] = col;
        this.posQ.add(tempArr);
        grid[row][col] = ' ';
        while(!complete){
            int smallest = Integer.MAX_VALUE;
            int smallPos = 0;
            for(int i = 0; i < comQ.size(); i++){
                if(isEdge(comQ.get(i)[0],comQ.get(i)[1])){
                    complete = true;
                    comp = i;
                }
            }
            if(complete){
                output = finalGrid[comQ.get(comp)[0]][comQ.get(comp)[1]];
                finalClear();
                return output;
            }
            for(int i = 0; i < posQ.size(); i++){
                int currNum = finalGrid[posQ.get(i)[0]][posQ.get(i)[1]];
                tempArr[0] = posQ.get(i)[0];
                tempArr[1] = posQ.get(i)[1];
                if(currNum < smallest && valGrid[posQ.get(i)[0]][posQ.get(i)[1]] != Integer.MIN_VALUE ){
                    smallest = currNum;
                    smallPos = i;
                }
            }
            if(smallest != Integer.MAX_VALUE){
                valGrid[posQ.get(smallPos)[0]][posQ.get(smallPos)[1]] = Integer.MIN_VALUE;
            }
            Integer[] tempArr2 = new Integer[2];
            tempArr2[0] = posQ.get(smallPos)[0];
            tempArr2[1] = posQ.get(smallPos)[1];
            comQ.add(tempArr2);
            checkAround(posQ.get(smallPos)[0],posQ.get(smallPos)[1]);
        }


        return 1;
    }
    public int checkAround(int row, int col){
        if(isEdge(row,col)){
            return this.totalEsc;
        }
        if(grid[row][col-1] != ' '){
            this.valQ.add(valGrid[row][col-1]);
            Integer[] temp = new Integer[2];
            temp[0] = row;
            temp[1] = col-1;
            this.posQ.add(temp);
            grid[row][col-1] = ' ';
            if(finalGrid[row][col] + valGrid[row][col-1] < finalGrid[row][col-1]){
                finalGrid[row][col-1] = finalGrid[row][col] + valGrid[row][col-1];
            }
        }
        if(grid[row][col+1] != ' '){
            this.valQ.add(valGrid[row][col+1]);
            Integer[] temp = new Integer[2];
            temp[0] = row;
            temp[1] = col+1;
            this.posQ.add(temp);
            grid[row][col+1] = ' ';
            if(finalGrid[row][col] + valGrid[row][col+1] < finalGrid[row][col+1]){
                finalGrid[row][col+1] = finalGrid[row][col] + valGrid[row][col+1];
            }
        }
        if(grid[row-1][col] != ' '){
            this.valQ.add(valGrid[row-1][col]);
            Integer[] temp = new Integer[2];
            temp[0] = row-1;
            temp[1] = col;
            this.posQ.add(temp);
            grid[row-1][col] = ' ';
            if(finalGrid[row][col] + valGrid[row-1][col] < finalGrid[row-1][col]){
                finalGrid[row-1][col] = finalGrid[row][col] + valGrid[row-1][col];
            }
        }
        if(grid[row+1][col] != ' '){
            this.valQ.add(valGrid[row+1][col]);
            Integer[] temp = new Integer[2];
            temp[0] = row+1;
            temp[1] = col;
            this.posQ.add(temp);
            grid[row+1][col] = ' ';
            if(finalGrid[row+1][col] + valGrid[row+1][col] < finalGrid[row+1][col]){
                finalGrid[row+1][col] = finalGrid[row][col] + valGrid[row+1][col];
            }
        }

        return -1;
    }
    public boolean isEdge(int row, int col){
        if(row == 0 || col == 0 || row == this.row-1 || col == this.col - 1){
            return true;
        }
        return false;
    }
}