package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private PersonListPanel personListPanel;
    private PersonDetailPanel personDetailPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane personDetailsPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private ImageView placeholderImage;

    /**
     * Creates a new MainWindow with the given Stage and Logic.
     * Initializes the window with default settings and accelerators.
     *
     * @param primaryStage The primary stage for this window
     * @param logic The logic manager that handles command execution
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);
        this.primaryStage = primaryStage;
        this.logic = logic;
        setWindowDefaultSize(logic.getGuiSettings());
        setAccelerators();
        helpWindow = new HelpWindow();
    }

    /**
     * Returns the primary stage of the application.
     *
     * @return The primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets up keyboard accelerators for menu items.
     */
    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        personDetailPanel = new PersonDetailPanel();
        personDetailsPanelPlaceholder.getChildren().add(personDetailPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        splitPane.setDividerPositions(0.6);

        splitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            splitPane.setDividerPositions(0.6);
        });

        splitPane.getItems().remove(personDetailsPanelPlaceholder);
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Makes the main window visible.
     */
    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Handles the view command by updating the UI to show person details.
     * If the details panel is not visible, adds it to the split pane.
     * Updates the person details based on the provided index in the command.
     *
     * @param commandText The full command text containing the view command and index
     */
    private void handleViewCommand(String commandText) {
        if (!splitPane.getItems().contains(personDetailsPanelPlaceholder)) {
            splitPane.getItems().add(personDetailsPanelPlaceholder);
            placeholderImage.setVisible(false);
            splitPane.setDividerPositions(0.6);
        }

        String[] commandParts = commandText.split("\\s+");
        if (commandParts.length > 1) {
            try {
                int index = Integer.parseInt(commandParts[1]) - 1; // Assuming 1-based indexing in UI
                Person personToView = logic.getFilteredPersonList().get(index);
                personDetailPanel.setPersonDetails(personToView);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                // Handle invalid index
                resultDisplay.setFeedbackToUser("Invalid person index.");
            }
        } else {
            resultDisplay.setFeedbackToUser("Please provide a person index to view.");
        }
    }

    private void handleCloseCommand() {
        splitPane.getItems().remove(personDetailsPanelPlaceholder);
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowPerson()) {
                handleViewCommand(commandText);
            } else {
                handleCloseCommand();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
