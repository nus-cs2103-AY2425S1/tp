package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static keycontacts.logic.parser.CliSyntax.PREFIX_GRADE_LEVEL;
import static keycontacts.logic.parser.CliSyntax.PREFIX_NAME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PHONE;

import keycontacts.logic.commands.FindCommand;
import keycontacts.logic.commands.FindCommand.FindStudentDescriptor;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.student.StudentDescriptorMatchesPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS,
                PREFIX_GRADE_LEVEL);

        if ((!argMultimap.anyPrefixesPresent(PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_GRADE_LEVEL))
                || argMultimap.isPreamblePresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_GRADE_LEVEL);

        FindStudentDescriptor findStudentDescriptor = new FindStudentDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            findStudentDescriptor.setName(argMultimap.getValue(PREFIX_NAME).get().trim());
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            findStudentDescriptor.setPhone(argMultimap.getValue(PREFIX_PHONE).get().trim());
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            findStudentDescriptor.setAddress(argMultimap.getValue(PREFIX_ADDRESS).get().trim());
        }
        if (argMultimap.getValue(PREFIX_GRADE_LEVEL).isPresent()) {
            findStudentDescriptor.setGradeLevel(argMultimap.getValue(PREFIX_GRADE_LEVEL).get().trim());
        }

        return new FindCommand(new StudentDescriptorMatchesPredicate(findStudentDescriptor));
    }

}
