package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISSUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAKE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VRN;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.car.Car;
import seedu.address.model.car.CarMake;
import seedu.address.model.car.CarModel;
import seedu.address.model.car.Vin;
import seedu.address.model.car.Vrn;
import seedu.address.model.issue.Issue;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Edits the details of an existing Client in the MATER address book.
 */
public class EditClientCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the indexed client. "
            + "Existing values will be overwritten by the input values.\n\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_VRN + "VEHICLE REGISTRATION NUMBER] "
            + "[" + PREFIX_VIN + "VEHICLE IDENTIFICATION NUMBER] "
            + "[" + PREFIX_MAKE + "VEHICLE MAKE] "
            + "[" + PREFIX_MODEL + "VEHICLE MODEL] "
            + "[" + PREFIX_ISSUE + "ISSUE]\n\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_VRN + "SNP5701C";
    public static final String MESSAGE_EDIT_CLIENT_SUCCESS = "Edited Client in MATER";
    public static final String MESSAGE_NOT_EDITED = "At least one field must be edited.";
    public static final String MESSAGE_FIELD_VALUES_SAME = "Edited field is the same as the initial field.";
    public static final String MESSAGE_CAR_DOES_NOT_EXIST = "Client does not have a Car to edit.";
    public static final String MESSAGE_DUPLICATE_PERSON = "Client already exists in MATER.";
    public static final String MESSAGE_DUPLICATE_CAR = "Car already exists in MATER.";
    public static final String MESSAGE_NO_CAR_TO_EDIT_ISSUES = "Client does not have a Car to edit Issues.";
    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;
    private final EditCarDescriptor editCarDescriptor;
    private final Boolean isPersonEdited;
    private final Boolean isCarEdited;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditClientCommand(
        Index index, EditPersonDescriptor editPersonDescriptor,
        EditCarDescriptor editCarDescriptor, Boolean isPersonEdited,
        Boolean isCarEdited
    ) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.editCarDescriptor = new EditCarDescriptor(editCarDescriptor);
        this.isPersonEdited = isPersonEdited;
        this.isCarEdited = isCarEdited;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Car editedCar = null;

        if (personToEdit.getCar() == null && isCarEdited) {
            throw new CommandException(MESSAGE_CAR_DOES_NOT_EXIST);
        }
        if (personToEdit.getCar() != null) {
            Car carToEdit = personToEdit.getCar();
            editedCar = createEditedCar(carToEdit, editCarDescriptor);
            if (carToEdit.equals(editedCar) && isCarEdited) {
                throw new CommandException(MESSAGE_FIELD_VALUES_SAME);
            }
            // if one or more fields is edited
            if ((!carToEdit.hasMatchingVrnAndVin(editedCar))
                // if there are more than one car with the same VRN or VIN
                && (model.getCarsWithSameVrnOrVinCount(editedCar) > 1
                // if there is a car with the same VRN and VIN
                || model.hasCarWithSameVrnAndVin(editedCar)
                // if both VRN and VIN are edited check if there is a car with the same VRN and VIN
                || (!carToEdit.hasMatchingVrnOrVin(editedCar) && model.hasCar(editedCar)))
                // check if car was edited
                && isCarEdited) {
                throw new CommandException(MESSAGE_DUPLICATE_CAR);
            }
        }

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor, editedCar);

        if (personToEdit.equals(editedPerson) && isPersonEdited) {
            throw new CommandException(MESSAGE_FIELD_VALUES_SAME);
        }

        if ((!personToEdit.isSamePerson(editedPerson)) && model.hasPerson(editedPerson) && isPersonEdited) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        // check if there is a car to edit issues to
        if (editedPerson.getCar() == null && editedPerson.getIssues().size() > 0) {
            throw new CommandException(MESSAGE_NO_CAR_TO_EDIT_ISSUES);
        }

        // check if there is a car to edit issues to
        if (editPersonDescriptor.getIssueEdited() && personToEdit.getCar() == null) {
            throw new CommandException(MESSAGE_NO_CAR_TO_EDIT_ISSUES);
        }

        // check if person was checked in.
        if (personToEdit.isServicing()) {
            editedPerson.setServicing();
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String message = Messages.formatSuccessMessage(editedPerson, MESSAGE_EDIT_CLIENT_SUCCESS);
        return new CommandResult(message);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit,
        EditPersonDescriptor editPersonDescriptor, Car editedCar
    ) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Issue> updatedIssues = editPersonDescriptor.getIssues().orElse(personToEdit.getIssues());
        Car updatedCar = editedCar;
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedIssues, updatedCar);
    }

    private static Car createEditedCar(Car carToEdit, EditCarDescriptor editCarDescriptor) {

        Vrn updatedVrn = editCarDescriptor.getVrn().orElse(carToEdit.getVrn());
        Vin updatedVin = editCarDescriptor.getVin().orElse(carToEdit.getVin());
        CarMake updatedMake = editCarDescriptor.getMake().orElse(carToEdit.getCarMake());
        CarModel updatedModel = editCarDescriptor.getModel().orElse(carToEdit.getCarModel());

        return new Car(updatedVrn, updatedVin, updatedMake, updatedModel);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditClientCommand)) {
            return false;
        }

        EditClientCommand otherEditClientCommand = (EditClientCommand) other;
        return index.equals(otherEditClientCommand.index)
                && editPersonDescriptor.equals(otherEditClientCommand.editPersonDescriptor);
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
        private Set<Issue> issues;
        private Car car;
        private Boolean isIssueEdited;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code issues} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setIssues(toCopy.issues);
            setCar(toCopy.car);
            setIssueEdited(toCopy.isIssueEdited);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, issues, car);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public void setIssueEdited(Boolean isIssueEdited) {
            this.isIssueEdited = isIssueEdited;
        }

        public Boolean getIssueEdited() {
            return isIssueEdited;
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

        /**
         * Sets {@code issues} to this object's {@code issues}.
         * A defensive copy of {@code issues} is used internally.
         */
        public void setIssues(Set<Issue> issues) {
            this.issues = (issues != null) ? new HashSet<>(issues) : null;
        }

        /**
         * Returns an unmodifiable issue set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code issues} is null.
         */
        public Optional<Set<Issue>> getIssues() {
            return (issues != null) ? Optional.of(Collections.unmodifiableSet(issues)) : Optional.empty();
        }

        public void setCar(Car car) {
            this.car = car;
        }

        public Optional<Car> getCar() {
            return Optional.ofNullable(car);
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
                    && Objects.equals(issues, otherEditPersonDescriptor.issues)
                    && Objects.equals(car, otherEditPersonDescriptor.car);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("issues", issues)
                    .add("car", car)
                    .add("isIssueEdited", isIssueEdited)
                    .toString();
        }
    }

    /**
     * Stores the details to edit the car with. Each non-empty field value will replace the
     * corresponding field value of the car.
     */
    public static class EditCarDescriptor {
        private Vrn vrn;
        private Vin vin;
        private CarMake make;
        private CarModel model;

        public EditCarDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code issues} is used internally.
         */
        public EditCarDescriptor(EditCarDescriptor toCopy) {
            setVrn(toCopy.vrn);
            setVin(toCopy.vin);
            setMake(toCopy.make);
            setModel(toCopy.model);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(vrn, vin, make, model);
        }

        public void setVrn(Vrn vrn) {
            this.vrn = vrn;
        }

        public Optional<Vrn> getVrn() {
            return Optional.ofNullable(vrn);
        }

        public void setVin(Vin vin) {
            this.vin = vin;
        }

        public Optional<Vin> getVin() {
            return Optional.ofNullable(vin);
        }

        public void setMake(CarMake make) {
            this.make = make;
        }

        public Optional<CarMake> getMake() {
            return Optional.ofNullable(make);
        }

        public void setModel(CarModel model) {
            this.model = model;
        }

        public Optional<CarModel> getModel() {
            return Optional.ofNullable(model);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCarDescriptor)) {
                return false;
            }

            EditCarDescriptor otherEditCarDescriptor = (EditCarDescriptor) other;
            return Objects.equals(vrn, otherEditCarDescriptor.vrn)
                    && Objects.equals(vin, otherEditCarDescriptor.vin)
                    && Objects.equals(make, otherEditCarDescriptor.make)
                    && Objects.equals(model, otherEditCarDescriptor.model);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("vrn", vrn)
                    .add("vin", vin)
                    .add("make", make)
                    .add("model", model)
                    .toString();
        }
    }
}
