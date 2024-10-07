package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddInsuranceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.InsurancePlan;

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
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInsuranceCommand.MESSAGE_USAGE),
                    ive);
        }

        String insuranceIdString = argMultimap.getValue(PREFIX_INSURANCE_ID).orElse("");
        int insuranceId = Integer.parseInt(insuranceIdString.trim());

        if (!InsurancePlan.checkValidPlan(insuranceId)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInsuranceCommand.MESSAGE_USAGE));
        }

        return new AddInsuranceCommand(index, insuranceId);
    }
}
