package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    public static final String DARK_THEME = "dark";
    public static final String LIGHT_THEME = "light";

    private static final String FXML = "MainWindow.fxml";

    private static final String DARK_THEME_PATH = "/view/DarkTheme.css";
    private static final String LIGHT_THEME_PATH = "/view/LightTheme.css";
    private static final String THEME_CHANGE_MESSAGE = "Theme changed to %1$s mode.";
    private static final String THEME_ALREADY_SET_MESSAGE = "Theme is already set to %1$s mode.";


    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private String theme;
    private Scene scene;

    // Independent Ui parts residing in this Ui container
    private ClientListPanel clientListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane clientListPanelPlaceholder;

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

        // Initialize scene
        this.scene = primaryStage.getScene();

        ReadOnlyUserPrefs userPrefs = logic.getUserPrefs();
        this.theme = userPrefs.getTheme(); // Load saved theme from user preferences
        applyTheme();

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
        clientListPanel = new ClientListPanel(logic.getFilteredClientList());
        clientListPanelPlaceholder.getChildren().add(clientListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

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

    public ClientListPanel getClientListPanel() {
        return clientListPanel;
    }

    /**
     * Applies the appropriate theme (light or dark) to the scene based on the current theme setting.
     *
     * If the theme is "light", the LIGHT_THEME stylesheet is applied.
     * Otherwise, the DARK_THEME stylesheet is applied.
     */
    public void applyTheme() {
        scene.getStylesheets().clear(); // Clear any previously applied styles

        if (theme.equalsIgnoreCase(LIGHT_THEME)) {
            scene.getStylesheets().add(getClass().getResource(LIGHT_THEME_PATH).toExternalForm());
        } else {
            scene.getStylesheets().add(getClass().getResource(DARK_THEME_PATH).toExternalForm());
        }
    }

    /**
     * Toggles between light and dark themes based on the current theme.
     * Updates user preferences and notifies the user of the change.
     */
    @FXML
    private void toggleTheme(String newTheme, String oldTheme, String newThemePath, String oldThemePath) {
        if (theme.equals(oldTheme)) {
            scene.getStylesheets().remove(getClass().getResource(oldThemePath).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(newThemePath).toExternalForm());
            theme = newTheme;

            // Get user prefs, set the theme, and update
            UserPrefs updatedPrefs = (UserPrefs) logic.getUserPrefs();
            logic.setUserPrefs(updatedPrefs.setTheme(newTheme));

            // Notify user
            resultDisplay.setFeedbackToUser(String.format(THEME_CHANGE_MESSAGE, theme));
        } else {
            resultDisplay.setFeedbackToUser(String.format(THEME_ALREADY_SET_MESSAGE, theme));
        }
    }

    /**
     * Handles switching to the light theme by calling toggleTheme with the appropriate parameters.
     */
    @FXML
    private void handleLightTheme() {
        toggleTheme(LIGHT_THEME, DARK_THEME, LIGHT_THEME_PATH, DARK_THEME_PATH);
    }

    /**
     * Handles switching to the dark theme by calling toggleTheme with the appropriate parameters.
     */
    @FXML
    private void handleDarkTheme() {
        toggleTheme(DARK_THEME, LIGHT_THEME, DARK_THEME_PATH, LIGHT_THEME_PATH);
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

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
