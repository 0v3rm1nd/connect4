import java.util.ArrayList;
import java.util.List;

public class Group34UberMegaGameLogic implements IGameLogic {
    private int x = 0;
    private int y = 0;
    private int playerID;

    private int opponentID;
    private int cutoffValue;
    private int turnsPlayed;
    private int[][] board;

    public Group34UberMegaGameLogic() {

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

    /*will evaluate the available moves and pick the most optimal one based on score given by an evaluation function*/
    public int decideNextMove() {
        //pick middle column if it starts first
        if (playerID == 1 && turnsPlayed == 0) {
            return x / 2;
        }

        //act as max player
        List<Action> actions = new ArrayList<Action>();
        //will represent all available actions for the current state of the game
        List<Integer> availableActions = HelperFunctions.availableActions(board);
        //get the score for every possible action
        for (Integer action : availableActions) {
            int[][] newState = resultState(board, action, playerID);
            actions.add(new Action(action, minValue(newState, 0, Integer.MIN_VALUE, Integer.MAX_VALUE)));
        }
        //find the max score among the actions
        Action bestAction = new Action(availableActions.get(0), Integer.MIN_VALUE);
        for (Action action : actions) {
            if (action.getScore() > bestAction.getScore()) {
                bestAction = action;
            }
        }

        return bestAction.getAction();
    }

    /*max portion of minimax*/
    private int maxValue(int[][] currentState, int d, int alpha, int beta) {
        //check if the current state is terminal
        Winner winner = gameFinished(currentState);
        if (!winner.equals(Winner.NOT_FINISHED)) {
            return utility(winner);
        }
        //will give back the eval score when the cutoffValue is reached
        if (d == cutoffValue) {
            return EvalFunc.eval(currentState, playerID, x, y) - EvalFunc.eval(currentState, opponentID, x, y);
        } else {
            //else we will execute the regular minimax with alpha-beta pruning
            for (Integer action : HelperFunctions.availableActions(currentState)) {
                int[][] newState = resultState(currentState, action, playerID);
                int score = minValue(newState, d + 1, alpha, beta);
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

    /*min portion of minimax*/
    private int minValue(int[][] currentState, int d, int alpha, int beta) {
        //check if the current state is terminal
        Winner winner = gameFinished(currentState);
        if (!winner.equals(Winner.NOT_FINISHED)) {
            return utility(winner);
        }
        //will give back the eval score when the cutoffValue is reached
        if (d == cutoffValue) {
            return EvalFunc.eval(currentState, playerID, x, y) - EvalFunc.eval(currentState, opponentID, x, y);
        } else {
            for (Integer action : HelperFunctions.availableActions(currentState)) {
                //else we will execute the regular minimax with alpha-beta pruning
                int[][] newState = resultState(currentState, action, opponentID);
                int score = maxValue(newState, d + 1, alpha, beta);
                if (score < beta) {
                    beta = score;
                }
                if (alpha >= beta) {
                    break;
                }
            }
            return beta;

        }
    }

    /*returns max int if player 1 wins and min int if player 2 wins*/
    private int utility(Winner winner) {
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

    /*return a new state with the action taken by the player*/
    private int[][] resultState(int[][] currentState, int column, int player) {
        int[][] newState = new int[x][y];
        for (int col = 0; col < x; col++) {
            for (int row = 0; row < y; row++) {
                newState[col][row] = currentState[col][row];
            }
        }
        updateBoard(newState, column, player);
        return newState;
    }

}
