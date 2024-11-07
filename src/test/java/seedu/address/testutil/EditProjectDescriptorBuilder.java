package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditProjectCommand;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.skill.Skill;

/**
 * A utility class to help with building EditProjectDescriptor objects.
 */
public class EditProjectDescriptorBuilder {

    private EditProjectCommand.EditProjectDescriptor descriptor;

    public EditProjectDescriptorBuilder() {
        descriptor = new EditProjectCommand.EditProjectDescriptor();
    }

    public EditProjectDescriptorBuilder(EditProjectCommand.EditProjectDescriptor descriptor) {
        this.descriptor = new EditProjectCommand.EditProjectDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditProjectDescriptor} with fields containing {@code project}'s details
     */
    public EditProjectDescriptorBuilder(Project project) {
        descriptor = new EditProjectCommand.EditProjectDescriptor();
        descriptor.setName(project.getName());
        descriptor.setSkills(project.getSkills());
    }

    /**
     * Sets the {@code Name} of the {@code EditProjectDescriptor} that we are building.
     */
    public EditProjectDescriptorBuilder withName(String name) {
        descriptor.setName(new ProjectName(name));
        return this;
    }

    /**
     * Parses the {@code skills} into a {@code Set<Skill>} and set it to the {@code EditProjectDescriptor}
     * that we are building.
     */
    public EditProjectDescriptorBuilder withSkills(String... skills) {
        Set<Skill> skillSet = Stream.of(skills).map(Skill::new).collect(Collectors.toSet());
        descriptor.setSkills(skillSet);
        return this;
    }

    public EditProjectCommand.EditProjectDescriptor build() {
        return descriptor;
    }
}
