package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class WelcomeCommand extends Command {

    public static final String COMMAND_WORD = "welcome";
    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103-f12-3.github.io/tp/UserGuide.html";
    public static final String WELCOME_MESSAGE = "Welcome to Grub!"
            + "Here are some of the commands to help you get started:"
            + "\n\t"
            + "add:\tAdds a restaurant to the address book."
            + "\n\t"
            + "list:\tShows a list of all restaurants in the address book."
            + "\n\t"
            + "edit:\tEdits an existing restaurant in the address book."
            + "\n\t"
            + "find:\tFinds restaurants whose names contain any of the given keywords."
            + "\n\t"
            + "tags:\tFinds restaurants whose tags contain any of the given keywords."
            + "\n\t"
            + "delete:\tDeletes the specified restaurant from the address book."
            + "\n\t"
            + "rate:\tRates the specified restaurant from the address book."
            + "\n\t"
            + "fav:\tSet the specified restaurant from the address book as favourite."
            + "\n\t"
            + "\nFor the full list, refer to the user guide: " + USERGUIDE_URL;


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows welcome message; runs once on app startup.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(WELCOME_MESSAGE, false, false);
    }
}
