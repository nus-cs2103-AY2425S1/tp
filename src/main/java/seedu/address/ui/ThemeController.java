package seedu.address.ui;

import java.util.Objects;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class serves as the controller for the GUI Themes
 */
public class ThemeController {
    private static Scene scene;
    private static Stage stage;

    /**
     * Sets up the scene and stage to be updated
     * @param primaryScene scene to be updated
     * @param primaryStage stage to be updated
     */
    public static void setUp(Scene primaryScene, Stage primaryStage) {
        scene = primaryScene;
        stage = primaryStage;
    }

    /**
     * Switch the theme based on user input
     * @param theme
     */
    public static void switchTheme(String theme) {
        scene.getStylesheets().clear(); // Clear current stylesheets
        switch (theme) {
        case "DARK":
            scene.getStylesheets().add(Objects.requireNonNull(ThemeController.class
                .getResource("/view/DarkTheme.css")).toExternalForm());
            break;
        case "LIGHT":
            scene.getStylesheets().add(Objects.requireNonNull(ThemeController.class
                .getResource("/view/LightTheme.css")).toExternalForm());
            break;
        default:
            System.out.println("Unknown theme: " + theme);
        }
    }

}
