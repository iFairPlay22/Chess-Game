package controller;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import model.entities.Cell;
import model.entities.GameObject;
import model.pieces.Bishop;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.entities.Piece;
import model.pieces.Queen;
import model.pieces.Rook;
import model.util.Coordinates;
import model.util.Time;
import view.GameView;
import view.LoadButton;
import view.ReloadButton;
import view.SaveButton;

public class GameController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final GameView view;
	private int gameState = 0;//0: in game, 1: victory, 2: defeat, 3: menu
	private final Time gameTimer;
	private final Time displayTimer;
	private final ArrayList<GameObject> gameObjectList;
	
	
	//########## SINGLETON ##########//
	
	private static GameController gameController;
	private static ApplicationContext context;
	
	public static GameController getInstance() {
		return gameController;
	}
	
	private GameController() {
		
		GameView.initGameView((int) context.getScreenInfo().getWidth(), (int) context.getScreenInfo().getHeight());
		view = GameView.getInstance();
		
		ArrayList<Piece> piecesSet = new ArrayList<Piece>();
		piecesSet.add(new Pawn(false, null));piecesSet.add(new Queen(false, null));piecesSet.add(new Rook(false, null));
		piecesSet.add(new Bishop(false, null));piecesSet.add(new Knight(false, null));piecesSet.add(new King(false, null));
		
		gameObjectList = new ArrayList<GameObject>();
		ArrayList<GameObject > pieceList = new ArrayList<GameObject>();
		for (int line = 0; line < 8; line++) {
			for (int column = 0; column < 8; column++) {
				boolean black = (line % 2 == 1 && column % 2 == 1) || (line % 2 == 0 && column % 2 == 0), placed = false;
				Coordinates coords = new Coordinates(line, column);
				String color = black ? "black" : "white";
				Piece newPiece = null;
				for (Piece piece : piecesSet) {
					if (piece.isPlacedIn(line, column)) {
						newPiece = piece.newInstance(line < 2, coords);
						gameObjectList.add(new Cell(coords, color, newPiece));
						pieceList.add(newPiece);
						placed = true;
						break;
					}
				}
				if (!placed) { gameObjectList.add(new Cell(coords, color, null)); }
			}
		}
		
		//Pour que les pions soient dessines au dessus des cases
		for (GameObject gameObject : pieceList) {
			gameObjectList.add(gameObject);
		}
		
		gameObjectList.add(new LoadButton());
		gameObjectList.add(new ReloadButton());
		gameObjectList.add(new SaveButton());
		
		gameTimer = new Time();
		displayTimer = new Time(25);
	}
	
	//########## MAIN GAME ##########//
	
	private void playGame() {
		displayTimer.initialize();
		gameTimer.initialize();
		while (true) {
			if (25 <= displayTimer.getPastTime()) {
				view.drawGame(context, gameObjectList, gameState, gameTimer);
				displayTimer.restart();
			}
			manageEvent();
		}
	}
	
	//########## EVENT MAIN ##########//
	
	private void manageEvent() {
		Event currentEvent = context.pollOrWaitEvent(50);

		if (currentEvent == null) { return ; }
		
		Action currentAction = currentEvent.getAction();
		if (currentAction == Action.KEY_PRESSED) {
			onKey(currentEvent);
		} else if (gameState == 0 || gameState == 4) {				
			if (currentAction != Action.POINTER_DOWN) {
				onClick();
			}
		}
	}
	
	//########## HANDLE ACTIONS ##########//

	private void onKey(Event currentEvent) {
		KeyboardKey keyboardkey = currentEvent.getKey();
		if (keyboardkey == KeyboardKey.S) {//Save
			try {
				save();
			} catch (Exception e) {
				System.out.println("Impossible to save");
				e.printStackTrace();
			}
		} else if (keyboardkey == KeyboardKey.L) {//Load
			try {
				load();
			} catch (Exception e) {
				System.out.println("Impossible to load");
				e.printStackTrace();
			}
		} else if (keyboardkey == KeyboardKey.R) {//Reload
			try {
				reload();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (keyboardkey == KeyboardKey.M) {//Menu
			gameState = gameState == 4 ? 0 : 4;
		} else {
			context.exit(0);
		}
	}

	private void onClick() {
		Point location = MouseInfo.getPointerInfo().getLocation();
		int x = (int) location.getX();
		int y = (int) location.getY();
		if (gameState == 0 && view.existsX(x) && view.existsY(y)) { //Game
			Coordinates coords = new Coordinates(view.lineFromY(y), view.columnFromX(x));
			for (GameObject gameObject : gameObjectList) {
				if (gameObject.isClicked(coords)) {
					gameObject.onClick();
				}
			}
		} else if (gameState == 4) { //Menu
			Coordinates coords = new Coordinates(x, y);
			for (GameObject gameObject : gameObjectList) {
				if (!gameObject.mainElement() && gameObject.isClicked(coords)) {
					gameObject.onClick();
				}
			}
		}
	}
	
	//########## GETTERS GAMEOBJECT ##########//
	
	public GameObject getGameObject(Coordinates coords) {
		for (GameObject gameObject : gameObjectList) {
			if (gameObject.hasCell(coords)) {
				return gameObject;
			}
		}
		return null;
	}
	
	//########## END ##########//
	
	public void end(boolean black) {
		gameState = black ? 1 : 2;
	}
	
	//########## SERIALIZABLE ##########//
	
	@SuppressWarnings("resource")
	public void save() throws FileNotFoundException, IOException {
		gameTimer.save();
		displayTimer.save();
		ObjectOutputStream objectOutputStream =  new ObjectOutputStream(new FileOutputStream(new File("src/save.txt")));
		objectOutputStream.writeObject(this);
		displayTimer.initialize();
		gameTimer.initialize();
	}
	
	@SuppressWarnings("resource")
	public void load() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream objectInputStream =  new ObjectInputStream(new FileInputStream(new File("src/save.txt")));
		gameController = (GameController) objectInputStream.readObject();
		gameController.playGame();
	}
	
	@SuppressWarnings("resource")
	public void reload() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream objectInputStream =  new ObjectInputStream(new FileInputStream(new File("src/newGame.txt")));
		gameController = (GameController) objectInputStream.readObject();
		gameController.playGame();
	}

	public static void main(String[] args) {
		Application.run(Color.WHITE, context -> {
			GameController.context = context;
			gameController = new GameController();
			gameController.playGame();
		});
	}
}
