package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddInsuranceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AddInsurance} object
 */
public class AddInsuranceCommandParser implements Parser<AddInsuranceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code AddInsuranceCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddInsuranceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INSURANCE_ID);
        Index index;
        int insuranceId;
        try {
            Optional<String> insuranceIdOptional = argMultimap.getValue(PREFIX_INSURANCE_ID);
            if (argMultimap.getPreamble().isEmpty()
                    || insuranceIdOptional.isEmpty()
                    || insuranceIdOptional.get().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddInsuranceCommand.MESSAGE_USAGE));
            }
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            insuranceId = ParserUtil.parseInsurancePlan(argMultimap.getValue(PREFIX_INSURANCE_ID).get());
        } catch (ParseException e) {
            throw new ParseException(e.getMessage(), e);
        }
        return new AddInsuranceCommand(index, insuranceId);
    }
}
