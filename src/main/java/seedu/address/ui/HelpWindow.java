package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103-f12-3.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Welcome to Grub! \n"
            + "Here are some of the commands to help you get started:"
            + "\n\t"
            + "add:\t\tAdds a restaurant to the address book."
            + "\n\t"
            + "list:\t\tShows a list of all restaurants in the address book."
            + "\n\t"
            + "edit:\t\tEdits an existing restaurant in the address book."
            + "\n\t"
            + "find:\t\tFinds restaurants whose names contain any of the given keywords."
            + "\n\t"
            + "tags:\t\tFinds restaurants whose tags contain any of the given keywords."
            + "\n\t"
            + "delete:\tDeletes the specified restaurant from the address book."
            + "\n\t"
            + "rate:\t\tRates the specified restaurant from the address book."
            + "\n\t"
            + "fav:\t\tMark the specified restaurant as a favorite, displaying favorites at the top."
            + "\n\t"
            + "unfav:\tUn-mark the specified restaurant as a favorite"
            + "\n\t"
            + "price:\tFinds restaurants who belong in any of the given price labels"
            + "\n\t"
            + "\nFor the full list, refer to the user guide: ";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private final HostServices hostServices;

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private Hyperlink helpLink;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root, HostServices hostServices) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);

        this.hostServices = hostServices;
        helpLink.setText(USERGUIDE_URL);
        helpLink.setOnAction(e -> hostServices.showDocument(USERGUIDE_URL));
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow(HostServices hostServices) {
        this(new Stage(), hostServices);
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

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Exits the help window.
     */
    @FXML
    private void closeWindow() {
        getRoot().hide();
    }
}
