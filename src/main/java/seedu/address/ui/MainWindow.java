package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
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

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private PersonDetailsWindow personDetailsWindow;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ImportWindow importWindow;
    private ExportWindow exportWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);
        logger.info("Initializing MainWindow");

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        logger.fine("Dependencies set: primaryStage and logic");

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        logger.fine("Window default size configured");

        setAccelerators();
        logger.fine("Accelerators set");

        personDetailsWindow = new PersonDetailsWindow(logic);
        helpWindow = new HelpWindow();
        importWindow = new ImportWindow(this.logic);
        exportWindow = new ExportWindow(this.logic);
        logger.fine("All window components initialized");
    }

    public Stage getPrimaryStage() {
        logger.fine("Getting primary stage");
        return primaryStage;
    }

    private void setAccelerators() {
        logger.fine("Setting menu accelerators");
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        logger.fine("Setting accelerator: " + keyCombination.toString() + " for menu item: " + menuItem.getText());
        menuItem.setAccelerator(keyCombination);

        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                logger.fine("Accelerator key pressed: " + keyCombination.toString());
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        logger.info("Filling inner parts of MainWindow");

        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), personDetailsWindow);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        logger.fine("PersonListPanel initialized and added to placeholder");

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        logger.fine("ResultDisplay initialized and added to placeholder");

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
        logger.fine("StatusBarFooter initialized and added to placeholder");

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        logger.fine("CommandBox initialized and added to placeholder");

        logger.info("All inner parts filled successfully");
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        logger.fine("Setting window default size: " + guiSettings.toString());
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
        logger.fine("Window size set successfully");
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        logger.info("Handling help window request");
        if (!helpWindow.isShowing()) {
            logger.fine("Opening help window");
            helpWindow.show();
        } else {
            logger.fine("Focusing on existing help window");
            helpWindow.focus();
        }
    }

    /**
     * Opens the import window or focuses on it if it's already opened.
     */
    @FXML
    public void handleImport() {
        logger.info("Handling import window request");
        if (!importWindow.isShowing()) {
            logger.fine("Opening import window");
            importWindow.show();
        } else {
            logger.fine("Focusing on existing import window");
            importWindow.focus();
        }
    }

    /**
     * Opens the export window or focuses on it if it's already opened.
     */
    @FXML
    public void handleExport() {
        logger.info("Handling export window request");
        if (!exportWindow.isShowing()) {
            logger.fine("Opening export window");
            exportWindow.show();
        } else {
            logger.fine("Focusing on existing export window");
            exportWindow.focus();
        }
    }

    void show() {
        logger.info("Showing main window");
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        logger.info("Handling application exit");
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        logger.fine("Saved GUI settings");

        personDetailsWindow.hide();
        importWindow.hide();
        helpWindow.hide();
        primaryStage.hide();
        logger.info("Application windows closed");
    }

    public PersonListPanel getPersonListPanel() {
        logger.fine("Getting PersonListPanel");
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        logger.info("Executing command: " + commandText);
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Command executed successfully. Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                logger.fine("Help command detected - opening help window");
                handleHelp();
            }

            if (commandResult.isExit()) {
                logger.fine("Exit command detected - closing application");
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.warning("Error executing command: " + commandText + ". Error: " + e.getMessage());
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}