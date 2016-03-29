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
            scoreColumn(state, scoreBoard, col, playerID, y);
        }
        //get the score for every row
        for (int row = 0; row < y; row++) {
            scoreRow(state, scoreBoard, row, playerID, y);
        }

        //get the score for right diag for rows y-4 on Column 0
        //get the score for left diag for rows y-4 on Column x-1
        for (int row = y - 1; row > y - 4; row--) {
            scoreDiagRight(state, scoreBoard, 0, row, playerID, x);
            scoreDiagLeft(state, scoreBoard, x - 1, row, playerID);
        }
        //get remaining right diag score from row y - 1 (Bottom row) to Column x - 4
        for (int col = 1; col < x - 4; col++) {
            scoreDiagRight(state, scoreBoard, col, y - 1, playerID, x);
        }
        //get the score for the remaining left diag from row y - 1 (Bottom row) to Column x - 4
        for (int col = x - 1; col > 3; col--) {
            scoreDiagLeft(state, scoreBoard, col, y - 1, playerID);
        }

        //sum the final score
        for (int col = 0; col < x; col++) {
            for (int row = 0; row < y; row++) {
                score += scoreBoard[col][row];
            }
        }
        return score;
    }

    /*get score diag right*/
    public static void scoreDiagRight(int[][] state, int[][] scoreBoard, int col, int row, int player, int x) {
        int currentColumn = col;
        int currentRow = row;
        //will represent the distance between two coins or the distance from the wall to the player
        int distance = 0;
        //number of connected coins
        int connectedCoins = 0;
        //loop until we hit the top row
        while (currentRow >= 0) {
            //or the right wall
            if (currentColumn > x - 1) {
                break;
            }
            int value = state[currentColumn][currentRow];
            //if the cell value is empty
            if (value == 0) {
                distance++;
                //if we have a big distance
                if (distance > 2) {
                    distance = 0;
                }
                //if we interrupt connected coins, it scores last visited cell
                if (connectedCoins > 0) {
                    scoreBoard[currentColumn - 1][currentRow + 1] += Math.pow(2.0, connectedCoins);
                    connectedCoins = 0;
                }
            }
            //if the cell is filled by the current player
            if (value == player) {
                if (distance > 1) {
                    scoreBoard[currentColumn][currentRow] += 8 / (int) Math.pow(2.0, distance);
                }
                connectedCoins++;
                //if we can hit 4 connected we do it
                if (connectedCoins == 3) {
                    scoreBoard[currentColumn][currentRow] += Math.pow(2.0, connectedCoins);
                }
                distance = 0;
            }
            //if the cell is occupied by an oponent
            else {
                connectedCoins = 0;
                distance = 0;
            }
            //move a row up and a column to the right
            currentColumn++;
            currentRow--;
        }

    }

    /*get score for left diag*/
    public static void scoreDiagLeft(int[][] state, int[][] scoreBoard, int col, int row, int player) {
        int currentRow = row;
        int currentColumn = col;
        int distance = 0;
        int connectedCoins = 0;
        //loop until we hit the top row
        while (currentRow >= 0) {
            //or it hits the left most column
            if (currentColumn <= 0) {
                break;
            }

            //the procedure is the same as in scoreDiagRight()
            int value = state[currentColumn][currentRow];
            if (value == 0) {
                distance++;
                if (distance > 2) {
                    distance = 0;
                }
                if (connectedCoins > 0) {
                    scoreBoard[currentColumn + 1][currentRow + 1] += Math.pow(2.0, connectedCoins);
                    connectedCoins = 0;
                }
            }
            if (value == player) {
                if (distance > 1) {
                    scoreBoard[currentColumn][currentRow] += 8 / (int) Math.pow(2.0, distance);
                }
                connectedCoins++;
                if (connectedCoins == 3) {
                    scoreBoard[currentColumn][currentRow] += Math.pow(2.0, connectedCoins);
                }
                distance = 0;
            } else {
                connectedCoins = 0;
                distance = 0;
            }
            currentColumn--;
            currentRow--;
        }

    }

    /*get score for every column*/
    public static void scoreColumn(int[][] state, int[][] scoreBoard, int col, int player, int y) {
        int distance = 0;
        int connectedCoins = 0;

        for (int row = 0; row < y; row++) {
            int value = state[col][row];
            //the procedure is the same as in scoreDiagRight()
            if (value == 0) {
                distance++;
                if (distance == 3) {
                    distance = 0;
                    connectedCoins = 0;
                }
                if (connectedCoins > 0) {
                    scoreBoard[col][row - 1] += Math.pow(2.0, connectedCoins);
                    connectedCoins = 0;
                }
            }
            if (value == player) {
                if (distance > 1) {
                    scoreBoard[col][row] += 8 / (int) Math.pow(2.0, distance);
                }
                connectedCoins++;
                if (connectedCoins == 3) {
                    scoreBoard[col][row] += Math.pow(2.0, connectedCoins);
                }
                distance = 0;
            } else {
                connectedCoins = 0;
                distance = 0;
            }
        }
    }

    /*get score for every row*/
    public static void scoreRow(int[][] state, int[][] scoreBoard, int row, int player, int y) {
        int distance = 0;
        int connectedCoins = 0;


        for (int col = 0; col < y; col++) {
            //the procedure is the same as in scoreDiagRight()
            int value = state[col][row];

            if (value == 0) {
                distance++;
                connectedCoins = 0;
                if (distance > 2) {
                    distance = 0;
                }
                if (connectedCoins > 0) {
                    scoreBoard[col - 1][row] += Math.pow(2.0, connectedCoins);
                    connectedCoins = 0;
                }
            }
            if (value == player) {
                if (distance > 1) {
                    scoreBoard[col][row] += 8 / (int) Math.pow(2.0, distance);
                }

                connectedCoins++;
                if (connectedCoins == 3) {

                    scoreBoard[col][row] += Math.pow(2.0, connectedCoins);
                }
                distance = 0;
            } else {
                connectedCoins = 0;
                distance = 0;
            }

        }
    }

}
