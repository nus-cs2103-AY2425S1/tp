package seedu.address.logic.parser.listingcommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.listingcommands.EditListingCommand;
import seedu.address.logic.commands.listingcommands.EditListingCommand.EditListingDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

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

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PRICE, PREFIX_AREA, PREFIX_ADDRESS,
                PREFIX_REGION, PREFIX_SELLER);

        Index currentListingIndex;

        try {
            currentListingIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
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
            Index sellerIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_SELLER).get());
            editListingDescriptor.setSellerIndex(sellerIndex);
        }

        if (!editListingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditListingCommand.MESSAGE_NOT_EDITED);
        }

        return new EditListingCommand(currentListingIndex, editListingDescriptor);
    }
}
