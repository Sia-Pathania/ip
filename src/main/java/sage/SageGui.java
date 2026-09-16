package sage;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/** JavaFX frontend for Sage's command-processing service. */
public class SageGui extends Application {
    private Sage sage;
    private VBox conversation;
    private ScrollPane messageScroll;

    @Override
    public void start(Stage stage) throws IOException {
        sage = new Sage();
        conversation = new VBox(8);
        conversation.setPadding(new Insets(10));
        conversation.setStyle("-fx-background-color: #f4f7fb;");
        addAppMessage("Hello! I'm Sage.\nI'm here whenever you feel like chatting!");
        messageScroll = new ScrollPane(conversation);
        messageScroll.setFitToWidth(true);
        messageScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messageScroll.setStyle("-fx-background: #f4f7fb; -fx-background-color: #f4f7fb;"
                + " -fx-border-color: transparent;");
        TextField input = new TextField();
        input.setPromptText("Type a command...");
        input.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;"
                + " -fx-border-color: #cbd5e1; -fx-padding: 7 10 7 10;");
        Button send = new Button("Send");
        send.setStyle("-fx-background-color: #315d85; -fx-text-fill: white;"
                + " -fx-background-radius: 8; -fx-padding: 7 12 7 12;");
        send.setOnAction(event -> send(input));
        input.setOnAction(event -> send(input));
        HBox controls = new HBox(6, input, send);
        controls.setPadding(new Insets(6));
        controls.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d5dce5 transparent transparent transparent;");
        HBox.setHgrow(input, Priority.ALWAYS);
        BorderPane root = new BorderPane(messageScroll);
        root.setBottom(controls);
        stage.setTitle("Sage");
        stage.setScene(new Scene(root, 560, 420));
        stage.show();
    }

    private void send(TextField input) {
        String command = input.getText().trim();
        if (command.isEmpty()) {
            return;
        }
        addUserMessage(command);
        try {
            addAppMessage(sage.processCommand(command));
            if (sage.isExitCommand(command)) {
                conversation.getScene().getWindow().hide();
            }
        } catch (IOException e) {
            addAppMessage("I couldn't save your tasks right now.");
        }
        input.clear();
    }

    /** Adds a message from the user to the right side of the conversation. */
    private void addUserMessage(String message) {
        Label bubble = createMessageBubble(message, "#d8eaff", "#12304a");
        HBox row = new HBox(bubble);
        row.setAlignment(Pos.CENTER_RIGHT);
        conversation.getChildren().add(row);
        scrollToLatestMessage();
    }

    /** Adds a message from Sage to the left side of the conversation. */
    private void addAppMessage(String message) {
        Label bubble = createMessageBubble(message, "#ffffff", "#263238");
        HBox row = new HBox(bubble);
        row.setAlignment(Pos.CENTER_LEFT);
        conversation.getChildren().add(row);
        scrollToLatestMessage();
    }

    /** Creates a wrapped, styled message bubble with a readable maximum width. */
    private Label createMessageBubble(String message, String backgroundColor, String textColor) {
        Label bubble = new Label(message);
        bubble.setWrapText(true);
        bubble.maxWidthProperty().bind(conversation.widthProperty().multiply(0.9));
        bubble.setPadding(new Insets(7, 10, 7, 10));
        bubble.setTextFill(Color.web(textColor));
        bubble.setStyle("-fx-background-color: " + backgroundColor
                + "; -fx-background-radius: 10; -fx-border-radius: 10;"
                + " -fx-border-color: #d5dce5; -fx-border-width: 1;");
        return bubble;
    }

    /** Scrolls the conversation to the newest message after JavaFX lays it out. */
    private void scrollToLatestMessage() {
        if (messageScroll != null) {
            Platform.runLater(() -> messageScroll.setVvalue(1.0));
        }
    }
}
