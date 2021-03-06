import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mind on 3/11/2016.
 */

/*check if there are 4 dots connected*/
public class HelperFunctions {

    public static IGameLogic.Winner check4Connected(int x, int y, int[][] board, int col, int row) {
        /*check if there are 4 connected vertically*/
        if (row <= y - 4) {
            int count = 0;
            int current = board[col][row];
            for (int i = 0; i < 4; i++) {
                if (board[col][row + i] == current) {
                    count++;
                } else
                    break;
            }
            if (count == 4) {
                return current == 1 ? IGameLogic.Winner.PLAYER1 : IGameLogic.Winner.PLAYER2;
            }
        }
        /*check if there are 4 connected horizontally*/
        if (col <= x - 4) {
            int count = 0;
            int current = board[col][row];
            for (int i = 0; i < 4; i++) {
                if (board[col + i][row] == current) {
                    count++;
                } else
                    break;
            }
            if (count == 4) {
                return current == 1 ? IGameLogic.Winner.PLAYER1 : IGameLogic.Winner.PLAYER2;
            }
        }
        /*check if there are 4 connected on the right diag*/
        if (col <= x - 4 && row <= y - 4) {
            int count = 0;
            int current = board[col][row];
            for (int i = 0; i < 4; i++) {
                if (board[col + i][row + i] == current) {
                    count++;
                } else
                    break;
            }
            if (count == 4) {
                return current == 1 ? IGameLogic.Winner.PLAYER1 : IGameLogic.Winner.PLAYER2;
            }
        }
        /*check if there are 4 connected on the left diag*/
        if (col >= x - 4 && row <= y - 4) {
            int count = 0;
            int current = board[col][row];
            for (int i = 0; i < 4; i++) {
                if (board[col - i][row + i] == current) {
                    count++;
                } else
                    break;
            }
            if (count == 4) {
                return current == 1 ? IGameLogic.Winner.PLAYER1 : IGameLogic.Winner.PLAYER2;
            }
        }
        return IGameLogic.Winner.NOT_FINISHED;

    }

    /*prints the whole board to the console*/
    public static void printBoard(int[][] board, int x, int y) {
        for (int row = 0; row < y; row++) {
            for (int col = 0; col < x; col++) {
                System.out.print(board[col][row] + " ");
            }
            System.out.println("\n");
        }
        System.out.print("\n------------------\n");
    }

    /*used for debugging/troubleshooting, the same as eval function but returns the whole matrix which can be afterwards printed via print*/
    public static int[][] scoreBoard(int[][] state, int playerID, int x, int y) {
        int[][] scoreBoard = new int[x][y];

        for (int col = 0; col < x; col++) {
            EvalFunc.countVertical(state, scoreBoard, col, playerID, y);
        }
        for (int row = 0; row < y; row++) {
            EvalFunc.countHorizontal(state, scoreBoard, row, playerID, y);
        }

        for (int row = y - 1; row > y - 4; row--) {
            EvalFunc.countDiagonalRight(state, scoreBoard, 0, row, playerID, x);
            EvalFunc.countDiagonalLeft(state, scoreBoard, x - 1, row, playerID);
        }
        for (int col = 1; col < x - 4; col++) {
            EvalFunc.countDiagonalRight(state, scoreBoard, col, y - 1, playerID, x);
        }
        for (int col = x - 1; col > 3; col--) {
            EvalFunc.countDiagonalLeft(state, scoreBoard, col, y - 1, playerID);
        }
        //Remaining Column Diagonals from row y
//        System.out.println("Eval score for player " + player + " is: " + total + " for board:");
//        printBoard(state);
        //System.out.println("Score: " + total + " For " + player);
        return scoreBoard;
    }


    /*return the indexes of the columns that are empty*/
    public static List<Integer> availableActions(int[][] board) {
        List<Integer> actions = new ArrayList<Integer>();
        for (int col = 0; col < board.length; col++) {
            if (board[col][0] == 0) {
                actions.add(col);
            }
        }
        return actions;
    }
}
