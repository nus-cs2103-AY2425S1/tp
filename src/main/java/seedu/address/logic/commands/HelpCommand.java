package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    // make use of magic numbers needed
    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = "See `" + COMMAND_WORD + "`: Shows program usage instructions.\n"
            + "Format: " + COMMAND_WORD;
    public static final String SHOWING_HELP_MESSAGE = "List of other commands: "
            + Messages.MESSAGE_COMMAND_LIST.replace("help, ", "") + "\n"
            + "For more details, type `help [COMMAND_WORD]` with the words found in the command list above."
            + "\nAlternatively, visit https://ay2425s1-cs2103-f09-1.github.io/tp/UserGuide.html for " +
            "complete user guide";
           // + "\nAlternatively, visit our complete user guide on the popup screen";
    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103-f09-1.github.io/tp/UserGuide.html";

    // studentStatus and Role can magic number
    public static final String HELP_ADD = AddCommand.MESSAGE_FUNCTION + "\n"
            + "Command format:\n" + AddCommand.MESSAGE_COMMAND_FORMAT + "\n"
            + AddCommand.MESSAGE_COMMAND_EXAMPLE + "\n"
            + "For Student Status, the accepted ones are: Undergraduate 1 to 6, Masters or PhD\n"
            + "For Role, the accepted ones are: President, Vice President, Admin, Marketing, Events (Internal),"
            + " Events (External), or External Relations\n"
            + "Multiple roles are allowed and only Nicknames are optional";
    public static final String HELP_DELETE = DeleteCommand.MESSAGE_FUNCTION + "\n"
            + "Command format:\n" + DeleteCommand.MESSAGE_COMMAND_FORMAT + "\n"
            + DeleteCommand.MESSAGE_COMMAND_EXAMPLE
            + "\nUsers can delete by full name via `delete n/ <full name>` OR `delete <full name>`";
    public static final String HELP_EDIT = EditCommand.MESSAGE_FUNCTION + "\n"
            + "Command format:\n" + EditCommand.MESSAGE_COMMAND_FORMAT + "\n"
            + EditCommand.MESSAGE_COMMAND_EXAMPLE;
    public static final String HELP_FIND = FindCommand.MESSAGE_FUNCTION + "\n"
            + "Command format:\n" + FindCommand.MESSAGE_COMMAND_FORMAT + "\n"
            + FindCommand.MESSAGE_COMMAND_EXAMPLE;
    public static final String HELP_LIST = ListCommand.MESSAGE_FUNCTION + "\n"
            + "Command format: " + ListCommand.MESSAGE_COMMAND_FORMAT + "\n"
            + "No other fields is required for the command but alternatives are allowed as follows:\n"
            + "Alternative One: `list all`\n"
            + "Alternative Two: `list contacts`\n"
            + "Alternative Three: `list all contacts`";
    public static final String HELP_CLEAR = ClearCommand.MESSAGE_FUNCTION + "\n"
            + "Command format: " + ClearCommand.MESSAGE_COMMAND_FORMAT;
    public static final String HELP_EXIT = ExitCommand.MESSAGE_FUNCTION + "\n"
            + "Command format: " + ExitCommand.MESSAGE_COMMAND_FORMAT;

    private final String message;
    private final boolean isHelp;

    /**
     * Constructs a {@code HelpCommand} with a custom help message.
     * This constructor is used when a specific help message is needed.
     *
     * @param message the custom help message to be shown when the command is executed
     */
    public HelpCommand(String message) {
        this.message = message;
        this.isHelp = false;
    }

    /**
     * Constructs a {@code HelpCommand} with the default help message.
     * This constructor is used when general help information is requested.
     */
    public HelpCommand() {
        this.message = SHOWING_HELP_MESSAGE;
        this.isHelp = true;
    }

    @Override
    public CommandResult execute(Model model) {
        if (isHelp) {
            return new CommandResult(this.message, true, false);
        } else {
            return new CommandResult(this.message, false, false);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof HelpCommand otherHelpCommand)) {
            return false;
        }

        if (isHelp != otherHelpCommand.isHelp) {
            return false;
        }

        if (isHelp) {
            return true;
        } else {
            return this.message.equals(otherHelpCommand.message);
        }
    }
}
