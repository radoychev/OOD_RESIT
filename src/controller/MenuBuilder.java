package controller;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Frame;
import javax.swing.JOptionPane;
import view.AboutBox;

public class MenuBuilder {

    public static MenuBar createMenuBar(Frame parent, IMenuHandler handler) {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        fileMenu.add(createMenuItem("Open", handler::loadPresentation));
        fileMenu.add(createMenuItem("New", () -> {
            handler.setSlideNumber(0);
            parent.repaint();
        }));
        fileMenu.add(createMenuItem("Save", handler::savePresentation));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem("Exit", handler::exit));
        menuBar.add(fileMenu);

        Menu viewMenu = new Menu("View");
        viewMenu.add(createMenuItem("Next", handler::nextSlide));
        viewMenu.add(createMenuItem("Prev", handler::prevSlide));
        viewMenu.add(createMenuItem("Go to", () -> {
            String pageNumberStr = JOptionPane.showInputDialog(parent, "Enter page number:");
            if (pageNumberStr != null) {
                try {
                    int pageNumber = Integer.parseInt(pageNumberStr);
                    handler.goToSlide(pageNumber - 1);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(parent, "Invalid number! Please enter a valid page number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }));
        menuBar.add(viewMenu);

        Menu helpMenu = new Menu("Help");
        helpMenu.add(createMenuItem("About", () -> AboutBox.show(parent)));
        menuBar.setHelpMenu(helpMenu);

        return menuBar;
    }

    private static MenuItem createMenuItem(String name, Runnable action) {
        MenuItem menuItem = new MenuItem(name);
        menuItem.addActionListener(e -> action.run());
        return menuItem;
    }
}
