package view;


import controller.GameController;
import model.util.Coordinates;

public class SaveButton extends Button {
	private static final long serialVersionUID = 1L;

	public SaveButton() {
		super(new Coordinates(300, 1050), 250, "saveButton");
	}
	
	@Override
	public void onClick() {
		try {
			GameController.getInstance().save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
