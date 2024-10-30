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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
                PREFIX_ADDRESS, PREFIX_PROJECT_STATUS,
                PREFIX_PAYMENT_STATUS, PREFIX_CLIENT_STATUS, PREFIX_DEADLINE);

        Map<String, Object> parameterMap = new HashMap<>();
        Optional<String> optionalName = argMultimap.getValue(PREFIX_NAME);
        Optional<String> optionalPhone = argMultimap.getValue(PREFIX_PHONE);
        Optional<String> optionalEmail = argMultimap.getValue(PREFIX_EMAIL);
        Optional<String> optionalAddress = argMultimap.getValue(PREFIX_ADDRESS);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Optional<String> optionalProjectStatus = argMultimap.getValue(PREFIX_PROJECT_STATUS);
        Optional<String> optionalPaymentStatus = argMultimap.getValue(PREFIX_PAYMENT_STATUS);
        Optional<String> optionalClientStatus = argMultimap.getValue(PREFIX_CLIENT_STATUS);
        Optional<String> optionalDeadline = argMultimap.getValue(PREFIX_DEADLINE);

        if (optionalName.isPresent()) {
            parameterMap.put(Name.NAME_KEY, ParserUtil.parseName(optionalName.get()));
        }
        if (optionalPhone.isPresent()) {
            parameterMap.put(Phone.PHONE_KEY, ParserUtil.parsePhone(optionalPhone.get()));
        }
        if (optionalEmail.isPresent()) {
            parameterMap.put(Email.EMAIL_KEY, ParserUtil.parseEmail(optionalEmail.get()));
        }
        if (optionalAddress.isPresent()) {
            parameterMap.put(Address.ADDRESS_KEY, ParserUtil.parseAddress(optionalAddress.get()));
        }
        if (optionalProjectStatus.isPresent()) {
            parameterMap.put(ProjectStatus.PROJECT_STATUS_KEY,
                    ParserUtil.parseProjectStatus(optionalProjectStatus.get()));
        }
        if (optionalPaymentStatus.isPresent()) {
            parameterMap.put(PaymentStatus.PAYMENT_STATUS_KEY,
                    ParserUtil.parsePaymentStatus(optionalPaymentStatus.get()));
        }
        if (optionalClientStatus.isPresent()) {
            parameterMap.put(ClientStatus.CLIENT_STATUS_KEY, ParserUtil.parseClientStatus(optionalClientStatus.get()));
        }
        if (optionalDeadline.isPresent()) {
            parameterMap.put(Deadline.DEADLINE_KEY, ParserUtil.parseDeadline(optionalDeadline.get()));
        }

        parameterMap.put(Tag.TAG_KEY, tagList);

        return new FindCommand(new ArgumentPredicate(parameterMap));
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
