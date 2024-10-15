package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    public static final String SHOWING_TEMP_MESSAGE = "Adding a person: add\n" +
            "Adds a person to the address book.\n" +
            "\n" +
            "Format: add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…\u200B \n" +
            "Listing all persons : list\n" +
            "Shows a list of all persons in the address book.\n" +
            "\n" +
            "Format: list\n" +
            "\n" +
            "Editing a person : edit\n" +
            "Edits an existing person in the address book.\n" +
            "\n" +
            "Format: edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…\u200B \n " +
            "Locating persons by name: find\n" +
            "Finds persons whose names contain any of the given keywords.\n" +
            "\n" +
            "Format: find KEYWORD [MORE_KEYWORDS]\n " +
            "Deleting a person : delete\n" +
            "Deletes the specified person from the address book.\n" +
            "\n" +
            "Format: delete INDEX\n" +
            "Clearing all entries : clear\n" +
            "Clears all entries from the address book.\n" +
            "\n" +
            "Format: clear\n" +
            "Exiting the program : exit\n" +
            "Exits the program.\n" +
            "\n" +
            "Format: exit\n" +
            "";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_TEMP_MESSAGE, true, false);
    }
}
