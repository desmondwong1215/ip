package helperbot.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private final HelperBot helperBot = new HelperBot("src/main/java/helperbot/storage/data/HelperBot.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setHelperBot(this.helperBot);
            fxmlLoader.<MainWindow>getController().setStage(stage);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent event) {
                    Main.this.helperBot.saveToFile();
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
