package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddWeddingCommand;
import seedu.address.logic.commands.ClearWeddingBookCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteNCommand;
import seedu.address.logic.commands.DeleteWeddingCommand;
import seedu.address.logic.commands.DeleteYCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListWeddingCommand;
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
    private WeddingListPanel weddingListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private Button wbButton;

    @FXML
    private Button abButton;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;
    @FXML
    private StackPane resultDisplayPlaceholder;
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
        toggleAddressBookButton(true);
        toggleWeddingBookButton(false);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
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
        listPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        CommandBox commandBox = new CommandBox(this::executeCommand);
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
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (isNonUiCommand(commandText)) {
                return commandResult;
            }

            // allows for the corresponding person or wedding list to be shown
            listPanelPlaceholder.getChildren().clear();
            if (isWeddingCommand(commandText)) {
                weddingListPanel = new WeddingListPanel(logic.getFilteredWeddingList());
                listPanelPlaceholder.getChildren().add(weddingListPanel.getRoot());
                toggleWeddingBookButton(true);
                toggleAddressBookButton(false);
            } else {
                personListPanel = new PersonListPanel(logic.getFilteredPersonList());
                listPanelPlaceholder.getChildren().add(personListPanel.getRoot());
                toggleWeddingBookButton(false);
                toggleAddressBookButton(true);
            }


            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Checks if the given command text is related to wedding operations.
     *
     * @param commandText the command input text to check
     * @return true if the command is a wedding-related command, false otherwise
     */
    public boolean isWeddingCommand(String commandText) {
        String firstWord = commandText.split("\\s+")[0];
        return firstWord.equals(ListWeddingCommand.COMMAND_WORD)
                || firstWord.equals(ListWeddingCommand.COMMAND_WORD_SHORT)
                || firstWord.equals(AddWeddingCommand.COMMAND_WORD)
                || firstWord.equals(AddWeddingCommand.COMMAND_WORD_SHORT)
                || firstWord.equals(DeleteWeddingCommand.COMMAND_WORD)
                || firstWord.equals(DeleteWeddingCommand.COMMAND_WORD_SHORT)
                || firstWord.equals(ClearWeddingBookCommand.COMMAND_WORD)
                || firstWord.equals(ClearWeddingBookCommand.COMMAND_WORD_SHORT);
    }

    /**
     * Checks if the given command text is a delete command that does not modify the UI.
     *
     * @param commandText the command input text to check
     * @return true if the command is a delete command that
     *      should not change the UI, false otherwise
     */
    public boolean isNonUiCommand(String commandText) {
        String firstWord = commandText.split("\\s+")[0];
        return firstWord.equals(DeleteYCommand.COMMAND_WORD)
                || firstWord.equals(DeleteNCommand.COMMAND_WORD)
                || firstWord.equals(HelpCommand.COMMAND_WORD);
    }

    private void toggleWeddingBookButton(boolean isToggled) {
        if (isToggled) {
            wbButton.setId("WeddingBookButtonToggled");
        } else {
            wbButton.setId("WeddingBookButtonNotToggled");
        }
    }

    // Method to toggle the address book button style
    private void toggleAddressBookButton(boolean isToggled) {
        if (isToggled) {
            abButton.setId("AddressBookButtonToggled");
        } else {
            abButton.setId("AddressBookButtonNotToggled");
        }
    }
}
