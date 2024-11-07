package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String[] trimmedArgs = args.trim().split(" ");
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_TAG, PREFIX_JOBCODE, PREFIX_REMARK);
        // Check if the arguments consist of a single word
        if (trimmedArgs.length == 1) {
            try {
                System.out.println(args);
                // Try to parse it as an index first
                Index index = ParserUtil.parseIndex(args);
                return new DeleteCommand(index);
            } catch (ParseException pe) { // cannot parse as index (because index is zero or less)
                if (this.isInteger(args)) {
                    System.out.println("argument is an integer");
                    throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                } else {
                    // If it is not an index, handle using parseOtherAttributes
                    if (!argMultiMap.getPreamble().isEmpty()) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
                    }
                    argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE,
                            PREFIX_EMAIL, PREFIX_JOBCODE, PREFIX_TAG, PREFIX_REMARK);
                    return parseOtherAttributes(argMultiMap);
                }
            }
        } // more than 2 arguments
            // check if all the predicates are valid
        if (!argMultiMap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_JOBCODE, PREFIX_TAG, PREFIX_REMARK);
        return parseOtherAttributes(argMultiMap);
    }

    /**
     * Parses the arguments based on name, email and phone
     * @param argMultimap processed from parse
     * @return DeleteCommand object with the contact index to be deleted
     * @throws ParseException
     */
    public DeleteCommand parseOtherAttributes(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> optionalName = argMultimap.getValue(PREFIX_NAME);
        Optional<String> optionalPhone = argMultimap.getValue(PREFIX_PHONE);
        Optional<String> optionalEmail = argMultimap.getValue(PREFIX_EMAIL);
        Optional<String> optionalJobCode = argMultimap.getValue(PREFIX_JOBCODE);
        Optional<String> optionalRemark = argMultimap.getValue(PREFIX_REMARK);
        Optional<String> optionalTag = argMultimap.getValue(PREFIX_TAG);
        if (optionalJobCode.isPresent() || optionalRemark.isPresent() || optionalTag.isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        if (optionalName.isPresent() && optionalPhone.isEmpty() && optionalEmail.isEmpty()) {
            // find using name only
            Name name = ParserUtil.parseName(optionalName.get());
            return new DeleteCommand(name);
        } else if (optionalEmail.isPresent() && optionalPhone.isEmpty() && optionalName.isEmpty()) {
            // find using email only
            Email email = ParserUtil.parseEmail(optionalEmail.get());
            return new DeleteCommand(email); // handles unique emails
        } else if (optionalPhone.isPresent() && optionalEmail.isEmpty() && optionalName.isEmpty()) {
            // find using phone only
            Phone phone = ParserUtil.parsePhone(optionalPhone.get());
            return new DeleteCommand(phone); // handles unique phone
        } else {
            // Wrong attributes used to find for the contacts. return delete format.
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

    private boolean isInteger(String str) {
        str = str.trim();
        System.out.println(str);
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
