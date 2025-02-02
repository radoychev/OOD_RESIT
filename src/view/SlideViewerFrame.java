package view;

import controller.KeyController;
import controller.MenuBuilder;
import controller.PresentationController;
import model.Presentation;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.MenuBar;
import javax.swing.JFrame;

public class SlideViewerFrame extends JFrame {
	private static final long serialVersionUID = 3227L;
	private static final String JABTITLE = "Jabberpoint 1.6 - OU";
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;

	private PresentationController presentationController;

	public SlideViewerFrame(String title, Presentation presentation) {
		super(title);
		SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
		this.presentationController = new PresentationController(presentation, slideViewerComponent);
		setupWindow(slideViewerComponent);
	}

	private void setupWindow(SlideViewerComponent slideViewerComponent) {
		setTitle(JABTITLE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				presentationController.exit();
			}
		});
		getContentPane().add(slideViewerComponent);
		addKeyListener(new KeyController(presentationController));
		setMenuBar(MenuBuilder.createMenuBar(this, presentationController));

		setSize(new Dimension(WIDTH, HEIGHT));
		setVisible(true);
	}
}
