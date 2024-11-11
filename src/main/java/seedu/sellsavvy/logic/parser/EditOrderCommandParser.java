package seedu.sellsavvy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.commands.ordercommands.EditOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.EditOrderCommand.EditOrderDescriptor;
import seedu.sellsavvy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditOrderCommand object.
 */
public class EditOrderCommandParser implements Parser<EditOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditOrderCommand
     * and returns an EditOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ITEM, PREFIX_QUANTITY, PREFIX_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditOrderCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ITEM, PREFIX_QUANTITY, PREFIX_DATE);

        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();

        if (argMultimap.getValue(PREFIX_ITEM).isPresent()) {
            editOrderDescriptor.setItem(ParserUtil.parseItem(argMultimap.getValue(PREFIX_ITEM).get()));
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            editOrderDescriptor.setQuantity(ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editOrderDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }

        if (!editOrderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditOrderCommand.MESSAGE_NOT_EDITED);
        }

        return new EditOrderCommand(index, editOrderDescriptor);
    }

}
