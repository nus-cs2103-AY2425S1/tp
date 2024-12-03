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
        initializeCommandSummaryList();
        setUpColumns();
        setupCells();

        commandSummaryTable.setItems(commandSummaryList);
        commandSummaryTable.setSelectionModel(null);
    }

    /**
     * Initializes the command summary list with predefined commands.
     */
    private void initializeCommandSummaryList() {
        commandSummaryList.addAll(
                new CommandSummary("Add", "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS b/AGE s/SEX "
                        + "ap/FUTURE APPOINTMENT t/TAG\ne.g., add n/Evie Sage p/88888888 e/eviesage@example.com "
                        + "a/Hickory Forest b/23 s/Female ap/11/11/2025 1100"),
                new CommandSummary("Clear", "clear"),
                new CommandSummary("Delete", "delete INDEX / delete NAME\ne.g., delete 3, delete Alex Yeoh"),
                new CommandSummary("Edit", "edit NAME [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [b/AGE] [s/SEX] "
                        + "[ap/FUTURE APPOINTMENT] [t/TAG]\ne.g., edit John Doe n/Betsy Crower t/ ap/"),
                new CommandSummary("Exit", "exit"),
                new CommandSummary("Filter", "filter [ap/APPOINTMENT_DATE_LOWER_BOUND - APPOINTMENT_DATE_UPPER_BOUND]"
                        + " [b/AGE_LOWER_BOUND - AGE_UPPER_BOUND] [t/TAG]...\n"
                        + "e.g.,filter b/70-79 t/medication t/Dr Tan"),
                new CommandSummary("Find", "find KEYWORD [MORE_KEYWORDS]\ne.g., find olive 87438"),
                new CommandSummary("Help", "help"),
                new CommandSummary("Import", "import FILENAME.json"),
                new CommandSummary("List", "list\nlist*"),
                new CommandSummary("Note", "note NAME [ap/PREVIOUS APPOINTMENT] [m/MEDICATION] [r/REMARK]\n"
                        + "e.g., note John Doe m/10mg Ibuprofen"),
                new CommandSummary("Sort", "sort \ne.g., sort"),
                new CommandSummary("Star", "star INDEX / star NAME\ne.g., star 3, star Alex Yeoh"),
                new CommandSummary("Unstar", "unstar INDEX / unstar NAME\ne.g., unstar 3, unstar Alex Yeoh"),
                new CommandSummary("View", "view INDEX / view NAME\ne.g., view 3, view Alex Yeoh")
        );
    }

    /**
     * Sets up the columns of the command summary table.
     */
    private void setUpColumns() {
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        formatColumn.setCellValueFactory(new PropertyValueFactory<>("format"));

        // Ensure the table fills the available space for both headers and columns
        commandSummaryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Set width for columns
        actionColumn.setMaxWidth(1f * Integer.MAX_VALUE * 15); // 15% of available width for action column
        formatColumn.setMaxWidth(1f * Integer.MAX_VALUE * 85); // 85% of available width for format column
    }

    /**
     * Sets up the cells for the format column to allow copying of text.
     */
    private void setupCells() {
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
                    textArea.setWrapText(true);
                    textArea.setEditable(false);
                    textArea.setPrefHeight(70);
                    textArea.setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-font-size: 16;");
                    setGraphic(textArea);
                }
            }
        });
    }
}
