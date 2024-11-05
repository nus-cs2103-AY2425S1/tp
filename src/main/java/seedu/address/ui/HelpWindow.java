package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USER_GUIDE_URL = "https://ay2425s1-cs2103t-f14a-4.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Need some help? Refer to our user guide at: ";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    private final Application currApp = new CampusConnectApp();

    @FXML
    private Label helpMessage;

    @FXML
    private Hyperlink ugLink;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        ugLink.setText(USER_GUIDE_URL);
        ugLink.setOnAction(event -> currApp.getHostServices().showDocument(USER_GUIDE_URL));
        ugLink.setVisited(false);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    private class CampusConnectApp extends Application {
        private static CampusConnectApp mInstance;
        public static void main(String[] args) throws Exception { launch(args); }

        public static CampusConnectApp getInstance() {
            return mInstance;
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            // your code
        }
    }
}
