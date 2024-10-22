package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignContactToWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class AssignContactToWeddingCommandParser implements Parser<AssignContactToWeddingCommand> {

    @Override
    public AssignContactToWeddingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap multimap = ArgumentTokenizer.tokenize(args, PREFIX_ASSIGN_CONTACT);

        //Reformatting error message for duplicate tags
        try {
            multimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGN_CONTACT);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Please only include one prefix c/ !"));
        }

        // If t/ prefix is missing
        if (multimap.getValue(PREFIX_ASSIGN_CONTACT).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignContactToWeddingCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(multimap.getPreamble());
        String personIndexs = multimap.getValue(PREFIX_ASSIGN_CONTACT).orElse("");

        Set<Index> personIndexSet;
        try {
            personIndexSet = Arrays.stream(personIndexs.split("\\s+"))
                    .map(Integer::parseInt)
                    .map(i -> Index.fromOneBased(i))
                    .collect(Collectors.toSet()); // Collect into a Set of Index objects

        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, e.getMessage()));
        }

        // If t/ prefix is followed by empty string
        if (personIndexSet.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "No contacts specified!"));
        }

        return new AssignContactToWeddingCommand(index, personIndexSet);
    }

}
