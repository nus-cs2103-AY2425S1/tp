package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.CommandSummaryUtil;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-f12-4.github.io/tp/UserGuide.html";
    public static final String NAVIGATE_INSTRUCTIONS = "Press Tab to navigate between the table and the buttons.";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    final ObservableList<String[]> data = CommandSummaryUtil.getCommandSummaryData();

    @FXML
    private TableView table = new TableView();
    @FXML
    private Label navigateInstructions;
    @FXML
    private Button copyButton;
    @FXML
    private Button closeButton;


    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException If this method is called on a thread other than the JavaFX Application Thread.
     */
    public void show() {
        int tableWidth = 800;
        int tableHeight = 600;
        Scene scene = new Scene(new Group(), tableWidth, tableHeight);
        logger.fine("Showing help page about the application.");
        table.setEditable(true);

        // Clear existing columns to prevent duplicates
        table.getColumns().clear();

        String[] columnNames = {"Action", "Format", "Examples"};
        double[] columnWidths = {0.1, 0.3, 0.5};

        for (int i = 0; i < columnNames.length; i++) {
            final int index = i; // Create a final copy of i
            TableColumn<String[], String> column = new TableColumn<>(columnNames[i]);
            column.setMinWidth(tableWidth * columnWidths[i]);
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[index]));
            table.getColumns().add(column);
            column.setCellFactory(tc -> {
                TableCell<String[], String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(column.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            });
        }

        // Setting style of rows - font
        StringProperty style = new SimpleStringProperty();
        style.bind(new SimpleStringProperty("-fx-font-family: Consolas"));

        // Set font of every row to Consolas
        table.setRowFactory(tv -> {
            TableRow<String> row = new TableRow<>();
            row.styleProperty().bind(style);
            return row;
        });

        // Set columns to fill up space instead of creating new ones
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(data);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        // Pseudo Centers the table
        vbox.setPadding(new Insets(10, tableWidth * 0.05, 10, tableWidth * 0.05));
        vbox.getChildren().addAll(table);

        // Create a Hyperlink
        Hyperlink userGuideLink = new Hyperlink("Click here for our User Guide!");
        userGuideLink.setOnAction(event -> {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(USERGUIDE_URL));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Add components to the VBox
        navigateInstructions.setText(NAVIGATE_INSTRUCTIONS);
        Font consolas = new Font("Consolas", 12);
        navigateInstructions.setFont(consolas);
        userGuideLink.setFont(consolas);
        copyButton.setFont(consolas);
        closeButton.setFont(consolas);
        vbox.getChildren().addAll(navigateInstructions, userGuideLink, copyButton, closeButton);

        // Center the VBox
        vbox.setAlignment(Pos.CENTER);

        // Add the VBox to the Scene
        Group group = (Group) scene.getRoot();
        group.getChildren().addAll(vbox);
        this.getRoot().setScene(scene);
        getRoot().show();
        getRoot().centerOnScreen();
        getRoot().setTitle("Command Cheatsheet");
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
