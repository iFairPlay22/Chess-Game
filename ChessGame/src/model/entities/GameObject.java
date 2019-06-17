package model.entities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;
import java.util.Objects;

import model.util.Coordinates;
import view.GameView;

public abstract class GameObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Coordinates coords;
	private transient Image image;
	private final String string;
	private boolean drawInGround = true;
	
	public GameObject(Coordinates coords, String string) {
		this.coords = coords;
		this.image = GameView.getInstance().getImage(string);
		this.string = string;
	}
	
	//############### UTIL METODS ###############//
	
	public String toString() {
		return "GameObject: " + coords.toString();
	}
	
	@Override
	public boolean equals(Object objects) {
		if (!(objects instanceof GameObject)) {
			return false;
		}
		GameObject gameObject = (GameObject) objects;
		return coords.equals(gameObject.coords);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(coords);
	}

	//############### COORDINATES ###############//
	
	public boolean isClicked(Coordinates clickedCoords) {
		if (drawInGround) {
			return GameView.getInstance().isClicked(coords, clickedCoords);
		} else {
			int x = clickedCoords.getX();
			int y = clickedCoords.getY();
			int xThis =	coords.getY();
			int yThis = coords.getX();
			return (xThis <= x && x <= xThis + cardSize()) && (yThis <= y && y <= yThis + cardSize());
		}
	}
	
	public int cardSize() {
		return 100;
	}
	
	public void onClick() {
		
	}
	
	public Coordinates getCoords() {
		return coords;
	}
	
	//############ GENERIC METHODS ############//
	
	public void draw(Graphics2D graphics) {
		GameView view = GameView.getInstance();
		if (image == null) {
			image = GameView.getInstance().getImage(string);
		}
		if (drawInGround) {
			view.imageInBoard(graphics, image, coords.getX(), coords.getY());
		} else {
			view.image(graphics, image, coords.getX(), coords.getY(), cardSize(), cardSize());
		}
	}
	
	public void drawSelected(Graphics2D graphics) {
		GameView view = GameView.getInstance();
		view.drawSelectedShape(graphics, coords.getX(), coords.getY());
	}
	
	public void destroy() {
		
	}
	
	public void update() {
		drawInGround = false;
	}

	public boolean hasCell(Coordinates coords) {
		return this.coords.equals(coords);
	}
	
	public void move(Coordinates coords) {
		Objects.requireNonNull(coords);
		this.coords = coords;
	}
	
	public boolean mainElement() {
		return true;
	}
}
