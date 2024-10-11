package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.Set;

public class TagCommandParserTest {
    @Test
    public void parse_oneTag_success() {
        TagCommandParser parser = new TagCommandParser();
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + "t/testTag1";
        Set<Tag> addedTags = SampleDataUtil.getTagSet("testTag1");
        TagCommand expectedCommand = new TagCommand(INDEX_FIRST_PERSON, addedTags);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
