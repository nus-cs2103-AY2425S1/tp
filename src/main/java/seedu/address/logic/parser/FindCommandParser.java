package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailPredicate;
import seedu.address.model.person.FullNameContainsPredicate;
import seedu.address.model.person.JobCodePredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhonePredicate;
import seedu.address.model.person.TagPredicate;


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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_TAG, PREFIX_JOBCODE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Step 1: Collect predicates based on provided inputs.
        List<Predicate<Person>> predicates = new ArrayList<>();

        argMultimap.getValue(PREFIX_NAME).ifPresent(name -> {
            String parsedName = ParserUtil.parseFind(name);
            predicates.add(new FullNameContainsPredicate(parsedName));
        });

        argMultimap.getValue(PREFIX_PHONE).ifPresent(phone -> {
            String parsedPhone = ParserUtil.parseFind(phone);
            predicates.add(new PhonePredicate(parsedPhone));
        });

        argMultimap.getValue(PREFIX_EMAIL).ifPresent(email -> {
            String parsedEmail = ParserUtil.parseFind(email);
            predicates.add(new EmailPredicate(parsedEmail));
        });

        argMultimap.getValue(PREFIX_TAG).ifPresent(tag -> {
            String parsedTag = ParserUtil.parseFind(tag);
            predicates.add(new TagPredicate(Collections.singletonList(parsedTag)));
        });

        argMultimap.getValue(PREFIX_JOBCODE).ifPresent(jobCode -> {
            String parsedJobCode = ParserUtil.parseFind(jobCode);
            predicates.add(new JobCodePredicate(parsedJobCode));
        });

        // Step 2: Check if it's empty
        if (predicates.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Step 3: Combine all predicates using Predicate.and()
        Predicate<Person> combinedPredicate = predicates.stream()
                .reduce(Predicate::and)
                .orElse(person -> true); // Default to match all if no predicates

        // Step 4: Return the new FindCommand with the combined predicate
        return new FindCommand(combinedPredicate);
    }
}
