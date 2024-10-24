package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.logic.commands.FindClientTypeCommand;
//import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.ClientTypeContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneBeginsWithKeywordPredicate;

/**
 * Parses input arguments and creates a new FindCommand object based on the prefix provided.
 * <p>
 * If the input starts with the prefix:
 * - "n/" (name): a {@code FindNameCommand} will be returned, which finds persons whose names contain
 * the specified keywords.
 * - "p/" (phone): a {@code FindPhoneCommand} will be returned, which finds persons whose phone numbers begin
 * with the specified keyword.
 * - "a/" (address): a {@code FindAddressCommand} will be returned, which finds persons whose addresses
 * contain the specified keywords.
 * - "c/" (client type): a {@code FindClientTypeCommand} will be returned, which finds persons
 * based on their client type.
 * <p>
 * The matching for name, address, and client type is case-insensitive, and the keywords can be separated by whitespace.
 * However, the phone number is treated as a single string.
 * <p>
 * The parser checks if the input is correctly formatted and throws a {@code ParseException} if any input is invalid.
 */
public class FindCommandParser implements Parser<Command> {

    private static final String PREFIX_NAME = "n/";
    private static final String PREFIX_PHONE = "p/";
    private static final String PREFIX_ADDRESS = "a/";
    private static final String PREFIX_CLIENT_TYPE = "c/";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a Command object for execution.
     *
     * @param args The input arguments string containing the search prefix and keyword(s).
     * @return A Command object (FindNameCommand, FindPhoneCommand, FindAddressCommand, or FindClientTypeCommand)
     *         depending on the prefix provided.
     * @throws ParseException if the user input does not conform to the expected format,
     *                        such as missing keywords or invalid prefixes.
     */
    public Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Check if the input starts with "n/", "p/", "a/", or "c/".
        if (trimmedArgs.startsWith(PREFIX_NAME)) {
            String[] nameKeywords = trimmedArgs.substring(PREFIX_NAME.length()).trim().split("\\s+");
            if (nameKeywords.length == 0 || nameKeywords[0].isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNameCommand.MESSAGE_USAGE));
            }
            return new FindNameCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else if (trimmedArgs.startsWith(PREFIX_PHONE)) {
            String phoneKeywords = trimmedArgs.substring(PREFIX_PHONE.length()).trim();
            if (phoneKeywords.isEmpty() || !phoneKeywords.matches("\\d+")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
            }
            return new FindPhoneCommand(new PhoneBeginsWithKeywordPredicate(phoneKeywords));
        } else if (trimmedArgs.startsWith(PREFIX_ADDRESS)) {
            String addressKeywords = trimmedArgs.substring(PREFIX_ADDRESS.length()).trim();
            if (addressKeywords.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAddressCommand.MESSAGE_USAGE));
            }
            return new FindAddressCommand(new AddressContainsKeywordsPredicate(addressKeywords));
        } else if (trimmedArgs.startsWith(PREFIX_CLIENT_TYPE)) {
            String clientTypeKeywords = trimmedArgs.substring(PREFIX_CLIENT_TYPE.length()).trim();
            if (clientTypeKeywords.isEmpty() || !clientTypeKeywords.matches("^[a-zA-Z0-9\\s]+$")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientTypeCommand.MESSAGE_USAGE));
            }
            return new FindClientTypeCommand(new ClientTypeContainsKeywordsPredicate(List.of(clientTypeKeywords)));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
}
