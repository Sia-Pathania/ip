package sage;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/** Controller for Sage's main chat window. */
public class MainWindow {
    @FXML private javafx.scene.control.ScrollPane messageScroll;
    @FXML private javafx.scene.layout.VBox conversation;
    @FXML private javafx.scene.control.TextField input;

    private Sage sage;
    private final Image senderImage = loadImage("/images/sender.png");
    private final Image receiverImage = loadImage("/images/receiver.png");

    /** Connects this controller to the Sage command processor. */
    public void setSage(Sage sage) {
        this.sage = sage;
    }

    /** Adds the initial greeting after the FXML controls have been injected. */
    @FXML
    public void initialize() {
        // Keep the conversation readable on wide or maximized windows.
        conversation.setMaxWidth(800);
        conversation.setPrefWidth(800);
        conversation.setFillWidth(false);
        conversation.setAlignment(Pos.TOP_CENTER);
        conversation.setPadding(new Insets(10));
        conversation.setStyle("-fx-background-color: #f4f7fb;");
        messageScroll.setStyle("-fx-background: #f4f7fb; -fx-background-color: #f4f7fb;");
        conversation.heightProperty().addListener(observable -> messageScroll.setVvalue(1.0));
        addReceiverMessage("Hello! I'm Sage.\nI'm here whenever you feel like chatting!");
    }

    /** Processes a command entered by the user and displays both messages. */
    @FXML
    private void send() {
        String command = input.getText().trim();
        if (command.isEmpty()) {
            return;
        }
        addSenderMessage(command);
        try {
            addReceiverMessage(sage.processCommand(command));
            if (sage.isExitCommand(command)) {
                conversation.getScene().getWindow().hide();
            }
        } catch (IOException e) {
            addReceiverMessage("I couldn't save your tasks right now.");
        }
        input.clear();
    }

    private void addSenderMessage(String message) {
        addMessage(DialogBox.getSenderDialog(message, senderImage));
    }

    private void addReceiverMessage(String message) {
        addMessage(DialogBox.getReceiverDialog(message, receiverImage));
    }

    private void addMessage(Node message) {
        conversation.getChildren().add(message);
        messageScroll.setVvalue(1.0);
    }

    private static Image loadImage(String path) {
        return new Image(MainWindow.class.getResourceAsStream(path));
    }
}
