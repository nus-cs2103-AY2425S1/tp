package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "Refer to the User Guide for more info: "
            + "https://ay2425s1-cs2103t-w11-1a.github.io/tp/UserGuide.html";

    public static final String URL_ONLY = "https://ay2425s1-cs2103t-w11-1a.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "List of commands: \n\n"
            + "<<Viewing help>>\n"
            + "Format: help\n\n"
            + "<<Adding a person>>\n"
            + "Format: add n/NAME p/PHONE_NUMBER e/EMAIL i/NRIC a/ADDRESS t/TRIAGE [tag/TAG]\n\n"
            + "<<Adding a remark>>\n"
            + "Format: remark NRIC r/REMARK\n\n"
            + "<<Adding an appointment>>\n"
            + "Format: appointment NRIC app/APPOINTMENT\n\n"
            + "<<Changing Triage Stages>>\n"
            + "Format: triage NRIC t/TRIAGE\n\n"
            + "<<Editing a person>>\n"
            + "Format: edit NRIC [n/NAME] [p/PHONE] [e/EMAIL] [i/NRIC] "
            + "[a/ADDRESS] [t/TRIAGE] [app/APPOINTMENT] [tag/TAG]\n\n"
            + "<<Logging patient information>>\n"
            + "Format: log NRIC DD-MM-YYYY HH:MM INFO\n\n"
            + "<<Listing all persons>>\n"
            + "Format: list\n\n"
            + "<<Sort persons by name or appointment>>\n"
            + "Format: sort name\n"
            + "Format: sort appointment\n\n"
            + "<<Schedule patients by appointment time>>\n"
            + "Format: schedule\n\n"
            + "<<Locating persons by name>>\n"
            + "Format: find KEYWORD [MORE_KEYWORDS]\n"
            + "Format: find tag/TAG [MORE_TAGS]\n\n"
            + "<<View a person's details>>\n"
            + "Format: view NRIC\n\n"
            + "<<Deleting a person>>\n"
            + "Format: delete NRIC\n\n"
            + "<<Clearing all entries>>\n"
            + "Format: clear\n\n"
            + "<<Exiting the program>>\n"
            + "Format: exit\n\n"
            + "List of SHORTCUT Commands: \n"
            + "add -> a | appointment -> appt | clear -> c | delete -> d | edit -> ed | exit -> ex\n"
            + "find -> f | help -> h | list -> l | remark -> r | sort -> s | view -> v | triage -> t\n\n";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label ugLink;

    @FXML
    private Label helpMessage;

    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        ugLink.setText(USERGUIDE_URL);

        Screen screen = Screen.getPrimary();
        double width = screen.getBounds().getWidth() * 0.8;
        double height = screen.getBounds().getHeight() * 0.8;

        root.setWidth(width);
        root.setHeight(height);

        // Make the window resizable
        root.setResizable(true);

        // Make the window draggable
        getRoot().getScene().setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        getRoot().getScene().setOnMouseDragged(event -> {
            getRoot().setX(event.getScreenX() - xOffset);
            getRoot().setY(event.getScreenY() - yOffset);
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
        url.putString(URL_ONLY);
        clipboard.setContent(url);
    }
}
