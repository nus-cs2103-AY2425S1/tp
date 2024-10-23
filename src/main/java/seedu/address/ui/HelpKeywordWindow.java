package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private VBox helpKeywordContainer;

    @FXML
    private Label header;

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
        case "delete":
            setTextDeleteCommand();
            break;
        case "view":
            setViewCommand();
            break;
        case "filter":
            setFilterCommand();
            break;
        default:
            break;
        }
    }

    /**
     * Sets the content of the help window based on the add keyword.
     */
    private void setTextAddCommand() {
        header.setText("Add Command: Adds a new patient record into the database system.");
        parametersHeader.setText("Parameters:");
        parameters.setText("NAME | NRIC | SEX(M/F) | DATE OF BIRTH(YYYY-MM-DD) | HEALTH SERVICE");
        usageHeader.setText("Command Usage:");
        usage.setText("add n/[NAME] i/[NRIC] s/[SEX] d/[DATE OF BIRTH] h/[HEALTH SERVICE]");
        exampleHeader.setText("Example:");
        example.setText("add n/Abraham Tan i/S9758366N s/M d/1997-10-27 h/Blood Test");
    }

    /**
     * Sets the content of the help window based on the addf keyword.
     */
    private void setTextAddfCommand() {
        header.setText("Add Full Command: "
                + "Adds a new patient record (with additional information) into the database system.");
        parametersHeader.setText("Parameters:");
        parameters.setText("NAME | NRIC | SEX(M/F) | DATE OF BIRTH(YYYY-MM-DD) | HEALTH SERVICE | PHONE NO. | EMAIL "
                + "| ADDRESS | BLOOD TYPE | NEXT-OF-KIN NAME | NEXT-OF-KIN PHONE NO. | ALLERGIES | "
                + "HEALTH RISK LEVEL(HIGH, MEDIUM, LOW) | PAST HEALTH RECORDS | ADDITIONAL NOTES");
        usageHeader.setText("Command Usage:");
        usage.setText("addf n/[NAME] i/[NRIC] s/[SEX] d/[DATE OF BIRTH] h/[HEALTH SERVICE] p/[PHONE NO.] e/[EMAIL] "
                + "a/[ADDRESS] b/[BLOOD TYPE] nokn/[NEXT-OF-KIN NAME] nokp/[NEXT-OF-KIN PHONE NO.] "
                + "al/[ALLERGIES] rl/[HEALTH RISK LEVEL] hr/[PAST HEALTH RECORDS] no/[ADDITIONAL NOTES]");
        exampleHeader.setText("Example:");
        example.setText("addf n/Abraham Tan i/S9758366N s/M d/1997-10-27 h/Blood Test p/87596666 "
                + "e/abrahamtan@gmail.com "
                + "a/Blk 123, NUS Road, S123123 b/A+ nokn/Lincoln Tan nokp/91234567 al/nuts, shellfish rl/HIGH "
                + "hr/Diabetes no/Patient needs extra care");
    }

    /**
     * Sets the content of the help window based on the appt keyword.
     */
    private void setTextApptCommand() {
        header.setText("Appointment Command: Records appointment times for registered patients into the system.");
        parametersHeader.setText("Parameters:");
        parameters.setText("APPOINTMENT DATE(YYYY-MM-DD), APPOINTMENT TIME(24 HOURS FORMAT) | NRIC");
        usageHeader.setText("Command Usage:");
        usage.setText("appt dt/[APPOINTMENT DATE T APPOINTMENT TIME] i/[NRIC]");
        exampleHeader.setText("Example:");
        example.setText("appt dt/2024-12-29T13:30 i/S9758366N");
    }

    /**
     * Sets the content of the help window based on the delete keyword.
     */
    private void setTextDeleteCommand() {
        header.setText("Delete Command: Deletes an existing patient record from the database system.");
        parametersHeader.setText("Parameters:");
        parameters.setText("NRIC");
        usageHeader.setText("Command Usage:");
        usage.setText("delete [NRIC]");
        exampleHeader.setText("Example:");
        example.setText("delete S9758366N");
    }

    /**
     * Sets the content of the help window based on the view keyword.
     */
    private void setViewCommand() {
        header.setText("View Command: Views the full profile of a patient in the database.");
        parametersHeader.setText("Parameters:");
        parameters.setText("NRIC");
        usageHeader.setText("Command Usage:");
        usage.setText("view i/[NRIC]");
        exampleHeader.setText("Example:");
        example.setText("view i/S1234567A");
    }

    /**
     * Sets the content of the help window based on the filter keyword.
     */
    private void setFilterCommand() {
        header.setText("Filter Command: Filters the patient records based on the specified parameters.\n"
                + "Start date not specified: Returns matching appointments for the end date only.\n"
                + "Health service not specified: Returns all appointments within the date range.\n"
                + "All parameters specified: Returns matching appointments that are both within the date range "
                + "and match the specified health service.");
        parametersHeader.setText("Parameters:");
        parameters.setText("START DATE(YYYY-MM-DD)(OPTIONAL) | END DATE(YYYY-MM-DD) | HEALTH SERVICE(OPTIONAL)");
        usageHeader.setText("Command Usage:");
        usage.setText("filter sd/[START DATE] ed/[END DATE] h/[HEALTH SERVICE]");
        exampleHeader.setText("Example:");
        example.setText("filter sd/2024-12-29 ed/2024-12-30 h/Blood Test");
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException
     *                               <ul>
     *                               <li>
     *                               if this method is called on a thread other than
     *                               the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or
     *                               layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
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
