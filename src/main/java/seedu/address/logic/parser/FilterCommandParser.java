package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.FilterCommand.MESSAGE_INCOMPLETE_COMMAND;
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
     * @throws ParseException if the user input does not conform to the expected format and if predicates are empty
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_REGISTER_NUMBER, PREFIX_SEX, PREFIX_STUDENT_CLASS, PREFIX_ECNAME, PREFIX_ECNUMBER, PREFIX_TAG);

        List<String> names = extractValues(argMultimap, PREFIX_NAME);
        List<String> phones = extractValues(argMultimap, PREFIX_PHONE);
        List<String> emails = extractValues(argMultimap, PREFIX_EMAIL);
        List<String> addresses = extractValues(argMultimap, PREFIX_ADDRESS);
        List<String> registerNumbers = extractValues(argMultimap, PREFIX_REGISTER_NUMBER);
        List<String> sexes = extractValues(argMultimap, PREFIX_SEX);
        List<String> classes = extractValues(argMultimap, PREFIX_STUDENT_CLASS);
        List<String> ecNames = extractValues(argMultimap, PREFIX_ECNAME);
        List<String> ecNumbers = extractValues(argMultimap, PREFIX_ECNUMBER);
        List<String> tags = extractValues(argMultimap, PREFIX_TAG);

        // if either predicate is empty
        if (names.contains("") || phones.contains("") || emails.contains("") || addresses.contains("")
                || registerNumbers.contains("") || sexes.contains("") || classes.contains("") || ecNames.contains("")
                || ecNumbers.contains("") || tags.contains("")) {
            throw new ParseException(MESSAGE_INCOMPLETE_COMMAND);
        }

        // if all predicates are empty
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

    /**
     * Extracts and splits the values associated with a specified prefix from the given {@code ArgumentMultimap}.
     * The values are trimmed and split by whitespace, and all individual values are combined into a single list.
     * @param argMultimap containing arguments and values
     * @param prefix after which the corresponding predicates are provided
     * @return list of strings containing all the individual values to be used as predicate for filtering
     */
    private List<String> extractValues(ArgumentMultimap argMultimap, Prefix prefix) {
        //split the attributes by spaces and flatmap it to put them all in one list
        return argMultimap.getAllValues(prefix).stream()
                .flatMap(value -> List.of(value.trim().split("\\s+")).stream())
                .toList();
    }

}
