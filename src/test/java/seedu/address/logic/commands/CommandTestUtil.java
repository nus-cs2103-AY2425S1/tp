package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PawPatrol;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.OwnerNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.PetNameContainsKeywordsPredicate;
import seedu.address.testutil.EditOwnerDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditPetDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_IC_NUMBER_AMY = "S1122334F";
    public static final String VALID_IC_NUMBER_BOB = "T0433221G";

    public static final String VALID_NAME_FLUFFY = "Fluffy";
    public static final String VALID_NAME_BELLA = "Bella";
    public static final String VALID_SPECIES_FLUFFY = "Dog";
    public static final String VALID_SPECIES_BELLA = "Cat";
    public static final String VALID_BREED_FLUFFY = "Pomeranian";
    public static final String VALID_BREED_BELLA = "Tabby";
    public static final String VALID_AGE_FLUFFY = "5";
    public static final String VALID_AGE_BELLA = "6";
    public static final String VALID_SEX_FLUFFY = "M";
    public static final String VALID_SEX_BELLA = "F";
    public static final String VALID_TAG_CUTE = "cute";
    public static final String VALID_TAG_PLAYFUL = "playful";

    public static final String IC_NUMBER_DESC_AMY = " " + PREFIX_IC_NUMBER + VALID_IC_NUMBER_AMY;
    public static final String IC_NUMBER_DESC_BOB = " " + PREFIX_IC_NUMBER + VALID_IC_NUMBER_BOB;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String NAME_DESC_FLUFFY = " " + PREFIX_NAME + VALID_NAME_FLUFFY;
    public static final String NAME_DESC_BELLA = " " + PREFIX_NAME + VALID_NAME_BELLA;
    public static final String SPECIES_DESC_FLUFFY = " " + PREFIX_SPECIES + VALID_SPECIES_FLUFFY;
    public static final String SPECIES_DESC_BELLA = " " + PREFIX_SPECIES + VALID_SPECIES_BELLA;
    public static final String BREED_DESC_FLUFFY = " " + PREFIX_BREED + VALID_BREED_FLUFFY;
    public static final String BREED_DESC_BELLA = " " + PREFIX_BREED + VALID_BREED_BELLA;
    public static final String AGE_DESC_FLUFFY = " " + PREFIX_AGE + VALID_AGE_FLUFFY;
    public static final String AGE_DESC_BELLA = " " + PREFIX_AGE + VALID_AGE_BELLA;
    public static final String SEX_DESC_FLUFFY = " " + PREFIX_SEX + VALID_SEX_FLUFFY;
    public static final String SEX_DESC_BELLA = " " + PREFIX_SEX + VALID_SEX_BELLA;
    public static final String TAG_DESC_CUTE = " " + PREFIX_TAG + VALID_TAG_CUTE;
    public static final String TAG_DESC_PLAYFUL = " " + PREFIX_TAG + VALID_TAG_PLAYFUL;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_IC_NUMBER_DESC = " " + PREFIX_IC_NUMBER + "T0000001A"; // invalid ic number
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_SPECIES_DESC = " " + PREFIX_SPECIES + "911a"; // '911' not allowed for species
    public static final String INVALID_BREED_DESC = " " + PREFIX_BREED + "bob!yahoo"; // ! not allowed for breed
    public static final String INVALID_AGE_DESC = " " + PREFIX_AGE; // empty string not allowed for age
    public static final String INVALID_SEX_DESC = " " + PREFIX_SEX + "*"; // '*' not allowed in sex

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final EditOwnerCommand.EditOwnerDescriptor DESC_OWNER_AMY;
    public static final EditOwnerCommand.EditOwnerDescriptor DESC_OWNER_BOB;

    public static final EditPetCommand.EditPetDescriptor DESC_PET_FLUFFY;
    public static final EditPetCommand.EditPetDescriptor DESC_PET_BELLA;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_OWNER_AMY = new EditOwnerDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).build();
        DESC_OWNER_BOB = new EditOwnerDescriptorBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).build();
        DESC_PET_FLUFFY = new EditPetDescriptorBuilder().withName(VALID_NAME_FLUFFY).withSpecies(VALID_SPECIES_FLUFFY)
                .withBreed(VALID_BREED_FLUFFY).withAge(VALID_AGE_FLUFFY).withSex(VALID_SEX_FLUFFY).build();
        DESC_PET_BELLA = new EditPetDescriptorBuilder().withName(VALID_NAME_BELLA).withSpecies(VALID_SPECIES_BELLA)
                .withBreed(VALID_BREED_BELLA).withAge(VALID_AGE_BELLA).withSex(VALID_SEX_BELLA).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - PawPatrol, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        PawPatrol expectedPawPatrol = new PawPatrol(actualModel.getPawPatrol());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedPawPatrol, actualModel.getPawPatrol());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s PawPatrol.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the owner at the given {@code targetIndex} in the
     * {@code model}'s PawPatrol.
     */
    public static void showOwnerAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredOwnerList().size());

        Owner owner = model.getFilteredOwnerList().get(targetIndex.getZeroBased());
        final String[] splitName = owner.getName().fullName.split("\\s+");
        model.updateFilteredOwnerList(new OwnerNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredOwnerList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the pet at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPetAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPetList().size());

        Pet pet = model.getFilteredPetList().get(targetIndex.getZeroBased());
        final String[] splitName = pet.getName().name.split("\\s+");
        model.updateFilteredPetList(new PetNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPetList().size());
    }
}
