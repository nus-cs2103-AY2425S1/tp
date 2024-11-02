package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
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
import seedu.address.model.client.Client;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final ListChangeListener<Client> clientListListener = change -> {
        while (change.next()) {
            if (change.wasAdded() || change.wasRemoved() || change.wasUpdated()) {
                updateStatusChart();
            }
        }
    };

    private Stage primaryStage;
    private Logic logic;

    private ClientListPanel clientListPanel;
    private ClientDetailPanel clientDetailPanel;
    private ResultDisplay resultDisplay;
    private StatusPieChart statusPieChart;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;
    @FXML
    private SplitPane topSplitPane;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane clientListPanelPlaceholder;

    @FXML
    private StackPane clientDetailsPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;
    @FXML
    private StackPane statusChartPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private ImageView placeholderImage;

    /**
     * Creates a new MainWindow with the given Stage and Logic.
     * Initializes the window with default settings and accelerators.
     *
     * @param primaryStage The primary stage for this window
     * @param logic The logic manager that handles command execution
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);
        this.primaryStage = primaryStage;
        this.logic = logic;
        setWindowDefaultSize(logic.getGuiSettings());
        setAccelerators();
        helpWindow = new HelpWindow();
        logic.getFilteredClientList().addListener(clientListListener);
    }

    /**
     * Returns the primary stage of the application.
     *
     * @return The primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets up keyboard accelerators for menu items.
     */
    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

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

        clientDetailPanel = new ClientDetailPanel();
        clientDetailsPanelPlaceholder.getChildren().add(clientDetailPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAgentAssistFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        statusPieChart = new StatusPieChart();
        statusChartPlaceholder.getChildren().add(statusPieChart.getRoot());

        setupSplitPanes();
        splitPane.setDividerPositions(0.5);

        splitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            splitPane.setDividerPositions(0.5);
        });

        splitPane.getItems().remove(clientDetailsPanelPlaceholder);
        topSplitPane.getStyleClass().add("top-split-pane");
        statusChartPlaceholder.getStyleClass().add("chart-container");
        updateStatusChart();
    }

    /**
     * Updates the status pie chart with current data
     */
    private void updateStatusChart() {
        final int[] counts = new int[3]; // [none, nonUrgent, urgent]

        for (Client client : logic.getFilteredClientList()) {
            switch (client.getStatus().status) {
            case NONE -> counts[0]++;
            case NON_URGENT -> counts[1]++;
            case URGENT -> counts[2]++;
            default -> {
                logger.warning("Unexpected status value: " + client.getStatus().status);
            }
            }
        }

        Platform.runLater(() -> {
            statusPieChart.updateChartData(counts[0], counts[1], counts[2]);

            int total = counts[0] + counts[1] + counts[2];
            logger.info(String.format("Chart updated - None: %d, Non-Urgent: %d, Urgent: %d (Total: %d)",
                    counts[0], counts[1], counts[2], total));
        });
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

    /**
     * Makes the main window visible.
     */
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
     * Handles the view command by updating the UI to show client details.
     * If the details panel is not visible, adds it to the split pane.
     * Updates the client details based on the provided index in the command.
     *
     * @param commandText The full command text containing the view command and index
     */
    private void handleViewCommand(String commandText) {
        if (!splitPane.getItems().contains(clientDetailsPanelPlaceholder)) {
            splitPane.getItems().add(clientDetailsPanelPlaceholder);
            placeholderImage.setVisible(false);
            splitPane.setDividerPositions(0.6);
        }

        String[] commandParts = commandText.split("\\s+");
        if (commandParts.length > 1) {
            try {
                int index = Integer.parseInt(commandParts[1]) - 1; // Assuming 1-based indexing in UI
                Client clientToView = logic.getFilteredClientList().get(index);
                clientDetailPanel.setClientDetails(clientToView);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                // Handle invalid index
                resultDisplay.setFeedbackToUser("Invalid client index.");
            }
        } else {
            resultDisplay.setFeedbackToUser("Please provide a client index to view.");
        }
    }

    private void handleCloseCommand() {
        splitPane.getItems().remove(clientDetailsPanelPlaceholder);
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

            if (commandResult.isShowClient()) {
                handleViewCommand(commandText);
            } else {
                handleCloseCommand();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Sets up the split panes and their divider positions
     */
    private void setupSplitPanes() {
        topSplitPane.setDividerPositions(0.8);

        topSplitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            if (Math.abs(newValue.doubleValue() - 0.8) > 0.05) {
                Platform.runLater(() -> topSplitPane.setDividerPositions(0.8));
            }
        });
    }
}
