package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_CONTACTS_SPECIFIED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.ParserUtil.parsePersonIndexString;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnassignContactFromWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * This UnssignContactFromWeddingCommandParser parses user inputs starting with "unassignw"
 * where the first index is interpreted as the wedding index and subsequent indexes after
 * the 'c/' prefix are interpreted as the person index's to unassign from the wedding
 */
public class UnassignContactFromWeddingCommandParser implements Parser<UnassignContactFromWeddingCommand> {
    @Override
    public UnassignContactFromWeddingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap multimap = ArgumentTokenizer.tokenize(args, PREFIX_CONTACT);

        //Reformatting error message for duplicate c/ instances
        try {
            multimap.verifyNoDuplicatePrefixesFor(PREFIX_CONTACT);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Please only include one prefix c/ !"));
        }

        // If c/ prefix is missing
        if (multimap.getValue(PREFIX_CONTACT).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnassignContactFromWeddingCommand.MESSAGE_USAGE));
        }

        Index weddingIndex;
        try {
            weddingIndex = ParserUtil.parseIndex(multimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnassignContactFromWeddingCommand.MESSAGE_USAGE));
        }

        String personIndexs = multimap.getValue(PREFIX_CONTACT).orElse("");

        if (personIndexs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_NO_CONTACTS_SPECIFIED));
        }

        Set<Index> personIndexSet = parsePersonIndexString(personIndexs);

        return new UnassignContactFromWeddingCommand(weddingIndex, personIndexSet);
    }

}
