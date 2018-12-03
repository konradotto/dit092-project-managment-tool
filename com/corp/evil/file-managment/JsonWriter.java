import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class JsonWriter {

    private static File file;
    private static final FileChooser fc;
    private static boolean fileSet = false;

    // initialise final file chooser (used to select a file)
    static {
        FileChooser fcTemp = new FileChooser();
        fcTemp.setTitle("Pick a JSON-file to save to.");
        fcTemp.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));

        fc = fcTemp;
    }

    public static void pickFile() {
        File file = fc.showOpenDialog(new Stage());
        fileSet = (file != null);
    }

    public static boolean write(String jsonText) {
        boolean successful = false;
        if (fileSet) {
            try (FileWriter fw = new FileWriter(file)) {
                fw.write(jsonText);
                successful = true;
            } catch (IOException e) {
                System.err.println("IOException in JsonWriter.write(String jsonText)." +
                        "Could not create a FileWriter for the selected file.");
                e.printStackTrace();
            }
        }
        return successful;
    }


}
