package yoyo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

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

    private YoyoAdapter yoyo;

    private Image userImage;
    private Image dukeImage;

    @FXML
    public void initialize() {
        // Load images
        try {
            userImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaUser.png")));
            dukeImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaDuke.png")));
        } catch (Exception e) {
            // Use null images - DialogBox should handle this gracefully
            userImage = null;
            dukeImage = null;
        }

        // Auto-scroll to bottom when content grows
        dialogContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollPane.setVvalue(1.0);
        });

        // Add initial greeting
        addBotLine("Hello! I'm Yoyo. How can I help?");
    }

    /**
     * Injects the YoyoAdapter instance
     */
    public void setYoyoAdapter(YoyoAdapter y) {
        yoyo = y;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Yoyo's reply and then appends them to the dialog container. Clears the
     * user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank()) {
            return;
        }

        addUserLine(input);
        String response = yoyo.respond(input);
        addBotLine(response);

        userInput.clear();

        yoyo.shouldExit(input);// Close gracefully after a short delay (optional)
// Platform.exit();
    }

    private void addUserLine(String text) {
        DialogBox box = DialogBox.getUserDialog(text, userImage);
        dialogContainer.getChildren().add(box);
    }

    private void addBotLine(String text) {
        DialogBox box = DialogBox.getDukeDialog(text, dukeImage);
        dialogContainer.getChildren().add(box);
    }
}
