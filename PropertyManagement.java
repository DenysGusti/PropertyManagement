import java.util.List;
import java.util.stream.Collectors;

public class PropertyManagement {
    private final PropertyManagementDAO dao;

    public PropertyManagement(PropertyManagementDAO dao) {
        this.dao = dao;
    }

    public List<Apartment> getAllApartments() {
        return dao.getApartments();
    }

    public Apartment getApartment(int id) {
        return dao.getApartmentById(id);
    }

    public void addApartment(Apartment apartment) {
        dao.saveApartment(apartment);
        System.out.println("Info: Apartment " + apartment.getId() + " added.");
    }

    public void deleteApartment(int id) {
        dao.deleteApartment(id);
        System.out.println("Info: Apartment " + id + " deleted.");
    }

    public int countAllApartments() {
        return dao.getApartments().size();
    }

    public int countByType(Class<?> apartmentType) {
        return (int) dao.getApartments().stream()
                .filter(apartmentType::isInstance)
                .count();
    }

    public double calculateMeanCosts() {
        List<Apartment> apartments = dao.getApartments();
        if (apartments.isEmpty())
            return 0;

        double totalCosts = apartments.stream()
                .mapToDouble(Apartment::getTotalCost)
                .sum();
        return totalCosts / apartments.size();
    }

    public List<Integer> findOldestApartments() {
        int maxAge = dao.getApartments().stream()
                .mapToInt(Apartment::getAge)
                .max()
                .orElseThrow(() -> new IllegalArgumentException("No apartments found."));

        return dao.getApartments().stream()
                .filter(a -> a.getAge() == maxAge)
                .map(Apartment::getId)
                .collect(Collectors.toList());
    }
}