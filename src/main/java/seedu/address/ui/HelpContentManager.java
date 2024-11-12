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
                 desktop app for managing clients tailored for Financial Advisors,
                 optimized for use via a Command Line Interface (CLI) while still
                 having the benefits of a Graphical User Interface (GUI). If you can
                 type fast, F.A.R.T can get your client management tasks done faster
                 than traditional GUI apps.\n
                 Navigate to the appropriate issue via the panel on the left.
                """);


        contentMap.put("Features", """
                Features
                 1. Words in UPPER_CASE are parameters to be supplied by the user. For example,
                 in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.\n
                 2. Items in square brackets are optional. For example, `n/NAME [t/TAG]` can
                 be used as `n/John Doe t/friend` or as `n/John Doe`.\n
                 3. Items with …​ after them can be used multiple times, including zero times.
                 For example, `[t/TAG]…​` can be used zero times (i.e. `add n/John Doe`),
                 or multiple times, such as `t/friend`, `t/friend t/family`, etc.\n
                 4. Parameters can be in any order. For example, if the command specifies
                 `n/NAME p/PHONE_NUMBER`, it can also be written as `p/PHONE_NUMBER n/NAME`.\n
                 5. Extraneous parameters for commands that do not take in parameters
                 (such as `help`, `list`, `exit`, and `clear`) will be ignored. For example,
                 if the command specifies `help 123`, it will be interpreted as `help`.\n
                 6. If you are using a PDF version of this document, be careful when copying
                 and pasting commands that span multiple lines as space characters surrounding
                 line-breaks may be omitted when copied over to the application.
                """);

        contentMap.put("Formatting: Name, Phone Number", """
                Formatting: Name, Phone Number
                 Do note these rules when attempting to interact with the various commands below.
                 Inputs that do not follow the rules will not be accepted by the F.A.R.T. address
                 book and the inputted data will not be added to the client list. Note that for all
                 commands, spaces at the start of any input will be cut off e.g. inputting
                 `n/        John Doe`will result in F.A.R.T. reading it as `n/John Doe`\n
                 Name
                 Valid Inputs: Alphanumeric characters, with or without spaces, e.g., `Martin`,
                 `Martin Luther King`, `John`, `J0hn`.
                 Common Invalid Inputs:
                 * The slash character `/`, e.g. `S/O`
                 * The period character `.`, e.g. `Jr.`\n
                 Phone Number
                 Valid Inputs: 8-digit numbers starting with 3, 6, 8, or 9 (Singaporean numbers).
                 The input must be exactly 8 characters long e.g., `91234567`, `81234567`.
                 Common Invalid Inputs:
                 * A non-Singaporean phone number e.g., `51234567`
                 * A phone number exceeding 8 digits in length e.g., `6581234567`
                 * Adding the country code at the beginning e.g. `+6581234567`
                 * Adding a space in between the numbers e.g. `8123 4567`
                """);

        contentMap.put("Formatting: Email, Birthday", """
                Formatting: Email, Birthday
                 Email
                 For all parts of the input that accept special characters, they cannot be
                 placed at the start or at the end. Special characters can only appear at
                 most once consecutively.\n
                 Valid Inputs: `local-part@domain`, e.g. `john.doe@example.com`,
                 `j_ohn@example.edu.org`, `john123@e-mail.com`, where:
                 * `local-part`: Alphanumeric characters, in addition to these special
                 characters: `+`, `_`, `.`, `-`.
                 * `@`: This character must be present.
                 * `domain`: Alphanumeric, in addition to these special characters: `.`, `-`.
                 The last two characters must be alphanumeric.
                 Common Invalid Inputs:
                 * Special characters at the start or at the end, e.g. `.john@example.com`,
                 `john@example.com.`
                 * More than one consecutive special character, e.g. `john..doe@example.com`
                 * Disallowed special characters, e.g. `john!@example.com`
                 * Missing `@` character, e.g. `johnexample.com`
                 * Last two characters not being alphanumeric, e.g. `john@example.c`\n
                 Birthday
                 Valid Inputs: Numerical characters in the format `DD MM YYYY`. Valid
                 dates should be used e.g. `01 01 2000`, `02 03 2004`.
                 Common Invalid Inputs:
                 * Incorrect format, e.g. `01/01/2000`, `01 31 2000`, `01 01 04`
                 * Invalid dates, e.g. `31 02 2000`, `32 01 2000`
                """);

        contentMap.put("Formatting: Address, Tag(s)", """
                Formatting: Address, Tag(s)
                 Address
                 Valid Inputs: This field accepts a string of any characters as its input,e.g.
                 `John Street, Block 123, #01-01`,
                 `Pro+fessional Building, Level 3, Unit 30`,
                 `東京都豊島区東池袋1-22-10 ヒューマックスパビリオン`.\n
                 Tag(s)
                 Aside from normal tags, F.A.R.T. also provides specialised tags. For more
                 information, refer to the Visual Features section.\n
                 Valid Inputs: Alphanumeric characters. To include multiple tags, repeat the `t/` label,
                 e.g. `t/friend`, `t/colleague t/neighbour`.
                 Invalid Inputs:
                 * Dropping the `t/` label, e.g. `t/colleague neighbour`
                 * Non-alphanumeric characters, e.g. `t/#1friend`
                """);

        contentMap.put("Viewing a client's details", """
                Viewing a Client’s Details
                 You may use your arrow keys to traverse the client list, then double-click on
                 a client in the client list to bring up a more detailed view of the client,
                 including information like address and birthday.\n
                 The detailed view can be broken down into three main sections. From the above:
                 1. The client's profile picture. A default profile picture is given to
                 all clients. This can be updated via the `upload` command.\n
                 2. The client's details. This can be updated by various commands, including
                 `edit`, `paid,` `unpaid`.\n
                 3. Three template messages. These are predetermined messages that have been
                 categorised by the different life phases that a client could be in. Clicking
                 on one of the buttons will copy the relevant message to your clipboard,allowing
                 you to quickly send a message to your client via your preferred messaging app.
                 The message will automatically use the name provided in the F.A.R.T. book.
                 A successful copy will result in `Template Message copied to clipboard!`
                 being shown in the `Response Window`.\n
                 Note:
                 The displayed client will persist, and edits will not be reflected until you select
                 another client or close the F.A.R.T book. If you made any changes to the current
                 client on display, kindly double-click on the same client to refresh the
                 detailed view.
                """);

        contentMap.put("Adding a client", """
                Adding a client: add
                 Met a potential client or someone new? This command adds a
                 client to the F.A.R.T book.\n
                 Format: `add n/NAME p/PHONE_NUMBER e/EMAIL
                  b/BIRTHDAY [a/ADDRESS][t/TAG]…​`\n
                 Examples:
                 `add n/John Doe p/98765432 e/johnd@example.com a/John
                 street, block 123, #01-01 b/11 09 2001`\n
                 `add n/Betsy Crowe t/friend e/betsycrowe@example.com
                 a/Newgate Prison p/8234567 b/11 09 2001 t/criminal`\n
                 `add n/Charlie Chen Cao Cong p/88888888 b/01 02 2003
                 e/charliechen88@huat.com t/mahjong`\n
                 Note:
                 1. As mentioned in the Formatting, only Singapore phone numbers and
                 valid email addresses (in the form `local-part@domain`) are accepted.\n
                 2. Birthdays should be added in DD MM YYYY format.\n
                 3. You may add duplicate clients (i.e., clients with the same name,
                 case-insensitive), but the F.A.R.T book will prompt you to confirm that you
                 are adding a duplicate client. If you select `No` in the prompt window, the
                 operation will be aborted and the client will not be added. Else, select
                 `Yes` to add the new client.
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
                 Format: `edit INDEX [n/NAME] [p/PHONE_NUMBER]
                 [e/EMAIL] [b/BIRTHDAY] [a/ADDRESS] [t/TAG]…​`\n
                 Edits the client at the specified INDEX. The index refers to the index number shown
                 in the displayed client list. The index must be a positive whole number (1, 2, 3, …​).
                 At least one of the optional fields must be provided. Existing values will be updated
                 to what you have currently typed in. When editing tags, the existing tags of the
                 client will be removed (i.e., adding of tags is not cumulative).\n
                 You can remove all the client’s tags by typing `t/` without specifying any tags
                 after it. Similarly, a client's address can be removed by typing `a/` without
                 specifying anything after it.
                 If you edit a client's name to be the same of that of another client in the F.A.R.T.
                 book, a prompt will appear asking you to confirm the edit action. If you select `No`
                 in the prompt window, the operation will be aborted and the name will not be
                 changed. Else, select `Yes` to proceed with the edit.\n
                 Examples:
                 `edit 1 p/91234567 e/johndoe@example.com`: Edits the phone number and
                 email address of the 1st client to be `91234567` and `johndoe@example.com`,
                 respectively.\n
                 `edit 2 n/Betsy Crower t/`: Edits the name of the 2nd client to
                 `Betsy Crower` and clears all existing tags.
                """);

        contentMap.put("Locating clients by attribute", """
                Locating clients by attribute: find
                 Need to quickly find a client by their address or something other
                 than their name? Use this command to find clients whose attributes
                 contain any of the given keywords.\n
                 Format: `find [n/NAME] [p/PHONE_NUMBER]
                 [e/EMAIL] [b/BIRTHDAY] [a/ADDRESS] [t/TAG]…​`\n
                 The search is case-insensitive. For example, `hans` will match `Hans`.
                 At least one of the optional fields must be provided. Keywords are matched
                 against the attribute that you specified (i.e., searching for names will
                 match against all names). Partial matches of a keyword will also be returned.
                 For example, `9123` will return clients with the phone number `91234567`.\n
                 Note:
                 When searching with more than one attribute, it will find clients who
                 satisfy ALL keywords. e.g. `find n/Alice a/Clementi` will return clients
                 named Alice AND has an address in Clementi.
                 Examples:
                 `find n/John`: returns clients with names `john` and `John Doe`.\n
                 `find a/serangoon`: returns `Bernice Yu`, `David Li`.
                 `find n/bernice a/serangoon`: returns `Bernice Yu`
                """);

        contentMap.put("Deleting a client", """
                Deleting a client : delete
                 Need to remove someone from your list? This command deletes the
                 specified client from the F.A.R.T book.\n
                 Format: `delete INDEX`\n
                 Deletes the client at the specified `INDEX`. The index refers to the index number
                 shown in the displayed client list. The index must be a positive whole
                 number (1, 2, 3, …​).\n
                 Examples:
                 `list` followed by `delete 2` deletes the 2nd client in the F.A.R.T book.\n
                 `find n/Betsy` followed by `delete 1` deletes the 1st client in the results
                 of the `find` command.\n
                 Note:
                 1. A `delete` command cannot be undone, and deleted clients cannot be retrieved.
                 2. When a `delete` command is inputted, the F.A.R.T Book will first prompt you to
                 confirm if the client that you want to delete is correct.If you select `No`, the
                 operation will be aborted and the client will not be deleted. Else, select `Yes`
                 to delete the client.
                """);

        contentMap.put("Clearing all entries", """
                Clearing all entries : clear
                 Want to get rid of all clients or the sample data we’ve provided?
                 This command clears all entries from the F.A.R.T book.\n
                 Format: `clear`\n
                 Note:
                 1. A `clear` command cannot be undone, and cleared clients cannot be retrieved.\n
                 2. When a `clear` command is inputted, the F.A.R.T Book will first prompt you
                 to confirm that you wish to clear the book. If you select `No`, the operation
                 will be aborted and the book will not be cleared. Else, select `Yes` to clear the book.\n
                 3. If you do change your mind after clearing the F.A.R.T book, you can retrieve only
                 the sample data by deleting the `addressbook.json` file in the `data` folder,
                 then launching F.A.R.T again.
                """);

        contentMap.put("Marking a client as paid", """
                Marking a client as paid: paid
                 Need to track client policy payments for the current period? This command
                 marks the specified client from the F.A.R.T book as paid.\n
                 Format: `paid INDEX f/FREQUENCY`\n
                 Marks the client at the specified `INDEX` as paid. The index refers to
                 the index number shown in the displayed client list. The index must
                 be a positive whole number (1, 2, 3, …​).
                 The frequency is the number of months between policy renewals, and it
                 can only be 1, 3, 6, or 12. The frequency will indicate which month
                 the client will be automatically updated to unpaid.\n
                 Examples:
                 `list` followed by `paid 2 f/6` marks the 2nd client in the F.A.R.T book
                 as paid and sets their policy renewal frequency to 6 months.\n
                 `find n/Betsy` followed by `paid 1 f/3` marks the 1st client in the results
                 of the `find` command as paid and sets their policy renewal frequency to 3 months.\n
                 Note:
                 1. All policies are assumed to begin and end on the first of the month.\n
                 2. As of now, each frequency only supports tracking for specific months.
                 For example, a 3 Month Frequency marks the client as unpaid on Jan, Apr,
                 Jul and Oct, while a 6 Month Frequency marks as unpaid on Jan and Jul.
                """);

        contentMap.put("Marking a client as unpaid", """
                Marking a client as unpaid: unpaid
                 Need to manually mark a client that has yet to pay? This command
                 marks the specified client from the F.A.R.T book as unpaid.\n
                 Format: `unpaid INDEX`\n
                 Marks the client at the specified `INDEX` as unpaid. The index refers to
                 the index number shown in the displayed client list. The index must
                 be a positive whole number (1, 2, 3, …​).
                 Changes the policy renewal frequency to 0.\n
                 Examples:
                 `list` followed by `unpaid 2` marks the 2nd client
                 in the F.A.R.T book as unpaid.\n
                 `find n/Betsy` followed by `unpaid 1` marks the 1st client
                 in the results of the `find` command as unpaid.\n
                 Note:
                 1. As clients will be automatically marked as unpaid based on their policy
                 renewal frequency, this command should only be used in special cases. A good
                 example would be if a client wishes to change their renewal frequency, thus
                 requiring you to update the new frequency into the F.A.R.T. book.
                """);

        contentMap.put("Uploading a client's profile picture", """
                Uploading a client’s profile picture: upload
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
                 will be released in future versions!\n
                 2. The profile picture may look odd if the image chosen is very small, too wide,
                 or too thin. For best results, use square images approximately 300px x 300px.
                 You can crop with your computer’s image editor or use an online editor like Adobe.\n
                 3. Only PNG images are supported. Please ensure your chosen image
                 is in PNG format.
                """);

        contentMap.put("Exiting the program", """
                Exiting the program : exit
                 Done with F.A.R.T.? This command closes the program. Your data is
                 automatically saved.\n
                 Format: `exit`
                """);

        contentMap.put("Visual Features", """
                Visual Features
                 F.A.R.T has some handy visual cues that highlight certain clients.\n
                 Net Worth Tagging
                 You can add tags to clients such as `highnetworth`, `midnetworth`, or
                 `lownetworth` which will be highlighted with different styles. Each client can only
                 have one net worth tag at a time. For example, if you add a `highnetworth` tag
                 to a client who already has a `midnetworth` tag, the existing tag will be removed
                 and replaced with `highnetworth`.\n
                 Birthday Highlighting
                 When a client’s birthday is approaching or has recently passed (within 7 days before
                 or after the current date), their name will appear in orange to remind you of the
                 special occasion. Additionally, hovering over their name will display a tooltip
                 reminder. Take this chance to wish them a happy birthday!\n
                 If you would like to disable these visual features, click on the
                 `File` tab at the top and uncheck `Enable Visuals`.
                """);

        contentMap.put("Saving and Editing Data", """
                Saving and Editing Data
                 F.A.R.T data is saved on your computer automatically after any command that
                 changes the data. There is no need to save manually. The location where the data
                 is stored is shown on the bottom status bar of the app.\n
                 F.A.R.T data is saved in a JSON file at
                 `[JAR file location]/data/addressbook.json`.
                 Advanced users are welcome to update data directly by editing this data file.\n
                 Caution:
                 If changes to the data file make its format invalid, F.A.R.T will discard all data
                 and start with an empty data file at the next run. Therefore, it is recommended to
                 back up the file before editing it. Additionally, certain edits may cause F.A.R.T to
                 behave unexpectedly (e.g., if a value entered is outside the acceptable range).
                 Therefore, edit the data file only if you are confident you can update it correctly.
                """);

        contentMap.put("FAQ", """
                FAQ
                 Q: How do I transfer my data to another computer?\n
                 A: Install this app on the other computer and overwrite the empty data
                 file it creates with the data file from your previous F.A.R.T home folder.\n
                """);

        contentMap.put("Known Issues", """
                Known Issues
                 1. Screen positioning: When using multiple screens, if you move the application
                 to a secondary screen and later switch to only the primary screen, the GUI may
                 open off-screen. To fix this, delete the `preferences.json` file created by the
                 application before running it again.\n
                 2. Help Window minimization: If you minimize the Help Window and then run the
                 `help` command (or use the `Help` menu, or the keyboard shortcut `F1`), the
                 minimized Help Window will remain minimized, and no new Help Window will
                 appear. To fix this, manually restore the minimized Help Window.\n
                 3. If you are unable to see the command input or result panels, resize the window to
                 a larger size by hovering over the window outline on your screen. A two-sided arrow
                 should appear, which you can click and drag to resize to an appropriate size. This
                 issue may be especially prevalent on smaller monitor sizes.\n
                 4. As mentioned in the Viewing a client's details section, a client's detailed view
                 does not automatically update to reflect changes. Once changes are made,
                 double-click on the same client to refresh said changes. Else, the changes will be
                 updated upon restarting the F.A.R.T. book.\n
                 5. If you are using arrow keys to navigate the client list, then enter a command in
                 the command box, you need to move your cursor back to the client list to select it again.
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
                "Introduction", "Features", "Formatting: Name, Phone Number",
                "Formatting: Email, Birthday", "Formatting: Address, Tag(s)",
                "Viewing a client's details", "Adding a client", "Listing all clients",
                "Editing a client", "Locating clients by attribute", "Deleting a client",
                "Clearing all entries", "Marking a client as paid", "Marking a client as unpaid",
                "Uploading a client's profile picture", "Exiting the program", "Visual Features",
                "Saving and Editing Data", "FAQ", "Known Issues", "Command Summary");

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
                new String[]{"Add", "add n/NAME p/PHONE_NUMBER e/EMAIL\n b/BIRTHDAY"
                        + "[a/ADDRESS] [t/TAG]…​", "add n/James Ho p/22224444\n"
                        + "e/jamesho@example.com\n a/123, Clementi Rd, 1234665 \nb/11 09 2001 "
                        + "t/friend t/colleague"},
                new String[]{"Clear", "clear", "-"},
                new String[]{"Delete", "delete INDEX", "delete 3"},
                new String[]{"Edit", "edit INDEX [n/NAME] [p/PHONE_NUMBER]\n [e/EMAIL]"
                        + "[b/BIRTHDAY] [a/ADDRESS] [t/TAG]…​", "edit 2 n/James Lee\n"
                        + "e/jameslee@example.com"},
                new String[]{"Find", "find [n/NAME] [p/PHONE_NUMBER] [e/EMAIL]\n"
                        + "[b/BIRTHDAY] [a/ADDRESS] [t/TAG]…​", "find e/jameslee@example.com"},
                new String[]{"List", "list", "-"},
                new String[]{"Help", "help", "-"},
                new String[]{"Paid", "paid INDEX f/FREQUENCY", "paid 3 f/3"},
                new String[]{"Unpaid", "unpaid INDEX", "unpaid 3"},
                new String[]{"Upload", "upload INDEX", "upload 3"}
        );


        assert !commandSummaryData.isEmpty() : "Command summary data should not be empty";
        table.setItems(commandSummaryData);
        table.setColumnResizePolicy(param -> true);
    }
}
