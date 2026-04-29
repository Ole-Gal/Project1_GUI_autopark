import java.util.Comparator;

public class CompPriceDesc implements Comparator<Car> {
    @Override
    public int compare(Car a, Car b) {
        return Float.compare(b.getPrice(), a.getPrice());
    }
}
