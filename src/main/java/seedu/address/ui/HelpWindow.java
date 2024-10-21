package seedu.address.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.model.contactrecord.ContactRecord;
import seedu.address.model.person.Address;
import seedu.address.model.person.CallFrequency;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-f14b-3.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the online user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TableView<Command> commandSummary;

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
        initializeCommandSummary();
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
     * Initializes the Command Summary which is a {@code TableView}
     */
    @SuppressWarnings("unchecked")
    @FXML
    private void initializeCommandSummary() {
        ObservableList<Command> observableCommandList = FXCollections.observableArrayList();
        populateCommandSummary(observableCommandList);

        commandSummary.setItems(observableCommandList);

        TableColumn<Command, String> actionCol = new TableColumn<>("Action");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("action"));

        TableColumn<Command, String> formatExampleCol = new TableColumn<>("Format, Examples");
        formatExampleCol.setCellValueFactory(new PropertyValueFactory<>("formatExample"));

        commandSummary.getColumns().setAll(actionCol, formatExampleCol);
    }

    /**
     * Populates the Command Summary with available commands
     * @param commandList
     */
    private void populateCommandSummary(ObservableList<Command> commandList) {
        Person samplePerson = new Person(new Nric("S4260423B"), new Name("John Doe"), new Phone("98765432"),
                new Email("johnd@example.com"), new Address("John street"), new HashSet<>(),
                new CallFrequency("7"));

        commandList.add(new AddCommand(samplePerson));
        commandList.add(new ClearCommand());
        commandList.add(new DeleteCommand(Index.fromOneBased(1)));
        commandList.add(new EditCommand(Index.fromOneBased(1),
                new EditCommand.EditPersonDescriptor()));
        commandList.add(new FindCommand(new NameContainsKeywordsPredicate(new ArrayList<>())));
        commandList.add(new HistoryCommand(Index.fromOneBased(1)));
        commandList.add(new ListCommand());

        String dateStr = "2020-12-01";
        assert(ContactRecord.isValidContactRecord(dateStr)) : "mark command example should have a valid date";
        commandList.add(new MarkCommand(Index.fromOneBased(1),
                new ContactRecord(LocalDate.parse(dateStr), "")));
        commandList.add(new HelpCommand());
        commandList.add(new ExitCommand());
    }
}
