package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.getSkillSet;

import org.junit.jupiter.api.Test;

public class ProjectTest {
    @Test
    public void hashCode_equalProjectIdProjectNameAndSkills_returnTrue() {
        ProjectId id = new ProjectId("1");
        ProjectName name = new ProjectName("lol");
        Project project1 = new Project(name, id, getSkillSet("Devops", "Database"));
        Project project2 = new Project(name, id, getSkillSet("Devops", "Database"));
        assertTrue(project1.hashCode() == project2.hashCode());
    }
}
