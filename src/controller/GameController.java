package controller;

import model.Tile;
import java.util.Random;

/**
 * class that holds the whole
 * business logic of the game;
 * controls the moves on the gameboard
 * given the user's choice of moves
 */
public class GameController {

    private static int tilesNumber = 4;
    private Tile[][] board = new Tile[tilesNumber][tilesNumber];

    public Tile[][] getBoard() {
        return board;
    }

    private Random random = new Random();

    private final static int winCase = 2048;
    private int highestTile = 0;
    private int score = 0;

    public int getScore(){
        return this.score;
    }

    private boolean checkingAvailableMoves = false;

    private State gameState;

    public String getGameState(){
        return String.valueOf(this.gameState);
    }

    private enum State{
        won, over
    }

    public GameController(){
        addRandomTile();
        addRandomTile();
    }

    //method adds a tile to the gameboard on an empty random position
    private void addRandomTile(){
        int column = (int)(Math.random() * 4);
        int row = (int)(Math.random() * 4);
        while(board[row][column] != null){
            column = (int)(Math.random() * 4);
            row = (int)(Math.random() * 4);
        }
        int val = random.nextInt(5) == 0 ? 4 : 2;
        board[row][column] = new Tile(val);
    }


    //method pushes tiles to the edge in direction up
    //and merges the identic tiles
    public boolean moveUp() {
        boolean moved = false;

        for (int i = 0; i < tilesNumber; i++) {
            for (int j = 1; j < tilesNumber; j++) {

                int currRow = j;
                int currCol = i;
                int prevRow = j - 1;
                int prevCol = i;

                if (board[currRow][currCol] == null)
                    continue;


                while (prevRow >= 0 && prevRow < tilesNumber && prevCol >= 0 && prevCol < tilesNumber) {

                    Tile prev = board[prevRow][prevCol];
                    Tile current = board[currRow][currCol];

                    if (prev == null) {

                        if (checkingAvailableMoves) {
                            return true;
                        }
                        board[prevRow][prevCol] = current;
                        board[currRow][currCol] = null;
                        currRow = prevRow;
                        prevRow -= 1;
                        moved = true;
                    } else if (prev.canMergeWith(current)) {

                        if (checkingAvailableMoves)
                            return true;

                        mergeTiles(prev, current, currRow, currCol);
                        moved = true;
                        break;
                    } else
                        break;
                }
            }
        }
        if (moved) {
            checkGameState();
        }

        return moved;
    }

    //method pushes tiles to the edge in direction down
    //and merges the identic tiles
    public boolean moveDown() {
        boolean moved = false;

        for (int i= 3; i >-1; i--) {
            for (int j = 2; j >-1; j--) {

                int currRow = j;
                int currCol = i;
                int prevRow = j + 1;
                int prevCol = i;

                if (board[currRow][currCol] == null)
                    continue;

                while (prevRow >= 0 && prevRow < tilesNumber && prevCol >= 0 && prevCol < tilesNumber) {
                    Tile prev = board[prevRow][prevCol];
                    Tile current = board[currRow][currCol];

                    if (prev == null) {

                        if (checkingAvailableMoves) {
                            return true;
                        }
                        board[prevRow][prevCol] = current;
                        board[currRow][currCol] = null;
                        currRow = prevRow;
                        prevRow = prevRow + 1;
                        moved = true;
                    } else if (prev.canMergeWith(current)) {

                        if (checkingAvailableMoves)
                            return true;

                        mergeTiles(prev, current, currRow, currCol);
                        moved = true;
                        break;
                    } else
                        break;
                }
            }
        }
        if (moved) {
            checkGameState();
        }

        return moved;
    }

    //method pushes tiles to the edge in direction left
    //and merges the identic tiles
    public boolean moveLeft() {
        boolean moved = false;

        for (int i = 1; i < tilesNumber; i++) {
            for (int j = 0; j < tilesNumber; j++) {

                int currRow = j;
                int currCol = i;
                int prevRow = j;
                int prevCol = i - 1;

                if (board[currRow][currCol] == null)
                    continue;

                while (prevRow >= 0 && prevRow < tilesNumber && prevCol >= 0 && prevCol < tilesNumber) {
                    Tile prev = board[prevRow][prevCol];
                    Tile current = board[currRow][currCol];

                    if (prev == null) {

                        if (checkingAvailableMoves) {
                            return true;
                        }
                        board[prevRow][prevCol] = current;
                        board[currRow][currCol] = null;
                        currCol = prevCol;
                        prevCol -= 1;
                        moved = true;
                    } else if (prev.canMergeWith(current)) {

                        if (checkingAvailableMoves)
                            return true;

                        mergeTiles(prev, current, currRow, currCol);
                        moved = true;
                        break;
                    } else
                        break;
                }
            }
        }
        if (moved) {
            checkGameState();
        }

        return moved;
    }

    //method pushes tiles to the edge in direction right
    //and merges the identic tiles
    public boolean moveRight() {
        boolean moved = false;

        for (int i = 2; i > -1; i--) {
            for (int j = 3; j > -1; j--) {

                int currRow = j;
                int currCol = i;
                int prevRow = j;
                int prevCol = i + 1;

                if (board[currRow][currCol] == null)
                    continue;

                while (prevRow >= 0 && prevRow < tilesNumber && prevCol >= 0 && prevCol < tilesNumber) {
                    Tile prev = board[prevRow][prevCol];
                    Tile current = board[currRow][currCol];

                    if (prev == null) {

                        if (checkingAvailableMoves) {
                            return true;
                        }
                        board[prevRow][prevCol] = current;
                        board[currRow][currCol] = null;
                        currCol = prevCol;
                        prevCol += 1;
                        moved = true;
                    } else if (prev.canMergeWith(current)) {

                        if (checkingAvailableMoves)
                            return true;

                        mergeTiles(prev, current, currRow, currCol);
                        moved = true;
                        break;
                    } else
                        break;
                }
            }
        }
        if (moved) {
            checkGameState();
        }

        return moved;
    }

    //method checks the state of the game
    private void checkGameState(){
        if (highestTile < winCase) {
            clearMerged();
            addRandomTile();
            if (!movesAvailable()) {
                gameState = State.over;
            }
        } else if (highestTile == winCase)
            gameState = State.won;
    }

    //method merges two tiles passed as parameter
    private void mergeTiles(Tile next, Tile current, int currRow, int currCol){
        int value = next.mergeWith(current);
        if (value > highestTile)
            highestTile = value;
        score += value;
        board[currRow][currCol] = null;
    }

    //method sets the merged value of all existing tiles to false after a move
    private void clearMerged(){
        for (int i = 0; i < tilesNumber; i++){
            for (int j = 0; j < tilesNumber; j++){
                if (board[i][j] != null){
                    board[i][j].setMerged(false);
                }
            }
        }
    }

    //method checks if there are any possible moves available in any direction or an empty tile
    boolean movesAvailable() {
        checkingAvailableMoves = true;
        boolean hasMoves = moveUp() || moveDown() || moveLeft() || moveRight();
        checkingAvailableMoves = false;
        return hasMoves;
    }
}
