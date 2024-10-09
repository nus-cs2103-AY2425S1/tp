package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.stream.Stream;

import seedu.address.logic.commands.EmployeeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;

/**
 * Parses input arguments and creates a new EmployeeCommand object
 */
public class EmployeeCommandParser implements Parser<EmployeeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmployeeCommand
     * and returns an EmployeeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmployeeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_DEPARTMENT, PREFIX_ROLE, PREFIX_CONTRACT_END_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_DEPARTMENT, PREFIX_ROLE, PREFIX_CONTRACT_END_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmployeeCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_DEPARTMENT, PREFIX_ROLE, PREFIX_CONTRACT_END_DATE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Department department = ParserUtil.parseDepartment(argMultimap.getValue(PREFIX_DEPARTMENT).get());
        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        ContractEndDate contractEndDate = ParserUtil.parseContractEndDate(
                argMultimap.getValue(PREFIX_CONTRACT_END_DATE).get());

        Person person = new Person(name, phone, email, address, department, role, contractEndDate);

        return new EmployeeCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
