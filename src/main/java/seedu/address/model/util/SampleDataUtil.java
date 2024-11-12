package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.employee.Address;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.Phone;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;
import seedu.address.model.project.ProjectName;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Employee[] getSampleEmployees() {
        return new Employee[] {
            new Employee(new EmployeeId("0"),
                new EmployeeName("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), getSkillSet("Frontend")),
            new Employee(new EmployeeId("1"),
                new EmployeeName("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), getSkillSet("UI")),
            new Employee(new EmployeeId("2"),
                new EmployeeName("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), getSkillSet("Backend")),
            new Employee(new EmployeeId("3"),
                new EmployeeName("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), getSkillSet("Database")),
            new Employee(new EmployeeId("4"),
                new EmployeeName("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), getSkillSet("Management")),
            new Employee(new EmployeeId("5"),
                new EmployeeName("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), getSkillSet("Devops", "Database"))
        };
    }

    public static Project[] getSampleProjects() {
        return new Project[] {
            new Project(new ProjectName("Project Alpha"), new ProjectId("1"), getSkillSet("Frontend")),
            new Project(new ProjectName("Project Beta"), new ProjectId("2"), getSkillSet("UI")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Employee sampleEmployee : getSampleEmployees()) {
            sampleAb.addEmployee(sampleEmployee);
        }
        for (Project sampleProject : getSampleProjects()) {
            sampleAb.addProject(sampleProject);
        }

        // Adding Assignments is done here as it requires references to the projects and employees.
        List<Employee> employeeList = sampleAb.getEmployeeList();
        List<Project> projectList = sampleAb.getProjectList();

        Employee employee0 = employeeList.get(0);
        Project project0 = projectList.get(0);
        Employee employee1 = employeeList.get(1);
        Project project1 = projectList.get(1);

        sampleAb.addAssignment(new Assignment(new AssignmentId("0"), project0, employee0));
        sampleAb.addAssignment(new Assignment(new AssignmentId("1"), project1, employee1));

        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a skill set containing the list of strings given.
     */
    public static Set<Skill> getSkillSet(String... strings) {
        return Arrays.stream(strings)
                .map(Skill::new)
                .collect(Collectors.toSet());
    }

}
