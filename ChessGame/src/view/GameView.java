package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import fr.umlv.zen5.ApplicationContext;
import model.entities.GameObject;
import model.util.Coordinates;
import model.util.Time;

public class GameView implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final int xOriginData;
	private final int yOriginData;
	
	private final int widthData;
	private final int heightData;

	private final int width;
	private final int height;
	
	private final int squareSize;

	private final static HashMap<String, Image> entities = new HashMap<String, Image>();
	
	private final int nbLines;
	private final int nbColumns;
	
	//##################### SINGLETON #####################//

	private GameView(int nbLines, int nbColumns, int width, int height) {
		this.width = width;
		this.height = height;
		this.squareSize = (height - 50) / nbLines;
		this.nbLines = nbLines;
		this.nbColumns = nbColumns;
		
		this.widthData = squareSize * nbLines;
		this.xOriginData = (width - this.widthData) / 2;
		this.heightData = squareSize * nbColumns;
		this.yOriginData = (height - this.heightData) / 2;

		getImage("background");
		getImage("victory", ".jpg");
		getImage("defeat", ".jpg");
		getImage("chessMenu", ".jpg");
	}
	
	private static GameView gameView;
	
	public static void initGameView(int width, int height) {
		gameView = new GameView(8, 8, width, height);
	}
	
	public static GameView getInstance() {
		return gameView;
	}
	
	//################ CHARGEMENT D'IMAGES ################//
	
	public Image getImage(String name) {
		return getImage(name, ".png");
	}
	
	public Image getImage(String name, String type) {
		Image image = entities.get(name);
		if (image == null) {
			try {
				entities.put(name, ImageIO.read(new File("img/" + name.toLowerCase() + type)));
			} catch (IOException e1) {
				System.out.println("Error while loading images! Image :" + name);
			}
			image = entities.get(name);
		}
		return image;
	}
	//################# AFFICHAGE D'IMAGES #################//

	public void drawGame(ApplicationContext context, ArrayList<GameObject> gameObjectList, int gameState, Time gameTimer) {
		context.renderFrame( graphics -> {
			if (gameState == 0) {
				backgroundImage(graphics, getImage("background"));
			} else if (gameState == 1) {
				backgroundImage(graphics, getImage("victory"));
			} else if (gameState == 2) {
				backgroundImage(graphics, getImage("defeat"));
			} else if (gameState == 4) {
				backgroundImage(graphics, getImage("chessMenu"));
			}
			string(graphics, gameTimer.toSeconds() + "s", 25, 25, 25, Color.WHITE);
			for (GameObject gameObject : gameObjectList) {
				if ((gameState == 0 && gameObject.mainElement()) || (gameState == 4 && !gameObject.mainElement())) {
					gameObject.draw(graphics);
				}
			}
		});
	}
	//################ CALCUL DE COORDONNEES ################//

	private int indexFromReaCoord(int coord, int origin) {
		return (coord - origin) / squareSize;
	}
	
	public boolean existsX(int x) {
		return (0 <= x && x < xOriginData + widthData);
	}

	public int columnFromX(int x) {
		if (0 <= x && x < xOriginData + widthData) {
			return indexFromReaCoord(x, xOriginData);
		} else {
			throw new IllegalStateException();
		}
	}
	
	public boolean existsY(int y) {
		return (0 <= y && y < yOriginData + heightData);
	}

	public int lineFromY(int y) {
		if (0 <= y && y < yOriginData + heightData) {
			return indexFromReaCoord(y, yOriginData);
		} else {
			throw new IllegalStateException();
		}
	}

	private int realCoordFromIndex(int index, int origin) {
		return origin + index * squareSize;
	}

	private int xFromColumn(int column) {
		return realCoordFromIndex(column, xOriginData);
	}

	private int yFromLine(int line) {
		return realCoordFromIndex(line, yOriginData);
	}
	
	public boolean existsLine(int line) {
		return 0 <= line && line < nbLines;
	}
	
	public boolean existsColumn(int column) {
		return 0 <= column && column < nbColumns;
	}

	//############### METHODES DE DESSIN ###############//
	
	public void imageInBoard(Graphics2D graphics, Image img, int line, int column) {
		graphics.drawImage(img, xFromColumn(column), yFromLine(line), squareSize, squareSize, null);
	}

	public void image(Graphics2D graphics, Image img, int line, int column, int width, int height) {
		graphics.drawImage(img, column, line, width, height, null);
	}

	public void backgroundImage(Graphics2D graphics, Image img) {
		graphics.drawImage(img, 0, 0, width, height, null);
	}

	public void string(Graphics2D graphics, String str, float x, float y, int length, Color color) {
		graphics.setColor(color);
		graphics.setFont(new Font("arial", length, length));
		graphics.drawString(str, x, y + 25);
	}

	public boolean isClicked(Coordinates cellCoords, Coordinates clicCoords) {
		int cellX = xFromColumn(cellCoords.getY());
		int cellY = yFromLine(cellCoords.getX());
		int clickX = xFromColumn(clicCoords.getY());
		int clickY = yFromLine(clicCoords.getX());
		return (cellX <= clickX && clickX < cellX + squareSize) && (cellY <= clickY && clickY < cellY + squareSize);
	}

	public void drawSelectedShape(Graphics2D graphics, int line, int column) {
		graphics.setColor(Color.GREEN);
		graphics.fill(new Rectangle2D.Float(xFromColumn(column), yFromLine(line), squareSize, squareSize));
	}
}
