package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ModCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ModuleName;

/**
 * Parses input arguments and creates a new ModCommand object
 */
public class ModCommandParser implements Parser<ModCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModCommand
     * and returns a ModCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args,
                PREFIX_MOD);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultiMap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ModCommand.MESSAGE_USAGE), ive);
        }

        String modName = argMultiMap.getValue(PREFIX_MOD).orElse("");

        return new ModCommand(index, new ModuleName(modName));
    }

}
