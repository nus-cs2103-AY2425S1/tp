/**package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.concert.Concert;
import seedu.address.model.person.Person;
import seedu.address.testutil.ConcertBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class LinkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexSpecifiedUnfilteredList_success() {
        Person linkedPerson = new PersonBuilder().build();
        Concert concert = new ConcertBuilder().build();
        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CONCERT);

        String expectedMessage = String.format(LinkCommand.MESSAGE_LINK_PERSON_SUCCESS, Messages.format(linkedPerson),
                Messages.format(concert));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.linkPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }
}*/
