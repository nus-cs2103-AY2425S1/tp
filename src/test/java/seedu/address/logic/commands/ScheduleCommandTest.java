package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.ScheduleBuilder;
import seedu.address.testutil.ScheduleDescriptorBuilder;

public class ScheduleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Schedule editedSchedule = new ScheduleBuilder()
                .withScheduleName(VALID_SCHEDULE_NAME_BOB)
                .withScheduleDate(VALID_SCHEDULE_DATE_BOB)
                .withScheduleTime(VALID_SCHEDULE_TIME_BOB)
                .build();
        ScheduleCommand.ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder(editedSchedule).build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_MAKE_SCHEDULE_SUCCESS,
                model.getFilteredPersonList().get(0).getName(), editedSchedule);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withScheduleName(VALID_SCHEDULE_NAME_BOB)
                .withScheduleDate(VALID_SCHEDULE_DATE_BOB)
                .withScheduleTime(VALID_SCHEDULE_TIME_BOB).build();
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index index = INDEX_FIRST_PERSON;
        Person person = model.getFilteredPersonList().get(index.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(person);
        Person editedPerson = personInList.withScheduleName(VALID_SCHEDULE_NAME_BOB).build();

        ScheduleCommand.ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder()
                .withScheduleName(VALID_SCHEDULE_NAME_BOB).build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(index, descriptor);

        String expectedMessage = String.format(
                ScheduleCommand.MESSAGE_MAKE_SCHEDULE_SUCCESS,
                editedPerson.getName(),
                editedPerson.getSchedule());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(person, editedPerson);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        ScheduleCommand scheduleCommand = new ScheduleCommand(
                INDEX_FIRST_PERSON, new ScheduleCommand.ScheduleDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(
                ScheduleCommand.MESSAGE_CLEAR_SCHEDULE_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person expectedPerson = new PersonBuilder(editedPerson)
                .withScheduleName("").withScheduleDate("").withScheduleTime("").build();
        expectedModel.setPerson(expectedModel.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()), expectedPerson);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_scheduleRemainsUnchanged_failure() {
        // all fields the same
        ScheduleCommand.ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder()
                .withScheduleName(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getSchedule().scheduleName)
                .withScheduleDate(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getSchedule().dateString)
                .withScheduleTime(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getSchedule().timeString)
                .build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = ScheduleCommand.MESSAGE_SCHEDULE_UNCHANGED;
        assertCommandFailure(scheduleCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ScheduleCommand.ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder()
                .withScheduleDate(VALID_SCHEDULE_DATE_BOB).build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(scheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ScheduleCommand.ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder()
                .withScheduleDate(VALID_SCHEDULE_DATE_BOB).build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(scheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
