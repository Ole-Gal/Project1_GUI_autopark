import java.util.Comparator;

public class CompPriceAsc implements Comparator<Car> {
    @Override
    public int compare(Car a, Car b) {
        return Float.compare(a.getPrice(), b.getPrice());
    }
}
