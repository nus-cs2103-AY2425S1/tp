package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ECNAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ECNAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ECPHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ECPHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ECRS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ECRS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ECNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ECPHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECPHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECRS_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_RELATIONSHIP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEmergencyContactCommand;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relationship;
import seedu.address.testutil.PersonBuilder;

public class AddEmergencyContactCommandParserTest {

    private static final String VALID_DESC = ECNAME_DESC_AMY + ECPHONE_DESC_AMY + ECRS_DESC_AMY;


    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEmergencyContactCommand.MESSAGE_USAGE);

    private AddEmergencyContactCommandParser parser = new AddEmergencyContactCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ECNAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "a "
                + VALID_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ECNAME_DESC
                + ECPHONE_DESC_AMY + ECRS_DESC_AMY, Name.MESSAGE_CONSTRAINTS); // invalid name
        // invalid phone
        assertParseFailure(parser, "1" + ECNAME_DESC_AMY + INVALID_ECPHONE_DESC + ECRS_DESC_AMY,
                Phone.MESSAGE_CONSTRAINTS);
        // invalid relationship
        assertParseFailure(parser, "1" + ECNAME_DESC_AMY + ECPHONE_DESC_AMY
                        + " " + PREFIX_EMERGENCY_CONTACT_RELATIONSHIP + "Alien",
                Relationship.RELATIONSHIP_TYPE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_ECNAME_DESC + INVALID_ECPHONE_DESC + ECRS_DESC_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Person samplePerson = new PersonBuilder(BOB).build();
        Set<EmergencyContact> emergencyContactSet =
                new LinkedHashSet<>(samplePerson.getEmergencyContacts());
        EmergencyContact emergencyContactToAdd = new EmergencyContact(new Name(VALID_ECNAME_BOB),
                new Phone(VALID_ECPHONE_BOB), new Relationship(VALID_ECRS_BOB));
        emergencyContactSet.add(emergencyContactToAdd);
        Person expectedPerson = new Person(samplePerson.getName(), samplePerson.getPhone(), samplePerson.getEmail(),
                samplePerson.getAddress(), emergencyContactSet, samplePerson.getDoctor(), samplePerson.getTags());

        // whitespace only preamble
        assertParseSuccess(parser, "1 " + ECNAME_DESC_BOB + ECPHONE_DESC_BOB
                        + ECRS_DESC_BOB, new AddEmergencyContactCommand(INDEX_FIRST_PERSON, emergencyContactToAdd));
    }
}
