package seedu.address.testutil;

import seedu.address.logic.commands.EditClientCommand.EditCarDescriptor;
import seedu.address.model.car.Car;
import seedu.address.model.car.CarMake;
import seedu.address.model.car.CarModel;
import seedu.address.model.car.Vin;
import seedu.address.model.car.Vrn;


/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditCarDescriptorBuilder {

    private EditCarDescriptor descriptor;

    public EditCarDescriptorBuilder() {
        descriptor = new EditCarDescriptor();
    }

    public EditCarDescriptorBuilder(EditCarDescriptor descriptor) {
        this.descriptor = new EditCarDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCarDescriptor} with fields containing {@code person}'s details
     */
    public EditCarDescriptorBuilder(Car car) {
        descriptor = new EditCarDescriptor();
        descriptor.setVrn(car.getVrn());
        descriptor.setVin(car.getVin());
        descriptor.setMake(car.getCarMake());
        descriptor.setModel(car.getCarModel());
    }

    /**
     * Sets the {@code Vrn} of the {@code EditCarDescriptor} that we are building.
     */
    public EditCarDescriptorBuilder withVrn(String vrn) {
        descriptor.setVrn(new Vrn(vrn));
        return this;
    }

    /**
     * Sets the {@code Vin} of the {@code EditCarDescriptor} that we are building.
     */
    public EditCarDescriptorBuilder withVin(String vin) {
        descriptor.setVin(new Vin(vin));
        return this;
    }

    /**
     * Sets the {@code car model} of the {@code EditCarDescriptor} that we are building.
     */
    public EditCarDescriptorBuilder withCarModel(String carModel) {
        descriptor.setModel(new CarModel(carModel));
        return this;
    }

    /**
     * Sets the {@code car make} of the {@code EditCarDescriptor} that we are building.
     */
    public EditCarDescriptorBuilder withCarMake(String carMake) {
        descriptor.setMake(new CarMake(carMake));
        return this;
    }

    public EditCarDescriptor build() {
        return descriptor;
    }
}
