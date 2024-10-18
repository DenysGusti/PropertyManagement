import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyManagementSerializationDAO implements PropertyManagementDAO {
    private final String filename;
    private List<Apartment> apartments;

    public PropertyManagementSerializationDAO(String filename) {
        this.filename = filename;
        this.apartments = new ArrayList<>();
        loadFromFile();
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        try (var ois = new ObjectInputStream(new FileInputStream(filename))) {
            apartments = (List<Apartment>) ois.readObject();
        } catch (FileNotFoundException e) {
            apartments.clear();
            saveToFile();
        } catch (Exception e) {
            System.out.println("Deserialization error: " + e.getMessage());
            System.exit(1);
        }
    }

    private void saveToFile() {
        try (var oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(apartments);
        } catch (IOException e) {
            System.out.println("Serialization error: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public List<Apartment> getApartments() {
        return apartments;
    }

    @Override
    public Apartment getApartmentById(int id) {
        return apartments.stream().filter(apartment -> apartment.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void saveApartment(Apartment apartment) {
        if (getApartmentById(apartment.getId()) != null)
            throw new IllegalArgumentException("Error: Apartment already exists. (id=" + apartment.getId() + ")");

        apartments.add(apartment);
        saveToFile();
    }

    @Override
    public void deleteApartment(int id) {
        Apartment apartment = getApartmentById(id);
        if (apartment == null)
            throw new IllegalArgumentException("Error: Apartment not found. (id=" + id + ")");

        apartments.remove(apartment);
        saveToFile();
    }
}