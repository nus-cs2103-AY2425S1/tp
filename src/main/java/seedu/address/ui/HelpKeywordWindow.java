package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpKeywordWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(HelpKeywordWindow.class);
    private static final String FXML = "HelpKeywordWindow.fxml";

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox helpKeywordContainer;

    @FXML
    private Label header;

    @FXML
    private Label description;

    @FXML
    private Label parametersHeader;

    @FXML
    private Label parameters;

    @FXML
    private Label usageHeader;

    @FXML
    private Label usage;

    @FXML
    private Label exampleHeader;

    @FXML
    private Label example;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpKeywordWindow(Stage root, String keyword) {
        super(FXML, root);
        setHelpKeywordContent(keyword);
        getRoot().setWidth(500);
        getRoot().setHeight(400);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpKeywordWindow(String keyword) {
        this(new Stage(), keyword);
    }

    private void setHelpKeywordContent(String keyword) {
        switch (keyword.toLowerCase()) {
        case "add":
            setTextAddCommand();
            break;
        case "addf":
            setTextAddfCommand();
            break;
        case "appt":
            setTextApptCommand();
            break;
        case "clear":
            setTextClearCommand();
            break;
        case "deleteappt":
            setTextDeleteApptCommand();
            break;
        case "delete":
            setTextDeleteCommand();
            break;
        case "edit":
            setTextEditCommand();
            break;
        case"exit":
            setTextExitCommand();
            break;
        case "filter":
            setTextFilterCommand();
            break;
        case "list":
            setTextListCommand();
            break;
        case "view":
            setTextViewCommand();
            break;
        default:
            break;
        }
    }

    /**
     * Sets the content of the help window based on the add keyword.
     */
    private void setTextAddCommand() {
        header.setText("Add Command: Adds a new patient record into the system.");
        description.setText("All parameters are compulsory and can be typed in any order.");
        parametersHeader.setText("Parameters:");
        parameters.setText("""
                NAME | NRIC | SEX | DATE OF BIRTH | PHONE NO.
                
                SEX - M / F
                DATE OF BIRTH - YYYY-MM-DD""");
        usageHeader.setText("Command Usage:");
        usage.setText("add n/[NAME] i/[NRIC] s/[SEX] d/[DATE OF BIRTH] p/[PHONE NO.]");
        exampleHeader.setText("Example:");
        example.setText("add n/Abraham Tan i/S9758366N s/M d/1997-10-27 p/87596666");
    }

    /**
     * Sets the content of the help window based on the addf keyword.
     */
    private void setTextAddfCommand() {
        header.setText("Add Full Command: "
                + "Adds a new patient record (with additional information) into the system.");
        description.setText("""
                Compulsory parameters are: NAME | NRIC | SEX | DATE OF BIRTH | PHONE NO.
                Parameters can be typed in any order.
                Multiple allergies can be added using multiple "al/" prefixes""");
        parametersHeader.setText("Parameters:");
        parameters.setText("NAME | NRIC | SEX | DATE OF BIRTH | PHONE NO. | EMAIL | ADDRESS | BLOOD TYPE | "
                + "NEXT-OF-KIN NAME | NEXT-OF-KIN PHONE NO. | ALLERGIES | HEALTH RISK LEVEL | EXISTING CONDITIONS | "
                + "ADDITIONAL NOTE\n\n"
                + "SEX - M / F\nDATE OF BIRTH - YYYY-MM-DD\nHEALTH RISK LEVEL - HIGH / MEDIUM / LOW");
        usageHeader.setText("Command Usage:");
        usage.setText("addf n/[NAME] i/[NRIC] s/[SEX] d/[DATE OF BIRTH] p/[PHONE NO.] e/[EMAIL] "
                + "a/[ADDRESS] b/[BLOOD TYPE] nokn/[NEXT-OF-KIN NAME] nokp/[NEXT-OF-KIN PHONE NO.] "
                + "al/[ALLERGIES] rl/[HEALTH RISK LEVEL] hr/[EXISTING CONDITIONS] no/[ADDITIONAL NOTES]");
        exampleHeader.setText("Example:");
        example.setText("addf n/Abraham Tan i/S9758366N s/M d/1997-10-27 p/87596666 e/abrahamtan@gmail.com "
                + "a/Blk 123, NUS Road, S123123 b/A+ nokn/Lincoln Tan nokp/91234567 "
                + "al/nuts al/shellfish rl/HIGH hr/Diabetes no/Patient needs extra care");
    }

    /**
     * Sets the content of the help window based on the appt keyword.
     */
    private void setTextApptCommand() {
        header.setText("Appointment Command: Records appointment times for registered patients into the system.");
        description.setText("");
        parametersHeader.setText("Parameters:");
        parameters.setText("APPOINTMENT DATE(YYYY-MM-DD), APPOINTMENT TIME(24 HOURS FORMAT) | NRIC");
        usageHeader.setText("Command Usage:");
        usage.setText("appt dt/[APPOINTMENT DATE T APPOINTMENT TIME] i/[NRIC]");
        exampleHeader.setText("Example:");
        example.setText("appt dt/2024-12-29T13:30 i/S9758366N");
    }

    /**
     * Sets the content of the help window based on the clear keyword.
     */
    public void setTextClearCommand() {
        header.setText("Clear Command: Clears all existing system records.");
        description.setText("This command will clear ALL data in the current system. Please use it carefully.");
        parametersHeader.setText("Parameters:");
        parameters.setText("NIL");
        usageHeader.setText("Command Usage:");
        usage.setText("clear");
        exampleHeader.setText("Example:");
        example.setText("clear");
    }

    /**
     * Sets the content of the help window based on the deleteappt keyword.
     */
    public void setTextDeleteApptCommand() {
        header.setText("Delete Appt: Deletes the specified appointment for the identified patient.");
        description.setText("""
                Identifies the specific patient using NRIC and deletes the appointment specified.
                NRIC provided must be a valid NRIC currently in the system.""");
        parametersHeader.setText("Parameters:");
        parameters.setText("");
        usageHeader.setText("Command Usage:");
        usage.setText("");
        exampleHeader.setText("Example:");
        example.setText("");
    }

    /**
     * Sets the content of the help window based on the delete keyword.
     */
    private void setTextDeleteCommand() {
        header.setText("Delete Command: Deletes an existing patient record from the system.");
        description.setText("""
                Identifies the specific patient using NRIC and deletes the entire patient record from the system.
                NRIC provided must be a valid NRIC currently in the system.""");
        parametersHeader.setText("Parameters:");
        parameters.setText("NRIC");
        usageHeader.setText("Command Usage:");
        usage.setText("delete [NRIC]");
        exampleHeader.setText("Example:");
        example.setText("delete S9758366N");
    }

    /**
     * Sets the content of the help window based on the edit keyword.
     */
    private void setTextEditCommand() {
        header.setText("Edit Command: Edits patient's detail(s) for an existing patient record in the system.");
        description.setText("""
                Identifies the specific patient using NRIC and edits the detail(s) specified in the input.
                Details specified in the input will replace existing details in the system.
                NRIC provided must be a valid NRIC currently in the system.
                Input must contain at least one field to be edited.
                Not all fields are compulsory.""");
        parametersHeader.setText("Parameters:");
        parameters.setText("NAME | NRIC | SEX | DATE OF BIRTH | PHONE NO. | EMAIL | ADDRESS | BLOOD TYPE | "
                + "NEXT-OF-KIN NAME | NEXT-OF-KIN PHONE NO. | ALLERGIES | HEALTH RISK LEVEL | EXISTING CONDITIONS | "
                + "ADDITIONAL NOTE\n\n"
                + "SEX - M / F\nDATE OF BIRTH - YYYY-MM-DD\nHEALTH RISK LEVEL - HIGH / MEDIUM / LOW");
        usageHeader.setText("Command Usage (all details are edited):");
        usage.setText("edit [NRIC] n/[NAME] i/[NRIC] s/[SEX] d/[DATE OF BIRTH] p/[PHONE NO.] e/[EMAIL] "
                + "a/[ADDRESS] b/[BLOOD TYPE] nokn/[NEXT-OF-KIN NAME] nokp/[NEXT-OF-KIN PHONE NO.] "
                + "al/[ALLERGIES] rl/[HEALTH RISK LEVEL] hr/[EXISTING CONDITIONS] no/[ADDITIONAL NOTES]");
        exampleHeader.setText("Example (all details are edited):");
        example.setText("edit S9758366N n/Keanu Reeves i/S9975483H s/M d/1997-11-30 p/86526969 "
                + "e/keanureeves@gmail.com a/Blk 512 Ang Mo Kio Ave 2 b/O+ nokn/Mila Kunis nokp/84126990 "
                + "al/nuts al/shellfish rl/LOW hr/diabetes no/Patient has previous gunshot wound to chest");
    }

    /**
     * Sets the content of the help window based on the exit keyword.
     */
    public void setTextExitCommand() {
        header.setText("Exit Command: Exits the system.");
        description.setText("Exits the system and closes the window.");
        parametersHeader.setText("Parameters:");
        parameters.setText("NIL");
        usageHeader.setText("Command Usage:");
        usage.setText("exit");
        exampleHeader.setText("Example:");
        example.setText("exit");
    }

    /**
     * Sets the content of the help window based on the filter keyword.
     */
    private void setTextFilterCommand() {
        header.setText("Filter Command: Filters existing patient records based on the specified parameters.");
        description.setText("Specified parameters: START DATE | END DATE | HEALTH SERVICE\n"
                + "START DATE and HEALTH SERVICE parameters are optional. END DATE parameter is compulsory.\n"
                + "All parameters specified: Returns all appointments from start-date to end-date which matches the "
                + "specified health service.\n"
                + "Start-date and End-date specified: Returns all appointments from start-date to end-date.\n"
                + "End-date specified: Returns all appointments on end-date.");
        parametersHeader.setText("Parameters:");
        parameters.setText("START DATE | END DATE | HEALTH SERVICE\n\n"
                + "START DATE | END DATE - YYYY-MM-DD\n"
                + "HEALTH SERVICE - Blood Test, Cancer Screening, Vaccination, Consult");
        usageHeader.setText("Command Usage (all parameters specified):");
        usage.setText("filter sd/[START DATE] ed/[END DATE] h/[HEALTH SERVICE]");
        exampleHeader.setText("Example (all parameters specified):");
        example.setText("filter sd/2024-12-29 ed/2024-12-30 h/Blood Test");
    }

    /**
     * Sets the content of the help window based on the list keyword.
     */
    private void setTextListCommand() {
        header.setText("List Command: Returns to home page.");
        description.setText("Lists all existing patient records in the system.");
        parametersHeader.setText("Parameters:");
        parameters.setText("NIL");
        usageHeader.setText("Command Usage:");
        usage.setText("list");
        exampleHeader.setText("Example:");
        example.setText("list");
    }

    /**
     * Sets the content of the help window based on the view keyword.
     */
    public void setTextViewCommand() {
        header.setText("View Command: Views full profile of identified patient in the system.");
        description.setText("""
                Identifies the specific patient using NRIC and shows the full profile of the patient.
                NRIC provided must be a valid NRIC currently in the system.""");
        parametersHeader.setText("Parameters:");
        parameters.setText("NRIC");
        usageHeader.setText("Command Usage:");
        usage.setText("view i/[NRIC]");
        exampleHeader.setText("Example:");
        example.setText("view i/S9758366N");
    }

    /**
     * Shows the help window.
     *
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
}
