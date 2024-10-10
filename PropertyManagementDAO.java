import java.util.List;

public interface PropertyManagementDAO {
    List<Apartment> getApartments();

    Apartment getApartmentById(int id);

    void saveApartment(Apartment apartment);

    void deleteApartment(int id);
}