package careconnect.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import careconnect.commons.core.GuiSettings;
import careconnect.commons.core.LogsCenter;
import careconnect.logic.Logic;
import careconnect.logic.Logic.ValidateSyntaxResultEnum;
import careconnect.logic.autocompleter.exceptions.AutocompleteException;
import careconnect.logic.commands.CommandResult;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.logic.parser.exceptions.ParseException;
import careconnect.model.person.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
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
    // Independent Ui parts residing in this Ui container
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
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();
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
     * Displays the details of the selected {@code Person} on the right pane.
     *
     * @param index of the selected {@code Person}
     */
    protected void showSelectedPerson(int index) {
        Person selectedPerson = logic.getFilteredPersonList().get(index);
        PersonDetailCard personDetailCard = new PersonDetailCard(selectedPerson);
        personDetailPlaceholder.getChildren().setAll(personDetailCard.getRoot());
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), this::showSelectedPerson);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        personDetailPlaceholder.getChildren().add(personDetailFallback.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, this::autocompleteCommand, this::validateSyntax);
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
            if (selectedIndex == CommandResult.NO_RECORD_SELECTED) {
                // display fallback ui if no index is selected
                personDetailPlaceholder.getChildren().setAll(personDetailFallback.getRoot());
            } else {
                // display detail page
                showSelectedPerson(selectedIndex);
            }

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
    private ValidateSyntaxResultEnum validateSyntax(String syntax) {
        ValidateSyntaxResultEnum isValidSyntax = logic.validateSyntax(syntax);
        logger.info("isValidSyntax: " + isValidSyntax);
        return isValidSyntax;
    }

}
