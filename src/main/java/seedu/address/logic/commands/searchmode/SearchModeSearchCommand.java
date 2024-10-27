package seedu.address.logic.commands.searchmode;

import seedu.address.logic.commands.Command;

public class SearchModeSearchCommand extends Command {
    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    public static final String MESSAGE_SUCCESS = "Listed all persons whose names contain the specified keywords.";

    public SearchModeSearchCommand() {
    }
}
