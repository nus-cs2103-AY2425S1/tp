package seedu.address.ui;

import static seedu.address.logic.Messages.MESSAGE_CANCEL_COMMAND;

import java.io.File;
import java.util.Set;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
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
    private static final Set<String> CONFIRM_WORDS = Set.of("y", "yes");
    private static final String IMPORT_DATA_TITLE = "Import Data";
    private static final String EXPORT_DATA_TITLE = "Export Data";
    // TODO: handle *.csv files
    private static final Set<FileChooser.ExtensionFilter> ACCEPTED_FILE_EXTENSIONS = Set.of(
            new FileChooser.ExtensionFilter("Data Files", "*.json")
    );

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private CommandResult lastCommandResult = null; // Tracks the most recent CommandResult

    // Independent Ui parts residing in this Ui container
    private CommandBox commandBox;
    private PersonListPanel personListPanel;
    private RentalInformationListPanel rentalInformationListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private FileChooser fileChooser;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane rentalInformationListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(ACCEPTED_FILE_EXTENSIONS);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
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

        rentalInformationListPanel = new RentalInformationListPanel(logic.getVisibleRentalInformationList(),
                logic.getVisibleClient());
        rentalInformationListPanelPlaceholder.getChildren().add(rentalInformationListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(this::executeCommand, this::checkPromptConfirmation);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
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

    private File selectImportFile() {
        fileChooser.setTitle(IMPORT_DATA_TITLE);
        return fileChooser.showOpenDialog(getPrimaryStage());
    }

    private File selectExportFile() {
        fileChooser.setTitle(EXPORT_DATA_TITLE);
        return fileChooser.showSaveDialog(getPrimaryStage());
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            lastCommandResult = logic.execute(commandText);
            executeTillTerminalResult();
            return lastCommandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setErrorFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Checks if the user accepted a confirmation prompt. If the prompt was confirmed, executes the continuation
     * function of the most recent {@link CommandResult} and returns the result.
     */
    private CommandResult checkPromptConfirmation(String userInput) throws CommandException {
        if (!isConfirmation(userInput)) {
            lastCommandResult = new CommandResult(MESSAGE_CANCEL_COMMAND);
        } else {
            lastCommandResult = lastCommandResult.confirmPrompt();
        }
        executeTillTerminalResult();
        return lastCommandResult;
    }

    /**
     * Executes the continuation function of the most recent {@link CommandResult} until a terminal result is reached.
     */
    private void executeTillTerminalResult() throws CommandException {
        while (true) {
            switch (lastCommandResult.getType()) {
            case ORDINARY: // terminal
                logger.info("Result: " + lastCommandResult.getFeedbackToUser());
                break;

            case SHOW_HELP: // terminal
                logger.info("Result: " + lastCommandResult.getFeedbackToUser());
                handleHelp();
                break;

            case EXIT: // terminal
                logger.info("Result: " + lastCommandResult.getFeedbackToUser());
                handleExit();
                break;

            case PROMPT: // terminal
                logger.info("Prompt: " + lastCommandResult.getFeedbackToUser());
                commandBox.waitForPrompt();
                break;

            case IMPORT_DATA: // intermediate
                File importFile = selectImportFile();
                if (importFile == null) {
                    lastCommandResult = new CommandResult(MESSAGE_CANCEL_COMMAND);
                    break;
                }

                logger.info(String.format("Importing data from &1%s", importFile.getPath()));
                lastCommandResult = lastCommandResult.processFile(logic.importFile(importFile));
                continue;

            case EXPORT_DATA: // intermediate
                File exportFile = selectExportFile();
                if (exportFile == null) {
                    lastCommandResult = new CommandResult(MESSAGE_CANCEL_COMMAND);
                    break;
                }

                logger.info(String.format("Exporting data to &1%s", exportFile.getPath()));
                lastCommandResult = lastCommandResult.processFile(logic.exportFile(exportFile));
                continue;

            default:
                throw new CommandException("An error occurred during the execution of this command");
                // This line should not be reached
            }

            break;
        }
        resultDisplay.setSuccessFeedbackToUser(lastCommandResult.getFeedbackToUser());
        commandBox.setFeedbackToUser(lastCommandResult.getHistory());
    }

    /**
     * Checks if the given user input corresponds to user confirming a prompt.
     */
    private boolean isConfirmation(String userInput) {
        return CONFIRM_WORDS.contains(userInput.trim().toLowerCase());
    }
}
