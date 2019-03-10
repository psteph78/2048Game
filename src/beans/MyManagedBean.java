package beans;

import controller.GameController;
import model.Tile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class MyManagedBean {

	private GameController game = new GameController();
	private Tile[][] board = game.getBoard();
    private int score = game.getScore();
    private int highestScore = game.getScore();
    private String gameState = game.getGameState();

	public String getGameState() {
		return gameState;
	}

	public void setGameState(String gameState) {
		this.gameState = gameState;
	}

	public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public MyManagedBean(){
	}

	public Tile[][] getBoard() {
		return board;
	}

	public void setBoard(Tile[][] board) {
		this.board = board;
	}

	public int getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}

	public void up(){
		game.moveUp();
		setBoard(game.getBoard());
		setScore(game.getScore());
		setGameState(game.getGameState());
		if (highestScore < game.getScore())
			setHighestScore(game.getScore());
	}

	public void down(){
		game.moveDown();
		setBoard(game.getBoard());
        setScore(game.getScore());
		setGameState(game.getGameState());
		if (highestScore < game.getScore())
			setHighestScore(game.getScore());
	}

	public void left(){
		game.moveLeft();
		setBoard(game.getBoard());
        setScore(game.getScore());
		setGameState(game.getGameState());
		if (highestScore < game.getScore())
			setHighestScore(game.getScore());
	}

	public void right(){
		game.moveRight();
		setBoard(game.getBoard());
        setScore(game.getScore());
		setGameState(game.getGameState());
		if (highestScore < game.getScore())
			setHighestScore(game.getScore());
	}

	public void reset(){
        game = new GameController();
        setBoard(game.getBoard());
        setScore(game.getScore());
		setGameState(game.getGameState());
    }

    public static void main(String[] args){
	    MyManagedBean bean = new MyManagedBean();

	    for (int i = 0; i < 4; i++){
	        for (int j = 0; j < 4; j++){
	            if (bean.getBoard()[i][j] == null){
                    System.out.print(" - ");
                }
                else {System.out.print(bean.getBoard()[i][j].getValue() + " ");}
            }
            System.out.println("\n");
        }
        System.out.println("\n");
        bean.right();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (bean.getBoard()[i][j] == null){
                    System.out.print(" - ");
                }
                else {System.out.print(bean.getBoard()[i][j].getValue() + " ");}
            }
            System.out.println("\n");
        }
	}
}
