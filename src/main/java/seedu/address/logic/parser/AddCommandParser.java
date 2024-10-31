package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIER;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.PrefixCheckResult;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandCommons;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Income;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
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
                        PREFIX_INCOME, PREFIX_TIER, PREFIX_NEW_REMARK, PREFIX_STATUS);
        PrefixCheckResult prefixCheckResult = arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS,
                PREFIX_JOB, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_INCOME);
        if (!prefixCheckResult.isAllPrefixPresent()) {
            String missingPrefixMessage =
                    String.format("The following mandatory prefixes are missing: %s",
                            prefixCheckResult.getMissingPrefixes());
            throw new ParseException(missingPrefixMessage + "\n" + String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCommand.MESSAGE_USAGE));
        } else if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_JOB,
                PREFIX_INCOME, PREFIX_TIER, PREFIX_NEW_REMARK, PREFIX_STATUS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Job job = ParserUtil.parseJob(argMultimap.getValue(PREFIX_JOB).get());
        Income income = ParserUtil.parseIncome(argMultimap.getValue(PREFIX_INCOME).get());
        Tier tier = ParserUtil.parseTier(argMultimap.getValue(PREFIX_TIER).orElse(CommandCommons.DEFAULT_TIER));
        Remark remark = ParserUtil.parseNewRemark(argMultimap.getValue(PREFIX_NEW_REMARK)
                .orElse(CommandCommons.DEFAULT_REMARK));
        Status status =
                ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).orElse(CommandCommons.DEFAULT_STATUS));
        Person person = new Person(name, phone, email, address, job, income, tier, remark, status);
        return new AddCommand(person);
    }

    /**
     * Returns {@code PrefixCheckResult} if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static PrefixCheckResult arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        List<Prefix> listOfMissingPrefixes = Stream.of(prefixes)
                .filter(prefix -> !argumentMultimap.getValue(prefix).isPresent())
                .collect(Collectors.toList());
        boolean isAllPrefixPresent =  listOfMissingPrefixes.isEmpty();
        return new PrefixCheckResult(isAllPrefixPresent, listOfMissingPrefixes);
    }

}
