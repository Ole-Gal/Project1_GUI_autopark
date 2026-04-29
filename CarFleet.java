    import java.util.*;

public class CarFleet {

    private String name;
    private List<Car> cars;

    public CarFleet() {
        name = "";
        cars = new ArrayList<>();
    }

    public CarFleet(String name) {
        this.name = name;
        cars = new ArrayList<>();
    }

    public CarFleet(String name, List<Car> list) {
        this.name = name;
        cars = new ArrayList<>(list);
    }

    public void setFleetName(String name) { this.name = name; }
    public String getFleetName() { return name; }
    public List<Car> getCars() { return cars; }

    @Override
    public String toString() {
        return String.format("Автопарк: %s, %5d автомобилей", name, getCarNum());
    }

    // ---- Добавление / удаление ----

    public boolean addCar(Car car) {
        if (getCar(car.getId()) != null) return false;
        return cars.add(car);
    }

    public boolean delCar(Car car) {
        return cars.remove(car);
    }

    public Car getCar(int id) {
        for (Car c : cars)
            if (c.getId() == id) return c;
        return null;
    }

    public int getCarNum() {
        return cars.size();
    }

    // ---- Выборки ----

    public float avgPrice() {
        if (cars.size() == 0) return 0;
        float sum = 0;
        for (Car c : cars) sum += c.getPrice();
        return sum / cars.size();
    }

    public CarFleet aboveAvgPrice() {
        float avg = avgPrice();
        CarFleet fleet = new CarFleet(
                String.format("%s\nАвтомобили с ценой выше средней %.2f:", name, avg)
        );
        for (Car c : cars)
            if (c.getPrice() > avg) fleet.addCar(c);
        return fleet;
    }

    public CarFleet betweenPrice(float p1, float p2) {
        CarFleet fleet = new CarFleet(
                String.format("%s\nАвтомобили с ценой в диапазоне %.2f–%.2f:", name, p1, p2)
        );
        for (Car c : cars)
            if (c.getPrice() >= p1 && c.getPrice() <= p2) fleet.addCar(c);
        return fleet;
    }

    // ---- Сортировки ----

    public CarFleet sort() {
        CarFleet fleet = new CarFleet(name, cars);
        fleet.cars.sort(Comparator.comparingInt(Car::getId));
        return fleet;
    }

    public CarFleet sort(Comparator<Car> comp, String msg) {
        CarFleet fleet = new CarFleet(String.format("%s\n%s:", name, msg), cars);
        fleet.cars.sort(comp);
        return fleet;
    }
    
    public void sortInPlaceById() {
    cars.sort(Comparator.comparingInt(Car::getId));
    }

    public void sortInPlaceByPrice() {
    cars.sort(Comparator.comparingDouble(Car::getPrice));
    }

    // ---- Итоги по годам ----

    public List<TotalRecord> totalCountSumYear() {
        if (cars.size() == 0) return null;

        List<Car> temp = new ArrayList<>(cars);
        SortedSet<Integer> years = new TreeSet<>();
        for (Car c : cars) years.add(c.getYear());

        List<Integer> yearList = new ArrayList<>(years);
        List<TotalRecord> result = new ArrayList<>();

        for (int year : yearList) {
            int count = 0;
            float sum = 0;

            Iterator<Car> it = temp.iterator();
            while (it.hasNext()) {
                Car c = it.next();
                if (c.getYear() == year) {
                    count++;
                    sum += c.getPrice();
                    it.remove();
                }
            }

            result.add(new TotalRecord(year, count, sum / count));
        }

        return result;
    }

    // ---- Фильтр ----

    public CarFleet filterOfName(String filter) {
        CarFleet fleet = new CarFleet(
                String.format("%s\nАвтомобили марки, начинающейся на \"%s\":", name, filter)
        );

        if (filter != null && !filter.isEmpty()) {
            filter = filter.toLowerCase();
            for (Car c : cars)
                if (c.getBrand().toLowerCase().startsWith(filter))
                    fleet.addCar(c);
        }

        return fleet;
    }

    // ---- Обновление записи ----

    public boolean updateCarByKey(Car car) {
        Car c = getCar(car.getId());
        if (c != null) {
            c.setBrand(car.getBrand());
            c.setPrice(car.getPrice());
            c.setYear(car.getYear());
            return true;
        }
        return false;
    }

    // ---- Групповое удаление ----

    public boolean deleteAvgPrice() {
        return cars.removeAll(aboveAvgPrice().cars);
    }
}
