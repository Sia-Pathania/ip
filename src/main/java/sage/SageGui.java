package sage;

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/** JavaFX frontend for Sage's command-processing service. */
public class SageGui extends Application {
    private Sage sage;
    private TextArea conversation;

    @Override
    public void start(Stage stage) throws IOException {
        sage = new Sage();
        conversation = new TextArea("Sage:\nHello! I'm Sage.\nI'm here whenever you feel like chatting!\n\n");
        conversation.setEditable(false);
        conversation.setWrapText(true);
        TextField input = new TextField();
        Button send = new Button("Send");
        send.setOnAction(event -> send(input));
        input.setOnAction(event -> send(input));
        HBox controls = new HBox(8, input, send);
        controls.setPadding(new Insets(8));
        HBox.setHgrow(input, Priority.ALWAYS);
        BorderPane root = new BorderPane(conversation);
        root.setBottom(controls);
        stage.setTitle("Sage");
        stage.setScene(new Scene(root, 600, 450));
        stage.show();
    }

    private void send(TextField input) {
        String command = input.getText().trim();
        if (command.isEmpty()) { return; }
        conversation.appendText("You:\n" + command + "\n\nSage:\n");
        try {
            conversation.appendText(sage.processCommand(command) + "\n\n");
            if (sage.isExitCommand(command)) { conversation.getScene().getWindow().hide(); }
        } catch (IOException e) {
            conversation.appendText("I couldn't save your tasks right now.\n\n");
        }
        input.clear();
    }
}
