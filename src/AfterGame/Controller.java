package AfterGame;

import Game.SpaceInvadersApp;
import PreGame.BackgroundMusic;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    public Label scoreField;

    public void backToMainMenuButton(ActionEvent actionEvent) throws IOException {
        BackgroundMusic.kill.start();
        Parent root = FXMLLoader.load(getClass().getResource("/PreGame/sample.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void exitButton(ActionEvent actionEvent) {
        BackgroundMusic.kill.start();
        System.exit(1989);
    }

    public void initialize() {
        scoreField.setText("Your Score: " + SpaceInvadersApp.point);
    }

    public void restartButton(ActionEvent actionEvent) {
        BackgroundMusic.kill.start();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        new SpaceInvadersApp().start(stage);
    }
}
