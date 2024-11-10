package seedu.address.logic.commands;

import static seedu.address.logic.Messages.COMMAND_FORMAT_PREAMBLE;
import static seedu.address.logic.Messages.LINE_BREAK;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.WHITESPACE;
import static seedu.address.logic.Messages.styleCommand;

import java.util.Arrays;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.contact.Role;
import seedu.address.model.contact.StudentStatus;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {
    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103-f09-1.github.io/tp/UserGuide.html";
    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_FORMAT_DETAIL = "help [COMMAND_WORD]";

    public static final String DETAIL_PREAMBLE_START = "For more details,";
    public static final String MESSAGE_REFER_USERGUIDE =
            "%1$s visit our complete user guide on the popup window %2$s";
    public static final String MESSAGE_REFER_USERGUIDE_GENERAL_HELP =
            String.format(MESSAGE_REFER_USERGUIDE, "Alternatively,", "");
    public static final String MESSAGE_REFER_USERGUIDE_DETAIL_HELP =
            String.format(MESSAGE_REFER_USERGUIDE, DETAIL_PREAMBLE_START, "which will be shown "
                    + "after typing " + styleCommand(COMMAND_WORD));
    public static final String MESSAGE_USAGE = "See " + styleCommand(COMMAND_WORD) + ": Shows program "
            + "usage instructions." + LINE_BREAK + COMMAND_FORMAT_PREAMBLE + WHITESPACE + styleCommand(COMMAND_WORD);
    public static final String SHOWING_HELP_MESSAGE = "List of other commands: "
            + Messages.MESSAGE_COMMAND_LIST.replace("help, ", "") + LINE_BREAK
            + DETAIL_PREAMBLE_START + WHITESPACE + "type " + styleCommand(COMMAND_FORMAT_DETAIL) + WHITESPACE
            + "with the words found in the command list above." + LINE_BREAK
            + MESSAGE_REFER_USERGUIDE_GENERAL_HELP;
    // studentStatus and Role can magic number
    public static final String HELP_ADD = AddCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE + WHITESPACE + AddCommand.MESSAGE_COMMAND_FORMAT + LINE_BREAK
            + AddCommand.MESSAGE_COMMAND_EXAMPLE + LINE_BREAK
            + formatAcceptedString("Student Status",
                new String[] { StudentStatus.UNDERGRADUATE_OFFICAL_CASE + WHITESPACE
                        + StudentStatus.UNDERGRADUATE_START_YEAR + WHITESPACE + "to"
                        + WHITESPACE + StudentStatus.UNDERGRADUATE_END_YEAR,
                    StudentStatus.MASTERS_OFFICIAL_CASE, StudentStatus.PHD_OFFICIAL_CASE }) + LINE_BREAK
            + formatAcceptedString("Role", Role.AVAILABLE_ROLES) + LINE_BREAK
            + "Multiple roles are allowed and only Nicknames are optional" + LINE_BREAK
            + MESSAGE_REFER_USERGUIDE_DETAIL_HELP;
    public static final String HELP_DELETE = DeleteCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE.replace(":", " -") + WHITESPACE
            + DeleteCommand.MESSAGE_COMMAND_FORMAT + LINE_BREAK + DeleteCommand.MESSAGE_COMMAND_EXAMPLE + LINE_BREAK
            + "Users can delete by full name of contact via " + DeleteCommand.MESSAGE_COMMAND_FORMAT_VERSION_THREE
            + WHITESPACE + "OR" + WHITESPACE + DeleteCommand.MESSAGE_COMMAND_FORMAT_VERSION_TWO + LINE_BREAK
            + MESSAGE_REFER_USERGUIDE_DETAIL_HELP;
    public static final String HELP_EDIT = EditCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE + WHITESPACE + EditCommand.MESSAGE_COMMAND_FORMAT + LINE_BREAK
            + EditCommand.MESSAGE_COMMAND_EXAMPLE + LINE_BREAK
            + MESSAGE_REFER_USERGUIDE_DETAIL_HELP;
    public static final String HELP_FIND = FindCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE + WHITESPACE + FindCommand.MESSAGE_COMMAND_FORMAT + LINE_BREAK
            + FindCommand.MESSAGE_COMMAND_EXAMPLE + LINE_BREAK
            + MESSAGE_REFER_USERGUIDE_DETAIL_HELP;;
    public static final String HELP_LIST = ListCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE + WHITESPACE + ListCommand.MESSAGE_COMMAND_FORMAT + LINE_BREAK
            + "No other fields is required for the command but alternatives are allowed as follows:" + LINE_BREAK
            + "Alternative One:" + WHITESPACE + ListCommand.MESSAGE_COMMAND_FORMAT_ALT_VERSION_ONE + LINE_BREAK
            + "Alternative Two:" + WHITESPACE + ListCommand.MESSAGE_COMMAND_FORMAT_ALT_VERSION_TWO + LINE_BREAK
            + "Alternative Three:" + WHITESPACE + ListCommand.MESSAGE_COMMAND_FORMAT_ALT_VERSION_THREE + LINE_BREAK
            + MESSAGE_REFER_USERGUIDE_DETAIL_HELP;
    public static final String HELP_CLEAR = ClearCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE + WHITESPACE + ClearCommand.MESSAGE_COMMAND_FORMAT;
    public static final String HELP_EXIT = ExitCommand.MESSAGE_FUNCTION + LINE_BREAK
            + COMMAND_FORMAT_PREAMBLE + WHITESPACE + ExitCommand.MESSAGE_COMMAND_FORMAT;

    public static final String MESSAGE_HELP_NOT_COMMAND_WORD =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Only command words are allowed after the help command\n" + HelpCommand.MESSAGE_USAGE);

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

    /**
     * Executes the HelpCommand, returning a CommandResult that contains the help message.
     *
     * @param model The model in which the command operates. This parameter is not used
     *              directly in HelpCommand but is included to adhere to the Command interface.
     * @return A CommandResult containing the message to be displayed to the user,
     *         with an indication if it's the general help message.
     */

    @Override
    public CommandResult execute(Model model) {
        if (isHelp) {
            return new CommandResult(this.message, true, false);
        } else {
            return new CommandResult(this.message, false, false);
        }
    }

    /**
     * Checks if the current HelpCommand object is equal to another object.
     *
     * @param obj The object to be compared with this instance of HelpCommand.
     * @return true if the objects are the same instance or if they are instances of
     *         HelpCommand with identical message and isHelp flag values; false otherwise.
     */
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

    /**
     * Formats a list of accepted values for a specific field into a readable string.
     *
     * @param fieldName The name of the field for which accepted values are being formatted.
     * @param strings An array of strings representing the accepted values for the field.
     * @return A formatted string listing the accepted values, with appropriate separators.
     */
    private static String formatAcceptedString(String fieldName, String[] strings) {
        int stringArraySize = strings.length;
        String toReturn = String.format("For %s, the accepted ones are:", fieldName);
        String partialListedString = Arrays.stream(strings).limit(stringArraySize - 1)
                .reduce("", (listedString, string) -> listedString + string + "," + WHITESPACE);
        toReturn += WHITESPACE + partialListedString + "or" + WHITESPACE + strings[stringArraySize - 1];
        return toReturn;
    }
}
