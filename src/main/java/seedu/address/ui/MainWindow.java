package seedu.address.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
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

    private static final String USER_GUIDE_URl = "https://ay2425s1-cs2103t-f09-3.github.io/tp/UserGuide.html";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel guestListPanel;
    private PersonListPanel vendorListPanel;

    private ResultDisplay resultDisplay;

    @FXML
    private HBox cardPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private HBox menuBar;

    @FXML
    private HBox contactLists;

    @FXML
    private VBox guestList;

    @FXML
    private VBox vendorList;

    @FXML
    private StackPane guestListPanelPlaceholder;

    @FXML
    private StackPane vendorListPanelPlaceholder;

    @FXML
    private VBox resultDisplayPlaceholder;

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
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        guestListPanel = new PersonListPanel(logic.getFilteredGuestList(), "Guests");
        guestListPanelPlaceholder.getChildren().add(guestListPanel.getRoot());

        vendorListPanel = new PersonListPanel(logic.getFilteredVendorList(), "Vendors");
        vendorListPanelPlaceholder.getChildren().add(vendorListPanel.getRoot());

        HBox.setHgrow(guestList, Priority.ALWAYS);
        HBox.setHgrow(vendorList, Priority.ALWAYS);

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        resultDisplay.setFeedbackToUser("Results of your command will be shown here!");

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        double defaultWidth = guiSettings.getWindowWidth();
        double defaultHeight = guiSettings.getWindowHeight();

        primaryStage.setWidth(defaultWidth);
        primaryStage.setMinWidth(889.0);
        primaryStage.setHeight(defaultHeight);
        primaryStage.setMinHeight(783.0);

        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Directs users to the UserGuide page for help.
     */
    @FXML
    public void handleHelp() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1.3));
        pause.setOnFinished(unused -> {
            try {
                URI uri = new URI(USER_GUIDE_URl);
                Desktop.getDesktop().browse(uri);
            } catch (Exception e) {
                logger.severe("Error occurred while opening the help URL:\n" + e);
            }
        });
        pause.play();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit(Event event) {
        event.consume();
        handleExit();
    }

    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);

        // Sets a 2-second delay before exiting
        // To make exiting the application feel less abrupt
        resultDisplay.setFeedbackToUser("BridalBuddy is closing now... See you again!");
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(unused -> Platform.exit());
        pause.play();
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
