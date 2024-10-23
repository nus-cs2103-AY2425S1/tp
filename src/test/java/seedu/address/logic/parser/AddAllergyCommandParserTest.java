package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddAllergyCommand;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Nric;

public class AddAllergyCommandParserTest {

    private final AddAllergyCommandParser parser = new AddAllergyCommandParser();

    @Test
    public void parse_allFieldsValid_success() {
        Nric aliceNric = ALICE.getNric();
        String allergy = "Peanut allergy";
        Set<Allergy> allergies = new HashSet<>();
        allergies.add(new Allergy(allergy));
        AddAllergyCommand expectedCommand = new AddAllergyCommand(aliceNric, allergies);
        assertParseSuccess(parser, " " + PREFIX_NRIC + aliceNric.value
                + " " + PREFIX_ALLERGY + allergy, expectedCommand);
    }

    @Test
    public void parse_missingFields_failure() {
        Nric aliceNric = ALICE.getNric();
        String allergy = "Peanut allergy";
        Set<Allergy> allergies = new HashSet<>();
        allergies.add(new Allergy(allergy));
        AddAllergyCommand expectedCommand = new AddAllergyCommand(aliceNric, allergies);
        assertParseFailure(parser, " " + PREFIX_ALLERGY + allergy,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddAllergyCommand.MESSAGE_USAGE));
    }
}
