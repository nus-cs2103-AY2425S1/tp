package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditOwnerCommand;
import seedu.address.logic.commands.EditOwnerCommand.EditOwnerDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand<?>> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand<?> parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }


        String entityType = trimmedArgs.substring(0, 1);
        String arguments = trimmedArgs.substring(1);

        String[] splitArgs = arguments.split("\\s+");


        if (splitArgs.length < 2) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        switch (entityType.toLowerCase()) {
        case "o":
            return parseOwnerEdit(trimmedArgs.substring(1));
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private EditCommand<?> parseOwnerEdit(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IC_NUMBER, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_IC_NUMBER, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_ADDRESS);

        EditOwnerDescriptor editOwnerDescriptor = new EditOwnerDescriptor();

        if (argMultimap.getValue(PREFIX_IC_NUMBER).isPresent()) {
            editOwnerDescriptor.setIdentificationNumber(
                ParserUtil.parseOwnerIcNumber(argMultimap.getValue(PREFIX_IC_NUMBER).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editOwnerDescriptor.setName(ParserUtil.parseOwnerName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editOwnerDescriptor.setPhone(ParserUtil.parseOwnerPhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editOwnerDescriptor.setEmail(ParserUtil.parseOwnerEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editOwnerDescriptor.setAddress(ParserUtil.parseOwnerAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (!editOwnerDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditOwnerCommand.MESSAGE_NOT_EDITED);
        }

        return new EditOwnerCommand(index, editOwnerDescriptor);
    }

}
