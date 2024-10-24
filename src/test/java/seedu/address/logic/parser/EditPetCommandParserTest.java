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
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CUTE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PLAYFUL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_FLUFFY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditPetCommand;
import seedu.address.logic.commands.EditPetCommand.EditPetDescriptor;
import seedu.address.model.pet.Age;
import seedu.address.model.pet.Breed;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Sex;
import seedu.address.model.pet.Species;
import seedu.address.testutil.EditPetDescriptorBuilder;

public class EditPetCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPetCommand.MESSAGE_USAGE);
    private static final String MESSAGE_ENTITY = "p";

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_BELLA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, MESSAGE_ENTITY + "1", EditPetCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, MESSAGE_ENTITY + "-5" + NAME_DESC_BELLA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, MESSAGE_ENTITY + "0" + NAME_DESC_BELLA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, MESSAGE_ENTITY + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, MESSAGE_ENTITY + "1 z/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, MESSAGE_ENTITY + "1" + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, MESSAGE_ENTITY + "1" + INVALID_SPECIES_DESC,
                Species.MESSAGE_CONSTRAINTS); // invalid species
        assertParseFailure(parser, MESSAGE_ENTITY + "1" + INVALID_BREED_DESC,
                Breed.MESSAGE_CONSTRAINTS); // invalid breed
        assertParseFailure(parser, MESSAGE_ENTITY + "1" + INVALID_AGE_DESC,
                Age.MESSAGE_CONSTRAINTS); // invalid age
        assertParseFailure(parser, MESSAGE_ENTITY + "1" + INVALID_SEX_DESC,
                Sex.MESSAGE_CONSTRAINTS); // invalid sex

        // invalid species followed by valid breed
        assertParseFailure(parser, MESSAGE_ENTITY + "1" + INVALID_SPECIES_DESC + BREED_DESC_BELLA,
                Species.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, MESSAGE_ENTITY + "1" + INVALID_NAME_DESC + INVALID_BREED_DESC
                        + VALID_AGE_BELLA + VALID_SPECIES_BELLA,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = MESSAGE_ENTITY + targetIndex.getOneBased() + SPECIES_DESC_FLUFFY
                + BREED_DESC_BELLA + AGE_DESC_BELLA + SEX_DESC_BELLA + NAME_DESC_BELLA;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_NAME_BELLA)
                .withSpecies(VALID_SPECIES_FLUFFY).withBreed(VALID_BREED_BELLA).withAge(VALID_AGE_BELLA)
                .withSex(VALID_SEX_BELLA).build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = MESSAGE_ENTITY + targetIndex.getOneBased() + SPECIES_DESC_FLUFFY + BREED_DESC_BELLA;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withSpecies(VALID_SPECIES_FLUFFY)
                .withBreed(VALID_BREED_BELLA).build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = MESSAGE_ENTITY + targetIndex.getOneBased() + NAME_DESC_BELLA;
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_NAME_BELLA).build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // species
        userInput = MESSAGE_ENTITY + targetIndex.getOneBased() + SPECIES_DESC_BELLA;
        descriptor = new EditPetDescriptorBuilder().withSpecies(VALID_SPECIES_BELLA).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // breed
        userInput = MESSAGE_ENTITY + targetIndex.getOneBased() + BREED_DESC_BELLA;
        descriptor = new EditPetDescriptorBuilder().withBreed(VALID_BREED_BELLA).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // age
        userInput = MESSAGE_ENTITY + targetIndex.getOneBased() + AGE_DESC_BELLA;
        descriptor = new EditPetDescriptorBuilder().withAge(VALID_AGE_BELLA).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // sex
        userInput = MESSAGE_ENTITY + targetIndex.getOneBased() + SEX_DESC_BELLA;
        descriptor = new EditPetDescriptorBuilder().withSex(VALID_SEX_BELLA).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = MESSAGE_ENTITY + targetIndex.getOneBased() + INVALID_SPECIES_DESC + SPECIES_DESC_FLUFFY;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SPECIES));

        // invalid followed by valid
        userInput = MESSAGE_ENTITY + targetIndex.getOneBased() + SPECIES_DESC_FLUFFY + INVALID_SPECIES_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SPECIES));

        // multiple valid fields repeated
        userInput = MESSAGE_ENTITY + targetIndex.getOneBased() + SPECIES_DESC_BELLA + AGE_DESC_BELLA + BREED_DESC_BELLA
                + SEX_DESC_BELLA + TAG_DESC_CUTE + SPECIES_DESC_BELLA + AGE_DESC_BELLA + BREED_DESC_BELLA
                + TAG_DESC_CUTE + SPECIES_DESC_FLUFFY + AGE_DESC_FLUFFY + BREED_DESC_FLUFFY + SEX_DESC_FLUFFY
                + TAG_DESC_PLAYFUL;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SPECIES, PREFIX_BREED, PREFIX_AGE, PREFIX_SEX));

        // multiple invalid values
        userInput = MESSAGE_ENTITY + targetIndex.getOneBased() + INVALID_SPECIES_DESC + INVALID_AGE_DESC
                + INVALID_BREED_DESC + INVALID_SEX_DESC + INVALID_SPECIES_DESC + INVALID_AGE_DESC + INVALID_BREED_DESC
                + INVALID_SEX_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SPECIES, PREFIX_BREED, PREFIX_AGE, PREFIX_SEX));
    }
}
