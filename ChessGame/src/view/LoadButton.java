package view;


import controller.GameController;
import model.util.Coordinates;

public class LoadButton extends Button {
	private static final long serialVersionUID = 1L;

	public LoadButton() {
		super(new Coordinates(300, 250), 250, "loadButton");
	}
	
	@Override
	public void onClick() {
		try {
			GameController.getInstance().load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
