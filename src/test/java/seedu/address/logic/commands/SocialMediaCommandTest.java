package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.SocialMedia;
import seedu.address.testutil.PersonBuilder;

public class SocialMediaCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newSocialMedia_success() {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(person).withSocialMedia("[ig-aliceey]").build();

        String handle = "aliceey";
        SocialMediaCommand socialMediaCommand = new SocialMediaCommand(handle, SocialMedia.Platform.INSTAGRAM,
                INDEX_FIRST_PERSON);
        String expectedMessage = String.format(SocialMediaCommand.MESSAGE_SOCIAL_MEDIA_SUCCESS, person.getName(),
                "[ig-aliceey]");
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(person, editedPerson);
        assertCommandSuccess(socialMediaCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_socialMedia_failure() {
        String handle = "my$$$username";

        SocialMediaCommand socialMediaCommand = new SocialMediaCommand(handle, SocialMedia.Platform.INSTAGRAM,
                INDEX_FIRST_PERSON);

        String expectedMessage = SocialMediaCommand.MESSAGE_INVALID_HANDLE;

        assertCommandFailure(socialMediaCommand, model, expectedMessage);

        String handle1 = "username";
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SocialMediaCommand socialMediaCommand1 = new SocialMediaCommand(handle1, SocialMedia.Platform.INSTAGRAM,
                outOfBoundIndex);

        String expectedMessage1 = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(socialMediaCommand1, model, expectedMessage1);
    }

    @Test
    public void equals() {
        final SocialMediaCommand standardCommand = new SocialMediaCommand("myusername",
                SocialMedia.Platform.INSTAGRAM, INDEX_FIRST_PERSON);
        // same values -> returns true
        SocialMediaCommand commandWithSameValues = new SocialMediaCommand("myusername",
                SocialMedia.Platform.INSTAGRAM, INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different handle -> returns false
        assertFalse(standardCommand.equals(new SocialMediaCommand("myuser", SocialMedia.Platform.INSTAGRAM,
                INDEX_FIRST_PERSON)));

        // different platform -> returns false
        assertFalse(standardCommand.equals(new SocialMediaCommand("myusername", SocialMedia.Platform.FACEBOOK,
                INDEX_FIRST_PERSON)));
    }
}
