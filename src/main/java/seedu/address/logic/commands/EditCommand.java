package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWED_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.OwedAmount;
import seedu.address.model.person.Paid;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rate;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.Subject;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_WORD_RANDOM_CASE = "EdiT";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_SCHEDULE + "SCHEDULE] "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_RATE + "RATE] "
            + "[" + PREFIX_PAID + "PAID] "
            + "[" + PREFIX_OWED_AMOUNT + "OWED] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_SCHEDULE + "Saturday-1000-1200 "
            + PREFIX_SUBJECT + "Mathematics "
            + PREFIX_RATE + "300 "
            + PREFIX_PAID + "600 "
            + PREFIX_OWED_AMOUNT + "300 ";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";


    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        long clashes = model.checkClashes(editedPerson);
        List<Person> clashingPersons = model.getClashingPersons(editedPerson);
        if (clashes == 0) {
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
        } else {
            return new CommandResult(
                    String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson))
                    + Messages.getWarningMessageForClashes(clashes, clashingPersons)
            );
        }

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Schedule updatedSchedule = editPersonDescriptor.getSchedule().orElse(personToEdit.getSchedule());
        Subject updatedSubject = editPersonDescriptor.getSubject().orElse(personToEdit.getSubject());
        Rate updatedRate = editPersonDescriptor.getRate().orElse(personToEdit.getRate());
        Paid updatedPaid = editPersonDescriptor.getPaid().orElse(personToEdit.getPaid());
        OwedAmount updatedOwedAmount = editPersonDescriptor.getOwedAmount().orElse(personToEdit.getOwedAmount());

        return new Person(
                updatedName, updatedPhone, updatedEmail, updatedAddress, updatedSchedule, updatedSubject, updatedRate,
                updatedPaid, updatedOwedAmount
        );
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
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Schedule schedule;
        private Subject subject;
        private Rate rate;
        private Paid paid;
        private OwedAmount owedAmount;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setSchedule(toCopy.schedule);
            setSubject(toCopy.subject);
            setRate(toCopy.rate);
            setPaid(toCopy.paid);
            setOwedAmount(toCopy.owedAmount);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, schedule, subject, rate, paid, owedAmount);
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

        public void setSchedule(Schedule schedule) {
            this.schedule = schedule;
        }

        public Optional<Schedule> getSchedule() {
            return Optional.ofNullable(schedule);
        }
        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        public Optional<Subject> getSubject() {
            return Optional.ofNullable(subject);
        }

        public void setRate(Rate rate) {
            this.rate = rate;
        }

        public Optional<Rate> getRate() {
            return Optional.ofNullable(rate);
        }

        public void setPaid(Paid paid) {
            this.paid = paid;
        }
        public Optional<Paid> getPaid() {
            return Optional.ofNullable(paid);
        }

        public void setOwedAmount(OwedAmount owedAmount) {
            this.owedAmount = owedAmount;
        }

        public Optional<OwedAmount> getOwedAmount() {
            return Optional.ofNullable(owedAmount);
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
                    && Objects.equals(schedule, otherEditPersonDescriptor.schedule)
                    && Objects.equals(subject, otherEditPersonDescriptor.subject)
                    && Objects.equals(rate, otherEditPersonDescriptor.rate)
                    && Objects.equals(paid, otherEditPersonDescriptor.paid)
                    && Objects.equals(owedAmount, otherEditPersonDescriptor.owedAmount);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("schedule", schedule)
                    .add("subject", subject)
                    .add("rate", rate)
                    .add("paid", paid)
                    .add("owedAmount", owedAmount)
                    .toString();
        }

    }
}
