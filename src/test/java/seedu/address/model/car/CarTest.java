package seedu.address.model.car;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CarTest {

    private static final Car ALICE_HYUNDAI_KONA = new Car(new Vrn("SJH 9514 P"),
            new Vin("KMHGH4JH3EU073801"),
            new CarMake("Hyundai"),
            new CarModel("Kona 1.6T"));

    private static final Car ALICE_HYUNDAI_KONA_COPY = new Car(new Vrn("SJH 9514 P"),
            new Vin("KMHGH4JH3EU073801"),
            new CarMake("Hyundai"),
            new CarModel("Kona 1.6T"));

    private static final Car BOB_HYUNDAI_KONA = new Car(new Vrn("S 6780 S"),
            new Vin("ABCDE12345ABCDE12"),
            new CarMake("Hyundai"),
            new CarModel("Kona 1.6T"));

    private static final Car ALICE_BMW = new Car(new Vrn("SJH 9514 P"),
            new Vin("KMHGH4JH3EU073801"),
            new CarMake("BMW"),
            new CarModel("520i"));

    @Test
    public void isSameCar() {
        // same object -> returns true
        assertTrue(ALICE_HYUNDAI_KONA.isSameCar(ALICE_HYUNDAI_KONA));

        // null -> returns false
        assertFalse(ALICE_HYUNDAI_KONA.isSameCar(null));

        // same make and model, vrn and vin different -> returns true
        assertTrue(ALICE_HYUNDAI_KONA.isSameCar(BOB_HYUNDAI_KONA));

        // different make and model, vrn and vin same -> returns false
        assertFalse(ALICE_HYUNDAI_KONA.isSameCar(ALICE_BMW));
    }

    @Test
    public void equals() {
        // same values -> returns true
        assertTrue(ALICE_HYUNDAI_KONA.equals(ALICE_HYUNDAI_KONA_COPY));

        // same object -> returns true
        assertTrue(ALICE_HYUNDAI_KONA.equals(ALICE_HYUNDAI_KONA));

        // null -> returns false
        assertFalse(ALICE_HYUNDAI_KONA.equals(null));

        // different type -> returns false
        assertFalse(ALICE_HYUNDAI_KONA.equals(5));

        // different vin and vrn / car -> returns false
        assertFalse(ALICE_HYUNDAI_KONA.equals(BOB_HYUNDAI_KONA));

        // different make and model -> returns false
        assertFalse(ALICE_HYUNDAI_KONA.equals(ALICE_BMW));
    }

    @Test
    public void toStringMethod() {
        String expected = Car.class.getCanonicalName()
                + "{vrn=" + ALICE_HYUNDAI_KONA.getVrn()
                + ", vin=" + ALICE_HYUNDAI_KONA.getVin()
                + ", make=" + ALICE_HYUNDAI_KONA.getCarMake()
                + ", model=" + ALICE_HYUNDAI_KONA.getCarModel() + "}";
        assertEquals(expected, ALICE_HYUNDAI_KONA.toString());
    }
}
