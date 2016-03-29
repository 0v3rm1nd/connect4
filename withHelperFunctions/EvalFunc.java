/**
 * Created by Mind on 3/11/2016.
 */
public class EvalFunc {
    /*evaluation function, scores every row, column, diag*/
    public static int eval(int[][] state, int playerID, int x, int y) {
        int[][] scoreBoard = new int[x][y];
        int score = 0;
        //get the score for every column
        for (int col = 0; col < x; col++) {
            countVertical(state, scoreBoard, col, playerID, y);
        }
        //get the score for every row
        for (int row = 0; row < y; row++) {
            countHorizontal(state, scoreBoard, row, playerID, y);
        }

        //get the score for right diag for rows y-4 on Column 0
        //get the score for left diag for rows y-4 on Column x-1
        for (int row = y - 1; row > y - 4; row--) {
            countDiagonalRight(state, scoreBoard, 0, row, playerID, x);
            countDiagonalLeft(state, scoreBoard, x - 1, row, playerID);
        }
        //get remaining right diag score from row y - 1 (Bottom row) to Column x - 4
        for (int col = 1; col < x - 4; col++) {
            countDiagonalRight(state, scoreBoard, col, y - 1, playerID, x);
        }
        //get the score for the remaining left diag from row y - 1 (Bottom row) to Column x - 4
        for (int col = x - 1; col > 3; col--) {
            countDiagonalLeft(state, scoreBoard, col, y - 1, playerID);
        }

        //sum the final score
        for (int col = 0; col < x; col++) {
            for (int row = 0; row < y; row++) {
                score += scoreBoard[col][row];
            }
        }
        return score;
    }

    public static void countDiagonalRight(int[][] state, int[][] scoreBoard, int col, int row, int player, int x) {
        int currentRow = row;
        int currentColumn = col;
//        int score = 0;
        //Represents distance from wall to player, or distance between two coins by the same player
        int distance = 0;
        //Represents Connected coins
        int connected = 0;
        //Loop until it hits the top row of the board
        while (currentRow >= 0) {
            //Or hits right wall
            if (currentColumn > x - 1) {
                break;
            }
            int cellValue = state[currentColumn][currentRow];
            //If cell is empty
            if (cellValue == 0) {
                distance++;
                //If gap is too large
                if (distance > 2) {
                    distance = 0;
                }
                //If it interrupts connected coins, it scores last visited cell
                if (connected > 0) {
                    scoreBoard[currentColumn - 1][currentRow + 1] += Math.pow(2.0, connected);
                    connected = 0;
                }
            }
            //If the cell is occupied by current player
            if (cellValue == player) {
                if (distance > 1) {
                    scoreBoard[currentColumn][currentRow] += 8 / (int) Math.pow(2.0, distance);
                }
                connected++;
                //If it hits maximum connected it scores those
                if (connected == 3) {
                    scoreBoard[currentColumn][currentRow] += Math.pow(2.0, connected);
                }
                distance = 0;
            }
            //If cell is occupied by opponent
            else {
                connected = 0;
                distance = 0;
            }
            //Move one row up the board and a column to the right
            currentColumn++;
            currentRow--;
        }

//        return score;
    }

    public static void countDiagonalLeft(int[][] state, int[][] scoreBoard, int col, int row, int player) {
        int currentRow = row;
        int currentColumn = col;
//        int score = 0;
        int distance = 0;
        int connected = 0;

        //Loop till it hits the top row of the board
        while (currentRow >= 0) {
            //Or it hits the left most column
            if (currentColumn <= 0) {
                break;
            }

            //Same procedure as in countDiagonalRight
            int cellValue = state[currentColumn][currentRow];
            if (cellValue == 0) {
                distance++;
                if (distance > 2) {
                    distance = 0;
                }
                if (connected > 0) {
                    scoreBoard[currentColumn + 1][currentRow + 1] += Math.pow(2.0, connected);
                    connected = 0;
                }
            }
            if (cellValue == player) {
                if (distance > 1) {
                    scoreBoard[currentColumn][currentRow] += 8 / (int) Math.pow(2.0, distance);
                }
                connected++;
                if (connected == 3) {
                    scoreBoard[currentColumn][currentRow] += Math.pow(2.0, connected);
                }
                distance = 0;
            } else {
                connected = 0;
                distance = 0;
            }
            currentColumn--;
            currentRow--;
        }

//        return score;
    }

    public static void countVertical(int[][] state, int[][] scoreBoard, int col, int player, int y) {
//        int score = 0;
        int distance = 0;
        int connected = 0;

        for (int row = 0; row < y; row++) {
            int cellValue = state[col][row];
            //Same procedure as in countDiagonalRight
            if (cellValue == 0) {
                distance++;
                if (distance == 3) {
                    distance = 0;
                    connected = 0;
                }
                if (connected > 0) {
                    scoreBoard[col][row - 1] += Math.pow(2.0, connected);
                    connected = 0;
                }
            }
            if (cellValue == player) {
                if (distance > 1) {
                    scoreBoard[col][row] += 8 / (int) Math.pow(2.0, distance);
                }
                connected++;
                if (connected == 3) {
                    scoreBoard[col][row] += Math.pow(2.0, connected);
                }
                distance = 0;
            } else {
                connected = 0;
                distance = 0;
            }
        }
//        return score;
    }

    public static void countHorizontal(int[][] state, int[][] scoreBoard, int row, int player, int y) {
//        int score = 0;
        int distance = 0;
        int connected = 0;


        for (int col = 0; col < y; col++) {
            //Same procedure as in countDiagonalRight
            int cellValue = state[col][row];

            if (cellValue == 0) {
                distance++;
                connected = 0;
                if (distance > 2) {
                    distance = 0;
                }
                if (connected > 0) {
                    scoreBoard[col - 1][row] += Math.pow(2.0, connected);
                    connected = 0;
                }
            }
            if (cellValue == player) {
                if (distance > 1) {
                    scoreBoard[col][row] += 8 / (int) Math.pow(2.0, distance);
                }

                connected++;
                if (connected == 3) {

                    scoreBoard[col][row] += Math.pow(2.0, connected);
                }
                distance = 0;
            } else {
                connected = 0;
                distance = 0;
            }

        }
//        return score;
    }

}
