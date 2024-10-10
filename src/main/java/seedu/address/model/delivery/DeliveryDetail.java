package seedu.address.model.delivery;

public class DeliveryDetail {
    private final String title;
    private final String description;

    private final double quantity;

    private final double cost;

    public DeliveryDetail(String name, String description, Double quantity, Double cost) {
        this.title = name;
        this.description = description;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getName() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("Title: %s | Descrption: %s", this.title, this.description);
    }
}
