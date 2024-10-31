package seedu.address.testutil;

import seedu.address.logic.commands.AssignCommand.AssignStudyGroupTagDescriptor;
import seedu.address.model.tag.StudyGroupTag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class AssignStudyGroupTagDescriptorBuilder {

    private AssignStudyGroupTagDescriptor descriptor;

    public AssignStudyGroupTagDescriptorBuilder() {
        descriptor = new AssignStudyGroupTagDescriptor();
    }

    public AssignStudyGroupTagDescriptorBuilder(AssignStudyGroupTagDescriptor descriptor) {
        this.descriptor = new AssignStudyGroupTagDescriptor(descriptor);
    }

    /**
     * Parses the {@code studyGroups} into a {@code Set<StudyGroupTag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public AssignStudyGroupTagDescriptorBuilder withStudyGroupTag(String studyGroup) {
        descriptor.setStudyGroupTag(new StudyGroupTag(studyGroup));
        return this;
    }

    public AssignStudyGroupTagDescriptor build() {
        return descriptor;
    }
}
