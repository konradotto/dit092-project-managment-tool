import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
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

    private final static FileFilter filter = new FileFilter() {

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return false;
            } else {
                String filename = file.getName().toLowerCase();
                return filename.endsWith(".json");
            }
        }

        @Override
        public String getDescription() {
            return "JSON Files (*.json)";
        }
    };

    private final static Frame frame = new Frame();

    public static <T> boolean save(T classInstance, File file) {
        return write(toJson(classInstance), file);
    }

    public static <T> boolean save(T classInstance) {
        return save(classInstance, pickFile());
    }

    public static <T> T load(Class<T> type) throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("select a JSON-project to load");

        chooser.setFileFilter(filter);
        frame.toFront();
        frame.setAlwaysOnTop(true);
        frame.requestFocus();
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
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("select a JSON-project to load");
        chooser.setFileFilter(filter);

        chooser.showSaveDialog(frame);
        return chooser.getSelectedFile();
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