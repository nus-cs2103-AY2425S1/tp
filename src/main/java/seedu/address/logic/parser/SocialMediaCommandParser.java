package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IG;
import static seedu.address.model.person.SocialMedia.Platform.CAROUSELL;
import static seedu.address.model.person.SocialMedia.Platform.FACEBOOK;
import static seedu.address.model.person.SocialMedia.Platform.INSTAGRAM;
import static seedu.address.model.person.SocialMedia.isValidHandleName;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SocialMediaCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SocialMedia;

/**
 * Parses input arguments and creates a new SocialMediaCommand object.
 */
public class SocialMediaCommandParser implements Parser<SocialMediaCommand> {

    @Override
    public SocialMediaCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IG, PREFIX_FB, PREFIX_CS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SocialMediaCommand.MESSAGE_USAGE), pe);
        }

        String handle = " ";
        SocialMedia.Platform platform = null;
        if (argMultimap.getValue(PREFIX_IG).isPresent()) {
            handle = argMultimap.getValue(PREFIX_IG).get();
            platform = INSTAGRAM;
        }
        if (argMultimap.getValue(PREFIX_FB).isPresent()) {
            handle = argMultimap.getValue(PREFIX_FB).get();
            platform = FACEBOOK;
        }
        if (argMultimap.getValue(PREFIX_CS).isPresent()) {
            handle = argMultimap.getValue(PREFIX_CS).get();
            platform = CAROUSELL;
        } else if (!argMultimap.getValue(PREFIX_IG).isPresent() && !argMultimap.getValue(PREFIX_FB).isPresent()
                && !argMultimap.getValue(PREFIX_CS).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE));
        }
        if (!isValidHandleName(handle)) {
            throw new ParseException(SocialMedia.MESSAGE_CONSTRAINTS);
        }
        return new SocialMediaCommand(handle, platform, index);
    }
}
