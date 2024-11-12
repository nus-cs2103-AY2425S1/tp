package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AbstractEditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.AddPublicAddressCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.addresses.PublicAddressesComposition;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class AddPublicAddressCommandParser implements Parser<AddPublicAddressCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPublicAddressCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (ArgumentTokenizer.containsExtraPrefixes(args, PREFIX_PUBLIC_ADDRESS_NETWORK, PREFIX_PUBLIC_ADDRESS_LABEL,
            PREFIX_PUBLIC_ADDRESS)) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPublicAddressCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_PUBLIC_ADDRESS_NETWORK, PREFIX_PUBLIC_ADDRESS,
                PREFIX_PUBLIC_ADDRESS_LABEL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPublicAddressCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_PUBLIC_ADDRESS_NETWORK, PREFIX_PUBLIC_ADDRESS,
            PREFIX_PUBLIC_ADDRESS_LABEL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPublicAddressCommand.MESSAGE_USAGE));
        }


        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PUBLIC_ADDRESS_NETWORK, PREFIX_PUBLIC_ADDRESS,
            PREFIX_PUBLIC_ADDRESS_LABEL);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        String networkString = argMultimap.getValue(PREFIX_PUBLIC_ADDRESS_NETWORK).get();
        String publicAddressString = argMultimap.getValue(PREFIX_PUBLIC_ADDRESS).get().toLowerCase();


        String publicAddressLabelString =
            argMultimap.getValue(PREFIX_PUBLIC_ADDRESS_LABEL).get(); // okay as prefixes are confirmed to be present

        PublicAddress publicAddress = ParserUtil.parsePublicAddress(publicAddressString,
            publicAddressLabelString, networkString);

        PublicAddressesComposition publicAddresses =
            new PublicAddressesComposition().addPublicAddress(publicAddress);
        editPersonDescriptor.setPublicAddresses(publicAddresses);

        return new AddPublicAddressCommand(index, editPersonDescriptor);
    }
}
