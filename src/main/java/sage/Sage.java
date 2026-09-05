package sage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sage.command.Command;
import sage.exception.SageException;
import sage.model.TaskList;
import sage.parser.Parser;
import sage.storage.Storage;
import sage.ui.Ui;

/** Coordinates Sage's parser, commands, task list, and storage. */
public class Sage {
    private final Parser parser;
    private final Storage storage;
    private final TaskList tasks;

    /** Creates Sage using the default persistent task file. */
    public Sage() throws IOException { this("data/sage.txt"); }

    /** Creates Sage using a supplied persistent task file. */
    public Sage(String filePath) throws IOException {
        parser = new Parser();
        storage = new Storage(filePath);
        tasks = storage.load();
    }

    /** Processes one command and returns the actual Sage response. */
    public String processCommand(String input) throws IOException {
        List<String> messages = new ArrayList<>();
        Ui output = new Ui(messages::add);
        try {
            Command command = parser.parseCommand(input);
            if (command == null) {
                messages.add("I didn't quite catch that. You can add a todo, deadline, or event whenever you're ready. Could you try again?");
            } else {
                command.execute(tasks, output, storage);
            }
        } catch (SageException e) {
            messages.add(e.getMessage());
        }
        return String.join(System.lineSeparator(), messages);
    }

    /** Returns whether the supplied input is the exit command. */
    public boolean isExitCommand(String input) { return parser.getCommandName(input).equals("bye"); }

    /** Runs the terminal interface. */
    public static void main(String[] args) throws IOException {
        Sage sage = new Sage();
        Ui ui = new Ui();
        ui.showWelcome();
        try (ui) {
            String input;
            while ((input = ui.readCommand()) != null) {
                ui.showDivider();
                String response = sage.processCommand(input);
                if (!response.isEmpty()) { ui.show(response.replace(System.lineSeparator(), "\n")); }
                ui.showDivider();
                if (sage.isExitCommand(input)) { break; }
            }
        }
    }
}
