package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIAGNOSIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WARD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ID,
                        PREFIX_WARD, PREFIX_DIAGNOSIS, PREFIX_MEDICATION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ID,
                PREFIX_WARD, PREFIX_DIAGNOSIS, PREFIX_MEDICATION);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            editPersonDescriptor.setId(ParserUtil.parseId(argMultimap.getValue(PREFIX_ID).get()));
        }

        if (argMultimap.getValue(PREFIX_WARD).isPresent()) {
            editPersonDescriptor.setWard(ParserUtil.parseWard(argMultimap.getValue(PREFIX_WARD).get()));
        }

        if (argMultimap.getValue(PREFIX_DIAGNOSIS).isPresent()) {
            editPersonDescriptor.setDiagnosis(ParserUtil.parseDiagnosis(argMultimap.getValue(PREFIX_DIAGNOSIS).get()));
        }

        if (argMultimap.getValue(PREFIX_MEDICATION).isPresent()) {
            editPersonDescriptor
            .setMedication(ParserUtil.parseMedication(argMultimap.getValue(PREFIX_MEDICATION).get()));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }


        return new EditCommand(index, editPersonDescriptor);
    }

}
