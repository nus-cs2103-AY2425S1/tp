package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Age;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.StudyGroupTag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing
     * {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setEmail(person.getEmail());
        descriptor.setGender(person.getGender());
        descriptor.setAge(person.getAge());
        descriptor.setStudyGroupTags(person.getStudyGroupTags());
        descriptor.setDetail(person.getDetail());
        descriptor.setTagsToRemove(null);
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withAge(String age) {
        descriptor.setAge(new Age(age));
        return this;
    }

    /**
     * Sets the {@code Detail} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withDetail(String detail) {
        descriptor.setDetail(new Detail(detail));
        return this;
    }

    /**
     * Parses the {@code studyGroups} into a {@code Set<StudyGroupTag>} and set it
     * to the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withStudyGroupTags(String... studyGroups) {
        Set<StudyGroupTag> studyGroupSet = Stream.of(studyGroups).map(StudyGroupTag::new).collect(Collectors.toSet());
        descriptor.setStudyGroupTags(studyGroupSet);
        return this;
    }

    /**
     * Parses the {@code tagsToRemove} into a {@code Set<StudyGroupTag>} and set it
     * to the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withTagsToRemove(String... tagsToRemove) {
        Set<StudyGroupTag> toRemoveSet = Stream.of(tagsToRemove).map(StudyGroupTag::new).collect(Collectors.toSet());
        descriptor.setTagsToRemove(toRemoveSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
