package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLLEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddGameCommand;
import seedu.address.logic.commands.AddGameCommand.GameDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a new AddGameCommand object.
 */
public class AddGameCommandParser implements Parser<AddGameCommand> {

    @Override
    public AddGameCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GAME, PREFIX_USERNAME,
                        PREFIX_SKILLLEVEL, PREFIX_ROLE);
        Index index;
        String gameName;
        if (!argMultimap.getValue(PREFIX_GAME).isPresent()) {
            throw new ParseException("Please specify a game!");
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            gameName = argMultimap.getValue(PREFIX_GAME).get();
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGameCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_GAME, PREFIX_USERNAME, PREFIX_SKILLLEVEL, PREFIX_ROLE);

        GameDescriptor addGameDescriptor = new GameDescriptor();

        if (argMultimap.getValue(PREFIX_USERNAME).isPresent()) {
            addGameDescriptor.setUsername(ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get()));
        }
        if (argMultimap.getValue(PREFIX_SKILLLEVEL).isPresent()) {
            addGameDescriptor.setSkillLevel(ParserUtil.parseSkillLevel(argMultimap.getValue(PREFIX_SKILLLEVEL).get()));
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            addGameDescriptor.setRole(ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()));
        }


        return new AddGameCommand(index, gameName, addGameDescriptor);
    }
}
