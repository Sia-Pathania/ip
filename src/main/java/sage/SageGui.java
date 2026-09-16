package sage;

import java.io.IOException;

import javafx.application.Application;
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

    @Override
    public void start(Stage stage) throws IOException {
        sage = new Sage();
        conversation = new VBox(12);
        conversation.setPadding(new Insets(16));
        conversation.setStyle("-fx-background-color: #f4f7fb;");
        addAppMessage("Hello! I'm Sage.\nI'm here whenever you feel like chatting!");
        ScrollPane messageScroll = new ScrollPane(conversation);
        messageScroll.setFitToWidth(true);
        messageScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messageScroll.setStyle("-fx-background: #f4f7fb; -fx-background-color: #f4f7fb;");
        TextField input = new TextField();
        Button send = new Button("Send");
        send.setOnAction(event -> send(input));
        input.setOnAction(event -> send(input));
        HBox controls = new HBox(8, input, send);
        controls.setPadding(new Insets(8));
        HBox.setHgrow(input, Priority.ALWAYS);
        BorderPane root = new BorderPane(messageScroll);
        root.setBottom(controls);
        stage.setTitle("Sage");
        stage.setScene(new Scene(root, 600, 450));
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
    }

    /** Adds a message from Sage to the left side of the conversation. */
    private void addAppMessage(String message) {
        Label bubble = createMessageBubble(message, "#ffffff", "#263238");
        HBox row = new HBox(bubble);
        row.setAlignment(Pos.CENTER_LEFT);
        conversation.getChildren().add(row);
    }

    /** Creates a wrapped, styled message bubble with a readable maximum width. */
    private Label createMessageBubble(String message, String backgroundColor, String textColor) {
        Label bubble = new Label(message);
        bubble.setWrapText(true);
        bubble.setMaxWidth(440);
        bubble.setPadding(new Insets(10, 14, 10, 14));
        bubble.setTextFill(Color.web(textColor));
        bubble.setStyle("-fx-background-color: " + backgroundColor
                + "; -fx-background-radius: 14; -fx-border-radius: 14;"
                + " -fx-border-color: #d5dce5; -fx-border-width: 1;");
        return bubble;
    }
}
