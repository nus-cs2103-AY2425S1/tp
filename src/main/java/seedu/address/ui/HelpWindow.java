package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103-f10-2.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide for more information: "
            + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;
    @FXML
    private Label helpMessage;
    @FXML
    private TableView<String[]> commandTable;
    @FXML
    private TableColumn<String[], String> commandColumn;
    @FXML
    private TableColumn<String[], String> descriptionColumn;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Initializes the help window.
     */
    @FXML
    public void initialize() {
        commandColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));

        commandColumn.setMinWidth(100);
        descriptionColumn.setMinWidth(300);
        descriptionColumn.setMaxWidth(Double.MAX_VALUE);

        commandColumn.setSortable(false);
        descriptionColumn.setSortable(false);

        ObservableList<String[]> commandList = FXCollections.observableArrayList(
                new String[] { "add", "Adds a contact to the list" },
                new String[] { "clear", "Clears the contact list" },
                new String[] { "delete", "Deletes contact(s) by index" },
                new String[] { "remark", "Adds a remark to a contact" },
                new String[] { "seed", "Seeds the contact list with sample data" },
                new String[] { "edit", "Edits a contact in the list" },
                new String[] { "list", "Lists all contacts" },
                new String[] { "find", "Finds contact(s) by keyword" },
                new String[] { "sort", "Sorts contacts by specific field" },
                new String[] { "view", "Views more information regarding a contact" },
                new String[] { "exit", "Exits the application" });

        commandTable.setItems(commandList);
        commandTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Set height of table dynamically
        commandTable.setFixedCellSize(30);
        commandTable.prefHeightProperty().bind(
                Bindings.size(commandTable.getItems()).multiply(commandTable.getFixedCellSize()).add(30));

    }

}
