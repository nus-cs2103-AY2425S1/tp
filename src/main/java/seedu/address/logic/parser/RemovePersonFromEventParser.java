package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.commands.RemovePersonFromEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;

public class RemovePersonFromEventParser implements Parser<RemovePersonFromEventCommand> {

    public RemovePersonFromEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_EVENT_INDEX, CliSyntax.PREFIX_PERSON_INDEX);

        argMultimap.verifyNoDuplicatePrefixesFor(CliSyntax.PREFIX_EVENT_INDEX, CliSyntax.PREFIX_PERSON_INDEX);

        Index personIndex;
        Index eventIndex;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_PERSON_INDEX).get());
            eventIndex = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_EVENT_INDEX).get());

        } catch (ParseException pe) {
            throw new ParseException(String.format(RemovePersonFromEventCommand.MESSAGE_USAGE), pe);
        }


        return new RemovePersonFromEventCommand(eventIndex, personIndex);

    }




}
