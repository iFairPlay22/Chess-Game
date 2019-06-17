package model.pieces;

import java.util.HashSet;

import model.entities.Piece;
import model.util.Coordinates;

public class Pawn extends Piece {
	private static final long serialVersionUID = 1L;

	public Pawn(boolean black, Coordinates coords) {
		super(black, black ? "Chess_pdt60" : "Chess_plt60", coords);
	}
	
	@Override
	public boolean isPlacedIn(int line, int column) {
		return line == 1 || line == 6;
	}
	
	@Override
	public Piece newInstance(boolean black, Coordinates coords) {
		return new Pawn(black, coords);
	}
	
	@Override
	public HashSet<Coordinates> getMovePossibilities() {
		HashSet<Coordinates> set = super.getMovePossibilities();
		Coordinates coord = getCoords();
		int beginLine = coord.getX();
		int beginColumn = coord.getY();
		if (black) {
			set.add(new Coordinates(beginLine + 1, beginColumn));
		} else {
			set.add(new Coordinates(beginLine - 1, beginColumn));			
		}
		return set;
	}
	
	@Override
	public HashSet<Coordinates> getAttackPossibilities() {
		HashSet<Coordinates> set = super.getAttackPossibilities();
		Coordinates coord = getCoords();
		int beginLine = coord.getX();
		int beginColumn = coord.getY();
		if (black) {
			set.add(new Coordinates(beginLine + 1, beginColumn + 1));
			set.add(new Coordinates(beginLine + 1, beginColumn - 1));
		} else {
			set.add(new Coordinates(beginLine - 1, beginColumn + 1));
			set.add(new Coordinates(beginLine - 1, beginColumn - 1));
		}
		return set;
	}
}
