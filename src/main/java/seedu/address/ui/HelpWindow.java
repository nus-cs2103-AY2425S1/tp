package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-t10-1.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TableView<CommandSummary> commandSummaryTable;
    @FXML
    private TableColumn<CommandSummary, String> actionColumn;
    @FXML
    private TableColumn<CommandSummary, String> formatColumn;

    private ObservableList<CommandSummary> commandSummaryList = FXCollections.observableArrayList();

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        initializeCommandSummary();
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
     * Initializes the command summary table and wraps the text in the cells.
     */
    private void initializeCommandSummary() {
        commandSummaryList.addAll(
                new CommandSummary("Add", "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS b/AGE s/SEX "
                        + "ap/APPOINTMENT t/TAG\ne.g., add n/Evie Sage p/88888888 e/eviesage@example.com "
                        + "a/Hickory Forest b/23 s/Female ap/11/11/2024 1100"),
                new CommandSummary("Clear", "clear"),
                new CommandSummary("Delete", "delete INDEX / delete NAME\ne.g., delete 3, delete Alex Yeoh"),
                new CommandSummary("Edit", "edit NAME [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [b/AGE] [s/SEX] "
                        + "[ap/APPOINTMENT] [t/TAG]\ne.g., edit John Doe n/Betsy Crower t/ ap/"),
                new CommandSummary("Export", "export"),
                new CommandSummary("Find", "find KEYWORD [MORE_KEYWORDS]\ne.g., find olive 87438"),
                new CommandSummary("Help", "help"),
                new CommandSummary("List", "list"),
                new CommandSummary("Note", "note NAME [ap/APPOINTMENT] [m/MEDICATION] [r/REMARK]\n"
                                           + "e.g., note John Doe m/10mg Ibuprofen"),
                new CommandSummary("Sort", "sort \ne.g., sort"),
                new CommandSummary("Star", "star INDEX / star NAME\ne.g., star 3, star Alex Yeoh"),
                new CommandSummary("Unstar", "unstar INDEX / unstar NAME\ne.g., unstar 3, unstar Alex Yeoh")
        );


        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        formatColumn.setCellValueFactory(new PropertyValueFactory<>("format"));

        // Ensure the table fills the available space for both headers and columns
        commandSummaryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Set proportional width for columns (affects both headers and columns)
        actionColumn.setMaxWidth(1f * Integer.MAX_VALUE * 15); // 15% of available width for action column
        formatColumn.setMaxWidth(1f * Integer.MAX_VALUE * 85); // 85% of available width for format column

        // Make format and examples selectable to copy
        formatColumn.setCellFactory(tc -> new TableCell<>() {
            private final TextArea textArea = new TextArea();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    textArea.setText(item);
                    textArea.setWrapText(true); // Enable text wrapping
                    textArea.setEditable(false); // Prevent users from editing text
                    textArea.setPrefHeight(80); // Adjust height to content
                    textArea.setStyle("-fx-background-color: transparent; -fx-padding: 5;"); // Transparent background
                    setGraphic(textArea);
                }
            }
        });

        // Set items into the table
        commandSummaryTable.setItems(commandSummaryList);
        commandSummaryTable.setSelectionModel(null);
    }
}
