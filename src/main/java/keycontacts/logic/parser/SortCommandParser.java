package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static keycontacts.logic.parser.CliSyntax.PREFIX_GRADE_LEVEL;
import static keycontacts.logic.parser.CliSyntax.PREFIX_NAME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PHONE;

import keycontacts.logic.commands.SortCommand;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.student.StudentComparator;
import keycontacts.model.student.StudentComparator.SortOrder;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        ArgumentList argList = ArgumentTokenizer.tokenizeToList(userInput, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS,
                PREFIX_GRADE_LEVEL);

        if (argList.getValue(0).equals("clear")) {
            return new SortCommand(null);
        }

        if (!argList.anyPrefixesPresent(PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_GRADE_LEVEL)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        argList.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_GRADE_LEVEL);

        StudentComparator studentComparator = new StudentComparator();

        for (int i = 1; i < argList.size(); i++) {
            String sortOrder = argList.getValue(i);
            if (!isValidSortOrder(sortOrder)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
            if (argList.getPrefix(i).equals(PREFIX_NAME)) {
                studentComparator.addComparator(StudentComparator.getComparatorForName(new SortOrder(sortOrder)));
            } else if (argList.getPrefix(i).equals(PREFIX_PHONE)) {
                studentComparator.addComparator(StudentComparator.getComparatorForPhone(new SortOrder(sortOrder)));
            } else if (argList.getPrefix(i).equals(PREFIX_ADDRESS)) {
                studentComparator.addComparator(StudentComparator.getComparatorForAddress(new SortOrder(sortOrder)));
            } else if (argList.getPrefix(i).equals(PREFIX_GRADE_LEVEL)) {
                studentComparator.addComparator(StudentComparator.getComparatorForGradeLevel(new SortOrder(sortOrder)));
            }
        }

        return new SortCommand(studentComparator);
    }

    private boolean isValidSortOrder(String sortOrder) {
        return sortOrder.equals("ASC") || sortOrder.equals("DESC");
    }

}
