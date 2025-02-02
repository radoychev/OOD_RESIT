package controller;

import java.awt.Frame;
import java.awt.MenuBar;

public class MenuController {
	private Frame parent;
	private PresentationController presentationController;

	public MenuController(Frame frame, PresentationController controller) {
		this.parent = frame;
		this.presentationController = controller;
		parent.setMenuBar(MenuBuilder.createMenuBar(frame, controller));
	}
}
