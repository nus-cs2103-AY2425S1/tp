package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;

import java.util.List;

import seedu.address.logic.commands.EditListingCommand;
import seedu.address.logic.commands.EditListingCommand.EditListingDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new EditListingCommand object.
 */
public class EditListingCommandParser implements Parser<EditListingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditListingCommand
     * and returns an EditListingCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public EditListingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_PRICE, PREFIX_AREA, PREFIX_ADDRESS, PREFIX_REGION, PREFIX_SELLER);

        List<String> nameValues = argMultimap.getAllValues(PREFIX_NAME);

        if (nameValues.size() > 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getAllValues(PREFIX_PRICE).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getAllValues(PREFIX_AREA).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getAllValues(PREFIX_ADDRESS).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getAllValues(PREFIX_REGION).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getAllValues(PREFIX_SELLER).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
        }

        Name currentListingName;

        if (nameValues.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
        }

        try {
            currentListingName = ParserUtil.parseName(nameValues.get(0));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditListingCommand.MESSAGE_USAGE), pe);
        }

        EditListingDescriptor editListingDescriptor = new EditListingDescriptor();
        if (nameValues.size() == 2) {
            Name newListingName = ParserUtil.parseName(nameValues.get(1));
            editListingDescriptor.setName(newListingName);
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            editListingDescriptor.setPrice(ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get()));
        }
        if (argMultimap.getValue(PREFIX_AREA).isPresent()) {
            editListingDescriptor.setArea(ParserUtil.parseArea(argMultimap.getValue(PREFIX_AREA).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editListingDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_REGION).isPresent()) {
            editListingDescriptor.setRegion(ParserUtil.parseRegion(argMultimap.getValue(PREFIX_REGION).get()));
        }
        if (argMultimap.getValue(PREFIX_SELLER).isPresent()) {
            Name sellerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_SELLER).get());
            editListingDescriptor.setSellerName(sellerName);
        }

        if (!editListingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditListingCommand.MESSAGE_NOT_EDITED);
        }

        return new EditListingCommand(currentListingName, editListingDescriptor);
    }
}
