	package ADTParkingGarageBADOY;
	
	import java.util.Scanner;
	import java.util.Random;
	
	public class ADTParkingGarageBADOY {
	
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	
	        ADTParkingGarageBADOY garage = new ADTParkingGarageBADOY(10);
	        String option;
	        do {
	            System.out.printf("Current garage capacity: %d/%d%n", garage.getCount(), garage.capacity);
	            System.out.println("Choose an option:");
	            System.out.println("1. Add cars");
	            System.out.println("2. Remove cars");
	            System.out.println("3. Change garage capacity");
	            System.out.println("4. Display garage license plates and lot numbers");
	            System.out.println("5. Exit");
	
	            option = scanner.nextLine();
	
	            switch (option) {
	                case "1":
	                    garage.addCars();
	                    break;
	                case "2":
	                    System.out.println("How many cars would you like to remove?");
	                    int numberOfCarsToRemove = scanner.nextInt();
	                    scanner.nextLine();
	                    garage.removeCars(numberOfCarsToRemove);
	                    break;
	                case "3":
	                    System.out.println("Enter the new capacity for garage:");
	                    int newCapacity = scanner.nextInt();
	                    scanner.nextLine();
	                    garage.changeCapacity(newCapacity);
	                    break;
	                case "4":
	                    garage.displayGarage();
	                    break;
	                case "5":
	                    System.out.println("Exiting program.");
	                    break;
	                default:
	                    System.out.println("Invalid option. Choose again.");
	            }
	        } while (!option.equals("5"));
	    }
	
	    private String[] lots;  //array to store license plates
	    private int capacity;   //maximum number of cars
	    private int count;      //current number of cars
	    private Random random;  //random number generator for license plates
	
	    //constructor for parking garage with a given capacity
	    public ADTParkingGarageBADOY(int capacity) {
	        this.capacity = capacity;
	        this.lots = new String[capacity];
	        this.count = 0;
	        this.random = new Random();
	    }
	
	    //generate a random license plate
	    private String generateLicensePlate() {
	        StringBuilder licensePlate = new StringBuilder();
	        //3 random uppercase letters
	        for (int i = 0; i < 3; i++) {
	            licensePlate.append((char) ('A' + random.nextInt(26)));
	        }
	        //3 random digits
	        for (int i = 0; i < 3; i++) {
	            licensePlate.append(random.nextInt(10));
	        }
	        return licensePlate.toString();
	    }
	
	    //park a car in the garage, assign a lot
	    public boolean parkCar(String licensePlate) {
	        if (isFull()) {
	            System.out.println("The garage is full. Cannot park more cars.");
	            return false;
	        }
	        for (int i = 0; i < capacity; i++) {
	            if (lots[i] == null) {
	                lots[i] = licensePlate;
	                System.out.println("Car with license plate " + licensePlate + " parked at lot " + (i + 1));
	                count++;
	                return true;
	            }
	        }
	        return false;
	    }
	
	    //add cars with user input and random generation
	    public void addCars() {
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("How many cars would you like to add?");
	        int totalCarsToAdd = scanner.nextInt();
	        scanner.nextLine();
	        System.out.println("How many of these license plates do you want to manually input?");
	        int manualCarCount = scanner.nextInt();
	        scanner.nextLine();
	        int randomCarCount = totalCarsToAdd - manualCarCount;
	
	        //manually inputted license plates
	        for (int i = 0; i < manualCarCount; i++) {
	            if (isFull()) {
	                System.out.println("The garage is full. Cannot park more cars.");
	                break;
	            }
	            System.out.print("Enter the license plate: ");
	            String licensePlate = scanner.nextLine();
	            parkCar(licensePlate);
	        }
	
	        //randomly generated license plates
	        for (int i = 0; i < randomCarCount; i++) {
	            if (isFull()) {
	                System.out.println("The garage is full. Cannot park more cars.");
	                break;
	            }
	            String generatedLicensePlate = generateLicensePlate();
	            System.out.println("Generated license plate: " + generatedLicensePlate);
	            parkCar(generatedLicensePlate);
	        }
	    }
	
	    //remove a specific license plate without shifting cars forward
	    public boolean retrieveCar(String licensePlate) {
	        for (int i = 0; i < capacity; i++) {
	            if (lots[i] != null && lots[i].equals(licensePlate)) {
	                System.out.println("Car with license plate " + licensePlate + " removed from lot " + (i + 1));
	                lots[i] = null;  //clear the lot without shifting
	                count--;
	                return true;
	            }
	        }
	        System.out.println("Car with license plate " + licensePlate + " not found.");
	        return false;
	    }
	
	    //remove a number of cars
	    public void removeCars(int numberOfCars) {
	        Scanner scanner = new Scanner(System.in);
	
	        for (int i = 0; i < numberOfCars; i++) {
	            if (isEmpty()) {
	                System.out.println("The garage is empty. No cars to remove.");
	                break;
	            }
	
	            System.out.print("Enter the license plate of the car to remove: ");
	            String licensePlate = scanner.nextLine();
	            retrieveCar(licensePlate);
	        }
	    }
	
	    //check if the garage is full
	    public boolean isFull() {
	        return count == capacity;
	    }
	    //method to check if the garage is empty
	    public boolean isEmpty() {
	        return count == 0;
	    }
	    //number of cars in the garage
	    public int getCount() {
	        return count;
	    }
	    
	    //method to change the garage capacity
	    public void changeCapacity(int newCapacity) {
	        if (newCapacity < count) {
	            System.out.println("New capacity is less than the current number of cars. Cannot resize.");
	            return;
	        }
	        this.capacity = newCapacity;
	        String[] newLots = new String[newCapacity];
	        System.arraycopy(lots, 0, newLots, 0, count);
	        this.lots = newLots;
	        System.out.println("Garage capacity changed to: " + newCapacity);
	    }
	
	    //method to display all slots, including empty ones
	    public void displayGarage() {
	        for (int i = 0; i < capacity; i++) {
	            if (lots[i] == null) {
	                System.out.println("Lot " + (i + 1) + ": Empty");
	            } else {
	                System.out.println("Lot " + (i + 1) + ": " + lots[i]);
	            }
	        }
	    }
	}
