package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

public class TagCommandParser {

    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SUBJECT, PREFIX_LEVEL);

        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_SUBJECT, PREFIX_LEVEL);

        Name personToTag;
        if (argMultiMap.getValue(PREFIX_NAME).isPresent()) {
            personToTag = ParserUtil.parseName(argMultiMap.getValue(PREFIX_NAME).get());
        } else {
            throw new ParseException("Name not given.");
        }

        EditCommand.EditPersonDescriptor editPersonTags = new EditCommand.EditPersonDescriptor();

        if (argMultiMap.getValue(PREFIX_SUBJECT).isPresent()) {
            editPersonTags.setSubjects(
                    ParserUtil.parseSubjects(
                            argMultiMap.getAllValues(PREFIX_SUBJECT)));
        }

        if (argMultiMap.getValue(PREFIX_LEVEL).isPresent()) {
            editPersonTags.setLevel(
                    ParserUtil.parseSchoolLevel(
                            argMultiMap.getValue(PREFIX_LEVEL).get()
                    ));
        }

        if (argMultiMap.getValue(PREFIX_SUBJECT).isEmpty()) {
            if (argMultiMap.getValue(PREFIX_LEVEL).isEmpty()) {
                throw new ParseException("Provide either a subject or a school level to tag with");
            }
        }

        return new TagCommand(personToTag, editPersonTags);

    }
}
