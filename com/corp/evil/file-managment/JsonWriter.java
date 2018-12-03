import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public final class JsonWriter {

    private static File file;
    private static final FileChooser fc;
    private static boolean fileSet = false;

    // initialise final file chooser
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


}
