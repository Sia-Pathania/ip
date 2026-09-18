package sage;

import javafx.application.Application;

/** Launches Sage's JavaFX application. */
public class Launcher {
    /** Starts JavaFX through the application launcher.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        Application.launch(SageGui.class, args);
    }
}
