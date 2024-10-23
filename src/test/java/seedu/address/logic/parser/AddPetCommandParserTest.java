/*
package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.BREED_DESC_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.BREED_DESC_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BREED_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPECIES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPets.BELLA;
import static seedu.address.testutil.TypicalPets.FLUFFY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddPetCommand;
import seedu.address.model.pet.Age;
import seedu.address.model.pet.Breed;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.Sex;
import seedu.address.model.pet.Species;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PetBuilder;

public class AddPetCommandParserTest {
    private AddPetCommandParser parser = new AddPetCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Pet expectedPet = new PetBuilder(FLUFFY).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_FLUFFY + SPECIES_DESC_FLUFFY
                + BREED_DESC_FLUFFY + AGE_DESC_FLUFFY + SEX_DESC_FLUFFY + TAG_DESC_FRIEND,
                new AddPetCommand(expectedPet));

        // multiple tags - all accepted
        Pet expectedPetMultipleTags = new PetBuilder(FLUFFY).withTags(VALID_TAG_FRIEND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_FLUFFY + SPECIES_DESC_FLUFFY + BREED_DESC_FLUFFY + AGE_DESC_FLUFFY + SEX_DESC_FLUFFY
                        + TAG_DESC_FRIEND,
                new AddPetCommand(expectedPetMultipleTags));
    }


    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPetString = NAME_DESC_FLUFFY + SPECIES_DESC_FLUFFY + BREED_DESC_FLUFFY
                + AGE_DESC_FLUFFY + SEX_DESC_FLUFFY + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_BELLA + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple species
        assertParseFailure(parser, SPECIES_DESC_BELLA + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SPECIES));

        // multiple breeds
        assertParseFailure(parser, BREED_DESC_BELLA + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BREED));

        // multiple ages
        assertParseFailure(parser, AGE_DESC_BELLA + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_AGE));

        // multiple sexes
        assertParseFailure(parser, SEX_DESC_BELLA + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_AGE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPetString + NAME_DESC_BELLA + SPECIES_DESC_BELLA + BREED_DESC_BELLA
                        + AGE_DESC_BELLA + SEX_DESC_BELLA + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_SPECIES, PREFIX_BREED, PREFIX_AGE,
                        PREFIX_SEX));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid species
        assertParseFailure(parser, INVALID_SPECIES_DESC + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SPECIES));

        // invalid breed
        assertParseFailure(parser, INVALID_BREED_DESC + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BREED));

        // invalid age
        assertParseFailure(parser, INVALID_AGE_DESC + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_AGE));

        // invalid sex
        assertParseFailure(parser, INVALID_SEX_DESC + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPetString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid species
        assertParseFailure(parser, validExpectedPetString + INVALID_SPECIES_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SPECIES));

        // invalid breed
        assertParseFailure(parser, validExpectedPetString + INVALID_BREED_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BREED));

        // invalid age
        assertParseFailure(parser, validExpectedPetString + INVALID_AGE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_AGE));

        // invalid sex
        assertParseFailure(parser, validExpectedPetString + INVALID_SEX_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Pet expectedPet = new PetBuilder(BELLA).withTags().build();
        assertParseSuccess(parser, NAME_DESC_BELLA + SPECIES_DESC_BELLA + BREED_DESC_BELLA + AGE_DESC_BELLA,
                new AddPetCommand(expectedPet));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPetCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser,
                VALID_NAME_FLUFFY + SPECIES_DESC_FLUFFY + BREED_DESC_FLUFFY + AGE_DESC_FLUFFY,
                expectedMessage);

        // missing SPECIES prefix
        assertParseFailure(parser,
                NAME_DESC_FLUFFY + VALID_SPECIES_FLUFFY + BREED_DESC_FLUFFY + AGE_DESC_FLUFFY,
                expectedMessage);

        // missing BREED prefix
        assertParseFailure(parser,
                NAME_DESC_FLUFFY + SPECIES_DESC_FLUFFY + VALID_BREED_FLUFFY + AGE_DESC_FLUFFY,
                expectedMessage);

        // missing AGE prefix
        assertParseFailure(parser,
                NAME_DESC_FLUFFY + SPECIES_DESC_FLUFFY + BREED_DESC_FLUFFY + VALID_AGE_FLUFFY,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                VALID_NAME_FLUFFY + VALID_SPECIES_FLUFFY + VALID_BREED_FLUFFY + VALID_AGE_FLUFFY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser,
                INVALID_NAME_DESC + SPECIES_DESC_FLUFFY + BREED_DESC_FLUFFY + AGE_DESC_FLUFFY
                + SEX_DESC_FLUFFY + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid species
        assertParseFailure(parser,
                NAME_DESC_FLUFFY + INVALID_SPECIES_DESC + BREED_DESC_FLUFFY + AGE_DESC_FLUFFY
                + SEX_DESC_FLUFFY + TAG_DESC_FRIEND, Species.MESSAGE_CONSTRAINTS);

        // invalid breed
        assertParseFailure(parser,
                NAME_DESC_FLUFFY + SPECIES_DESC_FLUFFY + INVALID_BREED_DESC + AGE_DESC_FLUFFY
                + SEX_DESC_FLUFFY + TAG_DESC_FRIEND, Breed.MESSAGE_CONSTRAINTS);

        // invalid age
        assertParseFailure(parser,
                NAME_DESC_FLUFFY + SPECIES_DESC_FLUFFY + BREED_DESC_FLUFFY + INVALID_AGE_DESC
                + SEX_DESC_FLUFFY + TAG_DESC_FRIEND, Age.MESSAGE_CONSTRAINTS);

        // invalid sex
        assertParseFailure(parser,
                NAME_DESC_FLUFFY + SPECIES_DESC_FLUFFY + BREED_DESC_FLUFFY + INVALID_AGE_DESC
                + INVALID_SEX_DESC + TAG_DESC_FRIEND, Sex.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser,
                NAME_DESC_FLUFFY + SPECIES_DESC_FLUFFY + BREED_DESC_FLUFFY + AGE_DESC_FLUFFY
                + SEX_DESC_FLUFFY + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                INVALID_NAME_DESC + SPECIES_DESC_FLUFFY + BREED_DESC_FLUFFY + INVALID_AGE_DESC
                + SEX_DESC_FLUFFY, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + NAME_DESC_FLUFFY + SPECIES_DESC_FLUFFY + BREED_DESC_FLUFFY
                        + AGE_DESC_FLUFFY + SEX_DESC_FLUFFY + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPetCommand.MESSAGE_USAGE));
    }
}
*/
