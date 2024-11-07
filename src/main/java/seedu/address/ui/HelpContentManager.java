package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Handles the management of help content such as table of contents, command summaries, and individual help entries.
 */
public class HelpContentManager {

    private Map<String, String> contentMap = new HashMap<>();
    private final ObservableList<String[]> commandSummaryData = FXCollections.observableArrayList();

    /**
     * Constructs a HelpContentManager and initializes the content map with predefined help content.
     */
    public HelpContentManager() {
        initializeContents();
        assert !contentMap.isEmpty() : "Content map should be initialized with values";
    }

    /** Initializes the help content sections and populates the content map with predefined help content. */
    public void initializeContents() {
        contentMap.put("Introduction", """
                Introduction
                 Financial Assurance Revolutionary Telemarketer (F.A.R.T) is a
                 desktop app for managing contacts tailored for Financial Advisors,
                 optimized for use via a Command Line Interface (CLI) while still
                 having the benefits of a Graphical User Interface (GUI). If you can
                 type fast, F.A.R.T can get your contact management tasks done faster
                 than traditional GUI apps.\n
                 Navigate to the appropriate issue via the panel on the left.
                """);


        contentMap.put("Features", """
                Features
                 1. Words in UPPER_CASE are parameters to be supplied by the
                 user. For example, in `add n/NAME`, `NAME` is a parameter
                 which can be used as `add n/John Doe`.\n
                 2. Items in square brackets are optional. For example,
                 `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or
                 as `n/John Doe`.\n
                 3. Items with …​ after them can be used multiple times,
                 including zero times. For example, `[t/TAG]…​` can be used
                 zero times (i.e. `add n/John Doe`), or multiple times,
                 such as `t/friend`, `t/friend t/family`, etc.\n
                 4. Parameters can be in any order. For example, if the
                 command specifies `n/NAME p/PHONE_NUMBER`, it can also be
                 written as `p/PHONE_NUMBER n/NAME`.\n
                 5. Extraneous parameters for commands that do not take in
                 parameters (such as `help`, `list`, `exit`, and `clear`)
                 will be ignored. For example, if the command specifies
                 `help 123`, it will be interpreted as `help`.\n
                 6. If you are using a PDF version of this document,
                 be careful when copying and pasting commands that span multiple lines
                 as space characters surrounding line-breaks may be omitted
                 when copied over to the application.
                """);

        contentMap.put("Adding a client", """
                Adding a client: add
                 Met a potential client or someone new?
                 This command adds a client to the F.A.R.T book.\n
                 Format: `add n/NAME p/PHONE_NUMBER e/EMAIL [a/ADDRESS]
                 b/BIRTHDAY [t/TAG]…​`\n
                 Tip: A person can have any number of tags, including 0.\n
                 Examples:
                 `add n/John Doe p/98765432 e/johnd@example.com a/John
                 street, block 123, #01-01 b/11 09 2001`
                 `add n/Betsy Crowe t/friend e/betsycrowe@example.com
                 a/Newgate Prison p/1234567 b/11 09 2001 t/criminal`\n
                 Note:
                 1. As of version 1.5, only valid email addresses (in the form
                 name@domain.com) and Singapore phone numbers are accepted.
                 Birthdays should be added in DD MM YYYY format.
                 2. You may add duplicate contacts (i.e., contacts with the same name,
                 case-insensitive), but the F.A.R.T book will prompt you to confirm
                 that you are adding a duplicate contact.
                """);

        contentMap.put("Listing all clients", """
                Listing all clients: list
                 Need to have a quick view of everyone in your F.A.R.T book
                 or reset it after filtering?
                 This command displays a list of all clients in the F.A.R.T book.
                """);

        contentMap.put("Editing a client", """
                Editing a client: edit
                 Need to update an client’s details?
                 This command edits an existing client in the F.A.R.T book.\n
                 Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]
                 [b/BIRTHDAY] [t/TAG]…​`\n
                 Edits the client at the specified INDEX. The index refers to the
                 index number shown in the displayed client list. The index must be a
                 positive whole number (1, 2, 3, …​).
                 At least one of the optional fields must be provided.
                 Existing values will be updated to what you have currently typed in.
                 When editing tags, the existing tags of the client will be removed
                 (i.e., adding of tags is not cumulative).
                 You can remove all the client’s tags by
                 typing `t/` without specifying any tags after it.\n
                 Examples:
                 `edit 1 p/91234567 e/johndoe@example.com`: Edits the phone
                 number and email address of the 1st client to be `91234567`
                 and `johndoe@example.com`, respectively.
                 `edit 2 n/Betsy Crower t/`: Edits the name of the 2nd client
                 to `Betsy Crower` and clears all existing tags.
                """);

        contentMap.put("Locating clients by attribute", """
                Locating clients by attribute: find
                 Need to quickly find a client by their address or something other
                 than their name? Use this command to find clients whose attributes
                 contain any of the given keywords.\n
                 Format: `find [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [b/BIRTHDAY]
                 [t/TAG]…​`\n
                 The search is case-insensitive. For example, `hans` will match
                 `Hans`. At least one of the optional fields must be provided.
                 Keywords are matched against the attribute that you specified
                 (i.e., searching for names will match against all names).
                 Partial matches of a keyword will also be returned. For example,
                 `9123` will return clients with the phone number `91234567`.\n
                 Examples:
                 `find n/John`: returns clients with names `john` and `John Doe`.
                 `find a/serangoon`: returns `Bernice Yu`, `David Li`.
                """);


        contentMap.put("Deleting a client", """
                Deleting a client : delete
                 Need to remove someone from your list? This command deletes the
                 specified client from the F.A.R.T book.\n
                 Format: `delete INDEX`\n
                 Deletes the client at the specified `INDEX`. The index refers to the
                 index number shown in the displayed client list. The index must be a
                 positive whole number (1, 2, 3, …​).\n
                 Examples:
                 `list` followed by `delete 2` deletes the 2nd client in the F.A.R.T book.
                 `find Betsy` followed by `delete 1` deletes the 1st client in the results
                 of the `find` command.\n
                 Note:
                 As a `delete` command cannot be undone, the F.A.R.T Book will first
                 prompt you to confirm if the contact that you want to delete is
                 correct. If you select `No`, the operation will be aborted and the
                 client will not be deleted.
                """);

        contentMap.put("Clearing all entries", """
                Clearing all entries : clear
                 Want to get rid of all contacts or the sample data we’ve provided?
                 This command clears all entries from the F.A.R.T book.\n
                 Format: `clear`\n
                 Note:
                 A `clear` command cannot be undone. The F.A.R.T Book will first prompt
                 you to confirm that you wish to clear the book. If you select `No`, the
                 clear action will be aborted.
                 If you do change your mind after clearing the F.A.R.T book, you may
                 retrieve the sample data again by deleting the `addressbook.json` file
                 in the `data` folder, then launching F.A.R.T again.
                """);

        contentMap.put("Marking a client as paid", """
                Marking a client as paid: paid
                 Need to track which clients have paid their policy premiums for the
                 current period? This command marks the specified client from the
                 F.A.R.T book as paid.\n
                 Format: `paid INDEX f/FREQUENCY`\n
                 Marks the client at the specified `INDEX` as paid. The index refers to
                 the index number shown in the displayed client list. The index must
                 be a positive whole number (1, 2, 3, …​).
                 The frequency is the number of months between policy renewals, and it
                 can only be 1, 3, 6, or 12. The frequency will indicate which month
                 the client will be automatically updated to unpaid.\n
                 Examples:
                 `list` followed by `paid 2 f/6` marks the 2nd client in the F.A.R.T book
                 as paid and sets their policy renewal frequency to 6 months.
                 `find Betsy` followed by `paid 1 f/6` marks the 1st client in the results
                 of the `find` command as paid.
                """);

        contentMap.put("Marking a client as unpaid", """
                Marking a client as unpaid: unpaid
                 Need to manually mark a client that has yet to pay? This command
                 marks the specified client from the F.A.R.T book as unpaid.\n
                 Format: `unpaid INDEX`\n
                 Marks the client at the specified `INDEX` as unpaid. The index refers
                 to the index number shown in the displayed client list. The index
                 must be a positive whole number (1, 2, 3, …​).
                 Changes the policy renewal frequency to 0.\n
                 Examples:
                 `list` followed by `unpaid 2` marks the 2nd client in the F.A.R.T book as
                 unpaid.
                 `find Betsy` followed by `unpaid 1` marks the 1st client in the results of
                 the `find` command as unpaid.
                """);

        contentMap.put("Upload a client's profile picture", """
                Upload a client’s profile picture
                 Too many clients? Keep track of their faces by uploading a profile
                 picture of them.\n
                 Format: `upload INDEX`\n
                 This opens a file browser where you can choose PNG images for the
                 client at the specified `INDEX`. The index refers to the index number
                 shown in the displayed client list. The index must be a positive
                 whole number (1, 2, 3, …​).\n
                 Tip:
                 1. This feature is for Windows users only. Mac and Linux users may
                 encounter errors if they use this feature. Support for other OSes
                 will be released in future versions!
                 2. The profile picture may look odd if the image chosen is very small,
                 too wide, or too thin. For best results, use square images
                 approximately 300px x 300px. You can crop with your computer’s
                 image editor or use an online editor like Adobe.
                 3. Only PNG images are supported. Please ensure your chosen image
                 is in PNG format.
                """);

        contentMap.put("Exiting the Program", """
                Exiting the program : exit
                 Done with F.A.R.T.? This command closes the program. Your data is
                 automatically saved.\n
                 Format: `exit`
                """);

        contentMap.put("Viewing a client's details", """
                Viewing a Client’s Details
                 When the app starts, a placeholder on the right panel will prompt
                 you to select a contact on the list.
                 Double-click on a contact in the contact list to bring up a more
                 detailed view of the client, including information like address
                 and birthday.\n
                 Note:
                 The displayed contact will persist, and edits will not be reflected
                 until you select another contact or close the F.A.R.T book. If you
                 made any changes to the current contact on display, kindly
                 double-click to refresh the detailed view.
                """);

        contentMap.put("Visual Features", """
                Visual Features
                 F.A.R.T has some handy visual cues that highlight certain clients.\n
                 Net Worth Tagging
                 You can add tags to clients such as `highnetworth`, `midnetworth`, or `lownetworth`
                 which will be highlighted with different styles.
                 Each client can only have one net worth tag at a time.
                 For example, if you add a `highnetworth` tag to a client
                 who already has a `midnetworth` tag, the existing tag will be removed
                 and replaced with `highnetworth`.\n
                 Birthday Highlighting
                 When a client’s birthday is approaching or has recently passed
                 (within 7 days before or after the current date),
                 their name will appear in orange to remind you of the special occasion.
                 Additionally, hovering over their name will display a tooltip reminder.
                 Take this chance to wish them a happy birthday!
                """);

        contentMap.put("Saving and Editing Data", """
                Saving and Editing Data\n
                 F.A.R.T data is saved on your computer automatically after any command that changes the data.
                 There is no need to save manually. The location where the data is stored is shown on the
                 bottom status bar of the app.\n
                 F.A.R.T data is saved in a JSON file at `[JAR file location]/data/addressbook.json`.
                 Advanced users are welcome to update data directly by editing this data file.\n
                 Caution:
                 If changes to the data file make its format invalid, F.A.R.T will discard all data and start
                 with an empty data file at the next run. Therefore, it is recommended to back up the file
                 before editing it.
                 Additionally, certain edits may cause F.A.R.T to behave unexpectedly
                 (e.g., if a value entered is outside of the acceptable range).
                 Therefore, edit the data file only if you are confident you can update it correctly.
                """);

        contentMap.put("FAQ and Known Issues", """
                FAQ and Known Issues
                 FAQ
                 Q: How do I transfer my data to another computer?
                 A: Install this app on the other computer and overwrite the empty data file it creates
                 with the data file from your previous F.A.R.T home folder.\n
                 Known Issues
                 1. Screen positioning: When using multiple screens, if you move the application to a secondary
                 screen and later switch to only the primary screen, the GUI may open off-screen. To fix this,
                 delete the `preferences.json` file created by the application before running it again.
                 2. Help Window minimization: If you minimize the Help Window and then run the `help` command (or
                 use the `Help` menu, or the keyboard shortcut `F1`), the minimized Help Window will remain minimized,
                 and no new Help Window will appear. To fix this, manually restore the minimized Help Window.
                """);

        assert contentMap.get("Introduction") != null : "Introduction content should not be null";
    }

    /**
     * Retrieves the content for a specific section.
     * @param key The key representing the section title.
     * @return The content for the given section.
     */
    public String getContent(String key) {
        assert key != null && !key.isEmpty() : "Key for content retrieval should not be null or empty";
        return contentMap.get(key);
    }

    /**
     * Returns an observable list containing the help topics (Table of Contents).
     * @return An observable list with the section titles for the help window.
     */
    public ObservableList<String> getTableOfContents() {
        ObservableList<String> tocList = FXCollections.observableArrayList(
                "Introduction", "Features", "Adding a client",
                "Listing all clients", "Editing a client", "Locating clients by attribute", "Deleting a client",
                "Clearing all entries", "Marking a client as paid", "Marking a client as unpaid",
                "Upload a client's profile picture", "Exiting the Program", "Viewing a client's details",
                "Visual Features", "Saving and Editing Data", "FAQ and Known Issues", "Command Summary");

        assert !tocList.isEmpty() : "Table of Contents should not be empty";
        return tocList;
    }

    /**
     * Initializes and populates the command summary data in the TableView.
     * @param table The TableView for displaying the command summary data.
     * @param actionColumn The column representing the command action.
     * @param formatColumn The column representing the command format.
     * @param exampleColumn The column representing the command example.
     */
    public void initializeCommandSummaryData(TableView<String[]> table, TableColumn<String[],
            String> actionColumn, TableColumn<String[],
            String> formatColumn, TableColumn<String[], String> exampleColumn) {

        assert table != null : "TableView should not be null";
        assert actionColumn != null && formatColumn != null
                && exampleColumn != null : "Table columns should not be null";

        actionColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
        formatColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
        exampleColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));

        commandSummaryData.clear();

        commandSummaryData.addAll(
                new String[]{"Add", "add n/NAME p/PHONE_NUMBER e/EMAIL\n [a/ADDRESS]"
                        + "b/BIRTHDAY [t/TAG]…​", "add n/James Ho p/22224444\n"
                        + "e/jamesho@example.com\n a/123, Clementi Rd, 1234665 \nb/11 09 2001 "
                        + "t/friend t/colleague"},
                new String[]{"Clear", "clear", "-"},
                new String[]{"Delete", "delete INDEX", "delete 3"},
                new String[]{"Edit", "edit INDEX [n/NAME] [p/PHONE_NUMBER]\n [e/EMAIL]"
                        + "[a/ADDRESS] [b/BIRTHDAY] [t/TAG]…​", "edit 2 n/James Lee\n"
                        + "e/jameslee@example.com"},
                new String[]{"Find", "find [n/NAME] [p/PHONE] [e/EMAIL]\n"
                        + "[a/ADDRESS] [b/BIRTHDAY] [t/TAG]…​", "find e/jameslee@example.com"},
                new String[]{"List", "list", "-"},
                new String[]{"Help", "help", "-"},
                new String[]{"Paid", "paid INDEX f/FREQUENCY", "paid 3 f/3"},
                new String[]{"Unpaid", "unpaid INDEX", "unpaid 3"}
        );


        assert !commandSummaryData.isEmpty() : "Command summary data should not be empty";
        table.setItems(commandSummaryData);
        table.setColumnResizePolicy(param -> true);
    }
}
