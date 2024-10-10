public class RentedApartment extends Apartment {
    private final double rentPerSquareMeter;
    private final int numberOfTenants;

    public RentedApartment(int id, double area, int rooms, int floor, int yearOfConstruction,
                           int postalCode, String street, int houseNumber, int apartmentNumber,
                           double rentPerSquareMeter, int numberOfTenants) {
        super(id, area, rooms, floor, yearOfConstruction, postalCode, street, houseNumber, apartmentNumber);

        if (rentPerSquareMeter <= 0)
            throw new IllegalArgumentException("Error: Rent must be positive.");
        if (numberOfTenants <= 0)
            throw new IllegalArgumentException("Error: There must be at least one tenant.");

        this.rentPerSquareMeter = rentPerSquareMeter;
        this.numberOfTenants = numberOfTenants;
    }

    @Override
    public double getTotalCost() {
        double surchargeMultiplier = Math.min(1 + 0.025 * (numberOfTenants - 1), 1.1);
        return rentPerSquareMeter * getArea() * surchargeMultiplier;
    }

    @Override
    public String toString() {
        return String.format(
                """
                        Type:              RA
                        %s
                        Rent/m2:           %s
                        Number of Tenants: %d""",
                super.toString(),
                getDecimalFormat().format(rentPerSquareMeter),
                numberOfTenants
        );
    }
}