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
