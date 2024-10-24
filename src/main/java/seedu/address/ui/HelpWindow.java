package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DemoteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmployeeCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PotentialCommand;
import seedu.address.logic.commands.PromoteCommand;
import seedu.address.logic.commands.SortCommand;


/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String CLEAR_COMMAND = ClearCommand.COMMAND_WORD
            + "\nFormat: " + ClearCommand.COMMAND_WORD
            + "\nPurpose: " + ClearCommand.MESSAGE_PURPOSE
            + "\nExample: " + ClearCommand.COMMAND_WORD;
    public static final String DELETE_COMMAND = DeleteCommand.COMMAND_WORD
            + "\nFormat: " + DeleteCommand.MESSAGE_FORMAT
            + "\nPurpose: " + DeleteCommand.MESSAGE_PURPOSE
            + "\nExample: " + DeleteCommand.MESSAGE_EXAMPLE;

    public static final String DEMOTE_COMMAND = DemoteCommand.COMMAND_WORD
            + "\nFormat: " + DemoteCommand.MESSAGE_FORMAT
            + "\nPurpose: " + DemoteCommand.MESSAGE_PURPOSE
            + "\nExample: " + DemoteCommand.MESSAGE_EXAMPLE;

    public static final String EDIT_COMMAND = EditCommand.COMMAND_WORD
            + "\nFormat: " + EditCommand.MESSAGE_FORMAT
            + "\nPurpose: " + EditCommand.MESSAGE_PURPOSE
            + "\nExample: " + EditCommand.MESSAGE_EXAMPLE;

    public static final String EMPLOYEE_COMMAND = EmployeeCommand.COMMAND_WORD
                    + "\nFormat: " + EmployeeCommand.MESSAGE_FORMAT
                    + "\nPurpose: " + EmployeeCommand.MESSAGE_PURPOSE
                    + "\nExample: " + EmployeeCommand.MESSAGE_EXAMPLE;

    public static final String EXIT_COMMAND = ExitCommand.COMMAND_WORD
                    + "\nFormat: " + ExitCommand.COMMAND_WORD
                    + "\nPurpose: " + ExitCommand.MESSAGE_PURPOSE
                    + "\nExample: " + ExitCommand.COMMAND_WORD;

    public static final String FIND_COMMAND = FindCommand.COMMAND_WORD
            + "\nFormat: " + FindCommand.MESSAGE_FORMAT
            + "\nPurpose: " + FindCommand.MESSAGE_PURPOSE
            + "\nExample: " + FindCommand.MESSAGE_EXAMPLE;
    public static final String HELP_COMMAND = HelpCommand.COMMAND_WORD
            + "\nFormat: " + HelpCommand.COMMAND_WORD
            + "\nPurpose: " + HelpCommand.MESSAGE_PURPOSE
            + "\nExample: " + HelpCommand.COMMAND_WORD;


    public static final String LIST_COMMAND = ListCommand.COMMAND_WORD
            + "\nFormat: " + ListCommand.MESSAGE_FORMAT
            + "\nPurpose: " + ListCommand.MESSAGE_PURPOSE
            + "\nExample: " + ListCommand.MESSAGE_EXAMPLE;

    public static final String POTENTIAL_COMMAND = PotentialCommand.COMMAND_WORD
            + "\nFormat: " + PotentialCommand.MESSAGE_FORMAT
            + "\nPurpose: " + PotentialCommand.MESSAGE_PURPOSE
            + "\nExample: " + PotentialCommand.MESSAGE_EXAMPLE;

    public static final String PROMOTE_COMMAND = PromoteCommand.COMMAND_WORD
            + "\nFormat: " + PromoteCommand.MESSAGE_FORMAT
            + "\nPurpose: " + PromoteCommand.MESSAGE_PURPOSE
            + "\nExample: " + PromoteCommand.MESSAGE_EXAMPLE;

    public static final String SORT_COMMAND = SortCommand.COMMAND_WORD
            + "\nFormat: " + SortCommand.MESSAGE_FORMAT
            + "\nPurpose: " + SortCommand.MESSAGE_PURPOSE
            + "\nExample: " + SortCommand.MESSAGE_EXAMPLE;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    private static final int ABSOLUTE_PADDING = 112;
    private static final int INSET_PADDING = 16;
    private static final Color HIGHLIGHT_RED_BACKGROUND = Color.rgb(255, 40, 40, 0.2);
    private static final Color HIGHLIGHT_ORANGE_BACKGROUND = Color.rgb(255, 152, 36, 0.4);
    private static final CornerRadii DEFAULT_CORNER_RADII = new CornerRadii(15);

    /**
     * Array of String containing the format, purpose and examples of all the commands in StaffSync.
     * Arranged in alphabetical order.
     * To be updated when new commands are added
     */
    private static final String[] ARRAY_OF_COMMAND_STRING = {
        CLEAR_COMMAND,
        DELETE_COMMAND,
        DEMOTE_COMMAND,
        EDIT_COMMAND,
        EMPLOYEE_COMMAND,
        EXIT_COMMAND,
        FIND_COMMAND,
        HELP_COMMAND,
        LIST_COMMAND,
        POTENTIAL_COMMAND,
        PROMOTE_COMMAND,
        SORT_COMMAND
    };

    private final int numOfCommands;

    @FXML
    private Text clearText;
    @FXML
    private Text deleteText;
    @FXML
    private Text demoteText;
    @FXML
    private Text editText;
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
    private Text promoteText;
    @FXML
    private Text sortText;

    /**
     * Collection of Text objects to be added into HelpWindow.fxml.
     * Arranged in alphabetical order.
     * To be updated together with adding a Text component in HelpWindow.fxml when new commands are added.
     */
    private final Text[] arrayText = {
        clearText,
        deleteText,
        demoteText,
        editText,
        employeeText,
        exitText,
        findText,
        helpText,
        listText,
        potentialText,
        promoteText,
        sortText
    };

    @FXML
    private MenuItem clearMenuItem;
    @FXML
    private MenuItem deleteMenuItem;
    @FXML
    private MenuItem demoteMenuItem;
    @FXML
    private MenuItem editMenuItem;
    @FXML
    private MenuItem employeeMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem findMenuItem;
    @FXML
    private MenuItem helpMenuItem;
    @FXML
    private MenuItem listMenuItem;
    @FXML
    private MenuItem potentialMenuItem;
    @FXML
    private MenuItem promoteMenuItem;
    @FXML
    private MenuItem sortMenuItem;

    /**
     * Collection of MenuItem objects to be added into HelpWindow.fxml.
     * Arranged in alphabetical order.
     * To be updated together with adding a MenuItem component in HelpWindow.fxml when new commands are added.
     */
    private final MenuItem[] arrayMenuItem = {
        clearMenuItem,
        deleteMenuItem,
        demoteMenuItem,
        editMenuItem,
        employeeMenuItem,
        exitMenuItem,
        findMenuItem,
        helpMenuItem,
        listMenuItem,
        potentialMenuItem,
        promoteMenuItem,
        sortMenuItem
    };

    @FXML
    private Text lastHighlighted;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox textVBox;

    private ArrayList<Text> textArrayList;

    private ArrayList<MenuItem> menuItemArrayList;

    /**
     * Creates a new HelpWindow. HelpWindow will display a list of commands
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);

        // Check if ARRAY_OF_COMMAND_STRING, arrayText and arrayMenuItem are updated
        assert (ARRAY_OF_COMMAND_STRING.length == arrayText.length)
                : "Number of command String is not equal to number of Text object";
        assert (ARRAY_OF_COMMAND_STRING.length == arrayMenuItem.length)
                : "Number of command String is not equal to number of MenuItem object";

        // Initialize the ArrayList and NUM_OF_COMMANDS
        List<Text> textList = Arrays.asList(arrayText);
        List<MenuItem> menuItemList = Arrays.asList(arrayMenuItem);
        textArrayList = new ArrayList<>(textList);
        menuItemArrayList = new ArrayList<>(menuItemList);
        numOfCommands = textArrayList.size();

        for (int i = 0; i < numOfCommands; i++) {
            // Setting the corresponding String content in the Text object
            Text commandText = textArrayList.get(i);
            String commandString = ARRAY_OF_COMMAND_STRING[i];
            commandText.setText(commandString);

            // Setting the corresponding MenuItem to scroll to the Text object
            ChangeListener<Number> listener = (obs, oldText, newText) -> {
                Platform.runLater(() -> {
                    commandText.setWrappingWidth(getRoot().getScene().getWidth() - ABSOLUTE_PADDING);
                    logger.log(Level.INFO, "Setting Height of ResultDisplay to "
                            + (getRoot().getScene().getWidth() - ABSOLUTE_PADDING));
                });
            };
            getRoot().widthProperty().addListener(listener);

            MenuItem commandMenuItem = menuItemArrayList.get(i);
            commandMenuItem.setOnAction(event -> scrollAndHighlightText(commandText));
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
     * Scrolls to the position of the target text and highlights the box
     * @param targetText text to be scrolled to
     */
    private void scrollAndHighlightText(Text targetText) {
        // if the command to be scrolled to is the last command, just scroll to the end
        int lastIndex = numOfCommands - 1;
        Text lastText = textArrayList.get(lastIndex);
        if (targetText == lastText) {
            scrollPane.setVvalue(1);
            highlightText(targetText);
            return;
        }

        // if the command to be scrolled to is the first command, just scroll to the top
        Text firstText = textArrayList.get(0);
        if (targetText == firstText) {
            scrollPane.setVvalue(0);
            highlightText(targetText);
            return;
        }

        // type-casted to Hbox as every Text in helpWindow is wrapped by a Hbox as its parent
        HBox box = (HBox) targetText.getParent();
        Bounds bounds = box.getBoundsInParent();
        double yPadding = 100;
        double yOffset = bounds.getMinY() + yPadding;
        double totalLength = textVBox.getHeight();
        double ratio = yOffset / totalLength;
        scrollPane.setVvalue(ratio);
        highlightText(targetText);
    }

    /**
     * Highlights the HBox that the target text is in
     *
     * @param targetText text where the HBox is to be highlighted
     */
    private void highlightText(Text targetText) {
        if (lastHighlighted != null) {
            HBox prevBox = (HBox) lastHighlighted.getParent();
            prevBox.setBackground(new Background(new BackgroundFill(
                    HIGHLIGHT_ORANGE_BACKGROUND,
                    DEFAULT_CORNER_RADII,
                    new Insets(INSET_PADDING, INSET_PADDING, 0, INSET_PADDING))));
        }
        // type-casted to Hbox as every Text in helpWindow is wrapped by a Hbox as its parent
        HBox vBox = (HBox) targetText.getParent();
        Background highlight = new Background(new BackgroundFill(
                HIGHLIGHT_RED_BACKGROUND,
                DEFAULT_CORNER_RADII,
                new Insets(INSET_PADDING, INSET_PADDING, 0, INSET_PADDING)));
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
