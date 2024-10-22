package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LINK_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LinkCommand object
 */
public class LinkCommandParser implements Parser<LinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LinkCommand
     * and returns a LinkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LinkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TO);
        String ownerStr = argMultimap.getPreamble();

        if (
            !argMultimap.getValue(PREFIX_TO).isPresent()
                || argMultimap.getPreamble().isEmpty()
        ) {
            throw new ParseException(
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE)
            );
        }

        Pair ownerPair = ParserUtil.parseIndexAndType(ownerStr);
        if (!ownerPair.second().equals("o")) {
            throw new ParseException(
              String.format(MESSAGE_INVALID_LINK_COMMAND, LinkCommand.MESSAGE_USAGE)
            );
        }

        Set<Index> petIndexSet = new HashSet<>();
        List<String> petArgs = argMultimap.getAllValues(PREFIX_TO);
        for (String petArg : petArgs) {
            Pair petPair = ParserUtil.parseIndexAndType(petArg);
            if (!petPair.second().equals("p")) {
                throw new ParseException(
                  String.format(MESSAGE_INVALID_LINK_COMMAND, LinkCommand.MESSAGE_USAGE)
                );
            }
            petIndexSet.add((Index) petPair.first());
        }
        return new LinkCommand(ownerPair.first(), petIndexSet);
    }
}
