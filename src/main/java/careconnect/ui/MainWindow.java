package careconnect.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import careconnect.Main;
import careconnect.commons.core.GuiSettings;
import careconnect.commons.core.LogsCenter;
import careconnect.logic.Logic;
import careconnect.logic.autocompleter.exceptions.AutocompleteException;
import careconnect.logic.commands.Command;
import careconnect.logic.commands.CommandResult;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.logic.parser.exceptions.ParseException;
import careconnect.model.person.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {
    private static final String UG_URL = "https://ay2425s1-cs2103t-w13-2.github.io/tp/UserGuide.html#quick-start";
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final PersonDetailFallback personDetailFallback = new PersonDetailFallback();
    private final Stage primaryStage;
    private final Logic logic;
    private FocusItems currentFocusItem;
    // Independent Ui parts residing in this Ui container
    private PersonDetailCard selectedPersonDetailCard;
    private CommandBox commandBox;
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane personDetailPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.initializeListener();
        this.logic = logic;
        this.selectedPersonDetailCard = null;
        this.currentFocusItem = null;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();
    }

    /**
     * Updates current focus item to provided focus item.
     *
     * @param focusItem the focusItem to update
     */
    public void updateCurrentFocusItem(FocusItems focusItem) {
        currentFocusItem = focusItem;
        ShiftTabFocusable shiftTabFocusable = currentFocusItem.getItem(this);
        assert(shiftTabFocusable != null);
        shiftTabFocusable.focus();
    }

    /**
     * Initializes the shift+tab button listener.
     */
    @FXML
    public void initializeListener() {
        // declare key combination for Shift + Tab
        KeyCombination shiftTabCombination = new KeyCodeCombination(KeyCode.TAB, KeyCombination.SHIFT_DOWN);

        // add listener
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (shiftTabCombination.match(event)) {
                if (currentFocusItem != null) {
                    FocusItems focusItem = currentFocusItem.next(this);
                    this.updateCurrentFocusItem(focusItem);
                }
                event.consume();
            }

        });
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
     * Sets the {@code selectedPersonDetailCard} field based on the provided selected index.
     * This method creates a new {@code PersonDetailCard} using the specified index and
     * updates the UI to reflect the selected person's details. If {@code selectedIndex} is equal
     * to {@code CommandResult.NO_RECORD_SELECTED}, a default {@code PersonDetailFallback} card
     * will be shown instead.
     *
     * @param selectedIndex the index of the selected person used to create the detail card
     */
    protected void setAndShowSelectedPerson(int selectedIndex) {
        if (selectedIndex == CommandResult.NO_RECORD_SELECTED) {
            this.selectedPersonDetailCard = null;
            personDetailPlaceholder.getChildren().setAll(personDetailFallback.getRoot());
        } else {
            Person selectedPerson = logic.getFilteredPersonList().get(selectedIndex);
            this.selectedPersonDetailCard = new PersonDetailCard(selectedPerson);
            personDetailPlaceholder.getChildren().setAll(this.selectedPersonDetailCard.getRoot());
        }
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), this::setAndShowSelectedPerson);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        personDetailPlaceholder.getChildren().add(personDetailFallback.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        this.commandBox = new CommandBox(this::executeCommand, this::autocompleteCommand, this::validateSyntax);
        updateCurrentFocusItem(FocusItems.COMMAND_BOX_ITEM);
        commandBoxPlaceholder.getChildren().add(this.commandBox.getRoot());
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
     * Opens the help page on web using default browser.
     */
    @FXML
    public void handleUgLink() {
        try {
            URI link = new URI(UG_URL);
            Desktop.getDesktop().browse(link);
        } catch (URISyntaxException | IOException ex) {
            resultDisplay.setFeedbackToUser("An error occurred while opening the help page.");
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
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleUgLink();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            int selectedIndex = commandResult.getSelectedIndex();
            this.setAndShowSelectedPerson(selectedIndex);

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Autocompletes the command and returns the suggestion.
     *
     * @see Logic#autocompleteCommand(String)
     */
    private String autocompleteCommand(String commandText) throws AutocompleteException {
        try {
            String autocompletedCommand = logic.autocompleteCommand(commandText);
            logger.info("Autocompleted Command: " + autocompletedCommand);
            return autocompletedCommand;
        } catch (AutocompleteException e) {
            logger.info("An error occurred while autocompleting command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Checks if given string is valid syntax
     *
     * @see Logic#validateSyntax(String)
     */
    private boolean validateSyntax(String syntax) {
        boolean isValidSyntax = logic.validateSyntax(syntax);
        logger.info("isValidSyntax: " + isValidSyntax);
        return isValidSyntax;
    }

    private enum FocusItems {
        COMMAND_BOX_ITEM {
            @Override
            public ShiftTabFocusable getItem(MainWindow mainWindow) {
                return mainWindow.commandBox;
            }

        },
        LOG_LIST_ITEM {
            @Override
            public ShiftTabFocusable getItem(MainWindow mainWindow) {
                return mainWindow.selectedPersonDetailCard;
            }
        },
        PERSON_LIST_ITEM {
            @Override
            public ShiftTabFocusable getItem(MainWindow mainWindow) {
                return mainWindow.personListPanel;
            }
        };

        // Method to get the next focus item (gets next until item not null)
        public FocusItems next(MainWindow mainWindow) {
            int count = 0;
            FocusItems next = values()[(this.ordinal() + 1) % values().length];
            while(next.getItem(mainWindow) == null) {
                // should not go into infinite loop, since focusItem is only set after
                // command box have been initialized
                assert(count <= values().length);
                next = values()[(next.ordinal() + 1) % values().length];
                count += 1;
            }
            return next;
        }

        // Method to get the previous focus item (gets previous until item not null)
        public FocusItems previous(MainWindow mainWindow) {
            int count = 0;
            FocusItems previous = values()[(this.ordinal() - 1 + values().length) % values().length];
            while(previous.getItem(mainWindow) == null) {
                // should not go into infinite loop, since focusItem is only set after
                // command box have been initialized
                assert(count <= values().length);
                previous = values()[(previous.ordinal() - 1 + values().length) % values().length];
                count += 1;
            }
            return previous;
        }

        public abstract ShiftTabFocusable getItem(MainWindow mainWindow);
    }

}
