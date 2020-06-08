package PreGame;

import Game.SpaceInvadersApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public void startGameButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        new SpaceInvadersApp().start(stage);
        BackgroundMusic.kill.start();
    }

    public void exitButton(ActionEvent actionEvent) {
        BackgroundMusic.kill.start();
        System.exit(1989);
    }
}
