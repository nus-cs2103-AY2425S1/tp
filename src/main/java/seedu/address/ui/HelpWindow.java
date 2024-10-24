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
    public static final String ADD_HELP_HEADER = "Add contacts: ";
    public static final String ADD_HELP = "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DESCRIPTION [c/CLIENT_TYPE]";
    public static final String LIST_HELP_HEADER = "List all contacts in ClientHub: ";
    public static final String LIST_HELP = "list";
    public static final String EDIT_HELP_HEADER = "Edit contacts: ";
    public static final String EDIT_HELP = "edit INDEX n/NAME p/PHONE e/EMAIL a/ADDRESS d/DESCRIPTION c/CLIENT_TYPE";
    public static final String DELETE_HELP_HEADER = "Delete contacts by name: ";
    public static final String DELETE_HELP = "delete NAME   or  " + "d NAME    or    " + "delete NAME/";
    public static final String CLEAR_HELP_HEADER = "Delete all contacts from ClientHub: ";
    public static final String CLEAR_HELP = "clear";
    public static final String FN_HELP_HEADER = "Find contacts by name: ";
    public static final String FN_HELP = "find n/NAME   or   " + "fn NAME";
    public static final String FP_HELP_HEADER = "Find contacts by phone number: ";
    public static final String FP_HELP = "find p/PHONE   or   " + "fp PHONE";
    public static final String FA_HELP_HEADER = "Find contacts by address: ";
    public static final String FA_HELP = "find a/ADDRESS    or    " + "fa ADDRESS";
    public static final String FC_HELP_HEADER = "Find contacts by Client Type: ";
    public static final String FC_HELP = "find c/CLIENT_TYPE    or   " + "fc CLIENT_TYPE";
    public static final String SORT_HELP_HEADER = "Sort contacts by nameL: ";
    public static final String SORT_HELP = "sort n/";
    public static final String VIEW_HELP_HEADER = "View a contact's full information: ";
    public static final String VIEW_HELP = "view NAME";
    public static final String EXIT_HELP_HEADER = "Exit and close ClientHub: ";
    public static final String EXIT_HELP = "exit";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private Label addHelpHeader;

    @FXML
    private Label addHelp;

    @FXML
    private Label listHelpHeader;

    @FXML
    private Label listHelp;

    @FXML
    private Label editHelpHeader;

    @FXML
    private Label editHelp;

    @FXML
    private Label deleteHelpHeader;


    @FXML
    private Label deleteHelp;

    @FXML
    private Label clearHelpHeader;

    @FXML
    private Label clearHelp;

    @FXML
    private Label fnHelpHeader;

    @FXML
    private Label fnHelp;

    @FXML
    private Label fpHelpHeader;

    @FXML
    private Label fpHelp;

    @FXML
    private Label faHelpHeader;

    @FXML
    private Label faHelp;

    @FXML
    private Label fcHelpHeader;

    @FXML
    private Label fcHelp;

    @FXML
    private Label sortHelpHeader;
    @FXML
    private Label sortHelp;

    @FXML
    private Label viewHelpHeader;

    @FXML
    private Label viewHelp;

    @FXML
    private Label exitHelpHeader;

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

        addHelpHeader.setText(ADD_HELP_HEADER);
        listHelpHeader.setText(LIST_HELP_HEADER);
        editHelpHeader.setText(EDIT_HELP_HEADER);
        deleteHelpHeader.setText(DELETE_HELP_HEADER);
        clearHelpHeader.setText(CLEAR_HELP_HEADER);
        fnHelpHeader.setText(FN_HELP_HEADER);
        fpHelpHeader.setText(FP_HELP_HEADER);
        faHelpHeader.setText(FA_HELP_HEADER);
        fcHelpHeader.setText(FC_HELP_HEADER);
        sortHelpHeader.setText(SORT_HELP_HEADER);
        viewHelpHeader.setText(VIEW_HELP_HEADER);
        exitHelpHeader.setText(EXIT_HELP_HEADER);

        addHelp.setText(ADD_HELP);
        listHelp.setText(LIST_HELP);
        editHelp.setText(EDIT_HELP);
        deleteHelp.setText(DELETE_HELP);
        clearHelp.setText(CLEAR_HELP);
        fnHelp.setText(FN_HELP);
        fpHelp.setText(FP_HELP);
        faHelp.setText(FA_HELP);
        fcHelp.setText(FC_HELP);
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
