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

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String DELETE_COMMAND =
            """
            delete

            Format: delete (e or ph) INDEX

            Purpose: Deletes the entry of the index in the current \
            list that is being displayed. The 2nd parameter (e or ph) must \
            correspond to the type of entry in the index.

            Example: delete e 1
            """;
    public static final String DEMOTE_COMMAND =
            """
            demote

            Format: demote NAME

            Purpose: Change the status of an employee in the list to a \
            potential hire.

            Example: demote John Doe
            """;
    public static final String EMPLOYEE_COMMAND =
            """
            employee

            Format: employee n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS \
            d/DEPARTMENT r/ROLE ced/CONTRACT_END_DATE

            Purpose: Adds an entry in the employee list with the \
            corresponding information.

            Example: employee n/John Doe p/81234567 e/johndoe@gmail.com \
            a/21 Lower Kent Ridge Rd d/Department of informatics \
            r/Head of Informatics ced/2021-01-21
            """;

    public static final String EXIT_COMMAND =
            """
            exit

            Format: exit

            Purpose: Terminates the program.

            Example: exit
            """;

    public static final String FIND_COMMAND =
            """
            find

            Format: find (e or ph or all) KEYWORD(S)

            Purpose: Displays a list of entries that contains the keyword(s) \
            in the corresponding employee and/or potential hire list.

            Example: find e John
            """;
    public static final String HELP_COMMAND =
            """
            help

            Format: help

            Purpose: Displays a window containing all the format of all \
            commands, its purpose and an example on how to use them.

            Example: help
            """;

    public static final String LIST_COMMAND =
            """
            list

            Format: list (e or ph or all)

            Purpose: Displays a list of entries with their information in \
            the corresponding employee and/or potential hire list.

            Example: list e
            """;

    public static final String POTENTIAL_COMMAND =
            """
            potential

            Format: potential n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS \
            d/DEPARTMENT r/ROLE

            Purpose: Adds an entry in the potential hire list with the \
            corresponding information.

            Example: potential n/John Doe p/81234567 e/johndoe@gmail.com \
            a/21 Lower Kent Ridge Rd d/Department of informatics \
            r/Head of Informatics
            """;

    public static final String PROMOTE_COMMAND =
            """
            promote

            Format: promote NAME

            Purpose: Change the status of a potential hire in the list \
            to an employee.

            Example: promote John Doe
            """;

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
        DELETE_COMMAND,
        DEMOTE_COMMAND,
        EMPLOYEE_COMMAND,
        EXIT_COMMAND,
        FIND_COMMAND,
        HELP_COMMAND,
        LIST_COMMAND,
        POTENTIAL_COMMAND,
        PROMOTE_COMMAND
    };

    private int numOfCommands;

    @FXML
    private Text deleteText;
    @FXML
    private Text demoteText;
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

    /**
     * Collection of Text objects to be added into HelpWindow.fxml.
     * Arranged in alphabetical order.
     * To be updated together with adding a Text component in HelpWindow.fxml when new commands are added.
     */
    private final Text[] arrayText = {
        deleteText,
        demoteText,
        employeeText,
        exitText,
        findText,
        helpText,
        listText,
        potentialText,
        promoteText
    };
    @FXML
    private MenuItem deleteMenuItem;
    @FXML
    private MenuItem demoteMenuItem;
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

    /**
     * Collection of MenuItem objects to be added into HelpWindow.fxml.
     * Arranged in alphabetical order.
     * To be updated together with adding a MenuItem component in HelpWindow.fxml when new commands are added.
     */
    private final MenuItem[] arrayMenuItem = {
        deleteMenuItem,
        demoteMenuItem,
        employeeMenuItem,
        exitMenuItem,
        findMenuItem,
        helpMenuItem,
        listMenuItem,
        potentialMenuItem,
        promoteMenuItem
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
