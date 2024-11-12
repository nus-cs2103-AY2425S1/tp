package seedu.hiredfiredpro.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.hiredfiredpro.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.hiredfiredpro.model.person.Email;
import seedu.hiredfiredpro.model.person.InterviewScore;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;
import seedu.hiredfiredpro.model.person.Person;
import seedu.hiredfiredpro.model.person.Phone;
import seedu.hiredfiredpro.model.skill.Skill;
import seedu.hiredfiredpro.model.tag.Tag;

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
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setJob(person.getJob());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setSkills(person.getSkills());
        descriptor.setInterviewScore(person.getInterviewScore());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Job} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withJob(String job) {
        descriptor.setJob(new Job(job));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Parses the {@code skills} into a {@code Set<Skill>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withSkills(String... skills) {
        Set<Skill> skillSet = Stream.of(skills).map(Skill::new).collect(Collectors.toSet());
        descriptor.setSkills(skillSet);
        return this;
    }

    /**
     * Sets the {@code InterviewScore} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withInterviewScore(String interviewScore) {
        descriptor.setInterviewScore(new InterviewScore(interviewScore));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }

}
