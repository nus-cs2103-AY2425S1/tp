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
    private final CarMake carMake; // e.g. Honda, Toyota, Mitsubishi
    private final CarModel carModel; // e.g. Jazz, Vios, Lancer

    /**
     * Every field must be present and not null.
     */
    public Car(Vrn vrn, Vin vin, CarMake carMake, CarModel carModel) {
        requireAllNonNull(vrn, vin, carMake, carModel);
        this.vrn = vrn;
        this.vin = vin;
        this.carMake = carMake;
        this.carModel = carModel;
    }

    public Vrn getVrn() {
        return this.vrn;
    }

    public Vin getVin() {
        return this.vin;
    }

    public CarMake getCarMake() {
        return this.carMake;
    }

    public CarModel getCarModel() {
        return this.carModel;
    }

    /**
     * Returns true if both Cars have the same Vrn or Vin.
     * This defines a weaker notion of equality between two Cars.
     */
    public boolean hasMatchingVrnOrVin(Car otherCar) {
        if (otherCar == this) {
            return true;
        }

        if (otherCar == null) {
            return false;
        }

        return this.vrn.equals(otherCar.getVrn()) || this.vin.equals(otherCar.getVin());
    }

    /**
     * Returns true if both Cars have the same Vrn or Vin.
     * This defines a weaker notion of equality between two Cars.
     */
    public boolean hasMatchingVrnAndVin(Car otherCar) {
        if (otherCar == this) {
            return true;
        }

        if (otherCar == null) {
            return false;
        }

        return this.vrn.equals(otherCar.getVrn()) && this.vin.equals(otherCar.getVin());
    }

    /**
     * Returns true if both Cars have the same CarMake and CarModel.
     * This defines a weaker notion of equality between two Cars.
     */
    public boolean isSameCar(Car otherCar) {
        if (otherCar == this) {
            return true;
        }

        if (otherCar == null) {
            return false;
        }

        return otherCar.getCarMake().equals(getCarMake())
                && otherCar.getCarModel().equals(getCarModel());
    }

    /**
     * Returns true if both Cars have the same Vrn, Vin, CarMake and CarModel.
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
                && this.carMake.equals(otherPerson.carMake)
                && this.carModel.equals(otherPerson.carModel);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.vrn, this.vin, this.carMake, this.carModel);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("vrn", this.vrn)
                .add("vin", this.vin)
                .add("make", this.carMake)
                .add("model", this.carModel)
                .toString();
    }

}
