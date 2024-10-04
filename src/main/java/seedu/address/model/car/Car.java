package seedu.address.model.car;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Car in MATER.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Car {

    private final Vrn vrn; // Vehicle Registration Number (Vrn).
    private final Vin vin; // Vehicle Identification Number (Vin).
    private final Make make; // e.g. Honda, Toyota, Mitsubishi
    private final Model model; // e.g. Jazz, Vios, Lancer

    /**
     * Every field must be present and not null.
     */
    public Car(Vrn vrn, Vin vin, Make make, Model model) {
        requireAllNonNull(vrn, vin, make, model);
        this.vrn = vrn;
        this.vin = vin;
        this.make = make;
        this.model = model;
    }

    public Vrn getVrn() {
        return this.vrn;
    }

    public Vin getVin() {
        return this.vin;
    }

    public Make getMake() {
        return this.make;
    }

    public Model getModel() {
        return this.model;
    }

    /**
     * Returns true if both Cars have the same make and model.
     * This defines a weaker notion of equality between two Cars.
     */
    public boolean isSameCar(Car otherCar) {
        if (otherCar == this) {
            return true;
        }

        if (otherCar == null) {
            return false;
        }

        return otherCar.getMake().equals(getMake()) && otherCar.getModel().equals(getModel());
    }

    /**
     * Returns true if both Cars have the same vrn, vin, make and model.
     * This defines a stronger notion of equality between two Cars.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Car)) {
            return false;
        }

        Car otherPerson = (Car) other;
        return this.vrn.equals(otherPerson.vrn)
            && this.vin.equals(otherPerson.vin)
            && this.make.equals(otherPerson.make)
            && this.model.equals(otherPerson.model);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.vrn, this.vin, this.make, this.model);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("vrn", this.vrn)
            .add("vin", this.vin)
            .add("make", this.make)
            .add("model", this.model)
            .toString();
    }

}
