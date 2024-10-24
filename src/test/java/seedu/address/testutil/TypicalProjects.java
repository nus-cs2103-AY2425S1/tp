package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.project.Project;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalProjects {

    public static final Project ALPHA = new ProjectBuilder()
            .withName("Project Alpha")
            .withId("A0276123J")
            .withSkills("moneyManagement").build();
    public static final Project BETA = new ProjectBuilder()
            .withName("Project Beta")
            .withId("A0276123K")
            .withSkills("gambling", "thievery").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalProjects() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Project project : getTypicalProjects()) {
            ab.addProject(project);
        }
        return ab;
    }

    public static List<Project> getTypicalProjects() {
        return new ArrayList<>(Arrays.asList(ALPHA, BETA));
    }
}
