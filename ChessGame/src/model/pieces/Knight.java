package model.pieces;

import java.util.HashSet;

import model.entities.Piece;
import model.util.Coordinates;

public class Knight extends Piece {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Knight(boolean black, Coordinates coords) {
		super(black, black ? "Chess_ndt60" : "Chess_nlt60", coords);
	}
	
	@Override
	public boolean isPlacedIn(int line, int column) {
		return (line == 0 && (column == 1 || column == 6)) || (line == 7 && (column == 1 || column == 6));
	}
	
	@Override
	public Piece newInstance(boolean black, Coordinates coords) {
		return new Knight(black, coords);
	}
	
	@Override
	public HashSet<Coordinates> getMovePossibilities() {
		HashSet<Coordinates> set = super.getMovePossibilities();
		Coordinates coord = getCoords();
		int beginLine = coord.getX();
		int beginColumn = coord.getY();
		set.add(new Coordinates(beginLine + 2, beginColumn - 1));
		set.add(new Coordinates(beginLine + 2, beginColumn + 1));
		set.add(new Coordinates(beginLine - 2, beginColumn - 1));
		set.add(new Coordinates(beginLine - 2, beginColumn + 1));
		set.add(new Coordinates(beginLine - 1, beginColumn + 2));
		set.add(new Coordinates(beginLine + 1, beginColumn + 2));
		set.add(new Coordinates(beginLine - 1, beginColumn - 2));
		set.add(new Coordinates(beginLine + 1, beginColumn - 2));
		return set;
	}
}
