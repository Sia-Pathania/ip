import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


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

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        File file = new File(filePath);

        // First time running: file doesn't exist yet
        if (!file.exists()) {
            return tasks;
        }

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            System.out.println(line); // temporary, to check that it reads
        }

        scanner.close();

        return tasks;
    }

}
