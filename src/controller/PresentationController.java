package controller;

import java.io.File;
import java.io.IOException;
import model.Presentation;
import view.SlideViewerComponent;
import data.XMLAccessor;
import utils.ConfigLoader;

public class PresentationController implements IMenuHandler {
    private Presentation presentation;
    private SlideViewerComponent slideViewerComponent;

    public PresentationController(Presentation presentation, SlideViewerComponent slideViewerComponent) {
        this.presentation = presentation;
        this.slideViewerComponent = slideViewerComponent;
        this.presentation.setShowView(slideViewerComponent);
    }

    public void updateView() {
        slideViewerComponent.update(presentation, presentation.getCurrentSlide());
    }

    public void nextSlide() {
        if (presentation.getSlideNumber() < presentation.getSize() - 1) {
            presentation.setSlideNumber(presentation.getSlideNumber() + 1);
            updateView();
        }
    }

    public void prevSlide() {
        if (presentation.getSlideNumber() > 0) {
            presentation.setSlideNumber(presentation.getSlideNumber() - 1);
            updateView();
        }
    }

    public void setSlideNumber(int number) {
        if (number >= 0 && number < presentation.getSize()) {
            presentation.setSlideNumber(number);
            updateView();
        } else {
            System.err.println("Invalid slide number: " + number);
        }
    }

    public void goToSlide(int number) {
        setSlideNumber(number);
    }

    public void exit() {
        System.out.println("Exiting application...");
        System.exit(0);
    }

    public void savePresentation() {
        String filename = ConfigLoader.getProperty("savedPresentationFile");
        if (filename == null || filename.isEmpty()) {
            System.err.println("Error: No savedPresentationFile path found in config.");
            return;
        }
        savePresentation(filename);
    }

    public void savePresentation(String filename) {
        try {
            new XMLAccessor().saveFile(presentation, filename);
            System.out.println("Presentation saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Error saving presentation: " + e.getMessage());
        }
    }

    private boolean isValidFile(String filename) {
        if (filename == null || filename.isEmpty()) {
            System.err.println("Error: Invalid filename.");
            return false;
        }
        File file = new File(filename);
        if (!file.exists()) {
            System.err.println("Error: File does not exist - " + filename);
            return false;
        }
        if (!file.isFile()) {
            System.err.println("Error: Not a valid file - " + filename);
            return false;
        }
        if (!filename.endsWith(".xml")) {
            System.err.println("Error: File is not an XML file - " + filename);
            return false;
        }
        return true;
    }

    public void loadPresentation() {
        String filename = ConfigLoader.getProperty("testPresentationFile");
        if (filename == null || filename.isEmpty()) {
            System.err.println("Error: No testPresentationFile path found in config.");
            return;
        }
        loadPresentation(filename);
    }

    public void loadPresentation(String filename) {
        if (isValidFile(filename)) {
            try {
                new XMLAccessor().loadFile(presentation, filename);
                presentation.setSlideNumber(0);
                updateView();
                System.out.println("Presentation loaded from: " + filename);
            } catch (IOException e) {
                System.err.println("Cannot load presentation: " + e.getMessage());
            }
        }
    }
}
