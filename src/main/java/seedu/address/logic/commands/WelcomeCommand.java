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
            + "add:\t\tAdds a restaurant to the address book."
            + "\n\t"
            + "list:\t\tShows a list of all restaurants in the address book."
            + "\n\t"
            + "edit:\t\tEdits an existing restaurant in the address book."
            + "\n\t"
            + "find:\t\tFinds restaurants whose names contain any of the given keywords."
            + "\n\t"
            + "tags:\t\tFinds restaurants whose tags contain any of the given keywords."
            + "\n\t"
            + "delete:\tDeletes the specified restaurant from the address book."
            + "\n\t"
            + "rate:\t\tRates the specified restaurant from the address book."
            + "\n\t"
            + "fav:\t\tSet the specified restaurant from the address book as favourite."
            + "\n\t"
            + "price:\tFinds restaurants who belong in any of the given price labels"
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
