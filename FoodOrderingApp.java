import java.util.*;

// Class representing a menu item
class MenuItem {
    String name;
    double price;

    MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

// Class representing a restaurant
class Restaurant {
    String name;
    List<MenuItem> menu;

    Restaurant(String name) {
        this.name = name;
        this.menu = new ArrayList<>();
    }

    void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    @Override
    public String toString() {
        return name;
    }
}

// Class representing an order
class Order {
    static int nextOrderId = 1;
    int orderId;
    String restaurantName;
    List<MenuItem> items;
    double totalAmount;
    String status; // Pending, In Progress, Delivered

    Order(String restaurantName, List<MenuItem> items) {
        this.orderId = nextOrderId++;
        this.restaurantName = restaurantName;
        this.items = new ArrayList<>(items);
        this.totalAmount = items.stream().mapToDouble(item -> item.price).sum();
        this.status = "Pending";
    }

    void updateStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId +
                "\nRestaurant: " + restaurantName +
                "\nItems: " + items +
                "\nTotal: $" + totalAmount +
                "\nStatus: " + status;
    }
}

// Main class for food ordering system
public class FoodOrderingApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Restaurant> restaurants = new ArrayList<>();
    private static final List<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        setupSampleData();
        System.out.println("Welcome to the Food Delivery App!");

        boolean running = true;
        while (running) {
            System.out.println("\n1. View Restaurants");
            System.out.println("2. Place an Order");
            System.out.println("3. View Orders");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> viewRestaurants();
                case 2 -> placeOrder();
                case 3 -> viewOrders();
                case 4 -> {
                    running = false;
                    System.out.println("Thank you for using the Food Delivery App!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void setupSampleData() {
        // Add sample restaurants and menus
        Restaurant r1 = new Restaurant("Pizza Palace");
        r1.addMenuItem(new MenuItem("Pepperoni Pizza", 12.99));
        r1.addMenuItem(new MenuItem("Margherita Pizza", 10.99));

        Restaurant r2 = new Restaurant("Burger Barn");
        r2.addMenuItem(new MenuItem("Cheeseburger", 8.99));
        r2.addMenuItem(new MenuItem("Veggie Burger", 7.99));

        restaurants.add(r1);
        restaurants.add(r2);
    }

    private static void viewRestaurants() {
        System.out.println("\nAvailable Restaurants:");
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println((i + 1) + ". " + restaurants.get(i));
        }
    }

    private static void placeOrder() {
        viewRestaurants();
        System.out.print("Select a restaurant (enter number): ");
        int restaurantIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (restaurantIndex < 0 || restaurantIndex >= restaurants.size()) {
            System.out.println("Invalid selection. Returning to main menu.");
            return;
        }

        Restaurant selectedRestaurant = restaurants.get(restaurantIndex);
        System.out.println("\nMenu of " + selectedRestaurant.name + ":");
        for (int i = 0; i < selectedRestaurant.menu.size(); i++) {
            System.out.println((i + 1) + ". " + selectedRestaurant.menu.get(i));
        }

        List<MenuItem> selectedItems = new ArrayList<>();
        while (true) {
            System.out.print("Enter item number to add to your order (0 to finish): ");
            int menuItemIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if (menuItemIndex == -1) {
                break;
            } else if (menuItemIndex < 0 || menuItemIndex >= selectedRestaurant.menu.size()) {
                System.out.println("Invalid selection. Please try again.");
            } else {
                selectedItems.add(selectedRestaurant.menu.get(menuItemIndex));
                System.out.println("Added: " + selectedRestaurant.menu.get(menuItemIndex));
            }
        }

        if (selectedItems.isEmpty()) {
            System.out.println("No items selected. Returning to main menu.");
            return;
        }

        Order newOrder = new Order(selectedRestaurant.name, selectedItems);
        orders.add(newOrder);
        System.out.println("\nOrder placed successfully!");
        System.out.println(newOrder);
    }

    private static void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders have been placed yet.");
            return;
        }

        System.out.println("\nYour Orders:");
        for (Order order : orders) {
            System.out.println(order);
            System.out.println("----------------------");
        }
    }
}
