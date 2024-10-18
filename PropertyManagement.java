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

    public int countByType(Class<? extends Apartment> apartmentType) {
        return (int) dao.getApartments().stream()
                .filter(apartmentType::isInstance)
                .count();
    }

    public double calculateMeanCosts() {
        return dao.getApartments().stream()
                .mapToDouble(Apartment::getTotalCost)
                .average()
                .orElse(0);
    }

    public List<Integer> findOldestApartments() {
        int maxAge = dao.getApartments().stream()
                .mapToInt(Apartment::getAge)
                .max()
                .orElse(-1);

        return dao.getApartments().stream()
                .filter(a -> a.getAge() == maxAge)
                .map(Apartment::getId)
                .collect(Collectors.toList());
    }
}