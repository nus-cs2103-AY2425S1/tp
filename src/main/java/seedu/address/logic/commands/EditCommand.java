package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.StudentId;

/**
 * Edits the details of an existing person in EduContacts.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": 2 possible usages\n"
            + "1. Edits the details of the person identified "
            + "by the studentId assigned to the corresponding student. \n"
            + "    Existing values will be overwritten by the input values.\n"
            + "    Parameters: ID "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_COURSE + " COURSE] "
            + "[" + PREFIX_ROLE + "ROLE] ...\n"
            + "    Example: " + COMMAND_WORD + " 12345678 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com\n"
            + "2. Edits a module of the person identified. \n"
            + "    Existing values will be overwritten by the input module.\n"
            + "    Parameters: ID "
            + PREFIX_MODULE + "OLD_MODULE NEW_MODULE\n"
            + "    Example: " + COMMAND_WORD + " 12345678 "
            + PREFIX_MODULE + "CS2103T CS2101";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in EduContacts.";
    public static final String MESSAGE_DUPLICATE_MODULE = "New module already exists in the person's module list.";
    public static final String MESSAGE_MODULE_NOT_FOUND = "Old module not found in the person's module list.";
    private final StudentId studentId;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param studentId of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(StudentId studentId, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(studentId);
        requireNonNull(editPersonDescriptor);

        this.studentId = studentId;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToEdit = lastShownList.stream()
                .filter(person -> person.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_STUDENTID));;

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }


        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (personToEdit.isSamePerson(model.getPersonToDisplay())) {
            model.setPersonToDisplay(editedPerson);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)), true);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor)
            throws CommandException {
        assert personToEdit != null;
        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Course updatedCourse = editPersonDescriptor.getCourse().orElse(personToEdit.getCourse());
        Role updatedRole = editPersonDescriptor.getRole().orElse(personToEdit.getRole());

        ArrayList<Module> updatedModules = editPersonDescriptor.getModules().orElse(personToEdit.getModules());
        if (editPersonDescriptor.hasModuleChanges()) {
            Module oldModule = editPersonDescriptor.oldModule;
            Module newModule = editPersonDescriptor.newModule;

            boolean isModuleRenamed = false;
            if (updatedModules.stream().anyMatch(m -> m.value.toUpperCase().equals(newModule.value.toUpperCase()))) {
                throw new CommandException(EditCommand.MESSAGE_DUPLICATE_MODULE);
            }

            for (int i = 0; i < updatedModules.size(); i++) {
                if (updatedModules.get(i).value.toUpperCase().equals(oldModule.value.toUpperCase())) {
                    Module updatedModule = new Module(newModule.value);
                    if (updatedModules.get(i).hasGrade()) {
                        updatedModule.setGrade(updatedModules.get(i).getGrade());
                    }
                    isModuleRenamed = true;
                    updatedModules.set(i, updatedModule);
                    break;
                }
            }

            if (!isModuleRenamed) {
                throw new CommandException(MESSAGE_MODULE_NOT_FOUND);
            }
        }

        Person editedPerson = new Person(personToEdit.getStudentId(), updatedName, updatedPhone, updatedEmail,
                updatedAddress, updatedCourse, updatedRole, updatedModules);

        return editedPerson;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return studentId.equals(otherEditCommand.studentId)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private StudentId studentId;
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Course course;
        private Role role;
        private ArrayList<Module> modules;
        private Module oldModule;
        private Module newModule;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code roles} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setStudentId(null);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setCourse(toCopy.course);
            setRole(toCopy.role);
            setModules(toCopy.modules);
            setOldModule(toCopy.oldModule);
            setNewModule(toCopy.newModule);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(studentId, name, phone, email, address, course, role, modules)
                    || hasModuleChanges();
        }

        public void setStudentId(StudentId studentId) {
            this.studentId = studentId;
        }

        public Optional<StudentId> getStudentId() {
            return Optional.ofNullable(studentId);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public Optional<Course> getCourse() {
            return Optional.ofNullable(course);
        }

        public void setNewModule(Module newModule) {
            this.newModule = newModule;
        }

        public Optional<Module> getNewModule() {
            return Optional.ofNullable(newModule);
        }

        public void setOldModule(Module oldModule) {
            this.oldModule = oldModule;
        }

        public Optional<Module> getOldModule() {
            return Optional.ofNullable(oldModule);
        }

        /**
         * Sets {@code roles} to this object's {@code roles}.
         * A defensive copy of {@code roles} is used internally.
         */
        public void setRole(Role role) {
            this.role = role;
        }

        /**
         * Returns an unmodifiable role set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code roles} is null.
         */
        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public void setModules(ArrayList<Module> modules) {
            this.modules = (modules != null) ? new ArrayList<>(modules) : null;
        }
        public Optional<ArrayList<Module>> getModules() {
            return (modules != null) ? Optional.of(modules) : Optional.empty();
        }

        public void addModule(Module module) {
            this.modules.add(module);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(course, otherEditPersonDescriptor.course)
                    && Objects.equals(role, otherEditPersonDescriptor.role)
                    && Objects.equals(modules, otherEditPersonDescriptor.modules)
                    && Objects.equals(oldModule, otherEditPersonDescriptor.oldModule)
                    && Objects.equals(newModule, otherEditPersonDescriptor.newModule);
        }

        public void setModuleChanges(Module oldModule, Module newModule) {
            this.oldModule = oldModule;
            this.newModule = newModule;
        }

        public boolean hasModuleChanges() {
            return oldModule != null && newModule != null;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("studentId", studentId)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("course", course)
                    .add("roles", role)
                    .add("modules", modules)
                    .add("oldModule", oldModule)
                    .add("newModule", newModule)
                    .toString();
        }
    }
}
