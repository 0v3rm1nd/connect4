import java.util.ArrayList;
import java.util.List;

public class UberMegaGameLogic implements IGameLogic {
    private int x = 0;
    private int y = 0;
    private int playerID;

    private int opponentID;
    private int cutoffValue;
    private int turnsPlayed;
    private int[][] board;

    public UberMegaGameLogic() {

    }

    /*initialize gameboard size and player ids*/
    public void initializeGame(int x, int y, int playerID) {
        //Initalizes local gameboard as well as player/opponent IDs
        this.x = x;
        this.y = y;
        this.playerID = playerID;
        this.opponentID = playerID == 1 ? 2 : 1;
        this.cutoffValue = 8;
        board = new int[x][y];
    }

    public Winner gameFinished() {
        return gameFinished(board);
    }

    /*determine if a state is terminal minimax state*/
    public Winner gameFinished(int[][] board) {
        for (int col = 0; col < x; col++) {
            for (int row = 0; row < y; row++) {
                if (board[col][row] != 0) {
                    Winner winner = HelperFunctions.check4Connected(x, y, board, col, row);
                    if (!winner.equals(Winner.NOT_FINISHED)) {
//                        System.out.println("Player: " + winner + " wins on col: " + col + " row: " + row);
                        return winner;
                    }
                }
            }
        }
        return Winner.NOT_FINISHED;
    }

    /*insert a coin into the board*/
    public void insertCoin(int column, int playerID) {

        if (updateBoard(board, column, playerID)) {
            turnsPlayed++;
            //used only for troubleshooting
            HelperFunctions.printBoard(HelperFunctions.scoreBoard(board, playerID, x, y), x, y);
            //System.out.println("column " + column + " playerID " + playerID);
        }
    }

    /*same function as in the GUI class*/
    private boolean updateBoard(int[][] board, int col, int player) {
        if (col == -1) {
            return false;
        }
        if (board[col][0] != 0) {
            return false;
        }
        int r = board[col].length - 1;
        while (board[col][r] != 0) r--;
        board[col][r] = player;
        return true;
    }


    public int decideNextMove() {
        //pick middle column if it starts first
        if (playerID == 1 && turnsPlayed == 0) {
            return x / 2;
        }
        //timer for troubleshooting purposes to verify we don't exceed the available time limits
        TimeCheck timer = new TimeCheck();
        timer.play();

        //This GameLogic will always act as the Maximizing Player.
        List<Action> actions = new ArrayList<Action>();
        //All Availble actions for the current game state.
        List<Integer> availableActions = availableActions(board);
        //Gets the possible score for every possible action
        for (Integer action : availableActions) {
            int[][] newState = result(board, action, playerID);
            actions.add(new Action(action, minValue(newState, action, 0, Integer.MIN_VALUE, Integer.MAX_VALUE)));
        }
        //Finds max score for the possible actions.
        Action bestAction = new Action(availableActions.get(0), Integer.MIN_VALUE);
        for (Action action : actions) {
            if (action.getScore() > bestAction.getScore()) {
                bestAction = action;
            }
        }
        System.out.println("Choosing column: " + bestAction.getAction() + " with score: " + bestAction.getScore());
        System.out.println("Decision took: " + timer.check());
        return bestAction.getAction();
    }

    private int maxValue(int[][] state, int a, int d, int alpha, int beta) {
        //Checks for terminal game state
        Winner winner = gameFinished(state);
        if (!winner.equals(Winner.NOT_FINISHED)) {
            return utility(winner);
        }
        //Returns evaluation score when at cutoff depth
        if (d == cutoffValue) {
            return EvalFunc.eval(state, playerID, x, y) - EvalFunc.eval(state, opponentID, x, y);
        } else {
            //Iterates all remaining for the GameLogic's player
            for (Integer action : availableActions(state)) {
                int[][] newState = result(state, action, playerID);
                int score = minValue(newState, action, d + 1, alpha, beta);
                if (score > alpha) {
                    alpha = score;
                }
                if (alpha >= beta) {
                    break;
                }
            }
            return alpha;
        }
    }

    private int minValue(int[][] state, int a, int d, int alpha, int beta) {
        //Checks for terminal game state
        Winner winner = gameFinished(state);
        if (!winner.equals(Winner.NOT_FINISHED)) {
            return utility(winner);
        }
        //Returns evaluation score when at cutoff depth
        if (d == cutoffValue) {
            return EvalFunc.eval(state, playerID, x, y) - EvalFunc.eval(state, opponentID, x, y);
        } else {
            for (Integer action : availableActions(state)) {
                //Iterates all remaining for the GameLogic's opponent
                int[][] newState = result(state, action, opponentID);
                int score = maxValue(newState, action, d + 1, alpha, beta);
                if (score < beta) {
                    beta = score;
                }
                if (alpha >= beta) {
                    break;
                }
            }
            //System.out.println(bestAction);
            return beta;

        }
    }

    private int utility(Winner winner) {
        //Returns max integer if the GameLogic's Player wins or min integer if it's opponent wins
        if (winner.equals(Winner.PLAYER1)) {
            if (playerID == 1) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        } else if (winner.equals(Winner.PLAYER2)) {
            if (playerID == 2) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        } else {
            return 0;
        }

    }

    //Returns a list of integers representing indexes of columns that are still open
    private List<Integer> availableActions(int[][] board) {
        List<Integer> actions = new ArrayList<Integer>();
        for (int col = 0; col < board.length; col++) {
            if (board[col][0] == 0) {
                actions.add(col);
            }
        }
        return actions;
    }

    //Returns a new state with the an action taken by a player.
    private int[][] result(int[][] state, int column, int player) {
        int[][] newState = new int[x][y];
        for (int col = 0; col < x; col++) {
            for (int row = 0; row < y; row++) {
                newState[col][row] = state[col][row];
            }
        }
        updateBoard(newState, column, player);
        return newState;
    }

}
