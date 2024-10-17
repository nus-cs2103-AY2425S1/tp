package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Tag;

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
        descriptor.setStudentId(person.getStudentId());
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setCourse(person.getCourse());
        descriptor.setTag(person.getTag());
        descriptor.setModules(person.getModules());
    }

    public EditPersonDescriptorBuilder setEmptyModuleList() {
        descriptor.setModules(null);
        return this;
    }

    /**
     * Sets the {@code StudentId} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withStudentId(String studentId) {
        descriptor.setStudentId(new StudentId(studentId));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
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
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Course} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withCourse(String course) {
        descriptor.setCourse(new Course(course));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTag(String tag) {
        descriptor.setTag(new Tag(tag));
        return this;
    }
    /**
     * Parses the ungraded {@code module} into a {@code ArrayList<Module>} and
     * set it to the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder addUngradedModule(String module) {
        Module personModule = new Module(module);
        if (descriptor.getModules().isEmpty()) {
            descriptor.setModules(new ArrayList<>());
        }
        descriptor.addModule(personModule);
        return this;
    }
    /**
     * Parses the graded {@code module} into a {@code ArrayList<Module>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder addGradedModule(String module, String grade) {
        Module personModule = new Module(module);
        Grade moduleGrade = new Grade(grade);
        personModule.setGrade(moduleGrade);
        if (descriptor.getModules().isEmpty()) {
            descriptor.setModules(new ArrayList<>());
        }
        descriptor.addModule(personModule);
        return this;
    }


    public EditPersonDescriptor build() {
        return descriptor;
    }
}
