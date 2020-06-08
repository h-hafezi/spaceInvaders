package Game;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
    boolean dead = false;
    final String type;

    Sprite(int x, int y, int w, int h, String type, Color color) {
        super(w, h, color);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
        setImage();
    }

    Sprite(int x, int y, int w, int h, String type, int i) {
        super(w, h, Color.WHITE);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
        switch (i % 4) {
            case 1:
                this.setFill(new ImagePattern(new Image("resources/grean.png")));
                break;
            case 2:
                this.setFill(new ImagePattern(new Image("resources/blue.png")));
                break;
            case 3:
                this.setFill(new ImagePattern(new Image("resources/red.png")));
                break;
            case 0:
                this.setFill(new ImagePattern(new Image("resources/yellow.png")));
                break;
        }

    }


    void setImage() {
        switch (this.type) {
            case "enemy-bullet":
                this.setFill(new ImagePattern(new Image("resources/45-456437_nuke-clipart-missile-launch-space-invaders-missile-png.png")));
                break;
            case "player-bullet":
                this.setFill(new ImagePattern(new Image("resources/140-1400346_space-invaders-ship-png-transparent-png.png")));
                break;
            case "enemy":
                this.setFill(new ImagePattern(new Image("resources/1156407_1.jpg")));
                break;
            case "player":
                this.setFill(new ImagePattern(new Image("resources/388-3880069_spaceship-starfish-pixel-art-clipart.png")));
                break;
        }
    }


    void moveLeft() {
        if (this.getTranslateX() > 0) {
            setTranslateX(getTranslateX() - 10);
        }
    }

    void moveRight() {
        if (this.getTranslateX() < SpaceInvadersApp.root.getWidth() - this.getWidth()) {
            setTranslateX(getTranslateX() + 10);
        }
    }

    void moveLeftForEnemy() {
        setTranslateX(getTranslateX() - 4);
    }

    void moveRightEnemy() {
        setTranslateX(getTranslateX() + 4);
    }

    void moveUp() {
        setTranslateY(getTranslateY() - 5);
    }

    void moveDown() {
        setTranslateY(getTranslateY() + 5);
    }

}
