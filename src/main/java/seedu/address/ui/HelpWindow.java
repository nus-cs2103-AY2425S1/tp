package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-f12-4.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the command cheatsheet: "
        + "\nFor detailed help, refer to the user guide at: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    final ObservableList<String[]> data = FXCollections.observableArrayList(
            new String[]{AddCommand.COMMAND_SUMMARY_ACTION, AddCommand.COMMAND_SUMMARY_FORMAT,
                         AddCommand.COMMAND_SUMMARY_EXAMPLES},
            new String[]{ClearCommand.COMMAND_SUMMARY_ACTION, ClearCommand.COMMAND_SUMMARY_FORMAT,
                         ClearCommand.COMMAND_SUMMARY_EXAMPLES},
            new String[]{DeleteCommand.COMMAND_SUMMARY_ACTION, DeleteCommand.COMMAND_SUMMARY_FORMAT,
                         DeleteCommand.COMMAND_SUMMARY_EXAMPLES},
            new String[]{EditCommand.COMMAND_SUMMARY_ACTION, EditCommand.COMMAND_SUMMARY_FORMAT,
                         EditCommand.COMMAND_SUMMARY_EXAMPLES},
            new String[]{ExitCommand.COMMAND_SUMMARY_ACTION, ExitCommand.COMMAND_SUMMARY_FORMAT,
                         ExitCommand.COMMAND_SUMMARY_EXAMPLES},
            new String[]{ExportCommand.COMMAND_SUMMARY_ACTION, ExportCommand.COMMAND_SUMMARY_FORMAT,
                         ExportCommand.COMMAND_SUMMARY_EXAMPLES},
            new String[]{FindCommand.COMMAND_SUMMARY_ACTION, FindCommand.COMMAND_SUMMARY_FORMAT,
                         FindCommand.COMMAND_SUMMARY_EXAMPLES},
            new String[]{HelpCommand.COMMAND_SUMMARY_ACTION, HelpCommand.COMMAND_SUMMARY_FORMAT,
                         HelpCommand.COMMAND_SUMMARY_EXAMPLES},
            new String[]{ListCommand.COMMAND_SUMMARY_ACTION, ListCommand.COMMAND_SUMMARY_FORMAT,
                         ListCommand.COMMAND_SUMMARY_EXAMPLES},
            new String[]{RedoCommand.COMMAND_SUMMARY_ACTION, RedoCommand.COMMAND_SUMMARY_FORMAT,
                         RedoCommand.COMMAND_SUMMARY_EXAMPLES},
            new String[]{UndoCommand.COMMAND_SUMMARY_ACTION, UndoCommand.COMMAND_SUMMARY_FORMAT,
                         UndoCommand.COMMAND_SUMMARY_EXAMPLES}
    );

    @FXML
    private TableView table = new TableView();
    @FXML
    private Button copyButton;

    @FXML
    private Button closeButton;

    @FXML
    private Label helpMessage;

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
     * If this method is called on a thread other than the JavaFX Application Thread.
     */
    public void show() {
        Scene scene = new Scene(new Group(), 600, 600);
        logger.fine("Showing help page about the application.");
        table.setEditable(true);

        // Clear existing columns to prevent duplicates
        table.getColumns().clear();

        TableColumn<String[], String> action = new TableColumn<>("Action");
        action.setMinWidth(100);
        action.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));

        TableColumn<String[], String> format = new TableColumn<>("Format");
        format.setMinWidth(200);
        format.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));

        TableColumn<String[], String> examples = new TableColumn<>("Examples");
        examples.setMinWidth(250);
        examples.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));

        // Set the resize policy to automatically adjust columns
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(action, format, examples);
        table.setItems(data);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(table);

        vbox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        vbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(table, Priority.ALWAYS);

        // Create a Hyperlink
        Hyperlink userGuideLink = new Hyperlink("Click here for our User Guide!");
        userGuideLink.setOnAction(event -> {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(USERGUIDE_URL));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Add the hyperlink to the VBox
        vbox.getChildren().addAll(userGuideLink, copyButton, closeButton);

        // Make VBox resizable
        vbox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        vbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        this.getRoot().setScene(scene);
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
     * Closes the help window.
     */
    @FXML
    private void closeHelpWindow() {
        getRoot().close();
    }
}
