public class OwnedApartment extends Apartment {
    private final double operatingCostsPerSquareMeter;
    private final double reserveFundPerSquareMeter;

    public OwnedApartment(int id, double area, int rooms, int floor, int yearOfConstruction,
                          int postalCode, String street, int houseNumber, int apartmentNumber,
                          double operatingCostsPerSquareMeter, double reserveFundPerSquareMeter) {
        super(id, area, rooms, floor, yearOfConstruction, postalCode, street, houseNumber, apartmentNumber);

        if (operatingCostsPerSquareMeter < 0)
            throw new IllegalArgumentException("Error: Invalid parameter.");
        if (reserveFundPerSquareMeter < 0)
            throw new IllegalArgumentException("Error: Invalid parameter.");

        this.operatingCostsPerSquareMeter = operatingCostsPerSquareMeter;
        this.reserveFundPerSquareMeter = reserveFundPerSquareMeter;
    }

    @Override
    public double getTotalCost() {
        double surchargeMultiplier = 1 + getFloor() * 0.02;
        return (operatingCostsPerSquareMeter + reserveFundPerSquareMeter) * getArea() * surchargeMultiplier;
    }

    @Override
    public String toString() {
        return String.format(
                """
                        Type:              OA
                        %s
                        Operating Costs:   %s
                        Reserve Fund:      %s""",
                super.toString(),
                getDecimalFormat().format(operatingCostsPerSquareMeter),
                getDecimalFormat().format(reserveFundPerSquareMeter)
        );
    }
}