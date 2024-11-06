package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandCommons.getErrorMessage;
import static seedu.address.logic.commands.CommandCommons.parseField;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIER;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.PrefixCheckResult;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandCommons;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Income;
import seedu.address.model.client.Job;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Remark;
import seedu.address.model.status.Status;
import seedu.address.model.tier.Tier;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_JOB,
                        PREFIX_INCOME, PREFIX_TIER, PREFIX_REMARK, PREFIX_STATUS);
        PrefixCheckResult prefixCheckResult = arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_JOB, PREFIX_INCOME);
        if (!prefixCheckResult.isAllPrefixPresent()) {
            String missingPrefixMessage = AddCommand.MISSING_PREFIX_MESSAGE_START
                    + prefixCheckResult.getMissingPrefixes();
            throw new ParseException(missingPrefixMessage + "\n" + String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCommand.MESSAGE_USAGE));
        } else if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_JOB,
                PREFIX_INCOME, PREFIX_TIER, PREFIX_REMARK, PREFIX_STATUS);
        Set<String> errors = new LinkedHashSet<>();
        Name name = parseField(() -> ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()), errors);
        Phone phone = parseField(() -> ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()), errors);
        Email email = parseField(() -> ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()), errors);
        Address address = parseField(() -> ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()),
                errors);
        Job job = parseField(() -> ParserUtil.parseJob(argMultimap.getValue(PREFIX_JOB).get()), errors);
        Income income = parseField(() -> ParserUtil.parseIncome(argMultimap.getValue(PREFIX_INCOME).get()),
                errors);
        Tier tier = parseField(() -> ParserUtil.parseTier(argMultimap.getValue(PREFIX_TIER)
                .orElse(CommandCommons.DEFAULT_TIER)), errors);
        Remark remark = parseField(() -> ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)
                .orElse(CommandCommons.DEFAULT_REMARK)), errors);
        Status status = parseField(() -> ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS)
                .orElse(CommandCommons.DEFAULT_STATUS)), errors);
        if (!errors.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    getErrorMessage(errors) + "\n" + AddCommand.MESSAGE_USAGE));
        }
        Client client = new Client(name, phone, email, address, job, income, tier, remark, status);
        return new AddCommand(client);
    }

    /**
     * Returns {@code PrefixCheckResult} if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static PrefixCheckResult arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        List<Prefix> listOfMissingPrefixes = Stream.of(prefixes)
                .filter(prefix -> !argumentMultimap.getValue(prefix).isPresent())
                .collect(Collectors.toList());
        boolean isAllPrefixPresent = listOfMissingPrefixes.isEmpty();
        return new PrefixCheckResult(isAllPrefixPresent, listOfMissingPrefixes);
    }

}
