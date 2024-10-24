package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SOCIALMEDIA;
import static seedu.address.logic.commands.CommandTestUtil.SOCIALMEDIA_CS;
import static seedu.address.logic.commands.CommandTestUtil.SOCIALMEDIA_FB;
import static seedu.address.logic.commands.CommandTestUtil.SOCIALMEDIA_IG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SocialMediaCommand;
import seedu.address.model.person.SocialMedia;

public class SocialMediaCommandParserTest {

    private SocialMediaCommandParser parser = new SocialMediaCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInputIg = "1" + SOCIALMEDIA_IG;

        SocialMediaCommand expectedCommandIg = new SocialMediaCommand("username", SocialMedia.Platform.INSTAGRAM,
                INDEX_FIRST_PERSON);

        assertParseSuccess(parser, userInputIg, expectedCommandIg);

        String userInputFb = "1" + SOCIALMEDIA_FB;

        SocialMediaCommand expectedCommandFb = new SocialMediaCommand("username", SocialMedia.Platform.FACEBOOK,
                INDEX_FIRST_PERSON);

        assertParseSuccess(parser, userInputFb, expectedCommandFb);

        String userInputCs = "1" + SOCIALMEDIA_CS;

        SocialMediaCommand expectedCommandCs = new SocialMediaCommand("username", SocialMedia.Platform.CAROUSELL,
                INDEX_FIRST_PERSON);

        assertParseSuccess(parser, userInputCs, expectedCommandCs);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE);

        // missing index
        assertParseFailure(parser, SOCIALMEDIA_IG, expectedMessage);

        // missing prefix
        assertParseFailure(parser, "1 ", expectedMessage);

        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_SOCIALMEDIA, SocialMedia.MESSAGE_CONSTRAINTS);
    }

}
