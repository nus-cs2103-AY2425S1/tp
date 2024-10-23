package seedu.address.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
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
        initializeUIComponents();
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

    private void initializeUIComponents() {
        setupTocListView();
        setupCommandSummary();
        updateTextFlow(contentManager.getContent("Introduction"));

        tocListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logicManager.handleTocSelection(newValue, this);
            }
        });
    }

    public void updateTextFlow(String content) {
        helpContentFlow.getChildren().clear();
        logicManager.formatTextFlow(content, helpContentFlow);
    }

    public void setupTocListView() {
        tocListView.setItems(contentManager.getTableOfContents());
    }

    public void setupCommandSummary() {
        contentManager.initializeCommandSummaryData(commandSummaryTable, actionColumn, formatColumn, exampleColumn);
        commandSummaryTable.setVisible(false);
    }

    public void displayCommandSummary() {
        helpContentFlow.getChildren().clear();
        commandSummaryTable.setVisible(true);
    }

    public void displayHelpContent(String key) {
        commandSummaryTable.setVisible(false);
        updateTextFlow(contentManager.getContent(key));
    }
}
