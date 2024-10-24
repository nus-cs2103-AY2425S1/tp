package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGISTER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_REGISTER_NUMBER, PREFIX_SEX, PREFIX_STUDENT_CLASS, PREFIX_ECNAME, PREFIX_ECNUMBER, PREFIX_TAG);

        List<String> names = argMultimap.getAllValues(PREFIX_NAME).stream().map(String::trim).toList();
        List<String> phones = argMultimap.getAllValues(PREFIX_PHONE).stream().map(String::trim).toList();
        List<String> emails = argMultimap.getAllValues(PREFIX_EMAIL).stream().map(String::trim).toList();
        List<String> addresses = argMultimap.getAllValues(PREFIX_ADDRESS).stream().map(String::trim).toList();
        List<String> registerNumbers = argMultimap.getAllValues(PREFIX_REGISTER_NUMBER).stream()
                .map(String::trim).toList();
        List<String> sexes = argMultimap.getAllValues(PREFIX_SEX).stream().map(String::trim).toList();
        List<String> classes = argMultimap.getAllValues(PREFIX_STUDENT_CLASS).stream().map(String::trim).toList();
        List<String> ecNames = argMultimap.getAllValues(PREFIX_ECNAME).stream().map(String::trim).toList();
        List<String> ecNumbers = argMultimap.getAllValues(PREFIX_ECNUMBER).stream().map(String::trim).toList();
        List<String> tags = argMultimap.getAllValues(PREFIX_TAG).stream().map(String::trim).toList();

        if (names.isEmpty() && phones.isEmpty() && emails.isEmpty() && addresses.isEmpty()
                && registerNumbers.isEmpty() && sexes.isEmpty() && classes.isEmpty()
                && ecNames.isEmpty() && ecNumbers.isEmpty() && tags.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        PersonPredicate predicate = new PersonPredicate(names, phones, emails, addresses, registerNumbers, sexes,
                classes, ecNames, ecNumbers, tags);

        return new FilterCommand(predicate);
    }

}
