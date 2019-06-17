package model.pieces;

import java.util.HashSet;

import model.entities.Piece;
import model.util.Coordinates;

public class Bishop extends Piece {
	private static final long serialVersionUID = 1L;

	public Bishop(boolean black, Coordinates coords) {
		super(black, black ? "Chess_bdt60" : "Chess_blt60", coords);
	}

	@Override
	public boolean isPlacedIn(int line, int column) {
		return (line == 0 && (column == 2 || column == 5)) || (line == 7 && (column == 2 || column == 5));
	}
	
	@Override
	public Piece newInstance(boolean black, Coordinates coords) {
		return new Bishop(black, coords);
	}
	
	@Override
	public HashSet<Coordinates> getMovePossibilities() {
		HashSet<Coordinates> set = super.getMovePossibilities();
		HashSet<Coordinates> vector = new HashSet<Coordinates>();
		vector.add(new Coordinates(1, 1)); vector.add(new Coordinates(1, -1));
		vector.add(new Coordinates(-1, 1)); vector.add(new Coordinates(-1, -1));
		completeMovePossibilities(vector, set);
		return set;
	}
}
