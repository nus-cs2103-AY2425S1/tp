package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailPredicate;
import seedu.address.model.person.FullNameContainsPredicate;
import seedu.address.model.person.JobCodePredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhonePredicate;
import seedu.address.model.person.RemarkPredicate;
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
    public FindCommand parse(String args) throws ParseException, RuntimeException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_TAG, PREFIX_JOBCODE, PREFIX_REMARK);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_JOBCODE, PREFIX_TAG, PREFIX_REMARK);

        // Step 1: Collect predicates based on provided inputs.
        List<Predicate<Person>> predicates = new ArrayList<>();

        Optional<String> optionalName = argMultimap.getValue(PREFIX_NAME);
        if (optionalName.isPresent()) {
            String parsedName = ParserUtil.parseName(optionalName.get()).fullName;
            predicates.add(new FullNameContainsPredicate(parsedName));
        }

        Optional<String> optionalPhone = argMultimap.getValue(PREFIX_PHONE);
        if (optionalPhone.isPresent()) {
            String parsedPhone = ParserUtil.parsePhoneFind(optionalPhone.get());
            predicates.add(new PhonePredicate(parsedPhone));
        }

        Optional<String> optionalEmail = argMultimap.getValue(PREFIX_EMAIL);
        if (optionalEmail.isPresent()) {
            String parsedEmail = ParserUtil.parseEmailFind(optionalEmail.get());
            predicates.add(new EmailPredicate(parsedEmail));
        }

        Optional<String> optionalTag = argMultimap.getValue(PREFIX_TAG);
        if (optionalTag.isPresent()) {
            String parsedTag = ParserUtil.parseTagFind(optionalTag.get());
            predicates.add(new TagPredicate(parsedTag));
        }

        Optional<String> optionalJobCode = argMultimap.getValue(PREFIX_JOBCODE);
        if (optionalJobCode.isPresent()) {
            String parsedJobCode = ParserUtil.parseJobCode(optionalJobCode.get()).value;
            predicates.add(new JobCodePredicate(parsedJobCode));
        }

        Optional<String> optionalRemark = argMultimap.getValue(PREFIX_REMARK);
        if (optionalRemark.isPresent()) {
            String parsedRemark = ParserUtil.parseRemark(optionalRemark.get()).value;
            predicates.add(new RemarkPredicate(parsedRemark));
        }

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
