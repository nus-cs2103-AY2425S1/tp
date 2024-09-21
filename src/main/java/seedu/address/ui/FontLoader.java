package seedu.address.ui;

import javafx.scene.text.Font;
import seedu.address.MainApp;

public class FontLoader {

    private static final String RESOURCE_PATH = "/fonts/";

    public static void loadFont(String fileName) {
        String fullPathname = RESOURCE_PATH + fileName;
        Font.loadFont(MainApp.class.getResourceAsStream(fullPathname), 18);
    }

    public static void getAvailableFonts() {
        Font.getFontNames().forEach(System.out::println);
    }
}
