

public class DataRecord {

    private String month;
    private double sales;

    public DataRecord(String date, double sales) {
        // Extract YYYY-MM from release_date (YYYY-MM-DD)
        this.month = date.substring(0, 7);
        this.sales = sales;
    }

    public String getMonth() {
        return month;
    }

    public double getSales() {
        return sales;
    }
}