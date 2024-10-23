package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.ArgumentPredicate;
import seedu.address.model.person.ClientStatus;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PaymentStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProjectStatus;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        // Handles case with no inputs
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_PROJECT_STATUS, PREFIX_PAYMENT_STATUS,
                        PREFIX_CLIENT_STATUS, PREFIX_DEADLINE);

        if (!anyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_TAG, PREFIX_PROJECT_STATUS, PREFIX_PAYMENT_STATUS,
                PREFIX_CLIENT_STATUS, PREFIX_DEADLINE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        assert anyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_TAG, PREFIX_PROJECT_STATUS, PREFIX_PAYMENT_STATUS,
                PREFIX_CLIENT_STATUS, PREFIX_DEADLINE) : "At least 1 prefix is present";

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_DEADLINE, PREFIX_PROJECT_STATUS,
                PREFIX_PAYMENT_STATUS, PREFIX_CLIENT_STATUS, PREFIX_DEADLINE);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).orElse("__No_Name__"));
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).orElse("__No_Phone__"));
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse("__No_Email__"));
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse("__No_Address__"));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        ProjectStatus projectStatus = ParserUtil.parseProjectStatus(argMultimap.getValue(PREFIX_PROJECT_STATUS)
                .orElse("__No_Project_Status__"));
        PaymentStatus paymentStatus = ParserUtil.parsePaymentStatus(argMultimap.getValue(PREFIX_PAYMENT_STATUS)
                .orElse("__No_Payment_Status__"));
        ClientStatus clientStatus = ParserUtil.parseClientStatus(argMultimap.getValue(PREFIX_CLIENT_STATUS)
                .orElse("__No_Client_Status__"));
        Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE)
                .orElse("__No_Deadline__"));

        Person person = new Person(name, phone, email, address, tagList, projectStatus,
                paymentStatus, clientStatus, deadline);
        return new FindCommand(new ArgumentPredicate(person));
    }

    /**
     * Returns true if at least one of the prefixes contains values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent()
                && !argumentMultimap.getValue(prefix).get().isEmpty());
    }

}
