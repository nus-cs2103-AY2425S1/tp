package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_COVERAGE_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_PREMIUM_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.EditPolicyDescriptor;
import seedu.address.model.policy.PolicyType;

/**
 * Parses input arguments and creates a new EditPolicyCommand object.
 */
public class EditPolicyCommandParser implements Parser<EditPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPolicyCommand
     * and returns an EditPolicyCommand object for execution.
     *
     * @param args the string arguments to parse.
     * @return an EditPolicyCommand containing the parsed index and EditPolicyDescriptor.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public EditPolicyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE,
                PREFIX_POLICY_PREMIUM_AMOUNT, PREFIX_POLICY_COVERAGE_AMOUNT, PREFIX_POLICY_EXPIRY_DATE);

        // Validate and get the index
        Index index = parseIndex(argMultimap);

        // Validate and create the EditPolicyDescriptor
        EditPolicyDescriptor editPolicyDescriptor = createEditPolicyDescriptor(argMultimap);

        // Check if at least one field has been edited
        if (!editPolicyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPolicyCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPolicyCommand(index, editPolicyDescriptor);
    }

    /**
     * Parses the index from the given ArgumentMultimap.
     *
     * @param argMultimap the ArgumentMultimap containing the user input arguments.
     * @return the parsed index.
     * @throws ParseException if the preamble is empty or the index is invalid.
     */
    private Index parseIndex(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE));
        }

        try {
            return ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE), ive);
        }
    }

    /**
     * Creates an EditPolicyDescriptor from the given ArgumentMultimap.
     *
     * @param argMultimap the ArgumentMultimap containing the user input arguments.
     * @return an EditPolicyDescriptor with the parsed policy type and optional fields.
     * @throws ParseException if the policy type is not present or if there are duplicate prefixes.
     */
    private EditPolicyDescriptor createEditPolicyDescriptor(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_TYPE,
                PREFIX_POLICY_PREMIUM_AMOUNT, PREFIX_POLICY_COVERAGE_AMOUNT, PREFIX_POLICY_EXPIRY_DATE);

        PolicyType policyType = ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get());
        EditPolicyDescriptor editPolicyDescriptor = new EditPolicyDescriptor(policyType);

        setOptionalFields(argMultimap, editPolicyDescriptor);

        return editPolicyDescriptor;
    }

    /**
     * Sets optional fields in the EditPolicyDescriptor based on the given ArgumentMultimap.
     *
     * @param argMultimap the ArgumentMultimap containing the user input arguments.
     * @param editPolicyDescriptor the EditPolicyDescriptor to set fields on.
     * @throws ParseException if there is an error parsing the optional fields.
     */
    private void setOptionalFields(ArgumentMultimap argMultimap, EditPolicyDescriptor editPolicyDescriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_POLICY_PREMIUM_AMOUNT).isPresent()) {
            editPolicyDescriptor.setPremiumAmount(ParserUtil.parsePremiumAmount(
                    argMultimap.getValue(PREFIX_POLICY_PREMIUM_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_POLICY_COVERAGE_AMOUNT).isPresent()) {
            editPolicyDescriptor.setCoverageAmount(ParserUtil.parseCoverageAmount(
                    argMultimap.getValue(PREFIX_POLICY_COVERAGE_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_POLICY_EXPIRY_DATE).isPresent()) {
            editPolicyDescriptor.setExpiryDate(ParserUtil.parseExpiryDate(
                    argMultimap.getValue(PREFIX_POLICY_EXPIRY_DATE).get()));
        }
    }
}
