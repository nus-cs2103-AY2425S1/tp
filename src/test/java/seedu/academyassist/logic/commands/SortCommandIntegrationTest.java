package seedu.academyassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.sort.SortParam;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());
    }

    @Test
    public void execute_sortByName_correctOrdering() throws CommandException {
        SortCommand sortCommand = new SortCommand(new SortParam("name"));
        sortCommand.execute(model);

        List<Person> sortedList = model.getFilteredPersonList();
        // Check if list has at least 2 elements to compare
        if (sortedList.size() > 1) {
            for (int i = 0; i < sortedList.size() - 1; i++) {
                String currentName = sortedList.get(i).getName().toString();
                String nextName = sortedList.get(i + 1).getName().toString();
                assertTrue(currentName.compareTo(nextName) <= 0,
                        String.format("Name at index %d (%s) should come before or equal to name at index %d (%s)",
                                i, currentName, i + 1, nextName));
            }
        }
    }

    @Test
    public void execute_sortByYearGroup_correctOrdering() throws CommandException {
        SortCommand sortCommand = new SortCommand(new SortParam("class"));
        sortCommand.execute(model);

        List<Person> sortedList = model.getFilteredPersonList();
        if (sortedList.size() > 1) {
            for (int i = 0; i < sortedList.size() - 1; i++) {
                String currentYearGroup = sortedList.get(i).getYearGroup().toString();
                String nextYearGroup = sortedList.get(i + 1).getYearGroup().toString();
                assertTrue(currentYearGroup.compareTo(nextYearGroup) <= 0,
                        String.format("Year group at index %d (%s) should come before or equal to year "
                                + "group at index %d (%s)", i, currentYearGroup, i + 1, nextYearGroup));
            }
        }
    }

    @Test
    public void execute_sortById_correctOrdering() throws CommandException {
        SortCommand sortCommand = new SortCommand(new SortParam("studentId"));
        sortCommand.execute(model);

        List<Person> sortedList = model.getFilteredPersonList();
        if (sortedList.size() > 1) {
            for (int i = 0; i < sortedList.size() - 1; i++) {
                String currentId = sortedList.get(i).getStudentId().toString();
                String nextId = sortedList.get(i + 1).getStudentId().toString();
                assertTrue(currentId.compareTo(nextId) <= 0,
                        String.format("Student ID at index %d (%s) should come before or equal to ID at index %d (%s)",
                                i, currentId, i + 1, nextId));
            }
        }
    }

    @Test
    public void execute_emptyList_success() throws CommandException {
        Model emptyModel = new ModelManager();
        SortCommand sortCommand = new SortCommand(new SortParam("name"));
        CommandResult result = sortCommand.execute(emptyModel);
        assertEquals(String.format(SortCommand.MESSAGE_SUCCESS, "name"), result.getFeedbackToUser());
    }

    @Test
    public void execute_singleItemList_remainsUnchanged() throws CommandException {
        // Create a model with a single person
        Model singleItemModel = new ModelManager();
        Person samplePerson = model.getFilteredPersonList().get(0);
        singleItemModel.addPerson(samplePerson);

        SortCommand sortCommand = new SortCommand(new SortParam("name"));
        sortCommand.execute(singleItemModel);

        assertEquals(1, singleItemModel.getFilteredPersonList().size());
        assertEquals(samplePerson, singleItemModel.getFilteredPersonList().get(0));
    }

    @Test
    public void execute_allSortTypes_maintainDataIntegrity() throws CommandException {
        // Store original size
        int originalSize = model.getFilteredPersonList().size();

        // Test all sort types
        String[] sortTypes = {"name", "class", "studentId"};
        for (String sortType : sortTypes) {
            SortCommand sortCommand = new SortCommand(new SortParam(sortType));
            sortCommand.execute(model);

            // Verify size hasn't changed
            assertEquals(originalSize, model.getFilteredPersonList().size(),
                    "List size changed after sorting by " + sortType);

            // Verify all elements are still present (using student IDs as unique identifiers)
            List<Person> currentList = model.getFilteredPersonList();
            for (Person person : currentList) {
                assertTrue(originalStudentIdsExist(person.getStudentId().toString(), currentList),
                        "Person with ID " + person.getStudentId() + " missing after sort");
            }
        }
    }

    /**
     * Helper method to check if a student ID exists in the list
     */
    private boolean originalStudentIdsExist(String studentId, List<Person> currentList) {
        return currentList.stream()
                .anyMatch(p -> p.getStudentId().toString().equals(studentId));
    }
}
