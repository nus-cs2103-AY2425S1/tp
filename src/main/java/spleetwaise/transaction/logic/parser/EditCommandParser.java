package spleetwaise.transaction.logic.parser;

import static spleetwaise.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static spleetwaise.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static spleetwaise.address.logic.parser.ParserUtil.parsePhone;
import static spleetwaise.transaction.logic.commands.EditCommand.MESSAGE_USAGE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.Set;
import java.util.stream.Stream;

import spleetwaise.address.commons.core.index.Index;
import spleetwaise.address.logic.parser.ArgumentMultimap;
import spleetwaise.address.logic.parser.ArgumentTokenizer;
import spleetwaise.address.logic.parser.Prefix;
import spleetwaise.address.model.person.Phone;
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

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public EditCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_PHONE, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_CATEGORY);

        // Parse index
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), e);
        }

        // Parse descriptors
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_PHONE, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_CATEGORY);

        EditTransactionDescriptor editTransactionDescriptor = new EditTransactionDescriptor();

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            Phone phone = parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            editTransactionDescriptor.setPerson(ParserUtil.getPersonFromPhone(phone));
        }

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

        if (!argMultimap.getAllValues(PREFIX_CATEGORY).isEmpty()) {
            Set<Category> categoriesSet = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));
            if (!categoriesSet.isEmpty()) {
                editTransactionDescriptor.setCategories(categoriesSet);
            }
        }

        // Check if any fields edited
        if (!editTransactionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editTransactionDescriptor);

    }

}
