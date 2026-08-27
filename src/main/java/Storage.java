import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath.toUri());

        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }

        FileWriter writer = new FileWriter(file);

        for (Task task : tasks) {
            String type;
            String details = "";

            if (task instanceof Deadline) {
                type = "D";
                details = ((Deadline) task).getBy();
            } else if (task instanceof Event) {
                type = "E";
                details = ((Event) task).getFrom() + "|"
                        + ((Event) task).getTo();
            } else {
                type = "T";
            }

            writer.write(type + "|" + (task.isDone() ? "1" : "0") + "|"
                    + task.getDescription() + "|" + details);
            writer.write(System.lineSeparator());
        }

        writer.close();
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return tasks;
        }
    }

}
