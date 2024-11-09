package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISSUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAKE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VRN;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditClientCommand;
import seedu.address.logic.commands.EditClientCommand.EditCarDescriptor;
import seedu.address.logic.commands.EditClientCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.issue.Issue;

/**
 * Parses input arguments and creates a new EditClientCommand object
 */
public class EditClientCommandParser implements Parser<EditClientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditClientCommand
     * and returns an EditClientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditClientCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_ISSUE,
                        PREFIX_VRN, PREFIX_VIN, PREFIX_MAKE, PREFIX_MODEL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClientCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_VRN, PREFIX_VIN, PREFIX_MAKE, PREFIX_MODEL);

        EditCarDescriptor editCarDescriptor = new EditCarDescriptor();

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        Boolean isPersonEdited = false;
        Boolean isCarEdited = false;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
            isPersonEdited = true;
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
            isPersonEdited = true;
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
            isPersonEdited = true;
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
            isPersonEdited = true;
        }

        if (argMultimap.getValue(PREFIX_ISSUE).isPresent()) {
            editPersonDescriptor.setIssueEdited(true);
        } else {
            editPersonDescriptor.setIssueEdited(false);
        }

        parseIssuesForEdit(argMultimap.getAllValues(PREFIX_ISSUE)).ifPresent(editPersonDescriptor::setIssues);

        if (argMultimap.getValue(PREFIX_VRN).isPresent()) {
            editCarDescriptor.setVrn(ParserUtil.parseVrn(argMultimap.getValue(PREFIX_VRN).get()));
            isCarEdited = true;
        }

        if (argMultimap.getValue(PREFIX_VIN).isPresent()) {
            editCarDescriptor.setVin(ParserUtil.parseVin(argMultimap.getValue(PREFIX_VIN).get()));
            isCarEdited = true;
        }

        if (argMultimap.getValue(PREFIX_MAKE).isPresent()) {
            editCarDescriptor.setMake(ParserUtil.parseCarMake(argMultimap.getValue(PREFIX_MAKE).get()));
            isCarEdited = true;
        }

        if (argMultimap.getValue(PREFIX_MODEL).isPresent()) {
            editCarDescriptor.setModel(ParserUtil.parseCarModel(argMultimap.getValue(PREFIX_MODEL).get()));
            isCarEdited = true;
        }

        if (!editPersonDescriptor.isAnyFieldEdited() && !editCarDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditClientCommand.MESSAGE_NOT_EDITED);
        }

        return new EditClientCommand(index, editPersonDescriptor, editCarDescriptor, isPersonEdited, isCarEdited);
    }


    /**
     * Parses {@code Collection<String> issues} into a {@code Set<Issue>} if {@code issues} is non-empty.
     * If {@code issues} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Issue>} containing zero issues.
     */
    private Optional<Set<Issue>> parseIssuesForEdit(Collection<String> issues) throws ParseException {
        assert issues != null;

        if (issues.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> issueSet = issues.size() == 1 && issues.contains("") ? Collections.emptySet() : issues;
        return Optional.of(ParserUtil.parseIssues(issueSet));
    }

}
