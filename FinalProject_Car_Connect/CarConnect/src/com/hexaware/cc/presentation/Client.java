/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (Main Module)
 * Date: 21/10/2024
 */


package com.hexaware.cc.presentation;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.sql.Date;
import com.hexaware.cc.entity.*;
import com.hexaware.cc.exception.*;
import com.hexaware.cc.service.*;

public class Client {
    @SuppressWarnings("resource")
    private static final Scanner scanner = new Scanner(System.in);
    
    private static ICustomer customerService;
    private static IVehicle vehicleService;
    private static IReservation reservationService;
    private static IAdmin adminService;

    static {
        try {
            customerService = new CustomerServiceImp();
            vehicleService = new VehicleServiceImp();
            reservationService = new ReservationServiceImp();
            adminService = new AdminServiceImp();
        } catch (DatabaseConnectionException e) {
            System.err.println("Failed to initialize services: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private static final AuthenticationService authService = new AuthenticationService(adminService, customerService);
    private static String currentUser = null;
    private static String currentRole = null;

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            try {
                if (currentUser == null) {
                    displayLoginMenu();
                    int choice = getIntInput("Enter your choice: ");
                    switch (choice) {
                        case 1 -> adminLogin();
                        case 2 -> customerLogin();
                        case 3 -> {
                            exit = true;
                            System.out.println("Thank you for using Car Connect. Goodbye!");
                        }
                        default -> System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    displayMainMenu();
                    int choice = getIntInput("Enter your choice: ");
                    handleMainMenuChoice(choice);
                    if (choice == 0) {
                        exit = true;
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void displayLoginMenu() {
        System.out.println("\n--- Car Connect Login ---");
        System.out.println("1. Admin Login");
        System.out.println("2. Customer Login");
        System.out.println("3. Exit");
    }

    private static void adminLogin() {
        try {
            String username = getStringInput("Enter username: ");
            String password = getStringInput("Enter password: ");
            
            if (authService.authenticateAdmin(username, password)) {
                currentUser = username;
                currentRole = "ADMIN";
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        } catch (AuthenticationException | DatabaseConnectionException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private static void customerLogin() {
        try {
            String username = getStringInput("Enter username: ");
            String password = getStringInput("Enter password: ");
            
            if (authService.authenticateCustomer(username, password)) {
                currentUser = username;
                currentRole = "CUSTOMER";
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        } catch (AuthenticationException | DatabaseConnectionException | CustomerNotFoundException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n--- Car Connect Main Menu ---");
        if ("ADMIN".equals(currentRole)) {
            System.out.println("1. Customer Operations");
            System.out.println("2. Vehicle Operations");
            System.out.println("3. Reservation Operations");
            System.out.println("4. Admin Operations");
            System.out.println("5. Generate Reports");
            System.out.println("0. Logout");
        } else {
            System.out.println("1. View Available Vehicles");
            System.out.println("2. My Reservations");
            System.out.println("3. Create Reservation");
            System.out.println("0. Logout");
        }
    }

    private static void handleMainMenuChoice(int choice) {
        try {
            if ("ADMIN".equals(currentRole)) {
                switch (choice) {
                    case 1 -> customerOperations();
                    case 2 -> vehicleOperations();
                    case 3 -> reservationOperations();
                    case 4 -> adminOperations();
                    case 5 -> generateReports();
                    case 0 -> logout();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } else {
                switch (choice) {
                    case 1 -> getAvailableVehicles();
                    case 3 -> createReservation();
                    case 0 -> logout();
                
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error processing your request: " + e.getMessage());
        }
    }


	private static void customerOperations() {
        boolean back = false;
        while (!back) {
            try {
                displayCustomerMenu();
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1 -> getCustomerById();
                    case 2 -> getCustomerByUsername();
                    case 3 -> registerCustomer();
                    case 4 -> updateCustomer();
                    case 5 -> deleteCustomer();
                    case 0 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error in customer operations: " + e.getMessage());
            }
        }
    }

    private static void displayCustomerMenu() {
        System.out.println("\n--- Customer Operations ---");
        System.out.println("1. Get Customer by ID");
        System.out.println("2. Get Customer by Username");
        System.out.println("3. Register New Customer");
        System.out.println("4. Update Customer");
        System.out.println("5. Delete Customer");
        System.out.println("0. Back to Main Menu");
    }

    private static void getCustomerById() {
        try {
            int customerId = getIntInput("Enter Customer ID: ");
            Customer customer = customerService.getCustomerById(customerId);
            if (customer != null) {
                System.out.println(customer);
            }
        } catch (CustomerNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error retrieving customer: " + e.getMessage());
        }
    }

    private static void getCustomerByUsername() {
        try {
            String username = getStringInput("Enter Username: ");
            Customer customer = customerService.getCustomerByUsername(username);
            if (customer != null) {
                System.out.println(customer);
            }
        } catch (CustomerNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error retrieving customer: " + e.getMessage());
        }
    }

    private static void registerCustomer() {
        try {
            Customer newCustomer = readCustomerData();
            customerService.registerCustomer(newCustomer);
            System.out.println("Customer registered successfully.");
        } catch (Exception e) {
            System.out.println("Failed to register customer: " + e.getMessage());
        }
    }

    private static void updateCustomer() {
        try {
            Customer updatedCustomer = readCustomerData();
            customerService.updateCustomer(updatedCustomer);
            System.out.println("Customer updated successfully.");
        } catch (Exception e) {
            System.out.println("Failed to update customer: " + e.getMessage());
        }
    }

    private static void deleteCustomer() {
        try {
            int customerIdToDelete = getIntInput("Enter Customer ID to delete: ");
            customerService.deleteCustomer(customerIdToDelete);
            System.out.println("Customer deleted successfully.");
        } catch (Exception e) {
            System.out.println("Failed to delete customer: " + e.getMessage());
        }
    }

    private static void vehicleOperations() {
        boolean back = false;
        while (!back) {
            try {
                displayVehicleMenu();
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1 -> getVehicleById();
                    case 2 -> getAvailableVehicles();
                    case 3 -> addVehicle();
                    case 4 -> updateVehicle();
                    case 5 -> removeVehicle();
                    case 0 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error in vehicle operations: " + e.getMessage());
            }
        }
    }

    private static void displayVehicleMenu() {
        System.out.println("\n--- Vehicle Operations ---");
        System.out.println("1. Get Vehicle by ID");
        System.out.println("2. Get Available Vehicles");
        System.out.println("3. Add New Vehicle");
        System.out.println("4. Update Vehicle");
        System.out.println("5. Remove Vehicle");
        System.out.println("0. Back to Main Menu");
    }

    private static void getVehicleById() {
        try {
            int vehicleId = getIntInput("Enter Vehicle ID: ");
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if (vehicle != null) {
                System.out.println(vehicle);
            }
        } catch (VehicleNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error retrieving vehicle: " + e.getMessage());
        }
    }

    private static void getAvailableVehicles() {
        try {
            List<Vehicle> availableVehicles = vehicleService.getAvailableVehicles();
            if (availableVehicles.isEmpty()) {
                System.out.println("No available vehicles found.");
            } else {
                System.out.println("Available Vehicles:");
                availableVehicles.forEach(System.out::println);
            }
        } catch (DatabaseConnectionException e) {
            System.out.println("Error retrieving available vehicles: " + e.getMessage());
        }
    }

    private static void addVehicle() {
        try {
            Vehicle newVehicle = readVehicleData();
            if (newVehicle != null) {
                vehicleService.addVehicle(newVehicle);
                System.out.println("Vehicle added successfully.");
            }
        } catch (Exception e) {
            System.out.println("Failed to add vehicle: " + e.getMessage());
        }
    }

    private static void updateVehicle() {
        try {
            Vehicle updatedVehicle = readVehicleData();
            if (updatedVehicle != null) {
                vehicleService.updateVehicle(updatedVehicle);
                System.out.println("Vehicle updated successfully.");
            }
        } catch (Exception e) {
            System.out.println("Failed to update vehicle: " + e.getMessage());
        }
    }

    private static void removeVehicle() {
        try {
            int vehicleId = getIntInput("Enter Vehicle ID to remove: ");
            vehicleService.removeVehicle(vehicleId);
            System.out.println("Vehicle removed successfully.");
        } catch (Exception e) {
            System.out.println("Failed to remove vehicle: " + e.getMessage());
        }
    }

    private static void reservationOperations() {
        boolean back = false;
        while (!back) {
            try {
                displayReservationMenu();
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1 -> getReservationById();
                    case 2 -> getReservationsByCustomerId();
                    case 3 -> createReservation();
                    case 4 -> updateReservation();
                    case 5 -> cancelReservation();
                    case 0 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error in reservation operations: " + e.getMessage());
            }
        }
    }

    private static void displayReservationMenu() {
        System.out.println("\n--- Reservation Operations ---");
        System.out.println("1. Get Reservation by ID");
        System.out.println("2. Get Reservations by Customer ID");
        System.out.println("3. Create New Reservation");
        System.out.println("4. Update Reservation");
        System.out.println("5. Cancel Reservation");
        System.out.println("0. Back to Main Menu");
    }

    private static void getReservationById() {
        try {
            int reservationId = getIntInput("Enter Reservation ID: ");
            Reservation reservation = reservationService.getReservationById(reservationId);
            if (reservation != null) {
                System.out.println(reservation);
            }
        } catch (ReservationException | DatabaseConnectionException e) {
            System.out.println("Error retrieving reservation: " + e.getMessage());
        }
    }

    private static void getReservationsByCustomerId() {
        try {
            int customerId = getIntInput("Enter Customer ID: ");
            List<Reservation> reservations = reservationService.getReservationsByCustomerId(customerId);
            if (reservations.isEmpty()) {
                System.out.println("No reservations found for this customer.");
            } else {
                System.out.println("Customer Reservations:");
                reservations.forEach(System.out::println);
            }
        } catch (DatabaseConnectionException e) {
            System.out.println("Error retrieving reservations: " + e.getMessage());
        }
    }

    private static void createReservation() {
        try {
            Reservation newReservation = readReservationData();
            if (newReservation != null) {
                reservationService.createReservation(newReservation);
                System.out.println("Reservation created successfully.");
            }
        } catch (Exception e) {
            System.out.println("Failed to create reservation: " + e.getMessage());
        }
    }

    private static void updateReservation() {
        try {
            Reservation updatedReservation = readReservationData();
            if (updatedReservation != null) {
                reservationService.updateReservation(updatedReservation);
                System.out.println("Reservation updated successfully.");
            }
        } catch (Exception e) {
            System.out.println("Failed to update reservation: " + e.getMessage());
        }
    }

    private static void cancelReservation() {
        try {
            int reservationId = getIntInput("Enter Reservation ID to cancel: ");
            reservationService.cancelReservation(reservationId);
            System.out.println("Reservation cancelled successfully.");
        } catch (Exception e) {
            System.out.println("Failed to cancel reservation: " + e.getMessage());
        }
    }

    private static void adminOperations() {
        boolean back = false;
        while (!back) {
            try {
                displayAdminMenu();
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1 -> getAdminById();
                    case 2 -> getAdminByUsername();
                    case 3 -> registerAdmin();
                    case 4 -> updateAdmin();
                    case 5 -> deleteAdmin();
                    case 0 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error in admin operations: " + e.getMessage());
            }
        }
    }

    private static void displayAdminMenu() {
        System.out.println("\n--- Admin Operations ---");
        System.out.println("1. Get Admin by ID");
        System.out.println("2. Get Admin by Username");
        System.out.println("3. Register New Admin");
        System.out.println("4. Update Admin");
        System.out.println("5. Delete Admin");
        System.out.println("0. Back to Main Menu");
    }

    private static void getAdminById() {
        try {
            int adminId = getIntInput("Enter Admin ID: ");
            Admin admin = adminService.getAdminById(adminId);
            if (admin != null) {
                System.out.println(admin);
            }
        } catch (AdminNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error retrieving admin: " + e.getMessage());
        }
    }

    private static void getAdminByUsername() {
        try {
            String username = getStringInput("Enter Username: ");
            Admin admin = adminService.getAdminByUsername(username);
            if (admin != null) {
                System.out.println(admin);
            }
        } catch (AdminNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error retrieving admin: " + e.getMessage());
        }
    }

    private static void registerAdmin() {
        try {
            Admin newAdmin = readAdminData();
            if (newAdmin != null) {
                adminService.registerAdmin(newAdmin);
                System.out.println("Admin registered successfully.");
            }
        } catch (Exception e) {
            System.out.println("Failed to register admin: " + e.getMessage());
        }
    }

    private static void updateAdmin() {
        try {
            Admin updatedAdmin = readAdminData();
            if (updatedAdmin != null) {
                adminService.updateAdmin(updatedAdmin);
                System.out.println("Admin updated successfully.");
            }
        } catch (Exception e) {
            System.out.println("Failed to update admin: " + e.getMessage());
        }
    }

    private static void deleteAdmin() {
        try {
            int adminId = getIntInput("Enter Admin ID to delete: ");
            adminService.deleteAdmin(adminId);
            System.out.println("Admin deleted successfully.");
        } catch (Exception e) {
            System.out.println("Failed to delete admin: " + e.getMessage());
        }
    }
    
    private static void generateReports() {
        try {
            if (!"ADMIN".equals(currentRole)) {
                System.out.println("Access denied. Admin privileges required.");
                return;
            }

            ReportGenerator reportGenerator = new ReportGenerator();
            System.out.println("\n--- Generate Reports ---");
            System.out.println("1. Reservation Summary");
            System.out.println("2. Vehicle Utilization");
            System.out.println("3. Revenue by Vehicle Type");
            System.out.println("4. Top Customers");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> {
                    Date startDate = getDateInput("Enter start date (YYYY-MM-DD): ");
                    Date endDate = getDateInput("Enter end date (YYYY-MM-DD): ");
                    Map<String, Object> summary = reportGenerator.generateReservationSummary(startDate, endDate);
                    System.out.println("Reservation Summary:");
                    summary.forEach((key, value) -> System.out.println(key + ": " + value));
                }
                case 2 -> {
                    List<Vehicle> highUtilization = reportGenerator.generateVehicleUtilizationReport();
                    System.out.println("High Utilization Vehicles:");
                    highUtilization.forEach(System.out::println);
                }
                case 3 -> {
                    Map<String, Double> revenueByType = reportGenerator.generateRevenueByVehicleType();
                    System.out.println("Revenue by Vehicle Type:");
                    revenueByType.forEach((type, revenue) -> 
                        System.out.println(type + ": $" + String.format("%.2f", revenue)));
                }
                case 4 -> {
                    List<Customer> topCustomers = reportGenerator.generateTopCustomersReport();
                    System.out.println("Top Customers:");
                    topCustomers.forEach(System.out::println);
                }
                default -> System.out.println("Invalid choice!");
            }
        } catch (Exception e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    private static void changePassword() {
        try {
            String oldPassword = getStringInput("Enter current password: ");
            String newPassword = getStringInput("Enter new password: ");
            
            if (authService.changePassword(currentUser, oldPassword, newPassword)) {
                System.out.println("Password changed successfully!");
            } else {
                System.out.println("Failed to change password. Please verify your current password.");
            }
        } catch (Exception e) {
            System.out.println("Error changing password: " + e.getMessage());
        }
    }

    private static void logout() {
        currentUser = null;
        currentRole = null;
        System.out.println("Logged out successfully!");
    }

    private static Vehicle readVehicleData() {
        try {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleID(getIntInput("Enter Vehicle ID: "));
            vehicle.setModel(getStringInput("Enter Model: "));
            vehicle.setMake(getStringInput("Enter Make: "));
            vehicle.setYear(getIntInput("Enter Year: "));
            vehicle.setColor(getStringInput("Enter Color: "));
            vehicle.setRegistrationNumber(getStringInput("Enter Registration Number: "));
            vehicle.setAvailability(true);
            vehicle.setDailyRate(getDoubleInput("Enter Daily Rate: "));
            return vehicle;
        } catch (Exception e) {
            System.out.println("Error reading vehicle data: " + e.getMessage());
            return null;
        }
    }

    private static Reservation readReservationData() {
        try {
            Reservation reservation = new Reservation();
            reservation.setReservationID(getIntInput("Enter Reservation ID: "));
            reservation.setCustomerID(getIntInput("Enter Customer ID: "));
            reservation.setVehicleID(getIntInput("Enter Vehicle ID: "));
            reservation.setStartDate(getDateInput("Enter Start Date (YYYY-MM-DD): "));
            reservation.setEndDate(getDateInput("Enter End Date (YYYY-MM-DD): "));
            reservation.setTotalCost(getDoubleInput("Enter Total Cost: "));
            reservation.setStatus("PENDING");
            return reservation;
        } catch (Exception e) {
            System.out.println("Error reading reservation data: " + e.getMessage());
            return null;
        }
    }

    private static Admin readAdminData() {
        try {
            Admin admin = new Admin();
            admin.setAdminID(getIntInput("Enter Admin ID: "));
            admin.setFirstName(getStringInput("Enter First Name: "));
            admin.setLastName(getStringInput("Enter Last Name: "));
            
            // Email validation
            while (true) {
                String email = getStringInput("Enter Email (must contain @): ");
                if (email.contains("@")) {
                    admin.setEmail(email);
                    break;
                }
                System.out.println("Invalid email format. Email must contain @");
            }
            
            // Phone number validation
            while (true) {
                String phone = getStringInput("Enter Phone Number (10 digits): ");
                if (phone.matches("\\d{10}")) {
                    admin.setPhoneNumber(phone);
                    break;
                }
                System.out.println("Invalid phone number. Must be exactly 10 digits");
            }
            
            admin.setUsername(getStringInput("Enter Username: "));
            
            // Password validation
            while (true) {
                String password = getStringInput("Enter Password (minimum 8 characters): ");
                if (password.length() >= 8) {
                    admin.setPassword(password);
                    break;
                }
                System.out.println("Invalid password. Must be at least 8 characters long");
            }
            
            admin.setRole(getStringInput("Enter Role: "));
            return admin;
        } catch (Exception e) {
            System.out.println("Error reading admin data: " + e.getMessage());
            return null;
        }
    }

    private static Customer readCustomerData() {
        try {
            Customer customer = new Customer();
            customer.setCustomerID(getIntInput("Enter Customer ID: "));
            customer.setFirstName(getStringInput("Enter First Name: "));
            customer.setLastName(getStringInput("Enter Last Name: "));
            
            // Email validation
            while (true) {
                String email = getStringInput("Enter Email (must contain @): ");
                if (email.contains("@")) {
                    customer.setEmail(email);
                    break;
                }
                System.out.println("Invalid email format. Email must contain @");
            }
            
            // Phone number validation
            while (true) {
                String phone = getStringInput("Enter Phone Number (10 digits): ");
                if (phone.matches("\\d{10}")) {
                    customer.setPhoneNumber(phone);
                    break;
                }
                System.out.println("Invalid phone number. Must be exactly 10 digits");
            }
            
            customer.setAddress(getStringInput("Enter Address: "));
            customer.setUsername(getStringInput("Enter Username: "));
            
            // Password validation
            while (true) {
                String password = getStringInput("Enter Password (minimum 8 characters): ");
                if (password.length() >= 8) {
                    customer.setPassword(password);
                    break;
                }
                System.out.println("Invalid password. Must be at least 8 characters long");
            }
            
            customer.setRegistrationDate(new Date(System.currentTimeMillis()));
            return customer;
        } catch (Exception e) {
            System.out.println("Error reading customer data: " + e.getMessage());
            return null;
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static Date getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Date.valueOf(scanner.nextLine().trim());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            }
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}