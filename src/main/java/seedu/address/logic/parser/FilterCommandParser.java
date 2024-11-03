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
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_REGISTER_NUMBER, PREFIX_SEX, PREFIX_STUDENT_CLASS, PREFIX_ECNAME, PREFIX_ECNUMBER, PREFIX_TAG);

        List<String> names = argMultimap.getAllValues(PREFIX_NAME).stream()
                .flatMap(name -> List.of(name.trim().split("\\s+")).stream()).toList();
        //split the names by spaces and flatmap it by putting all of them in one list

        List<String> phones = argMultimap.getAllValues(PREFIX_PHONE).stream()
                .flatMap(phone -> List.of(phone.trim().split("\\s+")).stream()).toList();

        List<String> emails = argMultimap.getAllValues(PREFIX_EMAIL).stream()
                .flatMap(email -> List.of(email.trim().split("\\s+")).stream()).toList();

        List<String> addresses = argMultimap.getAllValues(PREFIX_ADDRESS).stream()
                .flatMap(addr -> List.of(addr.trim().split("\\s+")).stream()).toList();

        List<String> registerNumbers = argMultimap.getAllValues(PREFIX_REGISTER_NUMBER).stream()
                .flatMap(regNo -> List.of(regNo.trim().split("\\s+")).stream()).toList();

        List<String> sexes = argMultimap.getAllValues(PREFIX_SEX).stream()
                .flatMap(sex -> List.of(sex.trim().split("\\s+")).stream()).toList();

        List<String> classes = argMultimap.getAllValues(PREFIX_STUDENT_CLASS).stream()
                .flatMap(clss -> List.of(clss.trim().split("\\s+")).stream()).toList();

        List<String> ecNames = argMultimap.getAllValues(PREFIX_ECNAME).stream()
                .flatMap(ecName -> List.of(ecName.trim().split("\\s+")).stream()).toList();

        List<String> ecNumbers = argMultimap.getAllValues(PREFIX_ECNUMBER).stream()
                .flatMap(ecNum -> List.of(ecNum.trim().split("\\s+")).stream()).toList();

        List<String> tags = argMultimap.getAllValues(PREFIX_TAG).stream()
                .flatMap(tag -> List.of(tag.trim().split("\\s+")).stream()).toList();

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
}
