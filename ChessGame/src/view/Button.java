package view;

import model.entities.GameObject;
import model.util.Coordinates;

public class Button extends GameObject {
	private static final long serialVersionUID = 1L;
	
	private final int cardSize;
	
	public Button(Coordinates coordinates, int cardSize, String tag) {
		super(coordinates, tag);
		this.cardSize = cardSize;
		update();
	}
	
	@Override
	public String toString() {
		return "Button";
	}
	
	@Override
	public int cardSize() {
		return cardSize;
	}
	
	@Override
	public boolean mainElement() {
		return false;
	}
}
