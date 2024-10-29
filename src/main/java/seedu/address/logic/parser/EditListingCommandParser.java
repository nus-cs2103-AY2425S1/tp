package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.commands.EditListingCommand;
import seedu.address.logic.commands.EditListingCommand.EditListingDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Parses input arguments and creates a new EditListingCommand object
 */
public class EditListingCommandParser implements Parser<EditListingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditListingCommand
     * and returns an EditListingCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditListingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRICE, PREFIX_AREA, PREFIX_ADDRESS, PREFIX_REGION,
                        PREFIX_SELLER, PREFIX_BUYER);

        Name listingName;

        if (!argMultimap.getValue(PREFIX_NAME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
        }

        try {
            listingName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditListingCommand.MESSAGE_USAGE), pe);
        }

        EditListingDescriptor editListingDescriptor = new EditListingDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editListingDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
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

        Set<Name> buyerNames = new HashSet<>();
        for (String buyerName : argMultimap.getAllValues(PREFIX_BUYER)) {
            buyerNames.add(ParserUtil.parseName(buyerName));
        }
        if (!buyerNames.isEmpty()) {
            editListingDescriptor.setBuyerNames(buyerNames);
        }


        if (!editListingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditListingCommand.MESSAGE_NOT_EDITED);
        }

        return new EditListingCommand(listingName, editListingDescriptor);
    }
}
