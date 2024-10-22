package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ETA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditDeliveryDescriptor;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.ItemName;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        boolean isInspect = AddressBookParser.getInspect();
        if (!isInspect) {
            return getEditPersonCommand(args);
        } else {
            return getEditDeliveryCommand(args);
        }
    }

    /**
     * Return EditCommand for person, parsed from args
     */
    private EditCommand getEditPersonCommand(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROLE,
                    PREFIX_ADDRESS, PREFIX_TAG);

        Index index;

        index = parseIndex(argMultimap);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROLE, PREFIX_ADDRESS);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            editPersonDescriptor.setRole(ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Return EditCommand for Delivery, parsed from args
     */
    private EditCommand getEditDeliveryCommand(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_ITEMS, PREFIX_ADDRESS, PREFIX_COST, PREFIX_ETA);
        Index index = parseIndex(argMultimap);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ADDRESS, PREFIX_COST, PREFIX_ETA);

        EditDeliveryDescriptor editDeliveryDescriptor = new EditDeliveryDescriptor();

        parseItemsForEdit(argMultimap.getAllValues(PREFIX_ITEMS)).ifPresent(editDeliveryDescriptor::setItems);
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editDeliveryDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            editDeliveryDescriptor.setCost(ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST).get()));
        }
        if (argMultimap.getValue(PREFIX_ETA).isPresent()) {
            editDeliveryDescriptor.setEta(ParserUtil.parseEta(argMultimap.getValue(PREFIX_ETA).get()));
        }
        if (!editDeliveryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }
        return new EditCommand(index, editDeliveryDescriptor);
    }

    private static Index parseIndex(ArgumentMultimap argMultimap) throws ParseException {
        Index index;
        try {
            List<Index> indexList = ParserUtil.parseIndex(argMultimap.getPreamble());
            if (indexList.isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
            index = indexList.get(0);
        } catch (ParseException pe) {
            String message = AddressBookParser.getInspect()
                                 ? EditCommand.INSPECT_MESSAGE_USAGE
                                 : EditCommand.MESSAGE_USAGE;
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, message), pe);
        }
        return index;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses {@code Collection<String> items} into a {@code Set<ItemName>} if {@code items} is non-empty.
     * If {@code items} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<ItemName>} containing zero tags.
     */
    private Optional<Set<ItemName>> parseItemsForEdit(Collection<String> items) throws ParseException {
        assert items != null;

        if (items.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> itemSet = items.size() == 1 && items.contains("") ? Collections.emptySet() : items;
        return Optional.of(ParserUtil.parseItems(itemSet));
    }

}
