package sage.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import sage.model.Deadline;
import sage.model.Event;
import sage.model.Task;
import sage.model.TaskList;
import sage.model.Todo;

public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public void save(TaskList tasks) throws IOException {
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
                details = ((Deadline) task).getBy().toString();
            } else if (task instanceof Event) {
                type = "E";
                details = ((Event) task).getFrom().toString() + "|"
                        + ((Event) task).getTo().toString();
            } else {
                type = "T";
            }

            writer.write(type + "|" + (task.isDone() ? "1" : "0") + "|"
                    + task.getDescription() + "|" + details);
            writer.write(System.lineSeparator());
        }

        writer.close();
    }

    public TaskList load() throws IOException {
        TaskList tasks = new TaskList();

        if (!Files.exists(filePath)) {
            return tasks;
        }

        for (String line : Files.readAllLines(filePath, StandardCharsets.UTF_8)) {
            String[] parts = line.split("\\|", -1);
            if (parts.length < 4) continue;
            Task task;
            if (parts[0].equals("D")) {
                LocalDateTime dateTime = LocalDateTime.parse(parts[3]);
                task = new Deadline(parts[2], dateTime);
            }
            else if (parts[0].equals("E") && parts.length >= 5) {
                LocalDateTime from = LocalDateTime.parse(parts[3]);
                LocalDateTime to = LocalDateTime.parse(parts[4]);
                task = new Event(parts[2], from, to);
            }
            else if (parts[0].equals("T")) {
                task = new Todo(parts[2]);
            }
            else continue;

            if (parts[1].equals("1")) {
                task.markAsDone();
            }
            tasks.add(task);
        }
        return tasks;
    }

}
