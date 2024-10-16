import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Year;

public abstract class Apartment implements Serializable {
    private final int id;
    private final double area;
    private final int rooms;
    private final int floor;
    private final int yearOfConstruction;
    private final int postalCode;
    private final String street;
    private final int houseNumber;
    private final int apartmentNumber;

    public Apartment(int id, double area, int rooms, int floor, int yearOfConstruction,
                     int postalCode, String street, int houseNumber, int apartmentNumber) {
        if (id <= 0)
            throw new IllegalArgumentException("Error: Invalid parameter.");
        if (area <= 0)
            throw new IllegalArgumentException("Error: Invalid parameter.");
        if (rooms <= 0)
            throw new IllegalArgumentException("Error: Invalid parameter.");
        if (floor < 0)
            throw new IllegalArgumentException("Error: Invalid parameter.");
        if (yearOfConstruction > Year.now().getValue())
            throw new IllegalArgumentException("Error: Invalid year of construction.");
        if (postalCode <= 0)
            throw new IllegalArgumentException("Error: Invalid parameter.");
        if (street == null || street.trim().isEmpty())
            throw new IllegalArgumentException("Error: Invalid parameter.");
        if (houseNumber <= 0)
            throw new IllegalArgumentException("Error: Invalid parameter.");
        if (apartmentNumber <= 0)
            throw new IllegalArgumentException("Error: Invalid parameter.");

        this.id = id;
        this.area = area;
        this.rooms = rooms;
        this.floor = floor;
        this.yearOfConstruction = yearOfConstruction;
        this.postalCode = postalCode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
    }

    public int getId() {
        return id;
    }

    public double getArea() {
        return area;
    }

    public int getRooms() {
        return rooms;
    }

    public int getFloor() {
        return floor;
    }

    public int getYearOfConstruction() {
        return yearOfConstruction;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public int getAge() {
        return Year.now().getValue() - this.yearOfConstruction;
    }

    public abstract double getTotalCost();

    public static DecimalFormat getDecimalFormat() {
        var dfs = DecimalFormatSymbols.getInstance();
        dfs.setDecimalSeparator('.');
        return new DecimalFormat("0.00", dfs);
    }

    @Override
    public String toString() {
        return String.format(
                """
                        Id:                %d
                        Area:              %s
                        Rooms:             %d
                        Floor:             %d
                        Year Built:        %d
                        Postal Code:       %s
                        Street:            %s
                        House Number:      %s
                        Apartment:         %s""",
                id,
                getDecimalFormat().format(area),
                rooms,
                floor,
                yearOfConstruction,
                postalCode,
                street,
                houseNumber,
                apartmentNumber
        );
    }
}