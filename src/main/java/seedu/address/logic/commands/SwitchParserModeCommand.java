package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
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
        requireNonNull(mode);
        this.mode = mode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            AbcliParser.switchMode(mode);
            boolean showMeetUpList = this.mode == ParserMode.MEETUP;
            boolean showBuyerList = this.mode == ParserMode.BUYER;
            boolean showPropertyList = this.mode == ParserMode.PROPERTY;
            return new CommandResult(SWITCH_SUCCESS_MESSAGE + mode, false,
                    false, showMeetUpList, showBuyerList, showPropertyList);
        } catch (ParseException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SwitchParserModeCommand)) {
            return false;
        }

        SwitchParserModeCommand otherSwitchParserCommand = (SwitchParserModeCommand) other;
        return mode.equals(otherSwitchParserCommand.mode);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("switching to: ", mode)
                .toString();
    }
}
