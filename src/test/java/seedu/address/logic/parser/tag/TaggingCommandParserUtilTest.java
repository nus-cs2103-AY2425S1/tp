package seedu.address.logic.parser.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.TaggingCommandParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;

public class TaggingCommandParserUtilTest {

    @Test
    public void parseIndexAndTags_validInput_returnsPair() throws Exception {
        String userInput = "1 " + PREFIX_TAG + "friend " + PREFIX_TAG + "colleague";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TAG);

        Pair<Index, Set<Tag>> result = TaggingCommandParserUtil.parseIndexAndTags(argMultimap, "dummy usage message");

        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag(new TagName("friend")));
        expectedTags.add(new Tag(new TagName("colleague")));

        assertEquals(Index.fromOneBased(1), result.getKey());
        assertEquals(expectedTags, result.getValue());
    }

    @Test
    public void parseIndexAndTags_invalidIndexType_throwsParseException() {
        String userInput = "a " + PREFIX_TAG + "friend";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TAG);

        ParseException thrown = assertThrows(ParseException.class, () ->
                TaggingCommandParserUtil.parseIndexAndTags(argMultimap, "dummy usage message")
        );
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "dummy usage message"), thrown.getMessage());
    }

    @Test
    public void parseIndexAndTags_missingTags_throwsParseException() {
        String userInput = "1";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TAG);

        ParseException thrown = assertThrows(ParseException.class, () ->
                TaggingCommandParserUtil.parseIndexAndTags(argMultimap, "dummy usage message")
        );
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                "dummy usage message"), thrown.getMessage());
    }

    @Test
    public void parseIndexAndTags_invalidTagNames_throwsParseException() {
        String userInput = "1 " + PREFIX_TAG + "invalidTag_";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TAG);

        ParseException thrown = assertThrows(ParseException.class, () ->
                TaggingCommandParserUtil.parseIndexAndTags(argMultimap, "dummy usage message")
        );

        assertEquals(TagName.MESSAGE_CONSTRAINTS, thrown.getMessage());
    }

}
