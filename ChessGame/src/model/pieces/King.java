package model.pieces;

import java.util.HashSet;

import model.entities.Piece;
import model.util.Coordinates;

public class King extends Piece {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public King(boolean black, Coordinates coords) {
		super(black, black ? "Chess_kdt60" : "Chess_klt60", coords);
	}
	
	@Override
	public boolean isPlacedIn(int line, int column) {
		return (line == 0 && column == 4) || (line == 7 && column == 3);
	}
	
	@Override
	public Piece newInstance(boolean black, Coordinates coords) {
		return new King(black, coords);
	}
	
	@Override
	public HashSet<Coordinates> getMovePossibilities() {
		HashSet<Coordinates> set = super.getMovePossibilities();
		Coordinates coord = getCoords();
		int beginLine = coord.getX();
		int beginColumn = coord.getY();
		set.add(new Coordinates(beginLine + 1, beginColumn));
		set.add(new Coordinates(beginLine - 1, beginColumn));
		set.add(new Coordinates(beginLine + 1, beginColumn - 1));
		set.add(new Coordinates(beginLine - 1, beginColumn - 1));
		set.add(new Coordinates(beginLine + 1, beginColumn + 1));
		set.add(new Coordinates(beginLine - 1, beginColumn + 1));
		set.add(new Coordinates(beginLine, beginColumn - 1));
		set.add(new Coordinates(beginLine, beginColumn + 1));
		return set;
	}
	
	@Override
	public boolean isKing() {
		return true;
	}
	
	@Override
	public boolean isBlackKing() {
		return black;
	}
}
