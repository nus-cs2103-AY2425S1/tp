package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-f10-1.github.io/tp/UserGuide.html";
    public static final String USERGUIDE_REF = "For more info, refer to the user guide: \n" + USERGUIDE_URL;

    public static final String ADD_HELP = "Add contacts: \n" +
            "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DESCRIPTION [c/CLIENT_TYPE]";

    public static final String LIST_HELP = "List all contacts: \n" +
            "list";

    public static final String EDIT_HELP = "Edit contacts by index: \n" +
            "edit INDEX n/NAME p/PHONE e/EMAIL a/ADDRESS d/DESCRIPTION c/CLIENT_TYPE";

    public static final String DELETE_HELP = "Delete contacts by name: \n" +
            "delete NAME   or  " + "d NAME    or    " + "delete NAME/";

    public static final String CLEAR_HELP = "Delete all contacts in ClientHub: \n" + "clear";

    public static final String FIND_NAME_HELP = "Find contacts by name: \n" +
            "find n/NAME   or   " + "fn NAME";

    public static final String FIND_PHONE_HELP = "Find contacts by phone number: \n" +
            "find p/PHONE   or   " + "fp PHONE";

    public static final String FIND_ADDRESS_HELP = "Find contacts by address: \n" +
            "find a/ADDRESS    or    " + "fa ADDRESS";

    public static final String FIND_CLIENT_TYPE_HELP = "Find contacts by client type: \n" +
            "find c/CLIENT_TYPE    or   " + "fc CLIENT_TYPE";

    public static final String SORT_HELP = "Sort contacts by name: \n" + "sort n/";

    public static final String VIEW_HELP = "View a contact's full information: \n" + "view NAME";

    public static final String EXIT_HELP = "Exit and close the program: \n" + "exit";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private Label addHelp;

    @FXML
    private Label listHelp;

    @FXML
    private Label editHelp;

    @FXML
    private Label deleteHelp;

    @FXML
    private Label clearHelp;

    @FXML
    private Label findNameHelp;

    @FXML
    private Label findPhoneHelp;

    @FXML
    private Label findAddressHelp;

    @FXML
    private Label findClientTypeHelp;
    @FXML
    private Label sortHelp;

    @FXML
    private Label viewHelp;

    @FXML
    private Label exitHelp;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(USERGUIDE_REF);
        addHelp.setText(ADD_HELP);
        listHelp.setText(LIST_HELP);
        editHelp.setText(EDIT_HELP);
        deleteHelp.setText(DELETE_HELP);
        clearHelp.setText(CLEAR_HELP);
        findNameHelp.setText(FIND_NAME_HELP);
        findPhoneHelp.setText(FIND_PHONE_HELP);
        findAddressHelp.setText(FIND_ADDRESS_HELP);
        findClientTypeHelp.setText(FIND_CLIENT_TYPE_HELP);
        sortHelp.setText(SORT_HELP);
        viewHelp.setText(VIEW_HELP);
        exitHelp.setText(EXIT_HELP);
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
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
