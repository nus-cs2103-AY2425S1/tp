package seedu.address.logic.commands;


import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.FRIEND_TAG;
import static seedu.address.testutil.TypicalPersons.OWES_MONEY_TAG;
import static seedu.address.testutil.TypicalPersons.getTypicalCampusConnect;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class DeleteTagCommandTest {
    private static final Model model = new ModelManager(getTypicalCampusConnect(), new UserPrefs());
    private static final String TEST_EMAIL = "test@test";
    private static final String TEST_PHONE = "84209817";
    private static final String TEST_USER = "test user";
    @Test
    public void execute_validIndexPersonList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person p = new PersonBuilder(firstPerson).withName(TEST_USER).withEmail(TEST_EMAIL)
                .withPhone(TEST_PHONE).withTags(FRIEND_TAG, OWES_MONEY_TAG).build();
        model.setPerson(firstPerson, p);

        Person expectedPerson = new PersonBuilder(firstPerson).withName(TEST_USER).withEmail(TEST_EMAIL)
                .withPhone(TEST_PHONE).withTags(OWES_MONEY_TAG).build();

        Model expectedModel = new ModelManager(getTypicalCampusConnect(), new UserPrefs());
        expectedModel.setPerson(firstPerson, expectedPerson);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, new Tag(FRIEND_TAG));
        String expectedMessage = String.format(Messages.MESSAGE_DELETE_TAG_SUCCESS,
                new Tag(FRIEND_TAG), p.getName());
        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

}
