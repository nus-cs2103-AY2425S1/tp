package seedu.address.logic.parser;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.util.FieldQuery;
import seedu.address.logic.commands.util.SearchField;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonSearchPredicate;
import seedu.address.model.tag.Tag;
/**
 * Parses input arguments and creates a new FindCommand object
 */

public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_LOCATION,
                        PREFIX_REMARK, PREFIX_TAG);
        if (!argMultimap.getPreamble().isEmpty()) {
            String str = argMultimap.getPreamble();
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_LOCATION,
                PREFIX_REMARK);
        String name = argMultimap.getValue(PREFIX_NAME).orElse("");
        String phone = argMultimap.getValue(PREFIX_PHONE).orElse("");
        String email = argMultimap.getValue(PREFIX_EMAIL).orElse("");
        String location = argMultimap.getValue(PREFIX_LOCATION).orElse("");
        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        List<FieldQuery> tagQuery = tagList.stream().map(x -> new FieldQuery(SearchField.TAG, x.getTagName())).toList();
        FieldQuery[] queries = new FieldQuery[] {
            new FieldQuery(SearchField.NAME, name),
            new FieldQuery(SearchField.PHONE, phone),
            new FieldQuery(SearchField.EMAIL, email),
            new FieldQuery(SearchField.LOCATION, location),
            new FieldQuery(SearchField.REMARK, remark)
        };
        List<FieldQuery> fieldQueryList = new ArrayList<>(Arrays.asList(queries));
        fieldQueryList.addAll(tagQuery);
        return new FindCommand(new PersonSearchPredicate(fieldQueryList));
    }
}
