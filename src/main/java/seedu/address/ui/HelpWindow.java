package seedu.address.ui;

import java.util.logging.Logger;
import javafx.fxml.FXML;

import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page.
 * Handles UI-related operations and delegates content and logic processing.
 */
public class HelpWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private TextFlow helpContentFlow;

    @FXML
    private ListView<String> tocListView;

    @FXML
    private TableView<String[]> commandSummaryTable;

    @FXML
    private TableColumn<String[], String> actionColumn;

    @FXML
    private TableColumn<String[], String> formatColumn;

    @FXML
    private TableColumn<String[], String> exampleColumn;

    private HelpContentManager contentManager;
    private HelpLogicManager logicManager;

    public HelpWindow(Stage root) {
        super(FXML, root);
        this.contentManager = new HelpContentManager();
        this.logicManager = new HelpLogicManager(contentManager);
        initializeUiComponents();
    }

    public HelpWindow() {
        this(new Stage());
    }

    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

    public void hide() {
        getRoot().hide();
    }

    public void focus() {
        getRoot().requestFocus();
    }

    /** Initializes the UI components such as the Table of Contents and command summary. */
    private void initializeUiComponents() {
        setupTocListView();
        setupCommandSummary();
        updateTextFlow(contentManager.getContent("Introduction"));

        tocListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logicManager.handleTocSelection(newValue, this);
            }
        });
    }

    /**
     * Updates the TextFlow with the given help content.
     * @param content The content to display in the TextFlow.
     */
    public void updateTextFlow(String content) {
        helpContentFlow.getChildren().clear();
        logicManager.formatTextFlow(content, helpContentFlow);
    }

    public void setupTocListView() {
        tocListView.setItems(contentManager.getTableOfContents());
    }

    /** Sets up the command summary TableView with its columns. */
    public void setupCommandSummary() {
        contentManager.initializeCommandSummaryData(commandSummaryTable, actionColumn, formatColumn, exampleColumn);
        commandSummaryTable.setVisible(false);
    }

    public void displayCommandSummary() {
        helpContentFlow.getChildren().clear();
        commandSummaryTable.setVisible(true);
    }

    /**
     * Displays the help content for the specified key.
     * @param key The key representing the help topic.
     */
    public void displayHelpContent(String key) {
        commandSummaryTable.setVisible(false);
        updateTextFlow(contentManager.getContent(key));
    }
}
