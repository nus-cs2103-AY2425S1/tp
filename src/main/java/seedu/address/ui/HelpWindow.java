package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;


/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    /**
     *  helpCommand data type used to populate the table
     */
    public static class HelpCommand {
        private final SimpleStringProperty command;
        private final SimpleStringProperty description;

        /**
         * Constructor for helpCommand datat type
         */
        public HelpCommand(String command, String description) {
            this.command = new SimpleStringProperty(command);
            this.description = new SimpleStringProperty(description);
        }

        public String getCommand() {
            return command.get();
        }

        public String getDescription() {
            return description.get();
        }
    }

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-t12-4.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Press Esc or F1 to close this window!"
            + "\nRefer to the user guide for more info: " + USERGUIDE_URL;


    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;
    @FXML
    private TableView<HelpCommand> helpTable;


    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);

        this.getRoot().setResizable(true);
        this.getRoot().setMinHeight(500);
        this.getRoot().setMinWidth(600);

        helpTable(helpTable);
        helpMessage.setText(HELP_MESSAGE);
        // Add key event filter for ESC key to close the window
        getRoot().addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ESCAPE
                    || event.getCode() == javafx.scene.input.KeyCode.F1) {
                hide();
                event.consume();
            }
        });
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    private void helpTable(TableView<HelpCommand> table) {

        helpTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<HelpCommand, String> commandColumn = new TableColumn<>("Command");
        commandColumn.setCellValueFactory(new PropertyValueFactory<>("command"));
        commandColumn.setMaxWidth(200);
        commandColumn.setMinWidth(100);

        TableColumn<HelpCommand, String> descriptionColumn = new TableColumn<>("Usage");
        descriptionColumn.setCellFactory(col -> {
            TableCell<HelpCommand, String> cell = new TableCell<>() {
                private final Text text = new Text();

                {
                    text.wrappingWidthProperty().bind(descriptionColumn.widthProperty());
                    setGraphic(text);
                    setPrefHeight(Control.USE_COMPUTED_SIZE);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        text.setText(null);
                    } else {
                        // Retrieve the full description using getDescription()
                        try {
                            HelpCommand helpCommand = getTableRow().getItem();
                            text.setText(helpCommand.getDescription());
                            setPrefHeight(text.getBoundsInLocal().getHeight() + 1); // Add padding
                        } catch (NullPointerException e) {
                            //do nothing
                        }
                    }
                }
            };
            return cell;
        });

        table.setEditable(false);

        ObservableList<HelpCommand> data =
                FXCollections.observableArrayList(new HelpCommand("Add",
                                "`add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [g/GAME]… [t/TAG]… [pt/TIME-TIME]…`"),
                        new HelpCommand("Delete", "`delete INDEX`"),
                        new HelpCommand("Edit", "`edit INDEX [n/NAME] [p/PHONE_NUMBER] "
                                + "[e/EMAIL] [a/ADDRESS] [g/GAME]… [t/TAG]… [pt/TIME]…​`"),
                        new HelpCommand("AddGame", "`addgame INDEX g/GAME [u/USERNAME]"
                                + " [s/SKILL_LEVEL] [r/ROLE]`"),
                        new HelpCommand("EditGame", "`editgame INDEX g/GAME [u/USERNAME]"
                                + " [s/SKILL_LEVEL] [r/ROLE]`"),
                        new HelpCommand("DeleteGame", "`deletegame INDEX g/GAME`"),
                        new HelpCommand("FavGame", "`favgame INDEX g/GAME`"),
                        new HelpCommand("UnFavGame", "`unfavgame INDEX g/GAME`"),
                        new HelpCommand("Find", "`find KEYWORD [MORE_KEYWORDS]…` \ne.g., "
                                + "`find James Jake`"),
                        new HelpCommand("FindTime", "`findtime TIME-TIME [TIME-TIME]…` \ne.g., "
                                + "`findtime 1700-1800 2130-2300`"),
                        new HelpCommand("Clear", "`clear`"),
                        new HelpCommand("List", "`list`"),
                        new HelpCommand("Undo", "`undo`"),
                        new HelpCommand("Help", "`help`"),
                        new HelpCommand("Save", "`save`"),
                        new HelpCommand("Load", "`load`"),
                        new HelpCommand("Exit", "`exit`")
                );

        // Add data to the table
        table.setItems(data);
        table.getColumns().add(commandColumn);
        table.getColumns().add(descriptionColumn);
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
}
