import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class JsonReaderWriter {

    public final static Charset STANDARD_ENCODING = StandardCharsets.UTF_8;

    private final static FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON FILES", "json");

    private static JFileChooser chooser;

    static {
        chooser = new JFileChooser();

        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(filter);
    }

    private final static Frame frame = new Frame();

    public static <T> boolean save(T classInstance, File file) {
        return write(toJson(classInstance), file);
    }

    public static <T> boolean save(T classInstance) {
        return save(classInstance, pickFile());
    }

    public static void readyFileChooser() {
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        frame.toFront();
        frame.setAlwaysOnTop(true);
        frame.requestFocus();
    }

    public static <T> T load(Class<T> type) throws IOException {
        readyFileChooser();
        chooser.setDialogTitle("select a JSON-project to load");
        chooser.showOpenDialog(frame);

        File jsonFile = chooser.getSelectedFile();
        return fromJsonFile(jsonFile, type);
    }

    /**
     *  Function to read any file as a String
     *
     *  @return String containing all characters of the passed file
     */
    private static String readFile(File f, Charset encoding) throws IOException {
        if (f == null) {
            throw new IOException("Reading from a NULL-file is not possible.");
        }
        byte[] encoded = Files.readAllBytes(Paths.get(f.getPath()));
        return new String(encoded, encoding);
    }

    public static File pickFile() {
        readyFileChooser();
        chooser.setDialogTitle("pick a JSON-File to save to");

        File file;
        do {
            chooser.showSaveDialog(frame);
            file = chooser.getSelectedFile();
        }
        while (file == null || file.getName().split("\\.")[0].length() == 0);         // prevent null and empty pre-ending

        // make sure the file ends on <.json>
        String temp = file.getAbsolutePath();
        if (!(temp.split("\\.").length == 2 && temp.endsWith(".json"))) {
            String[] splitFile = temp.split("\\.");
            System.out.println(splitFile[0]);
            file = new File(splitFile[0] + ".json");
        }

        return file;
    }

    public static boolean write(String jsonText, File file) {
        boolean successful = false;
        if (file != null) {
            try (FileWriter fw = new FileWriter(file)) {
                fw.write(jsonText);
                successful = true;
            } catch (IOException e) {
                System.err.println("IOException in JsonReaderWriter.write(String jsonText)." +
                        "Could not create a FileWriter for the selected file.");
                e.printStackTrace();
            }
        }
        return successful;
    }

    // transforming a generic type into a Json-string
    public static <T> String toJson(T classInstance) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(classInstance);
    }

    // transforming a json-string into an object of generic type
    public static <T> T fromJson(String jsonText, Class<T> type) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonText, type);
    }

    // transforming a file containing one class in Json format into a object of that class type
    public static <T> T fromJsonFile(File f, Class<T> type, Charset encoding) throws IOException {
        return fromJson(readFile(f, encoding), type);
    }

    // transforming a file containing one class in Json format into a object of that class type
    public static <T> T fromJsonFile(File f, Class<T> type) throws IOException {
        return fromJsonFile(f, type, STANDARD_ENCODING);
    }
}