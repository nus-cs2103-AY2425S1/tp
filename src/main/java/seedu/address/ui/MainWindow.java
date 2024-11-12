package seedu.address.ui;

import java.util.Map;
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
import seedu.address.model.person.Person;

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
    private ResultDisplay resultDisplay;
    private CommandBox commandBox;
    private HelpWindow helpWindow;
    private ReportBugWindow reportBugWindow;
    private OverviewPanel overviewPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem reportBugItem;

    @FXML
    private MenuItem summaryMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane overviewPanelPlaceholder;

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
        setAcceleratorsBugReport();

        helpWindow = new HelpWindow();
        reportBugWindow = new ReportBugWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    private void setAcceleratorsBugReport() {
        setAccelerator(reportBugItem, KeyCombination.valueOf("F10"));
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

    void fillInnerParts() {
        this.resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        this.commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        this.overviewPanel = new OverviewPanel();

        // Display summary data as default
        Map<String, Long> summaryData = logic.getSummaryData(); // Assume `getSummaryData` returns a valid summary map
        overviewPanel.showSummary(summaryData);

        overviewPanelPlaceholder.getChildren().add(overviewPanel.getRoot());

        this.personListPanel = new PersonListPanel(logic.getFilteredPersonList(), overviewPanel);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

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
        // If the Help Window is not showing, show it
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            // If the Help Window is minimized, restore it
            Stage helpStage = helpWindow.getRoot();
            if (helpStage.isIconified()) {
                helpStage.setIconified(false); // Restore the minimized Help Window
            }
            helpWindow.focus(); // Focus on the Help Window
        }
    }

    /**
     * Opens the report bug window or focuses on it if it's already opened.
     */
    @FXML
    public void handleReportBug() {
        if (!reportBugWindow.isShowing()) {
            reportBugWindow.show();
        } else {
            reportBugWindow.focus();
        }
    }

    /**
     * Displays the summary in the command result panel.
     */
    @FXML
    public void handleSummary() {
        try {
            executeCommand("summary");
            showSummaryInOverviewPanel();
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to execute summary command: " + e.getMessage());
        }
    }

    /**
     * Displays the summary in the command result panel.
     */
    private void showSummaryInOverviewPanel() {
        Map<String, Long> summaryData = logic.getSummaryData();
        overviewPanel.showSummary(summaryData);
    }

    /**
     * Updates the {@code OverviewPanel} to display the details of a specified {@code Person}.
     *
     * @param person The person whose details are to be displayed in the overview panel.
     */
    public void showOverviewPanel(Person person) {
        if (overviewPanel != null) {
            overviewPanel.updateOverviewDetails(person);
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
        reportBugWindow.hide();
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

            String[] feedbackToUser = commandResult.getFeedbackToUser().split(" ");
            if (feedbackToUser[0].equals("Viewed")) {
                int index = Integer.parseInt(commandText.split(" ")[1]);
                System.out.println(index);
                personListPanel.selectPersonAtIndex(index - 1);
            }

            // Check if the commandText was the summary command and update the overview panel accordingly
            if (commandText.trim().equalsIgnoreCase("summary")) {
                showSummaryInOverviewPanel();
            }

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
