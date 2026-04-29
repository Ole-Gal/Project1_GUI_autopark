public class Car {
    private int id;         // ключ
    private String brand;   // марка
    private float price;    // цена
    private int year;       // год выпуска

    public Car(int id, String brand, float price, int year) {
        this.id = id;
        this.brand = brand;
        this.price = price;
        this.year = year;
    }

    public int getId() { return id; }
    public String getBrand() { return brand; }
    public float getPrice() { return price; }
    public int getYear() { return year; }

    public void setId(int id) { this.id = id; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setPrice(float price) { this.price = price; }
    public void setYear(int year) { this.year = year; }

    @Override
    public String toString() {
        return String.format("%5d | %-10s | %8.2f | %4d",
                id, brand, price, year);
    }
}
