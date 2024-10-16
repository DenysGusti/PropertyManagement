import java.util.List;

public class PropertyManagementClient {
    public static void main(String[] args) {
        try {
            if (args.length < 2)
                throw new IllegalArgumentException("Error: Invalid parameter.");

            String filename = args[0];
            String command = args[1];

            var dao = new PropertyManagementSerializationDAO(filename);
            var pm = new PropertyManagement(dao);

            switch (command.toLowerCase()) {
                case "list":
                    handleListCommand(pm, args);
                    break;
                case "add":
                    handleAddCommand(pm, args);
                    break;
                case "delete":
                    handleDeleteCommand(pm, args);
                    break;
                case "count":
                    handleCountCommand(pm, args);
                    break;
                case "meancosts":
                    handleMeanCostsCommand(pm);
                    break;
                case "oldest":
                    handleOldestCommand(pm);
                    break;
                default:
                    throw new IllegalArgumentException("Error: Invalid parameter.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid parameter.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleListCommand(PropertyManagement pm, String[] args) {
        if (args.length == 2) {
            List<Apartment> apartments = pm.getAllApartments();
            if (apartments.isEmpty())
                System.out.println("No apartments found.");

            else for (var apartment : apartments) {
                System.out.println(apartment);
                System.out.println();
            }
        } else if (args.length == 3) {
            int id = Integer.parseInt(args[2]);
            Apartment apartment = pm.getApartment(id);
            if (apartment != null) {
                System.out.println(apartment);
                System.out.println();
            }
        } else throw new IllegalArgumentException("Error: Invalid parameter.");
    }

    private static void handleAddCommand(PropertyManagement pm, String[] args) {
        if (args.length != 14)
            throw new IllegalArgumentException("Error: Invalid parameter.");

        String type = args[2];
        int id = Integer.parseInt(args[3]);
        double area = Double.parseDouble(args[4]);
        int rooms = Integer.parseInt(args[5]);
        int floor = Integer.parseInt(args[6]);
        int yearOfConstruction = Integer.parseInt(args[7]);
        int postalCode = Integer.parseInt(args[8]);
        String street = args[9];
        int houseNumber = Integer.parseInt(args[10]);
        int apartmentNumber = Integer.parseInt(args[11]);

        if (type.equalsIgnoreCase("OA")) {
            double operatingCosts = Double.parseDouble(args[12]);
            double reserveFund = Double.parseDouble(args[13]);
            var oa = new OwnedApartment(id, area, rooms, floor, yearOfConstruction, postalCode, street, houseNumber, apartmentNumber, operatingCosts, reserveFund);
            pm.addApartment(oa);

        } else if (type.equalsIgnoreCase("RA")) {
            double rent = Double.parseDouble(args[12]);
            int tenants = Integer.parseInt(args[13]);
            var ra = new RentedApartment(id, area, rooms, floor, yearOfConstruction, postalCode, street, houseNumber, apartmentNumber, rent, tenants);
            pm.addApartment(ra);

        } else throw new IllegalArgumentException("Error: Invalid parameter.");
    }

    private static void handleDeleteCommand(PropertyManagement pm, String[] args) {
        if (args.length != 3)
            throw new IllegalArgumentException("Error: Invalid parameter.");

        int id = Integer.parseInt(args[2]);
        pm.deleteApartment(id);
    }

    private static void handleCountCommand(PropertyManagement pm, String[] args) {
        if (args.length == 2) {
            int count = pm.countAllApartments();
            System.out.println(count);
        } else if (args.length == 3) {
            String type = args[2];
            if (type.equalsIgnoreCase("OA"))
                System.out.println(pm.countByType(OwnedApartment.class));
            else if (type.equalsIgnoreCase("RA"))
                System.out.println(pm.countByType(RentedApartment.class));

            else throw new IllegalArgumentException("Error: Invalid parameter.");
        } else throw new IllegalArgumentException("Error: Invalid parameter.");
    }

    private static void handleMeanCostsCommand(PropertyManagement pm) {
        double meanCosts = pm.calculateMeanCosts();
        System.out.println(Apartment.getDecimalFormat().format(meanCosts));
    }

    private static void handleOldestCommand(PropertyManagement pm) {
        List<Integer> oldestIds = pm.findOldestApartments();
        for (int id : oldestIds)
            System.out.println("Id: " + id);
    }
}