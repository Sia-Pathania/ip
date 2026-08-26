import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        FileWriter writer = new FileWriter(filePath);

        for (Task task : tasks) {
            writer.write(task.toString() + System.lineSeparator());
        }

        writer.close();
    }
}
