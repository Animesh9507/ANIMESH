// BloodDonor.java
public class BloodDonor {
    private String id;
    private String name;
    private String bloodType;
    private String contactNumber;
    private String lastDonationDate;
    
    public BloodDonor(String id, String name, String bloodType, String contactNumber) {
        this.id = id;
        this.name = name;
        this.bloodType = bloodType;
        this.contactNumber = contactNumber;
        this.lastDonationDate = "Never";
    }
    
    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getBloodType() { return bloodType; }
    public String getContactNumber() { return contactNumber; }
    public String getLastDonationDate() { return lastDonationDate; }
    public void setLastDonationDate(String date) { this.lastDonationDate = date; }
}

// BloodInventory.java
import java.util.HashMap;
import java.util.Map;

public class BloodInventory {
    private Map<String, Integer> bloodStock;
    
    public BloodInventory() {
        bloodStock = new HashMap<>();
        // Initialize blood types with 0 units
        bloodStock.put("A+", 0);
        bloodStock.put("A-", 0);
        bloodStock.put("B+", 0);
        bloodStock.put("B-", 0);
        bloodStock.put("AB+", 0);
        bloodStock.put("AB-", 0);
        bloodStock.put("O+", 0);
        bloodStock.put("O-", 0);
    }
    
    public void addBlood(String bloodType, int units) {
        bloodStock.put(bloodType, bloodStock.get(bloodType) + units);
    }
    
    public boolean removeBlood(String bloodType, int units) {
        int currentStock = bloodStock.get(bloodType);
        if (currentStock >= units) {
            bloodStock.put(bloodType, currentStock - units);
            return true;
        }
        return false;
    }
    
    public void displayInventory() {
        System.out.println("\nCurrent Blood Inventory:");
        System.out.println("------------------------");
        for (Map.Entry<String, Integer> entry : bloodStock.entrySet()) {
            System.out.printf("%s: %d units\n", entry.getKey(), entry.getValue());
        }
    }
}

// BloodDonationSystem.java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class BloodDonationSystem {
    private List<BloodDonor> donors;
    private BloodInventory inventory;
    private Scanner scanner;
    
    public BloodDonationSystem() {
        donors = new ArrayList<>();
        inventory = new BloodInventory();
        scanner = new Scanner(System.in);
    }
    
    public void start() {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    registerDonor();
                    break;
                case 2:
                    recordDonation();
                    break;
                case 3:
                    requestBlood();
                    break;
                case 4:
                    inventory.displayInventory();
                    break;
                case 5:
                    displayDonors();
                    break;
                case 6:
                    System.out.println("Thank you for using the Blood Donation System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void displayMenu() {
        System.out.println("\nBlood Donation Management System");
        System.out.println("1. Register New Donor");
        System.out.println("2. Record Blood Donation");
        System.out.println("3. Request Blood");
        System.out.println("4. View Blood Inventory");
        System.out.println("5. View Donor List");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private void registerDonor() {
        System.out.println("\nNew Donor Registration");
        System.out.print("Enter Donor ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Blood Type (A+/A-/B+/B-/AB+/AB-/O+/O-): ");
        String bloodType = scanner.nextLine().toUpperCase();
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();
        
        donors.add(new BloodDonor(id, name, bloodType, contactNumber));
        System.out.println("Donor registered successfully!");
    }
    
    private void recordDonation() {
        System.out.print("Enter Donor ID: ");
        String id = scanner.nextLine();
        
        BloodDonor donor = findDonor(id);
        if (donor != null) {
            donor.setLastDonationDate(LocalDate.now().toString());
            inventory.addBlood(donor.getBloodType(), 1);
            System.out.println("Donation recorded successfully!");
        } else {
            System.out.println("Donor not found!");
        }
    }
    
    private void requestBlood() {
        System.out.print("Enter blood type needed: ");
        String bloodType = scanner.nextLine().toUpperCase();
        System.out.print("Enter units needed: ");
        int units = scanner.nextInt();
        
        if (inventory.removeBlood(bloodType, units)) {
            System.out.println("Blood request fulfilled successfully!");
        } else {
            System.out.println("Insufficient blood units in inventory!");
        }
    }
    
    private void displayDonors() {
        System.out.println("\nRegistered Donors:");
        System.out.println("------------------");
        for (BloodDonor donor : donors) {
            System.out.printf("ID: %s, Name: %s, Blood Type: %s, Last Donation: %s\n",
                donor.getId(), donor.getName(), donor.getBloodType(), donor.getLastDonationDate());
        }
    }
    
    private BloodDonor findDonor(String id) {
        return donors.stream()
            .filter(donor -> donor.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    public static void main(String[] args) {
        BloodDonationSystem system = new BloodDonationSystem();
        system.start();
    }
}
