package model.util;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
	private static final long serialVersionUID = 1L;

	private int x;
	private int y;

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	public Coordinates(int x, int y) {
		this.y = y;
		this.x = x;
	}

	public void moveX(int speed) {
		x += speed;
	}


	public void moveY(int speed) {
		y += speed;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Coordinates)) {
			return false;
		}
		Coordinates coords = (Coordinates) object;
		return y == coords.y && x == coords.x;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}

