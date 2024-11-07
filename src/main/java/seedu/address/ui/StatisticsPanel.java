package seedu.address.ui;

import java.util.Objects;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.AddressBookStatistics;



/**
 * Panel containing the statistics.
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsPanel.class);
    private AddressBookStatistics addressBookStatistics;

    @FXML
    private GridPane statisticsGridPane;

    /**
     * Creates a {@code StatisticsPanel} with the given {@code AddressBookStatistics}.
     *
     * @param addressBookStatistics The statistics of the current {@AddressBook}
     */
    public StatisticsPanel(AddressBookStatistics addressBookStatistics) {
        super(FXML);
        this.addressBookStatistics = addressBookStatistics;
        updateStatistics();
        logger.info("Initialized Statistics Panel");
    }

    /**
     * Populates the {@code StatisticsPanel} with the various charts derived from the existing
     * {@code AddressBookStatistics}.
     */
    public void updateStatistics() {
        logger.info("Updating Statistics View...");
        statisticsGridPane.getChildren().clear(); // Clear existing charts

        // Add initial bar chart with data
        BarChart<Number, String> propertiesBoughtAndSoldBarChart =
                createPropertiesSoldAndBoughtBarChart("Properties Sold and Bought", addressBookStatistics);
        statisticsGridPane.add(propertiesBoughtAndSoldBarChart, 0, 0);

        PieChart propertiesBoughtAndSoldPieChart =
                createPropertiesBoughtAndSoldPieChart("Properties Bought vs Properties Sold",
                        addressBookStatistics);
        statisticsGridPane.add(propertiesBoughtAndSoldPieChart, 0, 1);

        BarChart<Number, String> revenueAndExpensesBarChart =
                createRevenueAndExpensesBarChart("Revenue and Expenses", addressBookStatistics);
        statisticsGridPane.add(revenueAndExpensesBarChart, 1, 0);

        PieChart revenueAndExpensesPieChart =
                createRevenueAndExpensesPieChart("Revenue vs Expense", addressBookStatistics);
        statisticsGridPane.add(revenueAndExpensesPieChart, 1, 1);
        logger.info("Updated Statistics View");
    }

    /**
     * Returns a {@code BarChart} comparing the {@code PropertiesSold} and {@code PropertiesBought}.
     *
     * @param title of the {@code BarChart}
     * @param stats of the existing {@code AddressBookStatistics}
     * @return {@code BarChart}
     */
    private BarChart<Number, String> createPropertiesSoldAndBoughtBarChart(String title, AddressBookStatistics stats) {
        CategoryAxis yAxis = new CategoryAxis();
        NumberAxis xAxis = new NumberAxis();

        BarChart<Number, String> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);
        barChart.setPrefWidth(500);

        XYChart.Series<Number, String> boughtSeries = new XYChart.Series<>();
        XYChart.Series<Number, String> soldSeries = new XYChart.Series<>();

        boughtSeries.getData().add(new XYChart.Data<>(stats.getTotalPropertiesBought(), "Properties Bought"));
        soldSeries.getData().add(new XYChart.Data<>(stats.getTotalPropertiesSold(), "Properties Sold"));

        barChart.getData().add(boughtSeries);
        barChart.getData().add(soldSeries);

        barChart.getStyleClass().add("chart-container");

        barChart.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/barChart0.css"))
                .toExternalForm());

        barChart.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(barChart, javafx.scene.layout.Priority.ALWAYS);

        return barChart;
    }

    /**
     * Returns a {@code BarChart} comparing the {@code SalesRevenue} and {@code PurchaseExpense}.
     *
     * @param title of the {@code BarChart}
     * @param stats of the existing {@code AddressBookStatistics}
     * @return {@code BarChart}
     */
    private BarChart<Number, String> createRevenueAndExpensesBarChart(String title, AddressBookStatistics stats) {
        CategoryAxis yAxis = new CategoryAxis();
        NumberAxis xAxis = new NumberAxis();

        BarChart<Number, String> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);
        barChart.setPrefWidth(500);

        XYChart.Series<Number, String> revenueSeries = new XYChart.Series<>();
        XYChart.Series<Number, String> expensesSeries = new XYChart.Series<>();

        revenueSeries.getData().add(new XYChart.Data<>(stats.getTotalSalesRevenue(), "Sales Revenue"));
        expensesSeries.getData().add(new XYChart.Data<>(stats.getTotalPurchaseExpense(), "Purchase Expense"));

        barChart.getData().add(revenueSeries);
        barChart.getData().add(expensesSeries);

        barChart.getStyleClass().add("chart-container");

        barChart.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/barChart1.css"))
                .toExternalForm());

        barChart.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(barChart, javafx.scene.layout.Priority.ALWAYS);

        return barChart;
    }

    /**
     * Returns a {@code PieChart} comparing the proportion of {@code PropertiesSold} against {@code PropertiesBought}.
     *
     * @param title of the {@code PieChart}
     * @param stats of the existing {@code AddressBookStatistics}
     * @return {@code PieChart}
     */
    private PieChart createPropertiesBoughtAndSoldPieChart(String title, AddressBookStatistics stats) {
        PieChart pieChart = new PieChart();
        pieChart.setTitle(title);
        pieChart.setPrefWidth(500);

        int propertiesBought = stats.getTotalPropertiesBought();
        int propertiesSold = stats.getTotalPropertiesSold();

        if (propertiesBought == 0 && propertiesSold == 0) {
            PieChart.Data placeholderData = new PieChart.Data("No Records yet", 100);
            pieChart.getData().add(placeholderData);

            placeholderData.getNode().setStyle("-fx-pie-color: lightgray;");

            placeholderData.getNode().setOnMouseEntered(event -> {
                pieChart.setTitle("Properties Bought: 0, Properties Sold : 0");
            });
            placeholderData.getNode().setOnMouseExited(event -> {
                pieChart.setTitle(title);
            });
        } else {
            PieChart.Data propertiesBoughtData = new PieChart.Data("Properties Bought", propertiesBought);
            PieChart.Data propertiesSoldData = new PieChart.Data("Properties Sold", propertiesSold);
            pieChart.getData().addAll(propertiesBoughtData, propertiesSoldData);
        }

        pieChart.getStyleClass().add("chart-container");

        pieChart.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/pieChart0.css"))
                .toExternalForm());

        pieChart.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(pieChart, javafx.scene.layout.Priority.ALWAYS);

        return pieChart;
    }

    /**
     * Returns a {@code PieChart} comparing the proportion of {@code SalesRevenue} against {@code PurchaseExpense}.
     *
     * @param title of the {@code PieChart}
     * @param stats of the existing {@code AddressBookStatistics}
     * @return {@code PieChart}
     */
    private PieChart createRevenueAndExpensesPieChart(String title, AddressBookStatistics stats) {
        PieChart pieChart = new PieChart();
        pieChart.setTitle(title);
        pieChart.setPrefWidth(500);


        int salesRevenue = stats.getTotalSalesRevenue();
        int purchaseExpense = stats.getTotalPurchaseExpense();

        if (salesRevenue == 0 && purchaseExpense == 0) {
            PieChart.Data placeholderData = new PieChart.Data("No Records yet", 100);
            pieChart.getData().add(placeholderData);

            placeholderData.getNode().setStyle("-fx-pie-color: lightgray;");

            placeholderData.getNode().setOnMouseEntered(event -> {
                pieChart.setTitle("Sales Revenue: $0, Purchase Expense: $0");
            });
            placeholderData.getNode().setOnMouseExited(event -> {
                pieChart.setTitle(title);
            });
        } else {
            PieChart.Data salesRevenueData = new PieChart.Data("Sales Revenue", salesRevenue);
            PieChart.Data purchaseExpenseData = new PieChart.Data("Purchase Expense", purchaseExpense);
            pieChart.getData().addAll(salesRevenueData, purchaseExpenseData);
        }

        pieChart.getStyleClass().add("chart-container");

        pieChart.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/pieChart1.css"))
                .toExternalForm());

        pieChart.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(pieChart, javafx.scene.layout.Priority.ALWAYS);

        return pieChart;
    }

}
