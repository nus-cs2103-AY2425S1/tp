package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

    private static final double PERSON_LIST_PANEL_SPLIT_POSITION = 0.55;
    private static final double RESULT_DISPLAY_SPLIT_POSITION = 0.25;
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private SplitPane splitPane;
    private ContactDisplay contactDisplay;
    private ScrollPane scrollableContactDisplay;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

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

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

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
        logger.info("Filling up the inner parts of the window...");
        setupPersonListPanel();
        setupCommandBox();
        setupContactDisplay();
        setupSplitPane();
        setupResultDisplay();
        setupStatusBarFooter();
        addPersonSelectionListener();
        addPersonListChangeListener();
    }

    /**
     * Sets up the person list panel UI.
     */
    private void setupPersonListPanel() {
        logger.info("Setting up PersonListPanel...");
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        personListPanel.getPersonListView().setMouseTransparent(true);
        logger.info("PersonListPanel setup complete.");
    }

    /**
     * Sets up the command box UI.
     */
    private void setupCommandBox() {
        logger.info("Setting up CommandBox...");
        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        logger.info("CommandBox setup complete.");
    }

    /**
     * Sets up the contact display UI.
     */
    private void setupContactDisplay() {
        logger.info("Setting up ContactDisplay...");
        contactDisplay = new ContactDisplay();
        contactDisplay.showHelpDisplay();
        scrollableContactDisplay = contactDisplay.getScrollPane();
        logger.info("ContactDisplay setup complete.");
    }

    /**
     * Sets up the split pane for person list and contact display.
     */
    private void setupSplitPane() {
        logger.info("Setting up SplitPane...");
        splitPane = new SplitPane();
        splitPane.getItems().addAll(personListPanel.getRoot(), contactDisplay.getRoot());
        splitPane.setDividerPositions(PERSON_LIST_PANEL_SPLIT_POSITION);
        personListPanelPlaceholder.getChildren().clear();
        personListPanelPlaceholder.getChildren().add(splitPane);
        logger.info("SplitPane setup complete.");
    }

    /**
     * Sets up the result display pane.
     */
    private void setupResultDisplay() {
        logger.info("Setting up ResultDisplay...");
        SplitPane resultDisplaySplitPane = new SplitPane();
        resultDisplaySplitPane.setOrientation(Orientation.VERTICAL);
        resultDisplay = new ResultDisplay();
        resultDisplaySplitPane.getItems().addAll(resultDisplay.getRoot(), splitPane);
        resultDisplaySplitPane.setDividerPositions(RESULT_DISPLAY_SPLIT_POSITION);
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        VBox.setVgrow(resultDisplaySplitPane, Priority.ALWAYS);
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        logger.info("ResultDisplay setup complete.");
    }

    /**
     * Sets up the status bar footer.
     */
    private void setupStatusBarFooter() {
        logger.info("Setting up StatusBarFooter...");
        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
        logger.info("StatusBarFooter setup complete.");
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
     * Checks if a person is being selected.
     */
    private void addPersonSelectionListener() {
        logger.info("Adding Person selection listener...");
        personListPanel.getPersonListView().getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        logger.info("Person selected: " + newSelection);
                        contactDisplay.updateContactDetails(newSelection);
                    } else {
                        logger.info("No person selected.");
                        contactDisplay.clear();
                    }
                });
    }

    /**
     * Checks for change in the person list.
     */
    private void addPersonListChangeListener() {
        logger.info("Adding PersonList change listener...");
        personListPanel.getPersonList().addListener((ListChangeListener<Person>) change -> {
            while (change.next()) {
                Person selectedPerson = personListPanel.getPersonListView()
                        .getSelectionModel().getSelectedItem();
                if (change.wasRemoved()) {
                    logger.info("Person removed from list.");
                    if (!personListPanel.getPersonList().contains(selectedPerson)) {
                        contactDisplay.clear();
                    }
                }
                if (change.wasUpdated() || change.wasReplaced()) {
                    for (int i = change.getFrom(); i < change.getTo(); i++) {
                        if (i < personListPanel.getPersonList().size()) {
                            Person updatedPerson = personListPanel.getPersonList().get(i);
                            logger.info("Updated person: " + updatedPerson);
                            contactDisplay.updateContactDetails(updatedPerson);
                        }
                    }
                }
            }
        });
    }

    /**
     * Shows the list of possible commands on the right.
     */
    @FXML
    public void handleHelp() {
        contactDisplay.showHelpDisplay();
    }

    /**
     * Opens the help window or focuses on it if it's already opened.'
     */
    @FXML
    public void handleHelpWindow() {
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

            if (commandResult.getPersonToDisplay() != null) {
                contactDisplay.updateContactDetails(commandResult.getPersonToDisplay());
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowHelpWindow()) {
                handleHelpWindow();
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
