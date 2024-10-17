package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CompositePredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.StudentIdMatchesPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Prefix PREFIX_NAME = new Prefix("/n");
    private static final Prefix PREFIX_STUDENT_ID = new Prefix("/id");

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @param args user input string
     * @return FindCommand object configured with the appropriate predicates.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_STUDENT_ID);

        if (!areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_STUDENT_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Check for duplicate prefixes
        verifyNoDuplicatePrefixes(argMultimap, PREFIX_NAME, PREFIX_STUDENT_ID);

        CompositePredicate combinedPredicate = new CompositePredicate();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String nameInput = argMultimap.getValue(PREFIX_NAME).get();
            validateName(nameInput);

            // Use the nameInput as a single keyword for partial matching
            List<String> nameKeywords = Arrays.asList(nameInput.trim());
            combinedPredicate.addPredicate(new NameContainsKeywordsPredicate(nameKeywords));
        }

        if (argMultimap.getValue(PREFIX_STUDENT_ID).isPresent()) {
            String studentIdInput = argMultimap.getValue(PREFIX_STUDENT_ID).get();
            validateStudentId(studentIdInput);
            combinedPredicate.addPredicate(new StudentIdMatchesPredicate(studentIdInput));
        }

        return new FindCommand(combinedPredicate);
    }

    /**
     * Checks if any of the specified prefixes are present in the given ArgumentMultimap.
     *
     * @param argumentMultimap The ArgumentMultimap to check.
     * @param prefixes         The prefixes to look for.
     * @return True if any of the prefixes are present, false otherwise.
     */
    private boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes)
                .anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Verifies that none of the specified prefixes are duplicated in the given ArgumentMultimap.
     *
     * @param argumentMultimap The ArgumentMultimap to check.
     * @param prefixes         The prefixes to verify.
     * @throws ParseException If any prefix is duplicated.
     */
    private void verifyNoDuplicatePrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) throws
            ParseException {
        for (Prefix prefix : prefixes) {
            if (argumentMultimap.getAllValues(prefix).size() > 1) {
                throw new ParseException(String.format(Messages.MESSAGE_DUPLICATE_PREFIX, prefix.getPrefix()));
            }
        }
    }

    /**
     * Validates the provided name input.
     *
     * @param name The name input to validate.
     * @throws ParseException If the name is invalid.
     */
    private void validateName(String name) throws ParseException {
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            throw new ParseException(Messages.MESSAGE_NAME_CANNOT_BE_EMPTY);
        }
        if (trimmedName.contains("/")) {
            throw new ParseException(Messages.MESSAGE_INVALID_CHARACTER_IN_NAME);
        }
    }

    /**
     * Validates the provided student ID input.
     *
     * @param studentId The student ID input to validate.
     * @throws ParseException If the student ID is invalid.
     */
    private void validateStudentId(String studentId) throws ParseException {
        String cleanedId = studentId.trim().replaceAll(" ", "").toUpperCase();
        if (!cleanedId.matches("^[A-Z]\\d{7}[A-Z]$")) {
            throw new ParseException(Messages.MESSAGE_INVALID_STUDENT_ID_FORMAT);
        }
    }

}
