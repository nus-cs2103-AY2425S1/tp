package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddAllergyCommand;
import seedu.address.model.person.Nric;
import seedu.address.model.tag.Tag;

public class AddAllergyCommandParserTest {

    private final AddAllergyCommandParser parser = new AddAllergyCommandParser();

    @Test
    public void parse_allFieldsValid_success() {
        Nric aliceNric = ALICE.getNric();
        String allergy = "Peanut allergy";
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(allergy));
        AddAllergyCommand expectedCommand = new AddAllergyCommand(aliceNric, tags);
        assertParseSuccess(parser, " " + PREFIX_NRIC + aliceNric.value
                + " " + PREFIX_TAG + allergy, expectedCommand);
    }

    @Test
    public void parse_missingFields_failure() {
        Nric aliceNric = ALICE.getNric();
        String allergy = "Peanut allergy";
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(allergy));
        AddAllergyCommand expectedCommand = new AddAllergyCommand(aliceNric, tags);
        assertParseFailure(parser, " " + PREFIX_TAG + allergy,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddAllergyCommand.MESSAGE_USAGE));
    }
}
