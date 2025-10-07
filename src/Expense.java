public class Expense {
    private int id;					// just id for some expense
    private String date;			// format: dd-mm-yyyy
    private String description;		// just a fckin description bro, what do you expect?
    private double amount;			// format: n.00

    public Expense(int id, String date, String description, double amount) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }

    public void setDescription(String desc) { this.description = desc; }
    public void setAmount(double amt) { this.amount = amt; }

    public String toCSV() { return id + "," + date + "," + description + "," + amount; }
    public String toString() { return String.format("%-4d %-12s %-12s Rp. %.2f", id, date, description, amount); }

}
