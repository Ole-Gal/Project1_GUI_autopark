public class TotalRecord {
    private int year;
    private int count;
    private float avgPrice;

    public TotalRecord(int year, int count, float avgPrice) {
        this.year = year;
        this.count = count;
        this.avgPrice = avgPrice;
    }

    public int getYear() { return year; }
    public int getCount() { return count; }
    public float getAvgPrice() { return avgPrice; }

    @Override
    public String toString() {
        return String.format("%4d | %5d | %8.2f", year, count, avgPrice);
    }
}
