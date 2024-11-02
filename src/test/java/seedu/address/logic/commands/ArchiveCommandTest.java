package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DUPLICATED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LIST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LIST;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_LIST;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validSingleIndexUnfilteredPersonList_success() {
        Person personToArchive = model.getFilteredPersonList().get(INDEX_THIRD.getZeroBased());
        Person archivedPerson = new PersonBuilder().withName(CARL.getName().toString())
                .withPhone(CARL.getPhone().value)
                .withAddress(CARL.getAddress().value).withEmail(CARL.getEmail().value)
                .withArchive("true").build();

        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_THIRD_LIST);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVED_PERSON_SUCCESS,
                "\n" + Messages.format(personToArchive));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deletePerson(personToArchive);
        expectedModel.addPerson(archivedPerson);

        AddressBookParser.setInspect(false);
        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_outOfBoundIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> outOfBoundIndexList = new ArrayList<>();
        outOfBoundIndexList.add(outOfBoundIndex);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndexList);
        String expectedErrorMessage = String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                outOfBoundIndex.getOneBased());

        assertCommandFailure(archiveCommand, model, expectedErrorMessage);
    }

    @Test
    public void execute_duplicateIndexUnfilteredList_throwsCommandException() {
        List<Index> duplicatedList = new ArrayList<>();
        duplicatedList.add(INDEX_FIRST);
        duplicatedList.add(INDEX_FIRST);
        ArchiveCommand archiveCommand = new ArchiveCommand(duplicatedList);

        String expectedErrorMessage = String.format(MESSAGE_INVALID_DUPLICATED_INDEX,
                INDEX_FIRST.getOneBased());

        assertCommandFailure(archiveCommand, model, expectedErrorMessage);
    }

    //
    //@Test
    //public void execute_validSingleIndexUnfilteredDeliveryList_success() {
    //    Person inspectedPerson = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
    //    Person inspectedPersonWithArchivedDelivery = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
    //
    //    InspectWindow.getInspectedPerson();
    //    Delivery deliveryToArchive = inspectedPerson.getDeliveryList().asUnmodifiableObservableList().get(0);
    //    Delivery archivedDelivery = new Delivery(
    //            new DeliveryId("2"),
    //            new HashSet<>(Arrays.asList(new ItemName("Apples"))),
    //            new Address("311, Clementi Ave 2, #01-25, S120300"),
    //            new Cost("$300"),
    //            new Date("2010-05-05"), new Time("09:05:12"),
    //            new Eta("1990-04-04"),
    //            new Status("delivered"),
    //            new HashSet<>(Arrays.asList(new Tag("Early delivery"))),
    //            new Archive("true"));
    //
    //    ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_LIST);
    //
    //    String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVED_DELIVERY_SUCCESS,
    //            "\n" + Messages.format(deliveryToArchive));
    //
    //    Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //    expectedModel.setPerson(inspectedPerson, inspectedPersonWithArchivedDelivery);
    //
    //    inspectedPersonWithArchivedDelivery.deleteDelivery(INDEX_FIRST);
    //    inspectedPersonWithArchivedDelivery.addDelivery(archivedDelivery);
    //
    //    AddressBookParser.setInspect(true);
    //    assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    //}

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(INDEX_FIRST_LIST);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(INDEX_SECOND_LIST);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(INDEX_FIRST_LIST);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        List<Index> targetIndexList = new ArrayList<>();
        targetIndexList.add(targetIndex);
        ArchiveCommand archiveCommand = new ArchiveCommand(targetIndexList);
        String expected = ArchiveCommand.class.getCanonicalName() + "{targetIndex=" + targetIndexList + "}";
        assertEquals(expected, archiveCommand.toString());
    }
}
