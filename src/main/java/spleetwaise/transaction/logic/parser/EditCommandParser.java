package spleetwaise.transaction.logic.parser;

import static spleetwaise.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static spleetwaise.transaction.logic.commands.EditCommand.MESSAGE_USAGE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import spleetwaise.address.logic.parser.ArgumentMultimap;
import spleetwaise.address.logic.parser.ArgumentTokenizer;
import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.logic.parser.Parser;
import spleetwaise.commons.logic.parser.exceptions.ParseException;
import spleetwaise.transaction.logic.commands.EditCommand;
import spleetwaise.transaction.logic.commands.EditCommand.EditTransactionDescriptor;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    @Override
    public EditCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        //PREFIX_PHONE,
                        PREFIX_AMOUNT,
                        PREFIX_DESCRIPTION,
                        PREFIX_DATE,
                        PREFIX_CATEGORY
                );

        // Parse index
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), e);
        }

        // Parse descriptors
        argMultimap.verifyNoDuplicatePrefixesFor(
                //PREFIX_PHONE,
                PREFIX_AMOUNT,
                PREFIX_DESCRIPTION,
                PREFIX_DATE
        );

        EditTransactionDescriptor editTransactionDescriptor = new EditTransactionDescriptor();

        //if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
        //  Phone phone = parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        //  editTransactionDescriptor.setPerson(ParserUtil.getPersonFromPhone(phone));
        //}

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
            editTransactionDescriptor.setAmount(amount);
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            Description desc = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            editTransactionDescriptor.setDescription(desc);
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            editTransactionDescriptor.setDate(date);
        }

        parseCategoriesForEdit(argMultimap.getAllValues(PREFIX_CATEGORY)).ifPresent(
                editTransactionDescriptor::setCategories);

        // Check if any fields edited
        if (!editTransactionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editTransactionDescriptor);

    }

    /**
     * Parses {@code Collection<String> categories} into a {@code Set<Category>} if {@code categories} is non-empty. If
     * {@code categories} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Category>} containing zero categories.
     */
    private Optional<Set<Category>> parseCategoriesForEdit(Collection<String> categories) throws ParseException {
        assert categories != null;

        if (categories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> categorySet = categories.size() == 1 && categories.contains("") ? Collections.emptySet()
                : categories;
        return Optional.of(ParserUtil.parseCategories(categorySet));
    }


}
