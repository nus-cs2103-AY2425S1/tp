package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNAME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEcNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EcName;

/**
 * Parses input arguments and creates a new {@code AddECNameCommand} object.
 */
public class AddEcNameCommandParser implements Parser<AddEcNameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddECNameCommand}
     * and returns a {@code AddECNameCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEcNameCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ECNAME);

        if (!argMultimap.getValue(PREFIX_ECNAME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEcNameCommand.MESSAGE_USAGE));
        }

        Index index;
        EcName ecName;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            ecName = ParserUtil.parseEcName(argMultimap.getValue(PREFIX_ECNAME).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEcNameCommand.MESSAGE_USAGE), ive);
        }

        return new AddEcNameCommand(index, ecName);
    }

}
