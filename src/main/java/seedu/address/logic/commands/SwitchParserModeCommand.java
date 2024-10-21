package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AbcliParser;
import seedu.address.logic.parser.ParserMode;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Switches Parser mode.
 */
public class SwitchParserModeCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches parser modes. "
            + "Parameters: {b/m/p} (to choose between buyer, meetup, or property mode)\n"
            + "Example: " + COMMAND_WORD + " m";

    public static final String SWITCH_SUCCESS_MESSAGE = "Switched mode to: ";

    private final ParserMode mode;

    /**
     * Creates a SwitchParserModeCommand to switch to the specified {@code ParserMode}
     */
    public SwitchParserModeCommand(ParserMode mode) {
        this.mode = mode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            AbcliParser.switchMode(mode);
            return new CommandResult(SWITCH_SUCCESS_MESSAGE + mode, false,
                    false, false, false);
        } catch (ParseException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
