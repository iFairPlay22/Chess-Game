package model.entities;

import java.util.HashSet;
import java.util.Set;

import controller.GameController;
import model.util.Coordinates;
import view.GameView;

public abstract class Piece extends GameObject {
	private static final long serialVersionUID = 1L;
	private static boolean blackTurn = false;
	
	protected final boolean black;

	public Piece(boolean black, String tag, Coordinates coords) {
		super(coords, tag);
		this.black = black;
	}
	
	public boolean isPlacedIn(int line, int column) {
		return false;
	}
	
	public Piece newInstance(boolean black, Coordinates coords) {
		return null;
	}
	
	public void onClick() {
		Set<Cell> gameObjectSet = getCells();
		for (Cell cell : gameObjectSet) {
			cell.nowSelected();
		}
	}
	
	public HashSet<Coordinates> getMovePossibilities() {
		return new HashSet<Coordinates>();
	}
	
	public HashSet<Coordinates> getAttackPossibilities() {
		return new HashSet<Coordinates>();
	}
	
	public Set<Cell> getCells() {
		GameView view = GameView.getInstance();
		GameController gameController = GameController.getInstance();
		Set<Cell> cellsSet = new HashSet<Cell>();
		Set<Coordinates> moveSet = getMovePossibilities();
		Set<Coordinates> attackSet = getAttackPossibilities();
		if (moveSet == null || attackSet == null) { throw new IllegalStateException(); }
		for (Coordinates coords : moveSet) {
			int line = coords.getX();
			int column = coords.getY();
			if (view.existsLine(line) && view.existsColumn(column)) {
				addCell(gameController.getGameObject(coords), cellsSet);
			}
		}
		for (Coordinates coords : attackSet) {
			int line = coords.getX();
			int column = coords.getY();
			if (view.existsLine(line) && view.existsColumn(column)) {
				addAttackCell(gameController.getGameObject(coords), cellsSet);
			}
		}
		return cellsSet;
	}
	
	public void completeMovePossibilities(Set<Coordinates> vector, Set<Coordinates> set) {
		GameView view = GameView.getInstance();
		GameController gameController = GameController.getInstance();
		Coordinates coord = getCoords();
		int beginLine = coord.getX();
		int beginColumn = coord.getY();
		for (Coordinates coordinates : vector) {
			int line = coordinates.getX();
			int column = coordinates.getY();
			for (int i = 1; i < 9; i++) {
				Coordinates coords = new Coordinates(beginLine + line * i, beginColumn + column * i);
				if (view.existsLine(coords.getX()) && view.existsColumn(coords.getY())) {
					if (!addCoords(gameController.getGameObject(coords), set, coords)) {
						break;
					}
				}
			}
		}
	}
	
	private boolean addCoords(GameObject gameObject, Set<Coordinates> set, Coordinates coords) {
		Cell cell = (Cell) gameObject;
		if (cell != null) {
			set.add(coords);
			return !cell.hasPiece();
		}
		return false;
	}

	private void addCell(GameObject gameObject, Set<Cell> set) {
		Cell cell = (Cell) gameObject;
		Piece piece = cell.getPiece();
		if (cell != null && (!cell.hasPiece() || (cell.hasPiece() && piece.black != black))) {
			set.add(cell);
		}
	}
	
	private void addAttackCell(GameObject gameObject, Set<Cell> set) {
		Cell cell = (Cell) gameObject;
		Piece piece = cell.getPiece();
		if (cell != null && (cell.hasPiece() && piece.black != black)) {
			set.add(cell);
		}
	}
	
	@Override
	public void move(Coordinates coords) {
		blackTurn = !blackTurn;
		super.move(coords);
	}
	
	public boolean canDoAction() {
		return (black && blackTurn) || (!black && !blackTurn);
	}
	
	public boolean isKing() {
		return false;
	}

	public boolean isBlackKing() {
		return false;
	}
	
	private static int blackDestroyIndexLine = -150;
	private static int blackDestroyIndexColumn = 1225;
	
	private static int whiteDestroyIndexLine = -150;
	private static int whiteDestroyIndexColumn = 50;
	
	@Override
	public void destroy() {
		if (black) {
			blackDestroyIndexColumn = blackDestroyIndexColumn == 1225 ? 1375 : 1225;
			blackDestroyIndexLine = (blackDestroyIndexColumn == 1375) ? blackDestroyIndexLine + 150 : blackDestroyIndexLine;
			super.move(new Coordinates(blackDestroyIndexLine, blackDestroyIndexColumn));
		} else {
			whiteDestroyIndexColumn = whiteDestroyIndexColumn == 50 ? 200 : 50;
			whiteDestroyIndexLine = (whiteDestroyIndexColumn == 200) ? whiteDestroyIndexLine + 150 : whiteDestroyIndexLine;
			super.move(new Coordinates(whiteDestroyIndexLine, whiteDestroyIndexColumn));
		}
		super.update();
	}
	
	@Override
	public boolean isClicked(Coordinates clickedCoords) {
		return false;
	}
	
	@Override
	public boolean hasCell(Coordinates coords) {
		return false;
	}
}
