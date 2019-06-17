package view;


import controller.GameController;
import model.util.Coordinates;

public class ReloadButton extends Button {
	private static final long serialVersionUID = 1L;

	public ReloadButton() {
		super(new Coordinates(300, 650), 250, "restartButton");
	}

	@Override
	public void onClick() {
		try {
			GameController.getInstance().reload();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
