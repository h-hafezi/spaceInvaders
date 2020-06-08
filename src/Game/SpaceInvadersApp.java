package Game;

import PreGame.BackgroundMusic;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SpaceInvadersApp extends Application {

    //game attributes

    public static int point = 0;
    static Stage thisStage;
    static boolean gameIsOn = true;
    static double hardness = 0;
    static Pane root;
    static double t = 0;
    static int i = 0;
    static boolean hasBennRestarted = false;
    static AnimationTimer timer = null;

    //making the player

    static Sprite player = new Sprite(300, 750, 60, 60, "player", Color.BLUE);

    private Parent createContent() {
        root.getChildren().clear();
        root.setPrefSize(600, 800);
        root.getChildren().add(player);
        root.setBackground(
                new Background(
                        Collections.singletonList(new BackgroundFill(
                                Color.WHITE,
                                new CornerRadii(500),
                                new Insets(10))),
                        Collections.singletonList(new BackgroundImage(
                                new Image("resources/images.jpg", 700, 800, false, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT))));

        //setting the timer

        if (timer == null) {
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update();
                }
            };
        }

        timer.start();

        //setting the aliens

        nextLevel();

        return root;
    }

    private void nextLevel() {
        root.getChildren().clear();
        player = new Sprite(300, 750, 60, 60, "player", Color.BLUE);
        root.getChildren().add(player);
        System.gc();
        //removing the additional bullets and enemies

        sprites().forEach(s -> {
            if (s.type.equals("enemy-bullet") || s.type.equals("player-bullet")) {
                root.getChildren().remove(s);
                s.dead = true;
            } else if (s.type.equals("enemy")) {
                s.dead = true;
                s = null;
                root.getChildren().remove(s);
            }
        });

        //setting the aliens in the place

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                Sprite s = new Sprite(90 + i * 100, 20 + j * 50, 40, 40, "enemy", j);
                root.getChildren().add(s);
            }
        }

        hardness += 0.03;
    }

    private List<Sprite> sprites() {
        return root.getChildren().stream().map(n -> (Sprite) n).collect(Collectors.toList());
    }

    private void update() {

        t += 0.016;
        i++;
        int x = i % 100;

        sprites().forEach(s -> {
            switch (s.type) {

                case "enemy-bullet":
                    s.moveDown();

                    if (s.getBoundsInParent().intersects(player.getBoundsInParent()) && !s.dead) {
                        player.dead = true;
                        killed();
                        s.dead = true;
                    }
                    break;

                case "player-bullet":
                    s.moveUp();
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent()) && !s.dead) {
                            point += 10;
                            enemy.dead = true;
                            enemy = null;
                            root.getChildren().remove(s);
                            root.getChildren().remove(enemy);
                            s.dead = true;
                            hasBennRestarted = false;
                        }
                    });

                    sprites().stream().filter(e -> e.type.equals("enemy-bullet")).forEach(enemy_bullet -> {
                        if (s.getBoundsInParent().intersects(enemy_bullet.getBoundsInParent()) && !s.dead) {
                            enemy_bullet.dead = true;
                            enemy_bullet = null;
                            root.getChildren().remove(s);
                            root.getChildren().remove(enemy_bullet);
                            s.dead = true;
                        }
                    });

                    break;

                case "enemy":

                    //enemies random shooting

                    if (t > 2 && !s.dead) {
                        if (Math.random() < 0.08 + hardness) {
                            shoot(s);
                        }
                    }

                    //enemies moving

                    if (x == 0 || x == 5 || x == 10 || x == 15 || x == 20) {
                        s.moveLeftForEnemy();
                    } else if (x == 50 || x == 55 || x == 60 || x == 65 || x == 70) {
                        s.moveRightEnemy();
                    } else if (x > 97) {
                        s.moveDown();
                    }

                    //if enemies come too down you player will lose!

                    if (s.getTranslateY() > root.getHeight() - 100) {
                        killed();
                    }

                    break;
            }
        });

        root.getChildren().removeIf(n -> {
            Sprite s = (Sprite) n;
            return s.dead;
        });

        if (t > 2) {
            t = 0;
        }

        if (point % 500 == 0 && !hasBennRestarted) {
            nextLevel();
            hasBennRestarted = true;
        }
    }

    private void shoot(Sprite who) {
        Sprite s = new Sprite((int) who.getTranslateX() + 20, (int) who.getTranslateY(), 20, 40, who.type + "-bullet", Color.BLACK);
        root.getChildren().add(s);
    }

    @Override
    public void start(Stage stage) {

        //initializing

        root = new Pane();
        point = 0;
        gameIsOn = true;
        hardness = 0;
        hasBennRestarted = false;
        player = new Sprite(300, 750, 60, 60, "player", Color.BLUE);
        thisStage = stage;
        t = 0;
        i = 0;

        //setting action listeners

        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(e -> {
            switch (e.getCode().getCode()) {
                case KeyEvent.VK_LEFT:
                    player.moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    player.moveRight();
                    break;
            }
        });


        //space

        scene.setOnKeyReleased(e -> {
            if (e.getCode().getCode() == 32) {
                if (!player.dead) {
                    shoot(player);
                    BackgroundMusic.kill.start();
                }

            }
        });

        //setting scenes

        stage.getIcons().add(new Image("resources/yellow.png"));
        stage.setScene(scene);
        stage.show();

    }

    void killed() {
        BackgroundMusic.lose.start();
        try {
            timer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/AfterGame/afterGame.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = thisStage;
        assert root != null;
        stage.setScene(new Scene(root));
    }

}
