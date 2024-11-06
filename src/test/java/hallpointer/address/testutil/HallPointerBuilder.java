package hallpointer.address.testutil;

import hallpointer.address.model.HallPointer;
import hallpointer.address.model.member.Member;

/**
 * A utility class to help with building HallPointer objects.
 * Example usage: <br>
 *     {@code HallPointer ab = new HallPointerBuilder().withMember("John", "Doe").build();}
 */
public class HallPointerBuilder {

    private HallPointer hallPointer;

    public HallPointerBuilder() {
        hallPointer = new HallPointer();
    }

    public HallPointerBuilder(HallPointer hallPointer) {
        this.hallPointer = hallPointer;
    }

    /**
     * Adds a new {@code Member} to the {@code HallPointer} that we are building.
     */
    public HallPointerBuilder withMember(Member member) {
        hallPointer.addMember(member);
        return this;
    }

    public HallPointer build() {
        return hallPointer;
    }
}
