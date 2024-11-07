package bizbook.ui;

import java.util.logging.Logger;

import bizbook.commons.core.GuiSettings;
import bizbook.commons.core.LogsCenter;
import bizbook.logic.Logic;
import bizbook.logic.commands.CommandResult;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.logic.parser.exceptions.ParseException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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

    private static final String FXML = "MainWindow.fxml";
    private static final String DARK_THEME_URL = MainWindow.class.getResource("/view/DarkTheme.css").toExternalForm();
    private static final String LIGHT_THEME_URL = MainWindow.class.getResource("/view/LightTheme.css").toExternalForm();
    private static final String DARK_THEME_HELP_URL =
            MainWindow.class.getResource("/view/HelpWindowDark.css").toExternalForm();
    private static final String LIGHT_THEME_HELP_URL =
            MainWindow.class.getResource("/view/HelpWindowLight.css").toExternalForm();

    private boolean isDarkTheme = true;
    private Scene scene;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private CommandHistory commandHistory;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private PinnedPersonListPanel pinnedPersonListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ContactDetails contactDetailsPanel;
    private SearchBox searchBox;

    @FXML
    private MenuItem toggleThemeMenuItem;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane searchBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane pinnedListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane contactDetailsPanelPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.commandHistory = new CommandHistory();
        this.scene = getRoot().getScene();

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
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

        contactDetailsPanel = new ContactDetails(logic.getFocusedPerson());
        contactDetailsPanelPlaceholder.getChildren().add(contactDetailsPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), logic.getFocusedPerson());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        pinnedPersonListPanel = new PinnedPersonListPanel(logic.getPinnedPersonList(), logic.getFocusedPerson());
        pinnedListPanelPlaceholder.getChildren().add(pinnedPersonListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, commandHistory);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        searchBox = new SearchBox(this::executeFindCommand);
        searchBoxPlaceholder.getChildren().add(searchBox.getRoot());
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
            helpWindow.fillInnerParts();
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
    /**
     * Toggles between light and dark themes
     */
    @FXML
    private void handleThemeChange() {
        isDarkTheme = !isDarkTheme;

        // Get the scene's stylesheets
        ObservableList<String> stylesheets = scene.getStylesheets();
        ObservableList<String> helpStylesheets = helpWindow.getRoot().getScene().getStylesheets();
        stylesheets.clear();
        helpStylesheets.clear();

        // Add the appropriate stylesheet based on the theme
        if (isDarkTheme) {
            stylesheets.add(DARK_THEME_URL);
            helpStylesheets.add(DARK_THEME_HELP_URL);
            toggleThemeMenuItem.setText("Switch to Light Theme");
        } else {
            stylesheets.add(LIGHT_THEME_URL);
            helpStylesheets.add(LIGHT_THEME_HELP_URL);
            toggleThemeMenuItem.setText("Switch to Dark Theme");
        }

        logger.info("Theme switched to " + (isDarkTheme ? "dark" : "light") + " mode");
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
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isThemeChange()) {
                handleThemeChange();
            }

            searchBox.clearSearchBox();

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Executes the find command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeFindCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            throw e;
        }
    }
}
