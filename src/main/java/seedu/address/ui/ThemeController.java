package seedu.address.ui;

import java.util.Objects;
import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.stage.Stage;
import seedu.address.storage.ThemePreference;

/**
 * This class serves as the controller for the GUI Themes
 */
public class ThemeController {
    private static final Logger logger = Logger.getLogger(ThemeController.class.getName());
    private static Scene scene;
    private static Stage stage;
    private static ThemePreference themePreference;

    /**
     * Sets up the scene and stage to be updated
     * @param primaryScene scene to be updated
     * @param primaryStage stage to be updated
     */
    public static void setUp(Scene primaryScene, Stage primaryStage, ThemePreference currentPreference) {
        scene = primaryScene;
        stage = primaryStage;
        themePreference = currentPreference;
        switchTheme(themePreference.getTheme());
    }

    /**
     * Switch the theme based on user input
     * @param theme
     */
    public static void switchTheme(String theme) {
        scene.getStylesheets().clear(); // Clear current stylesheets
        assert (Objects.equals(theme, "DARK") || Objects.equals(theme, "LIGHT"));

        switch (theme) {
        case "DARK":
            scene.getStylesheets().add(Objects.requireNonNull(ThemeController.class
                .getResource("/view/DarkTheme.css")).toExternalForm());
            themePreference.setTheme(theme);
            logger.info("Theme is changed to: " + theme);
            break;
        case "LIGHT":
            scene.getStylesheets().add(Objects.requireNonNull(ThemeController.class
                .getResource("/view/LightTheme.css")).toExternalForm());
            themePreference.setTheme(theme);
            logger.info("Theme is changed to: " + theme);
            break;
        default:
            logger.info("Unknown Theme: " + theme);
        }
    }

}
