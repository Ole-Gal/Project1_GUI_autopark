import java.util.Comparator;

public class CompNameAscPriceDesc implements Comparator<Car> {
    @Override
    public int compare(Car a, Car b) {
        int cmp = a.getBrand().compareToIgnoreCase(b.getBrand());
        if (cmp != 0) return cmp;
        return Float.compare(b.getPrice(), a.getPrice());
    }
}
