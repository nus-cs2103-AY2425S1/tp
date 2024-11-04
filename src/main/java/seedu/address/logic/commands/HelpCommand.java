package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Command List: add, delete, edit, find, list\n"
            + "For more details, type `help [COMMAND_WORD]` with the words found in the command list above\n"
            + "Alternatively, visit our user guide on the popup screen\n";

    public static final String HELP_ADD = "`add n/[NAME] th/[TELEGRAM HANDLE] e/[EMAIL] s/[STUDENT_STATUS] r/[ROLE]`\n"
            + "Example: add n/ John Doe th/ John_Doe "
            + "e/ johndoe123@gmail.com s/ Undergraduate 2 r/ President\n"
            + "For Student Status, the accepted ones are: Undergraduate 1 to 6, Masters or PhD\n"
            + "For Role, the accepted ones are: President, Vice President, Admin, Marketing, Events (Internal),"
            + " Events (External), or External Relations";

    public static final String HELP_DELETE = "`delete [INDEX]` OR `delete [NAME]`\n"
            + "Example One: delete 1\n"
            + "Example Two: delete n/ John Doe\n";
    public static final String HELP_EDIT = "Example for Edit command: `edit [INDEX] [PREFIX] [new description]`\n"
            + "Example One: edit 1 n/ Jane Doe"
            + "Example Two: edit 2 n/John Doe nn/ johnny";
    public static final String HELP_FIND = "`find [PREFIX] [KEYWORD]`\n"
            + "Example One: find n/ Jane Doe"
            + "Example Two: find nn/ johnny";
    public static final String HELP_LIST = "`list`\n"
            + "Alternative One: `list all`\n"
            + "Alternative Two: `list contacts`\n"
            + "Alternative Three: `list all contacts`\n";

    public static final String HELP_CLEAR = "`clear`\n"
            + "Clears all the contacts";
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
