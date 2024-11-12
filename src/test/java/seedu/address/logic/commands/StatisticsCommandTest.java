package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.StatisticsCommand.appointmentsSoon;
import static seedu.address.logic.commands.StatisticsCommand.eligibleForScheme;
import static seedu.address.logic.commands.StatisticsCommand.highPriorityPeople;
import static seedu.address.logic.commands.StatisticsCommand.lowPriorityPeople;
import static seedu.address.logic.commands.StatisticsCommand.mediumPriorityPeople;
import static seedu.address.logic.commands.StatisticsCommand.nbOfPeople;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for StatisticsCommand.
 */
public class StatisticsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new ArrayList<>(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new ArrayList<>(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsAllStats() {
        List<Person> personList = expectedModel.getFilteredPersonList();
        List<Appointment> appointmentList = expectedModel.getFilteredAppointmentList();

        String expectedMessage = String.format(StatisticsCommand.MESSAGE_DISPLAY_STATISTICS_SUCCESS,
                nbOfPeople(personList) + "\n"
                + highPriorityPeople(personList) + "\n"
                + mediumPriorityPeople(personList) + "\n"
                + lowPriorityPeople(personList) + "\n"
                + appointmentsSoon(personList, appointmentList) + "\n"
                + eligibleForScheme(personList));

        assertCommandSuccess(new StatisticsCommand(), model,
                expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showFilteredListStats() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        List<Person> personList = expectedModel.getFilteredPersonList();
        List<Appointment> appointmentList = expectedModel.getFilteredAppointmentList();

        String expectedMessage = String.format(StatisticsCommand.MESSAGE_DISPLAY_STATISTICS_SUCCESS,
                nbOfPeople(personList) + "\n"
                + highPriorityPeople(personList) + "\n"
                + mediumPriorityPeople(personList) + "\n"
                + lowPriorityPeople(personList) + "\n"
                + appointmentsSoon(personList, appointmentList) + "\n"
                + eligibleForScheme(personList));

        assertCommandSuccess(new StatisticsCommand(), model,
               expectedMessage, expectedModel);
    }
}
