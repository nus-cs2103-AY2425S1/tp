package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String DELETECOMMAND =
            """
             1. delete <e/ph> <index>

             Deletes the entry in the employee or potential hire list
             at index.

             Example: delete e 1

             """;
    public static final String EMPLOYEECOMMAND =
            """
             2. employee n/<name> p/<phone number> a/<address> e/<email>
             d/<department> r/<role> ced/<contract end date>

             Adds an entry in the employee list with the corresponding information.

             Example: employee n/John Doe p/81234567 a/21 Lower Kent Ridge Rd
             e/johndoe@gmail.com d/Department of informatics r/Head of Informatics
             ced/01-01-2021

             """;

    public static final String EXITCOMMAND =
            """
             3. exit

             Terminates the program.

             Example: exit

             """;

    public static final String FINDCOMMAND =
            """
             4. find <e/ph> <keyword>

             Displays a list of entries that contains the keyword in the
             corresponding employee or potential hire list.

             Example: find e John

             """;
    public static final String HELPCOMMAND =
             """
             5. help

             Displays a window containing all the list of commands, its purpose
             and how to use them.

             Example: help

             """;

    public static final String LISTCOMMAND =
            """
             6. list <e/ph>

             Displays a list of entries with their information in the
             corresponding employee or potential hire list.

             Example: list e

             """;

    public static final String POTENTIALCOMMAND =
            """
             7. potential n/<name> p/<phone number> a/<address> e/<email>
             d/<department> r/<role>

             Adds an entry in the potential hire list with the corresponding information.

             Example: potential n/John Doe p/81234567 a/21 Lower Kent Ridge Rd
             e/johndoe@gmail.com d/Department of informatics r/Head of Informatics

             """;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private TextFlow helpMessage;

    /**
     * Creates a new HelpWindow. HelpWindow will display a list of commands
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        StringBuilder helpMessageBuilder = new StringBuilder("List of Commands: \n\n");
        helpMessageBuilder.append(DELETECOMMAND);
        helpMessageBuilder.append(EMPLOYEECOMMAND);
        helpMessageBuilder.append(EXITCOMMAND);
        helpMessageBuilder.append(FINDCOMMAND);
        helpMessageBuilder.append(HELPCOMMAND);
        helpMessageBuilder.append(LISTCOMMAND);
        helpMessageBuilder.append(POTENTIALCOMMAND);
        String message = helpMessageBuilder.toString();
        for (String line : message.split("\n")) {
            Text textLine = new Text(line + "\n");
            helpMessage.getChildren().add(textLine);
        }
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

}
