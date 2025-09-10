package helperbot.ui;

import java.util.Objects;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private Stage stage;

    private HelperBot helperBot;

    private final Image userImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(
            "/images/User.jpeg")));
    private final Image helperBotImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(
            "/images/HelperBot.jpeg")));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setHelperBot(HelperBot helperBot) {
        this.helperBot = helperBot;
        greet();
    }

    /** Injects state **/
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Greet the user.
     */
    private void greet() {
        dialogContainer.getChildren().add(DialogBox.getHelperBotDialog(helperBot.greet(), helperBotImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing HelperBot's reply and then appends
     * them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = helperBot.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getHelperBotDialog(response, helperBotImage)
        );
        userInput.clear();

        if (input.equalsIgnoreCase("bye")) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                // This code will run after the 1-second delay
                this.stage.close();
            });
            // Start the timer
            pause.play();
        }
    }
}

