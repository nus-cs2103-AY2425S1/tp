package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TeleHandleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TelegramHandle;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEHANDLE;

public class TeleHandleCommandParser implements Parser<TeleHandleCommand> {

    public TeleHandleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TELEHANDLE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TeleHandleCommand.MESSAGE_USAGE), ive);
        }

        String teleHandle = argMultimap.getValue(PREFIX_TELEHANDLE).orElse("");

        return new TeleHandleCommand(index, new TelegramHandle(teleHandle));


    }
}
