// Lab08vst.java
// This is the knights tour lab
// it makes a knight go around on chess board on every single spot without repeat
// Evin Lodder 4/23

import java.text.DecimalFormat;

public class Main
{
    public static int row = 0;
    public static int col = 0;
    public static int moves = 1;

    public static void main (String[] args)
    {
        int[][] board = new int[8][8];
        startBoard(board);
        for (int k = 2; k <= 64; k++)
        {
            nextMove(board, k);
        }
        showBoard(board);
        System.out.println("Completed " + moves + " moves");
    }


    // Initializes the 2D board array with 0s and 1 at the top-left.
    public static void startBoard(int[][] brd)
    {
        for(int i = 0; i < brd.length; i++) {
            for(int f = 0; f < brd[i].length; f++) {
                if(i == 0 && f == 0) {
                    brd[i][f] = 1;
                } else {
                    brd[i][f] = 0;
                }
            }
        }
    }

    // Displays a matrix of board values using the 2-digit format.
    public static void showBoard(int[][] brd)
    {
        DecimalFormat twoDigits = new DecimalFormat("00");
        for (int r = 0; r <=7; r++)
        {
            for (int c = 0; c <= 7; c++)
            {
                System.out.print(twoDigits.format(brd[r][c]) + "  ");
            }
            System.out.println();
        }
        System.out.println();

    }

    // Used by method nextMove to see if a board location was visited.
    // returns true if the location was not visited
    // returns false if the location was visited OR it's outside of board
    // note: this assumes brd is initialized because it indexes into 0-elem
    public static boolean checkVisit(int[][] brd, int r, int c)
    {
        if(r >= brd.length || r < 0) {
            return false;
        }
        else if(c >= brd[0].length || c < 0) {
            return false;
        }
        return brd[r][c] == 0;
    }

    public static int countPossibleMoves(int[][] brd, int row, int col) {
        int count = 0;
        //check all possible moves
        int[] rows = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] columns = {-1, 1, -2, 2, -2, 2, -1, 1};
        for(int i = 0; i < rows.length; i++) {
            //if unvisited, add to count
            if(checkVisit(brd, row + rows[i], col + columns[i])) {
                count += 1;
            }
        }
        return count;
    }
    // Finds a knight-type move to the next location, if possible.
    public static void nextMove(int[][] brd, int currentNumber)
    {
        //first, we have to find current location
        int row = -1;
        int col = -1;
        boolean found = false;
        for(int i = 0; i < brd.length; i++) {
            if(found) break;
            for(int j = 0; j < brd[i].length; j++) {
                if(brd[i][j] == currentNumber - 1) {
                    row = i;
                    col = j;
                    found = true;
                    break;
                }
            }
        }
        if(row < 0 || col < 0) return; //we have been cut off
        // we are going to use warnsdorff's Rule
        // for each square, calculate the amount of possible moves from each of the knight's possible moves
        // choose the square with the LOWEST possible moves
        // if tie, choose the one furthest from center of board

        //all possible moves for a knight
        int[] rows = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] columns = {-1, 1, -2, 2, -2, 2, -1, 1};
        int[] minMove = new int[2]; //mm[0] will be row, mm[1] will be col
        int min = 8;
        for(int i = 0; i < rows.length; i++) {
            //first, check if move is possible
            if(row + rows[i] > 7 || row + rows[i] < 0 || col + columns[i] > 7 || col + columns[i] < 0) {
                continue;
            }
            if(!checkVisit(brd, row + rows[i], col + columns[i])) {
                continue;
            }
            //count moves for this position
            int possibleMoves = countPossibleMoves(brd, row + rows[i], col + columns[i]);
            if(possibleMoves < min) {
                minMove[0] = rows[i];
                minMove[1] = columns[i];
                min = possibleMoves;
            } else if(possibleMoves == min) {
                //here is where we calculate distance
                double distanceOfCurrentMin = Math.sqrt(Math.pow(4.5 - row + minMove[0], 2) + Math.pow(4.5 - col + minMove[1], 2));
                double distanceOfNewMin = Math.sqrt(Math.pow(4.5 - row + rows[i], 2) + Math.pow(4.5 - col + columns[i], 2));
                if(distanceOfNewMin > distanceOfCurrentMin) {
                    minMove[0] = rows[i];
                    minMove[1] = columns[i];
                }
            }
        }
        //set the lowest count position to the current num
        brd[row + minMove[0]][col + minMove[1]] = currentNumber;
        moves++;
    }
}

