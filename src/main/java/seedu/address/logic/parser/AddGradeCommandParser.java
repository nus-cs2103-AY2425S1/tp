package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.logic.commands.AddGradeCommand.AddGradeCommandFormat;
import seedu.address.logic.parser.exceptions.ParseException;

/** Parses input arguments and creates a new AddGradeCommand object */
public class AddGradeCommandParser implements Parser<AddGradeCommand> {

    @Override
    public AddGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ASSIGNMENT, PREFIX_SCORE);
        AddGradeCommandFormat addGradeCommandFormat = new AddGradeCommandFormat();
        addGradeCommandFormat.setName(
                ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        addGradeCommandFormat.setScore(
                ParserUtil.parseScore(argMultimap.getValue(PREFIX_SCORE).get()));
        addGradeCommandFormat.setAssignment(
                ParserUtil.parseAssignmentName(argMultimap.getValue(PREFIX_ASSIGNMENT).get()));
        return new AddGradeCommand(addGradeCommandFormat);
    }
}
