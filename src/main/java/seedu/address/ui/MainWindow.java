package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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

    private static final String FXML = "MainWindow.fxml";
    private static final String DARK_THEME = "/view/DarkTheme.css";
    private static final String LIGHT_THEME = "/view/LightTheme.css";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    //private String theme = "light";
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
        this.scene = primaryStage.getScene(); // Ensure scene is initialized

        // Load user preferences to determine the initial theme
        ReadOnlyUserPrefs userPrefs = logic.getUserPrefs();
        this.theme = userPrefs.getTheme();  // Load saved theme from user preferences

        applyTheme();  // Apply the initial theme

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

//    public void postInit() {
//        // Load user preferences to determine the initial theme
//        ReadOnlyUserPrefs userPrefs = logic.getUserPrefs();
//        String theme = userPrefs.getTheme();
//        applyTheme(theme);
//    }

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

    public void applyTheme() {
        scene.getStylesheets().clear();  // Clear any previously applied styles
        if ("light".equalsIgnoreCase(theme)) {
            scene.getStylesheets().add(getClass().getResource(LIGHT_THEME).toExternalForm());
        } else {
            scene.getStylesheets().add(getClass().getResource(DARK_THEME).toExternalForm());
        }
    }

    @FXML
    private void handleLightTheme() {
        if (theme.equals("dark")) {
            scene.getStylesheets().remove(getClass().getResource(DARK_THEME).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(LIGHT_THEME).toExternalForm());
            theme = "light"; // Set to light theme

            // Get user prefs, set the theme, and update
            UserPrefs updatedPrefs = (UserPrefs) logic.getUserPrefs(); // Cast to UserPrefs
            logic.setUserPrefs(updatedPrefs.setTheme("light")); // Update user prefs

            // Notify the user about the theme change
            resultDisplay.setFeedbackToUser("Theme changed to " + theme + " mode.");
        } else {
            resultDisplay.setFeedbackToUser("Theme is already set to " + theme + " mode.");
        }
    }

    @FXML
    private void handleDarkTheme() {
        if (theme.equals("light")) {
            scene.getStylesheets().remove(getClass().getResource(LIGHT_THEME).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(DARK_THEME).toExternalForm());
            theme = "dark"; // Set to dark theme

            // Get user prefs, set the theme, and update
            UserPrefs updatedPrefs = (UserPrefs) logic.getUserPrefs(); // Cast to UserPrefs
            logic.setUserPrefs(updatedPrefs.setTheme("dark")); // Update user prefs

            // Notify the user about the theme change
            resultDisplay.setFeedbackToUser("Theme changed to " + theme + " mode.");
        } else {
            resultDisplay.setFeedbackToUser("Theme is already set to " + theme + " mode.");
        }
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
