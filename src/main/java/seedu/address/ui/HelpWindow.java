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
import javafx.scene.control.Label;
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
            + "\n" + ClearCommand.COMMAND_WORD
            + "\n" + ClearCommand.MESSAGE_PURPOSE
            + "\n" + ClearCommand.COMMAND_WORD;

    public static final String DELETE_COMMAND = DeleteCommand.COMMAND_WORD
            + "\n" + DeleteCommand.MESSAGE_FORMAT
            + "\n" + DeleteCommand.MESSAGE_PURPOSE
            + "\n" + DeleteCommand.MESSAGE_EXAMPLE;

    public static final String DEMOTE_COMMAND = DemoteCommand.COMMAND_WORD
            + "\n" + DemoteCommand.MESSAGE_FORMAT
            + "\n" + DemoteCommand.MESSAGE_PURPOSE
            + "\n" + DemoteCommand.MESSAGE_EXAMPLE;

    public static final String EDIT_COMMAND = EditCommand.COMMAND_WORD
            + "\n" + EditCommand.MESSAGE_FORMAT
            + "\n" + EditCommand.MESSAGE_PURPOSE
            + "\n" + EditCommand.MESSAGE_EXAMPLE;

    public static final String EMPLOYEE_COMMAND = EmployeeCommand.COMMAND_WORD
            + "\n" + EmployeeCommand.MESSAGE_FORMAT
            + "\n" + EmployeeCommand.MESSAGE_PURPOSE
            + "\n" + EmployeeCommand.MESSAGE_EXAMPLE;

    public static final String EXIT_COMMAND = ExitCommand.COMMAND_WORD
            + "\n" + ExitCommand.COMMAND_WORD
            + "\n" + ExitCommand.MESSAGE_PURPOSE
            + "\n" + ExitCommand.COMMAND_WORD;

    public static final String FIND_COMMAND = FindCommand.COMMAND_WORD
            + "\n" + FindCommand.MESSAGE_FORMAT
            + "\n" + FindCommand.MESSAGE_PURPOSE
            + "\n" + FindCommand.MESSAGE_EXAMPLE;

    public static final String HELP_COMMAND = HelpCommand.COMMAND_WORD
            + "\n" + HelpCommand.COMMAND_WORD
            + "\n" + HelpCommand.MESSAGE_PURPOSE
            + "\n" + HelpCommand.COMMAND_WORD;

    public static final String LIST_COMMAND = ListCommand.COMMAND_WORD
            + "\n" + ListCommand.MESSAGE_FORMAT
            + "\n" + ListCommand.MESSAGE_PURPOSE
            + "\n" + ListCommand.MESSAGE_EXAMPLE;

    public static final String POTENTIAL_COMMAND = PotentialCommand.COMMAND_WORD
            + "\n" + PotentialCommand.MESSAGE_FORMAT
            + "\n" + PotentialCommand.MESSAGE_PURPOSE
            + "\n" + PotentialCommand.MESSAGE_EXAMPLE;

    public static final String PROMOTE_COMMAND = PromoteCommand.COMMAND_WORD
            + "\n" + PromoteCommand.MESSAGE_FORMAT
            + "\n" + PromoteCommand.MESSAGE_PURPOSE
            + "\n" + PromoteCommand.MESSAGE_EXAMPLE;

    public static final String SORT_COMMAND = SortCommand.COMMAND_WORD
            + "\n" + SortCommand.MESSAGE_FORMAT
            + "\n" + SortCommand.MESSAGE_PURPOSE
            + "\n" + SortCommand.MESSAGE_EXAMPLE;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    private static final int ABSOLUTE_PADDING = 70;
    private static final int INSET_PADDING = 0;
    private static final Color HIGHLIGHT_ACTIVE_BACKGROUND = Color.rgb(90, 90, 90, 1);
    private static final Color HIGHLIGHT_HOVER_BACKGROUND = Color.rgb(70, 70, 70, 1);
    private static final CornerRadii DEFAULT_CORNER_RADII = new CornerRadii(0);

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
    private Label clearTextHeader;
    @FXML
    private Label deleteTextHeader;
    @FXML
    private Label demoteTextHeader;
    @FXML
    private Label editTextHeader;
    @FXML
    private Label employeeTextHeader;
    @FXML
    private Label exitTextHeader;
    @FXML
    private Label findTextHeader;
    @FXML
    private Label helpTextHeader;
    @FXML
    private Label listTextHeader;
    @FXML
    private Label potentialTextHeader;
    @FXML
    private Label promoteTextHeader;
    @FXML
    private Label sortTextHeader;

    @FXML
    private Label clearTextFormat;
    @FXML
    private Label deleteTextFormat;
    @FXML
    private Label demoteTextFormat;
    @FXML
    private Label editTextFormat;
    @FXML
    private Label employeeTextFormat;
    @FXML
    private Label exitTextFormat;
    @FXML
    private Label findTextFormat;
    @FXML
    private Label helpTextFormat;
    @FXML
    private Label listTextFormat;
    @FXML
    private Label potentialTextFormat;
    @FXML
    private Label promoteTextFormat;
    @FXML
    private Label sortTextFormat;

    @FXML
    private Text clearTextPurpose;
    @FXML
    private Text deleteTextPurpose;
    @FXML
    private Text demoteTextPurpose;
    @FXML
    private Text editTextPurpose;
    @FXML
    private Text employeeTextPurpose;
    @FXML
    private Text exitTextPurpose;
    @FXML
    private Text findTextPurpose;
    @FXML
    private Text helpTextPurpose;
    @FXML
    private Text listTextPurpose;
    @FXML
    private Text potentialTextPurpose;
    @FXML
    private Text promoteTextPurpose;
    @FXML
    private Text sortTextPurpose;

    @FXML
    private Text clearTextExample;
    @FXML
    private Text deleteTextExample;
    @FXML
    private Text demoteTextExample;
    @FXML
    private Text editTextExample;
    @FXML
    private Text employeeTextExample;
    @FXML
    private Text exitTextExample;
    @FXML
    private Text findTextExample;
    @FXML
    private Text helpTextExample;
    @FXML
    private Text listTextExample;
    @FXML
    private Text potentialTextExample;
    @FXML
    private Text promoteTextExample;
    @FXML
    private Text sortTextExample;

    /**
     * Collection of different Text objects to be added into HelpWindow.fxml.
     * Arranged in alphabetical order.
     * To be updated together with adding a Text component in HelpWindow.fxml when new commands are added.
     */

    private final Label[] arrayTextHeader = {
        clearTextHeader,
        deleteTextHeader,
        demoteTextHeader,
        editTextHeader,
        employeeTextHeader,
        exitTextHeader,
        findTextHeader,
        helpTextHeader,
        listTextHeader,
        potentialTextHeader,
        promoteTextHeader,
        sortTextHeader
    };

    private final Label[] arrayTextFormat = {
        clearTextFormat,
        deleteTextFormat,
        demoteTextFormat,
        editTextFormat,
        employeeTextFormat,
        exitTextFormat,
        findTextFormat,
        helpTextFormat,
        listTextFormat,
        potentialTextFormat,
        promoteTextFormat,
        sortTextFormat
    };

    private final Text[] arrayTextPurpose = {
        clearTextPurpose,
        deleteTextPurpose,
        demoteTextPurpose,
        editTextPurpose,
        employeeTextPurpose,
        exitTextPurpose,
        findTextPurpose,
        helpTextPurpose,
        listTextPurpose,
        potentialTextPurpose,
        promoteTextPurpose,
        sortTextPurpose
    };

    private final Text[] arrayTextExample = {
        clearTextExample,
        deleteTextExample,
        demoteTextExample,
        editTextExample,
        employeeTextExample,
        exitTextExample,
        findTextExample,
        helpTextExample,
        listTextExample,
        potentialTextExample,
        promoteTextExample,
        sortTextExample
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

    private ArrayList<Label> textArrayHeaderList;
    private ArrayList<Label> textArrayFormatList;
    private ArrayList<Text> textArrayPurposeList;
    private ArrayList<Text> textArrayExampleList;

    private ArrayList<MenuItem> menuItemArrayList;

    /**
     * Creates a new HelpWindow. HelpWindow will display a list of commands
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);

        // Check if ARRAY_OF_COMMAND_STRING, arrayText and arrayMenuItem are updated
        assert (ARRAY_OF_COMMAND_STRING.length == arrayTextFormat.length)
                : "Number of command String is not equal to number of Text object";
        assert (ARRAY_OF_COMMAND_STRING.length == arrayMenuItem.length)
                : "Number of command String is not equal to number of MenuItem object";

        // Initialize the ArrayList and NUM_OF_COMMANDS
        List<Label> textListHeader = Arrays.asList(arrayTextHeader);
        List<Label> textListFormat = Arrays.asList(arrayTextFormat);
        List<Text> textListPurpose = Arrays.asList(arrayTextPurpose);
        List<Text> textListExample = Arrays.asList(arrayTextExample);
        List<MenuItem> menuItemList = Arrays.asList(arrayMenuItem);
        textArrayHeaderList = new ArrayList<>(textListHeader);
        textArrayFormatList = new ArrayList<>(textListFormat);
        textArrayPurposeList = new ArrayList<>(textListPurpose);
        textArrayExampleList = new ArrayList<>(textListExample);
        menuItemArrayList = new ArrayList<>(menuItemList);
        numOfCommands = textArrayFormatList.size();

        for (int i = 0; i < numOfCommands; i++) {
            // Setting the corresponding String content in the Text object
            Label commandTextHeader = textArrayHeaderList.get(i);
            Label commandTextFormat = textArrayFormatList.get(i);
            Text commandTextPurpose = textArrayPurposeList.get(i);
            Text commandTextExample = textArrayExampleList.get(i);
            String[] commandString = ARRAY_OF_COMMAND_STRING[i].split("\n");
            commandTextHeader.setText(commandString[0]);
            commandTextFormat.setText(commandString[1]);
            commandTextPurpose.setText(commandString[2]);
            commandTextExample.setText(commandString[3]);

            // Setting the corresponding MenuItem to scroll to the Text object
            ChangeListener<Number> listener = (obs, oldText, newText) -> {
                Platform.runLater(() -> {
                    int newWidth = (int) getRoot().getScene().getWidth() - ABSOLUTE_PADDING;
                    commandTextPurpose.setWrappingWidth(newWidth);
                    commandTextExample.setWrappingWidth(newWidth);
                    logger.log(Level.INFO, "Setting Width of ResultDisplay to " + newWidth);
                });
            };
            getRoot().widthProperty().addListener(listener);

            MenuItem commandMenuItem = menuItemArrayList.get(i);
            commandMenuItem.setOnAction(event -> scrollAndHighlightText(commandTextPurpose));
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
        Text lastText = textArrayPurposeList.get(lastIndex);
        if (targetText == lastText) {
            scrollPane.setVvalue(1);
            highlightText(targetText);
            return;
        }

        // if the command to be scrolled to is the first command, just scroll to the top
        Text firstText = textArrayPurposeList.get(0);
        if (targetText == firstText) {
            scrollPane.setVvalue(0);
            highlightText(targetText);
            return;
        }

        /* type-casted to HBox as every Text in helpWindow is wrapped by a HBox as its 5th parent.
         * The 5th parent is the HBox that when highlighted, the whole component will be highlighted.
         */
        HBox box = (HBox) targetText.getParent().getParent().getParent().getParent().getParent();
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
            // The 5th parent is the HBox that when highlighted, the whole component will be highlighted.
            HBox prevBox = (HBox) lastHighlighted.getParent().getParent().getParent().getParent().getParent();
            prevBox.setBackground(new Background(new BackgroundFill(
                    HIGHLIGHT_HOVER_BACKGROUND,
                    DEFAULT_CORNER_RADII,
                    new Insets(INSET_PADDING, INSET_PADDING, 0, INSET_PADDING))));
        }

        /* type-casted to HBox as every Text in helpWindow is wrapped by a HBox as its 5th parent.
         * The 5th parent is the HBox that when highlighted, the whole component will be highlighted.
         */
        HBox hBox = (HBox) targetText.getParent().getParent().getParent().getParent().getParent();
        Background highlight = new Background(new BackgroundFill(
                HIGHLIGHT_ACTIVE_BACKGROUND,
                DEFAULT_CORNER_RADII,
                new Insets(INSET_PADDING, INSET_PADDING, 0, INSET_PADDING)));
        hBox.setBackground(highlight);
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
