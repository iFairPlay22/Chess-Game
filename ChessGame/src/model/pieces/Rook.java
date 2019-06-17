package model.pieces;

import java.util.HashSet;

import model.entities.Piece;
import model.util.Coordinates;

public class Rook extends Piece {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Rook(boolean black, Coordinates coords) {
		super(black, black ? "Chess_rdt60" : "Chess_rlt60", coords);
	}
	
	@Override
	public boolean isPlacedIn(int line, int column) {
		return (line == 0 && (column == 0 || column == 7)) || (line == 7 && (column == 0 || column == 7));
	}
	
	@Override
	public Piece newInstance(boolean black, Coordinates coords) {
		return new Rook(black, coords);
	}
	
	@Override
	public HashSet<Coordinates> getMovePossibilities() {
		HashSet<Coordinates> set = super.getMovePossibilities();
		HashSet<Coordinates> vector = new HashSet<Coordinates>();
		vector.add(new Coordinates(0, 1)); vector.add(new Coordinates(0, -1));
		vector.add(new Coordinates(-1, 0)); vector.add(new Coordinates(1, 0));
		completeMovePossibilities(vector, set);
		return set;
	}
}
