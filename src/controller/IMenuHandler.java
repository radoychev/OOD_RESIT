package controller;

public interface IMenuHandler {
    void loadPresentation();
    void savePresentation();
    void exit();
    void nextSlide();
    void prevSlide();
    void goToSlide(int number);
    void setSlideNumber(int number);
}
