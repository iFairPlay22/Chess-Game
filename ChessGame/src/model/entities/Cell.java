package model.entities;

import java.awt.Graphics2D;

import controller.GameController;
import model.util.Coordinates;

public class Cell extends GameObject {
	private static final long serialVersionUID = 1L;
	
	private static Cell selectedCell = null;
	
	private Piece piece;
	private boolean selected = false; 

	public Cell(Coordinates coords, String color, Piece piece) {
		super(coords, color);
		this.piece = piece;
	}

	@Override
	public String toString() {
		return "Cell: " + super.toString() + ", Contains: " + piece;
	}
	
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
		if (selected) { super.drawSelected(graphics); }
	}

	public void nowSelected() {
		selected = !selected;
	}
	
	public void update() {
		
	}
	
	public void onClick() {
		move();
		selection((selectedCell != null) ? selectedCell.piece : null);
	}
	
	private void move() {
		if (selected  && selectedCell != null && selectedCell.piece != null) {
			selectedCell.piece.onClick();
			selectedCell.piece.move(getCoords());
			if (piece != null) { 
				if (piece.isKing()) {
					GameController.getInstance().end(piece.isBlackKing());
				}
				piece.destroy(); 
			}
			piece = selectedCell.piece;
			selectedCell.piece = null;
			selectedCell = null;
			return ;
		}
	}
	
	private void selection(Piece selectedPiece) {
		if (piece != null && piece.canDoAction()) {
			if (selectedPiece == piece) {
				piece.onClick(); 
				selectedCell = null;
			} else {
				if (selectedPiece != null) {
					selectedPiece.onClick();
				}
				selectedCell = this;
				piece.onClick(); 
			}
		}
	}

	public boolean hasPiece() {
		return piece != null;
	}
	
	public Piece getPiece() {
		return piece;
	}
}
