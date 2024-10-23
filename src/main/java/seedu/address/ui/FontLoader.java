package seedu.address.ui;

import javafx.scene.text.Font;
import seedu.address.MainApp;

/**
 * Utility class with methods to load custom fonts.
 */
public class FontLoader {

    private static final String RESOURCE_PATH = "/fonts/";

    /**
     * Loads a font from a file.
     *
     * @param fileName The name of the file to load from.
     */
    public static void loadFont(String fileName) {
        String fullPathname = RESOURCE_PATH + fileName;
        Font.loadFont(MainApp.class.getResourceAsStream(fullPathname), 18);
    }

    /**
     * Prints the names of all loaded fonts to standard output.
     */
    public static void getAvailableFonts() {
        Font.getFontNames().forEach(System.out::println);
    }
}
