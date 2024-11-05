package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.model.Model;

import static seedu.address.logic.Messages.COMMAND_FORMAT_PREAMBLE;
import static seedu.address.logic.Messages.LINE_BREAK;
import static seedu.address.logic.Messages.WHITESPACE;
import static seedu.address.logic.Messages.styleCommand;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {
    private static final String USERGUIDE_URL = "https://ay2425s1-cs2103-f09-1.github.io/tp/UserGuide.html";
    // make use of magic numbers needed
    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_FORMAT_DETAIL = "help [COMMAND_WORD]";
    public static final String MESSAGE_REFER_USERGUIDE = "%s visit " + USERGUIDE_URL + WHITESPACE + "for " +
        "complete user" + " guide";


    public static final String MESSAGE_USAGE = "See " + styleCommand(COMMAND_WORD) + ": Shows program " +
            "usage instructions." + LINE_BREAK + COMMAND_FORMAT_PREAMBLE + WHITESPACE + COMMAND_WORD;
    public static final String SHOWING_HELP_MESSAGE = "List of other commands: "
            + Messages.MESSAGE_COMMAND_LIST.replace("help, ", "") + LINE_BREAK
            + "For more details, type " + styleCommand(COMMAND_FORMAT_DETAIL) + WHITESPACE + "with the " +
            "words found in the command list above."
            + LINE_BREAK + String.format(MESSAGE_REFER_USERGUIDE, "Alternatively,");
           // + "\nAlternatively, visit our complete user guide on the popup screen";

    // studentStatus and Role can magic number
    public static final String HELP_ADD = AddCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE + WHITESPACE + AddCommand.MESSAGE_COMMAND_FORMAT + LINE_BREAK
            + AddCommand.MESSAGE_COMMAND_EXAMPLE + "\n"
            + "For Student Status, the accepted ones are: Undergraduate 1 to 6, Masters or PhD\n"
            + "For Role, the accepted ones are: President, Vice President, Admin, Marketing, Events (Internal),"
            + " Events (External), or External Relations\n"
            + "Multiple roles are allowed and only Nicknames are optional" + LINE_BREAK
            + String.format(MESSAGE_REFER_USERGUIDE, "For more details,");
    public static final String HELP_DELETE = DeleteCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE.replace(":", " -") + WHITESPACE + DeleteCommand.MESSAGE_COMMAND_FORMAT + LINE_BREAK
            + DeleteCommand.MESSAGE_COMMAND_EXAMPLE
            + "\nUsers can delete by full name of contact via `delete n/<FULL_NAME_TO_DELETE>` OR `delete " +
            "<FULL_NAME_TO_DELETE>`" + LINE_BREAK
            + String.format(MESSAGE_REFER_USERGUIDE, "For more details,");
    public static final String HELP_EDIT = EditCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE + WHITESPACE + EditCommand.MESSAGE_COMMAND_FORMAT + LINE_BREAK
            + EditCommand.MESSAGE_COMMAND_EXAMPLE + LINE_BREAK
            + String.format(MESSAGE_REFER_USERGUIDE, "For more details,");
    public static final String HELP_FIND = FindCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE + WHITESPACE + FindCommand.MESSAGE_COMMAND_FORMAT + LINE_BREAK
            + FindCommand.MESSAGE_COMMAND_EXAMPLE + LINE_BREAK
            + String.format(MESSAGE_REFER_USERGUIDE, "For more details,");;
    public static final String HELP_LIST = ListCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE + WHITESPACE + ListCommand.MESSAGE_COMMAND_FORMAT + LINE_BREAK
            + "No other fields is required for the command but alternatives are allowed as follows:\n"
            + "Alternative One: `list all`\n"
            + "Alternative Two: `list contacts`\n"
            + "Alternative Three: `list all contacts`" + LINE_BREAK
            + String.format(MESSAGE_REFER_USERGUIDE, "For more details,");
    public static final String HELP_CLEAR = ClearCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE + WHITESPACE + ClearCommand.MESSAGE_COMMAND_FORMAT;
    public static final String HELP_EXIT = ExitCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE + WHITESPACE + ExitCommand.MESSAGE_COMMAND_FORMAT;

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
