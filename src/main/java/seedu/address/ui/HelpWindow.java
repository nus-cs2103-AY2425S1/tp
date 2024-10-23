package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.awt.Desktop;
import java.net.URI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
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

    private Map<String, String> contentMap = new HashMap<>();
    private final ObservableList<String[]> commandSummaryData = FXCollections.observableArrayList();

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        initializeContents();
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
     * Initializes the Table of Contents and the corresponding help content.
     */
    private void initializeContents() {
        tocListView.setItems(FXCollections.observableArrayList(
                "Introduction",
                "Quick Start",
                "Command Format Guidelines",
                "Adding a Contact",
                "Listing All Contacts",
                "Editing a Contact",
                "Finding Contacts",
                "Deleting a Contact",
                "Clearing All Entries",
                "Marking a Person as Paid/Unpaid",
                "Saving and Editing Data",
                "Exiting the Program",
                "Command Summary"
        ));

        contentMap.put("Introduction", """
                Introduction\n
                Financial Assurance Revolutionary Telemarketer (FART) is a desktop app for managing
                contacts just for you. Financial Advisors, optimized for use via a Command Line Interface (CLI)
                while still having the benefits of a Graphical User Interface (GUI). If you can type fast, FART
                can get your contact management tasks done faster than traditional GUI apps.\n
                Navigate to the appropriate issue via the panel on the left.
                """);

        contentMap.put("Quick Start", """
                Quick start\n
                1. Ensure you have Java `17` or above installed in your Computer.
                (Visit the Oracle website for Java 17 installation.)\n
                2. Download the latest `.jar` file from the GitHub releases page.\n
                3. Copy the `.jar` file to your desired folder and open a terminal.\n
                4. Run the application using `java -jar fart_in_a.jar`.\n
                5. Type commands in the command box and press Enter.
                """);

        contentMap.put("Command Format Guidelines", """
                Command Format Guidelines\n
                1. Words in UPPER_CASE are parameters to be supplied by the user. For example, in `add n/NAME`, 
                `NAME` is a parameter which can be used as `add n/John Doe`.\n
                2. Items in square brackets are optional. For example, `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` 
                or as `n/John Doe`.\n
                3. Items with …​ after them can be used multiple times, including zero times. For example, `[t/TAG]…​` can be used 
                zero times (i.e. `add n/John Doe`), or multiple times, such as `t/friend`, `t/friend t/family`, etc.\n
                4. Parameters can be in any order. For example, if the command specifies `n/NAME p/PHONE_NUMBER`, 
                it can also be written as `p/PHONE_NUMBER n/NAME`.\n
                5. Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`, and `clear`) 
                will be ignored. For example, if the command specifies `help 123`, it will be interpreted as `help`.
                """);

        contentMap.put("Adding a Contact", """
                Adding a Contact\n
                Adds a person to the FART book.\n
                Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS b/BIRTHDAY [t/TAG]…​`\n
                Tip: A person can have any number of tags, including 0.\n
                Examples:
                `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 b/11 09 2001`
                `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 b/11 09 2001 t/criminal`
                """);

        contentMap.put("Listing All Contacts", """
                Listing All Contacts\n
                Use the `list` command to display all contacts.
                """);

        contentMap.put("Editing a Contact", """
                Editing a Contact\n
                Edits an existing person in the FART book.\n
                Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [b/BIRTHDAY] [t/TAG]…​`\n
                INDEX refers to the index number shown in the displayed person list. The index must be a positive integer (1, 2, 3, …​).
                At least one of the optional fields must be provided.
                Existing values will be updated to the new input values.
                When editing tags, the existing tags of the person will be removed (i.e., adding tags is not cumulative).
                You can remove all the person’s tags by typing `t/` without specifying any tags after it.\n
                Examples:
                `edit 1 p/91234567 e/johndoe@example.com`: Edits the phone number and email address of the 1st person to be 
                `91234567` and `johndoe@example.com`, respectively.
                `edit 2 n/Betsy Crower t/`: Edits the name of the 2nd person to `Betsy Crower` and clears all existing tags.
                """);

        contentMap.put("Finding Contacts", """
                Finding Contacts\n
                Finds persons whose attributes contain any of the given keywords.\n
                Format: `find KEYWORD [MORE_KEYWORDS]`\n
                The search is case-insensitive. For example, `hans` will match `Hans`. Keywords are matched against Name, 
                Phone, Address, Email, Birthday, and Tag. The order of the keywords does not matter. For example, `Hans Bo` 
                will match `Bo Hans`. Persons matching at least one keyword will be returned (i.e., OR search). 
                For example, `Hans Bo` will return `Hans Gruber`, `Bo Yang`. Partial matches of a keyword will also be 
                returned. For example, `9123` will return the person with the phone number `91234567`.\n
                Examples:
                `find John` returns `john` and `John Doe`.
                `find alex david` returns `Alex Yeoh`, `David Li`.
                """);

        contentMap.put("Deleting a Contact", """
                Deleting a Contact\n
                Deletes the specified person from the FART book.\n
                Format: `delete INDEX`\n
                Deletes the person at the specified INDEX. The **INDEX** refers to the index number shown in the 
                displayed person list. The index must be a positive integer (1, 2, 3, …​).\n
                Examples:
                `list` followed by `delete 2` deletes the 2nd person in the FART book. `find Betsy` followed by 
                `delete 1` deletes the 1st person in the results of the find command.\n
                Note: As the delete command cannot be undone, the FART Book will first prompt you to confirm that the 
                contact you want to delete is correct. If you select Cancel, the deletion will be aborted.
                """);

        contentMap.put("Clearing All Entries", """
                Clearing All Entries\n
                Clears all entries from the FART book.\n
                Format: `clear`\n
                This command will remove all contacts from the FART book.
                """);

        contentMap.put("Marking a Person as Paid/Unpaid", """
                Marking a Person as Paid/Unpaid\n
                Marks the specified person from the FART book as paid/unpaid.\n
                Format: `paid INDEX` / `unpaid INDEX`\n
                Marks the person at the specified INDEX as paid/unpaid. The INDEX refers to the index number 
                shown in the displayed person list. The index must be a positive integer (1, 2, 3, …​).\n
                Examples:
                `list` followed by `paid 2` marks the 2nd person in the FART book as paid.
                `find Betsy` followed by `paid 1` marks the 1st person in the results of the find command as paid.
                `list` followed by `unpaid 2` marks the 2nd person in the FART book as unpaid.
                `find Betsy` followed by `unpaid 1` marks the 1st person in the results of the find command as unpaid.
                """);

        contentMap.put("Saving and Editing Data", """
                Saving and Editing Data\n
                FART data is automatically saved after any command. Data is stored in a JSON file at 
                `[JAR file location]/data/addressbook.json`.
                """);

        contentMap.put("Exiting the Program", """
                Exiting the Program\n
                Use the `exit` command to quit the application.
                """);

        contentMap.put("Command Summary", """
                Command Summary\n
                | Action | Format | Examples |
                |--------|--------|----------|
                | Add    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS b/BIRTHDAY [t/TAG]…​` | `add n/James Ho p/22224444 
                e/jamesho@example.com a/123, Clementi Rd, 1234665 b/11 09 2001 t/friend t/colleague` |
                | Clear  | `clear`     | - |
                | Delete | `delete INDEX` | `delete 3` |
                | Edit   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​` | `edit 2 n/James Lee 
                e/jameslee@example.com` |
                | Find   | `find KEYWORD [MORE_KEYWORDS]` | `find James Jake` |
                | List   | `list`      | - |
                | Help   | `help`      | - |
                | Paid   | `paid INDEX` | `paid 3` |
                | Unpaid | `unpaid INDEX` | `unpaid 3` |
                """);


        initializeCommandSummaryData();

        updateTextFlow(contentMap.get("Introduction"));

        tocListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if ("Command Summary".equals(newValue)) {
                    helpContentFlow.getChildren().clear(); //
                    commandSummaryTable.setVisible(true);
                } else {
                    commandSummaryTable.setVisible(false);
                    updateTextFlow(contentMap.get(newValue));
                }
            }
        });
    }

    /**
     * Initializes the command summary data for the TableView.
     */
    private void initializeCommandSummaryData() {
        actionColumn.setCellValueFactory
                (cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
        formatColumn.setCellValueFactory
                (cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
        exampleColumn.setCellValueFactory
                (cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));

        commandSummaryData.clear();

        commandSummaryData.addAll(
                new String[]{"Add", "add n/NAME p/PHONE_NUMBER\n " +
                        "e/EMAIL a/ADDRESS b/BIRTHDAY [t/TAG]…​", "add n/James Ho p/22224444 \n" +
                        "e/jamesho@example.com a/123, \nClementi Rd, 1234665 b/11 09 2001 \nt/friend t/colleague"},
                new String[]{"Clear", "clear", ""},
                new String[]{"Delete", "delete INDEX", "delete 3"},
                new String[]{"Edit", "edit INDEX [n/NAME] [p/PHONE_NUMBER]\n " +
                        "[e/EMAIL] [a/ADDRESS] [t/TAG]…​", "edit 2 n/James Lee\n e/jameslee@example.com"},
                new String[]{"Find", "find KEYWORD [MORE_KEYWORDS]", "find James Jake"},
                new String[]{"List", "list", ""},
                new String[]{"Help", "help", ""},
                new String[]{"Paid", "paid INDEX", "paid 3"},
                new String[]{"Unpaid", "unpaid INDEX", "unpaid 3"}
        );
        commandSummaryData.removeIf(row -> row == null || row.length == 0 ||
                (row[0].isEmpty() && row[1].isEmpty() && row[2].isEmpty()));

        commandSummaryTable.setItems(commandSummaryData);

        commandSummaryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Updates TextFlow with bold, large titles, hyperlinks, and code formatting for text in backticks.
     */
    private void updateTextFlow(String content) {
        helpContentFlow.getChildren().clear();

        String[] parts = content.split("\n", 2);
        String titleText = parts[0];
        String bodyText = parts.length > 1 ? parts[1] : "";

        Text title = new Text(titleText + "\n");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        helpContentFlow.getChildren().add(title);

        if (content.contains("Quick start")) {
            Text beforeLink1 = new Text("\n1. Ensure you have Java ");
            Hyperlink javaLink = new Hyperlink("https://www.oracle.com/java/technologies/downloads/#java17");
            javaLink.setOnAction(e -> openHyperlink("https://www.oracle.com/java/technologies/downloads/#java17"));

            Text afterLink1 = new Text("\n\n2. Download the latest ");
            Hyperlink jarLink = new Hyperlink("https://github.com/se-edu/addressbook-level3/releases");
            jarLink.setOnAction(e -> openHyperlink("https://github.com/se-edu/addressbook-level3/releases"));

            Text instructions = new Text("\n\n3. Copy the `.jar` file to your desired folder and open a terminal." +
                    "\n\n4. Run the application using `java -jar fart_in_a.jar`." +
                    "\n\n5. Type commands in the command box and press Enter.");

            helpContentFlow.getChildren().addAll(beforeLink1, javaLink, afterLink1, jarLink);

            processTextWithBackticks(instructions.getText());
        } else {
            processTextWithBackticks(bodyText);
        }
    }

    /**
     * Processes text and applies formatting to sections within backticks (`` ` ``), treating them as code.
     */
    private void processTextWithBackticks(String text) {
        String[] segments = text.split("`");
        boolean isCode = false;

        for (String segment : segments) {
            Text t = new Text(segment);
            if (isCode) {
                t.setFont(Font.font("Monospaced"));
                t.setStyle("-fx-font-size: 14px; -fx-fill: #3b82f6;");
            } else {
                t.setStyle("-fx-font-size: 14px;");
            }
            helpContentFlow.getChildren().add(t);
            isCode = !isCode;
        }
    }

    /**
     * Opens a hyperlink in the default browser.
     */
    private void openHyperlink(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.out.println("Platform does not support desktop browsing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
