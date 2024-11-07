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

    private Stage primaryStage;
    private Logic logic;

    private ClientListPanel clientListPanel;
    private ClientDetailPanel clientDetailPanel;
    private ResultDisplay resultDisplay;
    private StatusPieChart statusPieChart;
    private HelpWindow helpWindow;

    private Client currentlyViewedClient;

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

    private final ListChangeListener<Client> clientListListener = change -> {
        while (change.next()) {
            if (change.wasAdded() || change.wasRemoved() || change.wasUpdated()) {
                updateStatusChart();
                handleClientListChange(change);
            }
        }
    };

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
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
     * Returns the main {@code Stage} of the application.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Configures the keyboard shortcuts for menu items.
     */
    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the specified {@code keyCombination} as the shortcut for the given {@code menuItem}.
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
     * Initializes and adds the primary UI components to the main window.
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
     * Handles updates in the client list, updating the UI as necessary.
     */
    private void handleClientListChange(ListChangeListener.Change<? extends Client> change) {
        if (currentlyViewedClient != null) {
            if (change.wasRemoved() && change.getRemoved().stream()
                    .anyMatch(client -> client.equals(currentlyViewedClient))) {
                boolean clientStillExists = logic.getFilteredClientList().stream()
                        .anyMatch(client -> client.equals(currentlyViewedClient));
                if (!clientStillExists) {
                    handleCloseCommand();
                    return;
                }
            }

            boolean clientFound = false;
            for (Client client : logic.getFilteredClientList()) {
                if (client.equals(currentlyViewedClient)) {
                    if (client != currentlyViewedClient) {
                        currentlyViewedClient = client;
                        Platform.runLater(() -> clientDetailPanel.setClientDetails(client));
                    }
                    clientFound = true;
                    break;
                }
            }

            if (!clientFound) {
                handleCloseCommand();
            }
        }
    }

    /**
     * Executes the specified command text and returns the result.
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            int indexToReview = getIndexForReview(commandText);
            CommandResult commandResult = executeMainCommand(commandText);

            if (!commandText.trim().toLowerCase().equals("close")) {
                updateViewPanelWithFilteredList();
            }

            handlePostCommandExecution(commandResult, commandText, indexToReview);
            return commandResult;
        } catch (CommandException | ParseException e) {
            handleCommandError(e);
            throw e;
        }
    }

    /**
     * Updates the view panel with the filtered client list.
     */
    private void updateViewPanelWithFilteredList() {
        if (currentlyViewedClient != null && !isClientInFilteredList(currentlyViewedClient)) {
            handleCloseCommand();
        }
    }

    /**
     * Checks if the specified client is in the current filtered list.
     */
    private boolean isClientInFilteredList(Client client) {
        return logic.getFilteredClientList().stream()
                .anyMatch(c -> c.equals(client));
    }

    /**
     * Returns the index of the client to review based on the command text.
     */
    private int getIndexForReview(String commandText) {
        if (commandText.trim().toLowerCase().startsWith("edit") && currentlyViewedClient != null) {
            for (int i = 0; i < logic.getFilteredClientList().size(); i++) {
                if (logic.getFilteredClientList().get(i).equals(currentlyViewedClient)) {
                    return i + 1;
                }
            }
        }
        return -1;
    }

    /**
     * Executes the main command and returns the result.
     */
    private CommandResult executeMainCommand(String commandText) throws CommandException, ParseException {
        CommandResult commandResult = logic.execute(commandText);
        logger.info("Result: " + commandResult.getFeedbackToUser());
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
        return commandResult;
    }

    /**
     * Handles actions to take after a command is executed, such as displaying help or closing the view.
     */
    private void handlePostCommandExecution(CommandResult commandResult, String commandText, int indexToReview) {
        if (indexToReview > 0) {
            reviewClientAfterEdit(indexToReview);
        } else if (commandResult.isShowClient()) {
            handleViewCommand(commandResult);
        } else if (commandText.trim().toLowerCase().equals("close")) {
            handleCloseCommand();
        } else if (commandResult.isConfirmedDeletion() && currentlyViewedClient != null
                && currentlyViewedClient.equals(commandResult.getDeletedClient())) {
            handleCloseCommand();
        }

        if (commandResult.isShowHelp()) {
            handleHelp();
        }
        if (commandResult.isExit()) {
            handleExit();
        }
    }

    /**
     * Displays error messages when a command fails.
     */
    private void handleCommandError(Exception e) {
        logger.info("Invalid command: " + e.getMessage());
        resultDisplay.setFeedbackToUser(e.getMessage());
    }

    /**
     * Re-views a client after an edit operation.
     */
    private void reviewClientAfterEdit(int index) {
        try {
            CommandResult viewResult = logic.execute("view " + index);
            handleViewCommand(viewResult);
        } catch (Exception e) {
            logger.warning("Failed to re-view client after edit: " + e.getMessage());
        }
    }

    /**
     * Updates the status chart with the current client data.
     */
    private void updateStatusChart() {
        final int[] counts = new int[3];

        for (Client client : logic.getFilteredClientList()) {
            switch (client.getStatus().status) {
            case NA -> counts[0]++;
            case NON_URGENT -> counts[1]++;
            case URGENT -> counts[2]++;
            default -> {
                logger.warning("Unexpected status value: " + client.getStatus().status);
            }
            }
        }

        Platform.runLater(() -> {
            statusPieChart.updateChartData(counts[0], counts[1], counts[2]);
        });
    }

    /**
     * Handles the view command, displaying the specified client's details.
     */
    private void handleViewCommand(CommandResult commandResult) {
        Client clientToView = commandResult.getViewedClient();
        if (clientToView != null) {
            currentlyViewedClient = clientToView;
            if (!splitPane.getItems().contains(clientDetailsPanelPlaceholder)) {
                splitPane.getItems().add(clientDetailsPanelPlaceholder);
                placeholderImage.setVisible(false);
                splitPane.setDividerPositions(0.6);
            }
            clientDetailPanel.setClientDetails(clientToView);
        }
    }

    /**
     * Closes the client details view.
     */
    private void handleCloseCommand() {
        if (splitPane.getItems().contains(clientDetailsPanelPlaceholder)) {
            splitPane.getItems().remove(clientDetailsPanelPlaceholder);
            placeholderImage.setVisible(true);
            currentlyViewedClient = null;
        }
    }

    /**
     * Opens the help window or focuses on it if already open.
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
     * Exits the application, saving the current window settings.
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
     * Sets the default window size based on {@code guiSettings}.
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
     * Configures the split panes and their divider positions.
     */
    private void setupSplitPanes() {
        topSplitPane.setDividerPositions(0.8);
        topSplitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            if (Math.abs(newValue.doubleValue() - 0.8) > 0.05) {
                Platform.runLater(() -> topSplitPane.setDividerPositions(0.8));
            }
        });
    }

    /**
     * Displays the primary stage.
     */
    void show() {
        primaryStage.show();
    }
}
