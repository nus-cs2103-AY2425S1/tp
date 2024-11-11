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
    public static final String ADD_HELP_1 = "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DESCRIPTION c/CLIENT_TYPE...";
    public static final String ADD_HELP_2 = "a n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DESCRIPTION c/CLIENT_TYPE...";
    public static final String LIST_HELP_HEADER = "List all contacts in ClientHub: ";
    public static final String LIST_HELP = "list";
    public static final String EDIT_HELP_HEADER = "Edit contacts: ";
    public static final String EDIT_HELP = "edit INDEX n/NAME p/PHONE e/EMAIL a/ADDRESS d/DESCRIPTION c/CLIENT_TYPE...";
    public static final String DELETE_HELP_HEADER = "Delete contacts by name: ";
    public static final String DELETE_HELP_1 = "delete NAME";
    public static final String DELETE_HELP_2 = "d NAME";
    public static final String DELETE_HELP_3 = "delete NAME$";
    public static final String CLEAR_HELP_HEADER = "Delete all contacts from ClientHub: ";
    public static final String CLEAR_HELP = "clear";
    public static final String FN_HELP_HEADER = "Find contacts by name: ";
    public static final String FN_HELP_1 = "find n/NAME";
    public static final String FN_HELP_2 = "fn NAME";
    public static final String FN_HELP_3 = "find n/NAME$";
    public static final String FP_HELP_HEADER = "Find contacts by phone number: ";
    public static final String FP_HELP_1 = "find p/PHONE";
    public static final String FP_HELP_2 = "fp PHONE";
    public static final String FA_HELP_HEADER = "Find contacts by address: ";
    public static final String FA_HELP_1 = "find a/ADDRESS";
    public static final String FA_HELP_2 = "fa ADDRESS";
    public static final String FC_HELP_HEADER = "Find contacts by Client Type: ";
    public static final String FC_HELP_1 = "find c/CLIENT_TYPE";
    public static final String FC_HELP_2 = "fc CLIENT_TYPE";
    public static final String SORT_HELP_HEADER = "Sort contacts by name: ";
    public static final String SORT_HELP = "sort";
    public static final String VIEW_HELP_HEADER = "View a contact's full information: ";
    public static final String VIEW_HELP_1 = "view NAME";
    public static final String VIEW_HELP_2 = "v NAME";
    public static final String VIEW_HELP_3 = "view NAME$";
    public static final String RA_HELP_HEADER = "Add a reminder for a contact: ";
    public static final String RA_HELP_1 = "radd n/NAME dt/DATE and TIME d/DESCRIPTION";
    public static final String RA_HELP_2 = "ra n/NAME dt/DATE and TIME d/DESCRIPTION";
    public static final String RA_HELP_3 = "radd n/NAME$ dt/DATE and TIME d/DESCRIPTION";
    public static final String RE_HELP_HEADER = "Edit a reminder for a contact: ";
    public static final String RE_HELP_1 = "redit INDEX [dt/DATE and TIME] [d/DESCRIPTION]";
    public static final String RE_HELP_2 = "re INDEX [dt/DATE and TIME] [d/DESCRIPTION]";
    public static final String RD_HELP_HEADER = "Delete a reminder for a contact: ";
    public static final String RD_HELP_1 = "rdelete INDEX";
    public static final String RD_HELP_2 = "rd INDEX";
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
    private Label addHelp1;

    @FXML
    private Label addHelp2;

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
    private Label deleteHelp1;

    @FXML
    private Label deleteHelp2;

    @FXML
    private Label deleteHelp3;

    @FXML
    private Label clearHelpHeader;

    @FXML
    private Label clearHelp;

    @FXML
    private Label fnHelpHeader;

    @FXML
    private Label fnHelp1;

    @FXML
    private Label fnHelp2;

    @FXML
    private Label fnHelp3;

    @FXML
    private Label fpHelpHeader;

    @FXML
    private Label fpHelp1;

    @FXML
    private Label fpHelp2;

    @FXML
    private Label faHelpHeader;

    @FXML
    private Label faHelp1;

    @FXML
    private Label faHelp2;

    @FXML
    private Label fcHelpHeader;

    @FXML
    private Label fcHelp1;

    @FXML
    private Label fcHelp2;

    @FXML
    private Label sortHelpHeader;

    @FXML
    private Label sortHelp;

    @FXML
    private Label viewHelpHeader;

    @FXML
    private Label viewHelp1;

    @FXML
    private Label viewHelp2;

    @FXML
    private Label viewHelp3;

    @FXML
    private Label raHelpHeader;

    @FXML
    private Label raHelp1;

    @FXML
    private Label raHelp2;

    @FXML
    private Label raHelp3;

    @FXML
    private Label reHelpHeader;

    @FXML
    private Label reHelp1;

    @FXML
    private Label reHelp2;

    @FXML
    private Label rdHelpHeader;

    @FXML
    private Label rdHelp1;

    @FXML
    private Label rdHelp2;

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
        raHelpHeader.setText(RA_HELP_HEADER);
        reHelpHeader.setText(RE_HELP_HEADER);
        rdHelpHeader.setText(RD_HELP_HEADER);
        exitHelpHeader.setText(EXIT_HELP_HEADER);

        addHelp1.setText(ADD_HELP_1);
        addHelp2.setText(ADD_HELP_2);
        listHelp.setText(LIST_HELP);
        editHelp.setText(EDIT_HELP);
        deleteHelp1.setText(DELETE_HELP_1);
        deleteHelp2.setText(DELETE_HELP_2);
        deleteHelp3.setText(DELETE_HELP_3);
        clearHelp.setText(CLEAR_HELP);
        fnHelp1.setText(FN_HELP_1);
        fnHelp2.setText(FN_HELP_2);
        fnHelp3.setText(FN_HELP_3);
        fpHelp1.setText(FP_HELP_1);
        fpHelp2.setText(FP_HELP_2);
        faHelp1.setText(FA_HELP_1);
        faHelp2.setText(FA_HELP_2);
        fcHelp1.setText(FC_HELP_1);
        fcHelp2.setText(FC_HELP_2);
        sortHelp.setText(SORT_HELP);
        viewHelp1.setText(VIEW_HELP_1);
        viewHelp2.setText(VIEW_HELP_2);
        viewHelp3.setText(VIEW_HELP_3);
        raHelp1.setText(RA_HELP_1);
        raHelp2.setText(RA_HELP_2);
        raHelp3.setText(RA_HELP_3);
        reHelp1.setText(RE_HELP_1);
        reHelp2.setText(RE_HELP_2);
        rdHelp1.setText(RD_HELP_1);
        rdHelp2.setText(RD_HELP_2);
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
