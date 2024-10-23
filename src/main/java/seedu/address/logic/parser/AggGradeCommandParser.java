package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.address.logic.commands.AggGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AggGradeCommandParser implements Parser<AggGradeCommand> {
    @Override
    public AggGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (argMultiMap.getPreamble().isEmpty()) {
            // TODO
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "TODO"));
        }

        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        AggGradeCommand.Operation operation = ParserUtil.parseAggGradeOperation(argMultiMap.getPreamble());
        Optional<String> name = argMultiMap.getValue(PREFIX_NAME);
        String examName = name.isPresent() ? ParserUtil.parseTestName(name.get()) : null;

        return new AggGradeCommand(operation, examName);
    }
}
