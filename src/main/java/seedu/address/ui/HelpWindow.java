package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
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

    // For implementing web functionality -- we need to get the user's current hosting service
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

        // hyperlink setups: set hyperlink as unclicked, hyperlink text, handle onAction logic
        setHyperlink();
        ugLink.setText(USER_GUIDE_URL);
        ugLink.setOnAction(event -> {
            currApp.getHostServices().showDocument(USER_GUIDE_URL);
            this.setHyperlink();
        });
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
        logger.fine("Showing help page for CampusConnect.");
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

    /**
     * Resets the hyperlink to an unclicked state.
     */
    public void resetHyperlink() {
        ugLink.setVisited(false);
    }

    /**
     * Sets hyperlink to a clicked state.
     * No other class or method should be touching this except the event handler of the hyperlink in HelpWindow,
     * where the hyperlink logic is handled.
     */
    private void setHyperlink() {
        ugLink.setVisited(true);
    }

    /**
     * Handles extraction of default browser to HelpWindow.
     * This is to enable the use the {@code getInstance} method, which gets the user's default browser.
     * Necessary for hyperlink to open.
     */
    private static class CampusConnectApp extends Application {
        private static CampusConnectApp mInstance;

        public static CampusConnectApp getInstance() {
            return mInstance;
        }

        /**
         * Empty overridden start method from {@code Application}.
         * This is to enable the extension of the abstract {@code Application} class.
         */
        @Override
        public void start(Stage primaryStage) {
        }
    }
}
