package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private TextArea helpContent;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        setHelpContent();
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
     * Sets the content of the help window.
     */
    private void setHelpContent() {
        String userGuideContent = """
            Financial Assurance Revolutionary Telemarketer (FART) is a **desktop app for managing contacts just for you 
            Financial Advisors, optimized for use via a Command Line Interface** (CLI) 
            while still having the benefits of a Graphical User Interface (GUI). 
            If you can type fast, FART can get your contact management tasks done faster than traditional GUI apps.

            ## Quick start

            1. Ensure you have Java `17` or above installed in your Computer.
               1. Should you require help, [here](https://www.oracle.com/java/technologies/downloads/#java17) is 
               the download link to Java '17'.
               2. After accessing the website, 
               please choose the right download link for your operating system (Linux, macOS, or Windows).

            2. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

            3. Copy the file to the folder you want to use as the _home folder_ for your FART.
               A good home folder would be the Downloads folder as it makes the next step easier.

            4. Open a command terminal, `cd` into the folder you put the jar file in, 
            and use the `java -jar fart_in_a.jar` command to run the application.
               A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.
               ![Ui](images/Ui.png)

            5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** 
            and pressing Enter will open the help window.

            Some example commands you can try:

               * `list` : Lists all contacts.
               * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 b/11 09 2001` : 
               Adds a contact named `John Doe` to the FART book and automatically marks them as unpaid.
               * `delete 3` : Deletes the 3rd contact shown in the current list.
               * `paid 3` : Marks the 3rd contact shown in the current list as paid.
               * `clear` : Deletes all contacts.
               * `find john`  : Displays all contacts with keyword(s) matching "john"
               * `exit` : Exits the app.

            ## Features

            ### Viewing help : `help`

            Shows a message explaining how to access the help page.

            ### Adding a person: `add`

            Adds a person to the FART book.

            Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS b/BIRTHDAY [t/TAG]…​`

            ### Listing all persons : `list`

            Shows a list of all persons in the FART book.

            ### Editing a person : `edit`

            Edits an existing person in the FART book.

            ### Locating persons by name: `find`

            Finds persons whose attributes contain any of the given keywords.

            ### Deleting a person : `delete`

            Deletes the specified person from the FART book.

            ### Marking a person as paid: `paid`

            Marks the specified person from the FART book as paid.

            ### Marking a person as unpaid: `unpaid`

            Marks the specified person from the FART book as unpaid.

            ### Exiting the program : `exit`

            Exits the program.

            ### Saving the data

            FART data is saved in the hard disk automatically after any command that changes the data. 
            There is no need to save manually.

            ### Editing the data file

            FART data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`.

            ## Command summary

            Action | Format, Examples
            --------|------------------
            **Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS b/BIRTHDAY [t/TAG]…​` <br> 
            e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, 
            Clementi Rd, 1234665 b/11 09 2001 t/friend t/colleague`
            **Clear** | `clear`
            **Delete** | `delete INDEX`<br> e.g., `delete 3`
            **Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> 
            e.g.,`edit 2 n/James Lee e/jameslee@example.com`
            **Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
            **List** | `list`
            **Help** | `help`
            **Paid** | `paid 3`
            **Unpaid** | `unpaid 3`
            """;

        // Set the content to the TextArea in the help window
        helpContent.setText(userGuideContent);
        helpContent.setWrapText(true);
        helpContent.setEditable(false);
    }
}
