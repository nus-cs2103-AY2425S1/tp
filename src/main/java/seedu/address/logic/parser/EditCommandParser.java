package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditPetCommand;
import seedu.address.logic.commands.EditPetCommand.EditPetDescriptor;
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
        case "p":
            return parsePetEdit(trimmedArgs.substring(1));
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private EditCommand<?> parsePetEdit(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SPECIES, PREFIX_BREED, PREFIX_AGE, PREFIX_SEX);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_SPECIES, PREFIX_BREED, PREFIX_AGE, PREFIX_SEX);

        EditPetDescriptor editPetDescriptor = new EditPetDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPetDescriptor.setName(ParserUtil.parsePetName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_SPECIES).isPresent()) {
            editPetDescriptor.setSpecies(ParserUtil.parsePetSpecies(argMultimap.getValue(PREFIX_SPECIES).get()));
        }
        if (argMultimap.getValue(PREFIX_BREED).isPresent()) {
            editPetDescriptor.setBreed(ParserUtil.parsePetBreed(argMultimap.getValue(PREFIX_BREED).get()));
        }
        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            editPetDescriptor.setAge(ParserUtil.parsePetAge(argMultimap.getValue(PREFIX_AGE).get()));
        }
        if (argMultimap.getValue(PREFIX_SEX).isPresent()) {
            editPetDescriptor.setSex(ParserUtil.parsePetSex(argMultimap.getValue(PREFIX_SEX).get()));
        }

        if (!editPetDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPetCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPetCommand(index, editPetDescriptor);
    }

}
