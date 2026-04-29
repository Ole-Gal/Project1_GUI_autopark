import java.util.*;

public class Transfer {

    // -----------------------------
    // 1. Преобразование строки ? Car
    // -----------------------------
    public static Car lineToCar(String line, int fieldsNum) {
        if (line == null) return null;

        String[] words = line.split("[\\s,]+"); // разделители: пробелы и запятые
        if (words.length != fieldsNum) return null;

        try {
            int id = Integer.parseInt(words[0]);
            String brand = words[1];
            float price = Float.parseFloat(words[2]);
            int year = Integer.parseInt(words[3]);

            return new Car(id, brand, price, year);

        } catch (Exception e) {
            return null;
        }
    }

    // -----------------------------
    // 2. Преобразование списка строк ? список Car
    // -----------------------------
    public static List<Car> StringsToCars(List<String> lines) {
        if (lines == null) return null;

        List<Car> list = new ArrayList<>();

        for (String line : lines) {
            Car car = lineToCar(line, 4);
            if (car == null) return null; // ошибка формата
            list.add(car);
        }

        return list;
    }

    // -----------------------------
    // 3. Преобразование списка Car ? список строк
    // -----------------------------
    public static List<String> CarsToStrings(List<Car> cars) {
        List<String> lines = new ArrayList<>();

        for (Car c : cars) {
            String s = String.format("%d, %s, %.2f, %d",
                    c.getId(), c.getBrand(), c.getPrice(), c.getYear());
            lines.add(s);
        }

        return lines;
    }
}
