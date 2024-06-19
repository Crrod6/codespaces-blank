import java.util.ArrayList;
import java.util.List;

class Port {
    private int id; // ID του λιμανιού
    private String name; // Όνομα του λιμανιού
    private double lat; // Γεωγραφικό πλάτος
    private double lon; // Γεωγραφικό μήκος
    private List<Container> containers; // Λίστα κοντέινερ στο λιμάνι
    private List<Ship> ships; // Λίστα πλοίων στο λιμάνι

    public Port(int id, String name, double lat, double lon) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.containers = new ArrayList<>();
        this.ships = new ArrayList<>();
    }

    // Προσθήκη κοντέινερ στο λιμάνι
    public void addContainer(Container container) {
        containers.add(container);
    }

    // Προσθήκη πλοίου στο λιμάνι
    public void addShip(Ship ship) {
        ships.add(ship);
    }

    // Αφαίρεση πλοίου από το λιμάνι
    public void removeShip(Ship ship) {
        ships.remove(ship);
    }

    // Υπολογισμός απόστασης από άλλο λιμάνι
    public double calculateDistance(Port otherPort) {
        double lat1 = Math.toRadians(this.lat);
        double lon1 = Math.toRadians(this.lon);
        double lat2 = Math.toRadians(otherPort.getLat());
        double lon2 = Math.toRadians(otherPort.getLon());

        return Math.acos(
                (Math.sin(lat1) * Math.sin(lat2)) + 
                (Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1))
        ) * 6371; // 6371km είναι η ακτίνα της Γης
    }

    // Επιστροφή των ιδιοτήτων του λιμανιού
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return "Port{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", containers=" + containers +
                ", ships=" + ships +
                '}';
    }
}

class Ship {
    private int id; // ID του πλοίου
    private String name; // Όνομα του πλοίου
    private double fuelConsumptionPerKm; // Κατανάλωση καυσίμου ανά χλμ
    private double maxWeight; // Μέγιστο βάρος κοντέινερ
    private int maxContainers; // Μέγιστος αριθμός κοντέινερ
    private int maxHeavyContainers; // Μέγιστος αριθμός βαριών κοντέινερ
    private Port currentPort; // Τρέχον λιμάνι
    private double fuel; // Καύσιμο σε λίτρα
    private List<Container> containers; // Λίστα κοντέινερ στο πλοίο

    public Ship(int id, String name, double fuelConsumptionPerKm, double maxWeight, int maxContainers, int maxHeavyContainers, Port currentPort) {
        this.id = id;
        this.name = name;
        this.fuelConsumptionPerKm = fuelConsumptionPerKm;
        this.maxWeight = maxWeight;
        this.maxContainers = maxContainers;
        this.maxHeavyContainers = maxHeavyContainers;
        this.currentPort = currentPort;
        this.fuel = 0;
        this.containers = new ArrayList<>();
    }

    // Προσθήκη καυσίμου
    public void addFuel(double amount) {
        this.fuel += amount;
    }

    // Προσθήκη κοντέινερ στο πλοίο
    public void addContainer(Container container) {
        containers.add(container);
    }

    // Αφαίρεση κοντέινερ από το πλοίο
    public void removeContainer(Container container) {
        containers.remove(container);
    }

    // Επιστροφή των ιδιοτήτων του πλοίου
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getFuelConsumptionPerKm() {
        return fuelConsumptionPerKm;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public int getMaxContainers() {
        return maxContainers;
    }

    public int getMaxHeavyContainers() {
        return maxHeavyContainers;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public double getFuel() {
        return fuel;
    }

    public List<Container> getContainers() {
        return containers;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fuelConsumptionPerKm=" + fuelConsumptionPerKm +
                ", maxWeight=" + maxWeight +
                ", maxContainers=" + maxContainers +
                ", maxHeavyContainers=" + maxHeavyContainers +
                ", currentPort=" + currentPort.getName() +
                ", fuel=" + fuel +
                ", containers=" + containers +
                '}';
    }
}

abstract class Container {
    protected int id; // ID του κοντέινερ
    protected int weight; // Βάρος του κοντέινερ

    public Container(int id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    // Αφηρημένη μέθοδος για υπολογισμό κατανάλωσης καυσίμου
    public abstract double getFuelConsumption(double distance);

    // Επιστροφή των ιδιοτήτων του κοντέινερ
    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", weight=" + weight +
                '}';
    }
}

class SimpleContainer extends Container {
    public SimpleContainer(int id, int weight) {
        super(id, weight);
    }

    @Override
    public double getFuelConsumption(double distance) {
        return 20 * (weight / 1000.0) * distance;
    }
}

class RefrigeratedContainer extends Container {
    public RefrigeratedContainer(int id, int weight) {
        super(id, weight);
    }

    @Override
    public double getFuelConsumption(double distance) {
        return 35 * (weight / 1000.0) * distance;
    }
}

class LiquidContainer extends Container {
    public LiquidContainer(int id, int weight) {
        super(id, weight);
    }

    @Override
    public double getFuelConsumption(double distance) {
        return 27 * (weight / 1000.0) * distance;
    }
}

public class Main {
    public static void main(String[] args) {
        // Δημιουργία των λιμανιών από το αρχείο ports.txt
        List<Port> ports = readPortsFromFile("ports.txt");
        
        // Δημιουργία των πλοίων από το αρχείο ships.txt
        List<Ship> ships = readShipsFromFile("ships.txt", ports);
        
        // Δημιουργία των κοντέινερ από το αρχείο containers.txt
        List<Container> containers = readContainersFromFile("containers.txt");
        
        // Τοποθέτηση όλων των κοντέινερ στο λιμάνι του Πειραιά
        Port piraeus = ports.get(0);
        containers.forEach(piraeus::addContainer);
        
        // Προσομοίωση Α
        simulationA(ships, ports);
        
        // Εκτύπωση της κατάστασης των λιμανιών και των πλοίων μετά την προσομοίωση Α
        printPortsAndShipsStatus(ports, ships);
        
        // Επαναφορά στην αρχική κατάσταση
        resetToInitialState(ports, ships, containers);
        
        // Προσομοίωση Β
        simulationB(ships, ports);
        
        // Εκτύπωση της κατάστασης των λιμανιών και των πλοίων μετά την προσομοίωση Β
        printPortsAndShipsStatus(ports, ships);
    }

    private static List<Port> readPortsFromFile(String filename) {
        // Προσωρινή υλοποίηση
        return new ArrayList<>();
    }

    private static List<Ship> readShipsFromFile(String filename, List<Port> ports) {
        // Προσωρινή υλοποίηση
        return new ArrayList<>();
    }

    private static List<Container> readContainersFromFile(String filename) {
        // Προσωρινή υλοποίηση
        return new ArrayList<>();
    }

    private static void simulationA(List<Ship> ships, List<Port> ports) {
        // Προσωρινή υλοποίηση
    }

    private static void simulationB(List<Ship> ships, List<Port> ports) {
        // Προσωρινή υλοποίηση
    }

    private static void printPortsAndShipsStatus(List<Port> ports, List<Ship> ships) {
        // Προσωρινή υλοποίηση
        System.out.println("Λιμάνια:");
        ports.forEach(System.out::println);
        System.out.println("Πλοία:");
        ships.forEach(System.out::println);
    }

    private static void resetToInitialState(List<Port> ports, List<Ship> ships, List<Container> containers) {
        // Προσωρινή υλοποίηση
    }
}
