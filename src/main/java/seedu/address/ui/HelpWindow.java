package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String DELETE_COMMAND =
            """

                Format: delete (e or ph) INDEX

                Purpose: Deletes the entry in the employee or potential hire
                list at index. Index refers to the index number shown in the
                displayed list.

                Example: delete e 1
             """;
    public static final String EMPLOYEE_COMMAND =
            """

                Format: employee n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS
                d/DEPARTMENT r/ROLE ced/CONTRACT_END_DATE

                Purpose: Adds an entry in the employee list with the
                corresponding information.

                Example: employee n/John Doe p/81234567 e/johndoe@gmail.com
                a/21 Lower Kent Ridge Rd d/Department of informatics
                r/Head of Informatics ced/01-01-2021
             """;

    public static final String EXIT_COMMAND =
            """

                Format: exit

                Purpose: Terminates the program.

                Example: exit
             """;

    public static final String FIND_COMMAND =
            """

                Format: find (/e or /ph) KEYWORD(S)

                Purpose: Displays a list of entries that contains the keyword(s)
                in the corresponding employee and/or potential hire list.

                Example: find e John
             """;
    public static final String HELP_COMMAND =
             """

                Format: help

                Purpose: Displays a window containing all the format of all
                commands, its purpose and an example on how to use them.

                Example: help
             """;

    public static final String LIST_COMMAND =
            """

                Format: list (/e or /ph)

                Purpose: Displays a list of entries with their information in
                the corresponding employee and/or potential hire list.

                Example: list /e
             """;

    public static final String POTENTIAL_COMMAND =
            """

                Format: potential n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS
                d/DEPARTMENT r/ROLE

                Purpose: Adds an entry in the potential hire list with the
                corresponding information.

                Example: potential n/John Doe p/81234567 e/johndoe@gmail.com
                a/21 Lower Kent Ridge Rd d/Department of informatics
                r/Head of Informatics
             """;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Text deleteText;
    @FXML
    private Text employeeText;
    @FXML
    private Text exitText;
    @FXML
    private Text findText;
    @FXML
    private Text helpText;
    @FXML
    private Text listText;
    @FXML
    private Text potentialText;
    @FXML
    private Text lastHighlighted;

    @FXML
    private MenuItem menuDelete;
    @FXML
    private MenuItem menuEmployee;
    @FXML
    private MenuItem menuExit;
    @FXML
    private MenuItem menuFind;
    @FXML
    private MenuItem menuHelp;
    @FXML
    private MenuItem menuList;
    @FXML
    private MenuItem menuPotential;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox textVBox;

    /**
     * Creates a new HelpWindow. HelpWindow will display a list of commands
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        deleteText.setText(DELETE_COMMAND);
        employeeText.setText(EMPLOYEE_COMMAND);
        exitText.setText(EXIT_COMMAND);
        findText.setText(FIND_COMMAND);
        helpText.setText(HELP_COMMAND);
        listText.setText(LIST_COMMAND);
        potentialText.setText(POTENTIAL_COMMAND);

        menuDelete.setOnAction(event -> scrollAndHighlightText(deleteText));
        menuEmployee.setOnAction(event -> scrollAndHighlightText(employeeText));
        menuExit.setOnAction(event -> scrollAndHighlightText(exitText));
        menuFind.setOnAction(event -> scrollAndHighlightText(findText));
        menuHelp.setOnAction(event -> scrollAndHighlightText(helpText));
        menuList.setOnAction(event -> scrollAndHighlightText(listText));
        menuPotential.setOnAction(event -> scrollAndHighlightText(potentialText));
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
     * Scrolls to the position of the target text and highlights the box
     * @param targetText text to be scrolled to
     */
    private void scrollAndHighlightText(Text targetText) {
        // if the text to be scrolled to is the last text, just scroll to the end
        if (targetText == potentialText) {
            scrollPane.setVvalue(1);
            highlightText(targetText);
            return;
        }
        // type-casted to Vbox as every Text in helpWindow is wrapped by a Vbox as its parent
        VBox box = (VBox) targetText.getParent();
        Bounds bounds = box.getBoundsInParent();
        double yOffset = bounds.getMinY();
        double totalLength = textVBox.getHeight();
        double ratio = yOffset / totalLength;
        scrollPane.setVvalue(ratio);
        highlightText(targetText);
    }

    /**
     * Highlights the VBox that the target text is in
     *
     * @param targetText text where the VBox is to be highlighted
     */
    private void highlightText(Text targetText) {
        if (lastHighlighted != null) {
            VBox prevBox = (VBox) lastHighlighted.getParent();
            prevBox.setBackground(null);
        }
        // type-casted to Vbox as every Text in helpWindow is wrapped by a Vbox as its parent
        VBox vBox = (VBox) targetText.getParent();
        Background highlight = new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, null));
        vBox.setBackground(highlight);
        lastHighlighted = targetText;
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
