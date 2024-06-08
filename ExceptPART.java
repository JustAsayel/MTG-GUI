import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ConcurrentModificationException;

class NoSuchFieldException extends RuntimeException {
    public NoSuchFieldException(String message) {
        super(message);
    }
}

class NoSuchMethodException extends RuntimeException {
    public NoSuchMethodException(String message) {
        super(message);
    }
}

class InsufficientBudgetException extends RuntimeException {
    public InsufficientBudgetException(String message) {
        super(message);
    }
}

class InvalidCityException extends RuntimeException {
    public InvalidCityException(String message) {
        super(message);
    }
}

class InvalidPaymentMethodException extends RuntimeException {
    public InvalidPaymentMethodException(String message) {
        super(message);
    }
}

class InputMismatchException extends RuntimeException {
    public InputMismatchException(String message) {
        super(message);
    }
}

class NoSuchElementException extends RuntimeException {
    public NoSuchElementException(String message) {
        super(message);
    }
}

class ConcurrentModificationException extends RuntimeException {
    public ConcurrentModificationException(String message) {
        super(message);
    }
}

/* Super class */
class Cities {
    int budget;
    int days = 7;
    static String[] WeekDays = { " Sunday :", " Monday :", " Tuesday :", " Wednesday :", " Thursday :", " Friday :",
            " Saturday :" };

    public void setBudget(int newBud) {
        budget = newBud;
    }

    public void Prnint(String[] x) {
        System.out.println("\nYour schedule for a week :");
        for (int i = 0; i < 7; i++) {
            System.out.print("\n" + WeekDays[i] + "\n");
            System.out.print(x[i] + "\n");
        }
    }

    public void printSchedule(String[] highSchedule, String[] lowSchedule) {
        if (budget >= 2500) {
            Prnint(highSchedule);
        } else {
            Prnint(lowSchedule);
        }
    }

    public void printSchedule(String[] highSchedule) {
        if (budget >= 2500) {
            Prnint(highSchedule);
        } else {
            System.out.print("Sorry, your budget is very low try again...."); /* Possibility of incorrect entry */
        }
    }

    public void printSchedule() {
    }

    public static String[] Rotate(String[] arr, int rotations) {
        int length = arr.length;
        rotations = rotations % length;
        String[] rotatedSchedule = new String[length];
        for (int i = 0; i < length; i++) {
            int rotatedIndex = (i + rotations) % length;
            rotatedSchedule[rotatedIndex] = arr[i];
        }
        return rotatedSchedule;
    }

    public static void PrintRotatedSchedule(String[] RotatedSchedule) {
        for (int i = 0; i < 7; i++) {
            System.out.print("\n" + WeekDays[i] + "\n");
            System.out.print(RotatedSchedule[i] + "\n");
        }
    }
}

class Riyadh extends Cities {
    String[] HriyadhScheduale = {
            " 1-Breakfast(hotel)\n 2-Kingdom Suspension Bridge\n 3-Riyadh Park\n 4-Beef Bar Restaurant",
            " 1-Lovefer Branch\n 2-Cinema (Boulevard)\n 3-Meraki Restuarant",
            " Free Day", " 1-Rishshaw London Branch\n 2-Winter Wonderland\n 3-PizzaBar Restaurant",
            " 1-Breakfast(hotel)\n 2-Al-Nakheel Mall\n 3-Oia Restuarant",
            " 1-Easy Bakery Branch\n 2-Dalila Camp Event",
            " 1-Breakfast(hotel)\n 2-KingAbdullah Financial District\n 3-Al-Nakheel Mall"
    };

    String[] LriyadhScheduale = {
            " 1-F60r Branch\n 2-KingAbdullah Park\n 3- Riyadh Front",
            " 1-Breakfast(hotel)\n 2- Winter Wonderland\n 3- Sign Restuarant",
            " 1-Breakfast(hotel)\n 2- Historic Murabba Palace\n 3- Roor Restuarant",
            " 1-Arkmi Restuarant\n 2- Al-Nakheel Mall\n 3- Riyadh Zoo\n 4- Roasted Way Restuarant",
            " Free Day", " 1-Salam Park\n 2- Suspension Bridge\n 3- Urban Restuarant\n"
    };

    @Override
    public void printSchedule() {
        printSchedule(HriyadhScheduale, LriyadhScheduale);
    }
}

class Jeddah extends Cities {
    String[] HjeddahScheduale = {
            "1-Caffeine Lab Branch\n  2-Al-Tayebat international city\n",
            " 1-BROOTS Coffee & Cocoaln\n  2-ALshallal",
            "1-Al-Khayyat CentreIn\n 2-Maqadeer Restuarant",
            "1-Fakieh Aquarium\n 2-THE YEMENI VILLAE",
            "1-Sakura Restuarant\n 2-Hemi Cafe & Roastrey ",
            " 1-Al-Arab Mall\n 2-San Carlo Cicchetti Restuarant\n"
    };

    String[] LjeddahScheduale = {
            "1-American Corner Restuarant\n 2-King Abdullah E",
            "1-Atallah Happy Land ParkIn\n 2-Fire",
            "1-Fakieh Aquarium\n 2-Zillion Restuarant\n",
            "1-IHOP Restuarant\n 2-AL-Tayebat internatio",
            "1-Thoul BeachIn\n 2-Al Sayed SeaFood Restauran",
            "1-Red Sea MallIn\n 2-Nando's\n 3- Roshn WaterFront\n"
    };

    @Override
    public void printSchedule() {
        printSchedule(HjeddahScheduale, LjeddahScheduale);
    }
}

class Abha extends Cities {
    String[] HabhaScheduale = {
            " 1-Al-Rashid Mall\n 2-Verde Restuarant", " 1-Le Voyage Restuarant\n 2-Abu Kheyal",
            " 1-Giorno Coffe\n 2-Mystrey Restuarant", " 1-Aya Sofy\n 2-High City\n 3-VotelSt",
            " 1-Almaha Mall\n 2-View Cafe", " 1-Al-Sahab Park\n 2-Black Box Cafe\n 3-Ala Bali Restuarant",
            " 1-Damside Park\n 2-Will Cafe", " 1-The Dabbab Walkway\n 2-Tonir Restuarant"
    };

    String[] LabhaScheduale = {
            "1  1-Le Voyage Restuarant\n 2-Abu Kheyal", " 1-Giorno Coffe\n 2-Mystrey Restuarant",
            " 1-Aya Sofy\n 2-High City\n 3-VotelSt", " 1-Almaha Mall\n 2-View Cafe",
            " 1-Al-Sahab Park\n 2-Black Box Cafe\n 3-Ala Bali Restuarant", " 1-Damside Park\n 2-Will Cafe",
            " 1-The Dabbab Walkway\n 2-Tonir Restuarant"
    };

    @Override
    public void printSchedule() {
        printSchedule(HabhaScheduale, LabhaScheduale);
    }
}

class Alula extends Cities {
    String[] AlulaScheduale = {
            "1-The old city of AlUla\n 2-AlUla Museum\n 3- Go to the hotel (AlUla Mirrors)\n 4-Have dinner at Al Diwan Restaurant",
            "1-Madain Saleh\n 2-Dadan: It is an ancient Lihyanite city\n 3-Lunch: Al Manara Restaurant",
            "1-Elephant Rock: Take a tour to see the unique rock formation that resembles an elephant\n 2-Adventure activities: Enjoy activities like horse riding, dune bashing, and sandboarding in the desert\n 3-Dinner: Al-Fayrouz Restaurant",
            "1-AlUla Oasis\n 2- Madras Park\n 3-Go to AlUla Desert Resort\n 4-Have dinner at Al Khaira Restaurant",
            "Free Day",
            "1-Old railway station\n 2- Book a caravan in Al-Wadai and spend the rest of the day there"
    };

    @Override
    public void printSchedule() {
        Prnint(AlulaScheduale);
    }
}

class EasternProvince extends Cities {
    String[] HEasternScheduale = {
            "1-Arrival to Dammam Airport and entry to the (Sheraton Hotel)\n2-Have breakfast at the hotel\n3-Have lunch at( Lumiere) Restaurant\n4-Go shopping at( Nakheel Mall)\n",
            "1-Have breakfast at the hotel\n2-Go to( Paul Gardenia) Resort in Al Khobar and spend the day there\n",
            "1-Have breakfast at (Solas )Restaurant 2-Ithra visit\n3-Watch a movie at (Ajdan Walk Cinema)\n4-Have dinner at the hotel\n",
            "Free Day\n",
            "1-Have breakfast at the hotel\n2-Go shopping at (Dhahran Mall)\n3-Have dinner at (City Walk)\n",
            "1-Have lunch at (Parkers) Restaurant\n2-Visit the Scitech Museum\n3-Go to Deghaithir Island and enjoy the food and scenery\n",
            "1-Visit (Loupage) Water Games\n2-Go shopping at (Al Khobar Plaza)\n3-Have dinner at (Miso) Restaurant\n"
    };

    String[] LEasternScheduale = {
            "1-Arrival to Dammam Airport and entry to (Braira Hotel)\n2-Go to the beach\n3- Have dinner at(Onion Restaurant)\n",
            "1-Have breakfast at (Daily Rose) Restaurant\n2- Watch a movie at (Nakheel Mall Cinema)\n3- (Ithra visit)\n",
            "1-Have breakfast at the hotel\n 2- Visit (Half Moon) Bay and spend the day there\n3-Eat dinner at (McDonald's)\n",
            "Free day\n",
            "1- Visit (Al-Ahsa region)\n2- Visit the(date factory)\n3- Have lunch at (Erbil Restaurant)\n4-Visiting the (village markets in Al-Ahsa)\n",
            "1-Have breakfast (at City Walk)\n2- Go shopping at (Al Khobar Mall)\n3- Have dinner at (KFC)\n",
            "1-Visit (Lobo Jan Water Games)\n2- Have lunch at (Nando's)\n"
    };

    @Override
    public void printSchedule() {
        printSchedule(HEasternScheduale, LEasternScheduale);
    }
}

// Asayel
class PersonalInformation {
    private String name;
    private int age;
    private String nationality;
    private double budget;
    private Person[] people;

    public PersonalInformation(String name, int age, String nationality, double budget) {
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.budget = budget;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addPeople(Scanner input) {
        System.out.print("Enter the number of people: ");
        int numPeople = input.nextInt();
        input.nextLine();
        people = new Person[numPeople];
        for (int i = 0; i < numPeople; i++) {
            System.out.print("Enter name for person " + (i + 1) + ": ");
            String name = input.nextLine();
            System.out.print("Enter age for person " + (i + 1) + ": ");
            int age = input.nextInt();
            input.nextLine();
            Person person = new Person(name, age);
            people[i] = person;
        }
    }

    public double convertBudgetToSAR() {
        double convertedBudget;
        try {
            if (nationality.equalsIgnoreCase("USD")) {
                convertedBudget = budget * 3.75;
            } else if (nationality.equalsIgnoreCase("SAR")) {
                convertedBudget = budget;
            } else {
                throw new IllegalArgumentException("Invalid nationality. Only USD or SAR allowed.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            convertedBudget = 0.0;
        }
        return convertedBudget;
    }

    public void inquireAboutAdditionalServices() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Do you need additional services for your tourism? (Yes/No): ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Yes")) {
                additionalServicesNeeded = true;
                provideAdditionalServices();
            } else if (choice.equalsIgnoreCase("No")) {
                additionalServicesNeeded = false;
            } else {
                throw new IllegalArgumentException("Invalid choice. Please enter Yes or No.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private void provideAdditionalServices() {
        System.out.println("Additional services provided:");
        System.out.println("1. City tour");
        System.out.println("2. Airport pickup");
        System.out.println("3. Guided excursions");
    }
}

// Norah
class AdditionalService {
    protected String serviceName;
    protected double serviceCost;

    public AdditionalService(String serviceName, double serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void displayServiceDetails() {
        System.out.println("Service: " + serviceName);
        System.out.println("Cost: $" + serviceCost);
    }
}

class CarRentalService extends AdditionalService {
    public CarRentalService() {
        super("Car Rental", 50.0);
    }

    public void rentCar(String carType, int duration) {
        System.out.println("Car rented: " + carType + " for " + duration + " days.");
    }
}

class SpecialTreatmentService extends AdditionalService {
    public SpecialTreatmentService() {
        super("Special Treatment", 100.0);
    }

    public void provideSpaTreatment() {
        System.out.println("Spa treatment provided.");
    }

}

class AdditionalServices {
    public void inquireAboutAdditionalServices() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select the additional services you need:");
        System.out.println("1. Car rental");
        System.out.println("2. Special treatment");
        int serviceChoice = scanner.nextInt();
        switch (serviceChoice) {
            case 1:
                System.out.println("You have selected car rental service.");
                CarRentalService carRentalService = new CarRentalService();
                carRentalService.displayServiceDetails(); // Display service details
                System.out.print("Enter the type of car: ");
                String carType = scanner.next();
                System.out.print("Enter the duration (in days) for rental: ");
                int duration = scanner.nextInt();
                carRentalService.rentCar(carType, duration);
                break;
            case 2:
                System.out.println("You have selected special treatment service.");
                SpecialTreatmentService specialTreatmentService = new SpecialTreatmentService();
                specialTreatmentService.displayServiceDetails(); // Display service details
                specialTreatmentService.provideSpaTreatment();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
        scanner.close();
    }
}

// Haifa
class Trip {
    private Calendar startDate;
    private Calendar endDate;

    public Trip(Calendar startDate, Calendar endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getDurationInDays() {
        long diffInMillis = endDate.getTimeInMillis() - startDate.getTimeInMillis();
        return (int) (diffInMillis / (24 * 60 * 60 * 1000)) + 1;
    }
}

class TripValidator {
    public static boolean validateTripDuration(Calendar start, Calendar end) {
        long durationInMillis = end.getTimeInMillis() - start.getTimeInMillis();
        int durationInDays = (int) (durationInMillis / (24 * 60 * 60 * 1000)) + 1;
        return durationInDays >= 2 && durationInDays <= 7;
    }
}

// Atheer
class PAYMENT {
    private double amount;
    private int pmentMethod_num;
    private String[] paymentMethod = { "Credit Card", "PayPal" };

    public PAYMENT(double amount, int pmentMethod_num) {
        this.amount = amount;
        this.pmentMethod_num = pmentMethod_num;
    }

    public void processPayment() {
        try {
            if (pmentMethod_num < paymentMethod.length)
                System.out.println("You selected payment Method: " + paymentMethod[pmentMethod_num]);
            else
                throw new ArrayIndexOutOfBoundsException(
                        "You must enter a number corresponding to a payment method from the list.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
        }
        try {
            if (amount > 0)
                System.out.println("Payment processed successfully for " + amount + " via "
                        + paymentMethod[pmentMethod_num]);
            else
                throw new IllegalArgumentException("Amount must be greater than zero.");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}

// Lama
class Rating {
    public void evaluateProgram() {
        Scanner rate = new Scanner(System.in);

        try {
            System.out.println(
                    "On a scale of 1 to 5, with 1 being very dissatisfied and 5 being very satisfied, how would you rate your overall satisfaction with the flight schedule?");
            int flightScheduleSatisfaction = rate.nextInt();
            if (flightScheduleSatisfaction < 1 || flightScheduleSatisfaction > 5) {
                throw new IllegalArgumentException("Invalid rating. Rating must be between 1 and 5.");
            }
            System.out.println(
                    "On a scale of 1 to 5, with 1 being very dissatisfied and 5 being very satisfied, how would you rate your overall satisfaction with the customer service?");
            int customerServiceSatisfaction = rate.nextInt();
            if (customerServiceSatisfaction < 1 || customerServiceSatisfaction > 5) {
                throw new IllegalArgumentException("Invalid rating. Rating must be between 1 and 5.");
            }

            if (flightScheduleSatisfaction >= 4) {
                System.out.println("You have expressed that you were very satisfied with the flight schedule.");
            } else if (flightScheduleSatisfaction >= 3) {
                System.out.println("You have expressed that you were somewhat satisfied with the flight schedule.");
            } else {
                System.out.println("You have expressed that you were not satisfied with the flight schedule.");
            }

            if (customerServiceSatisfaction >= 4) {
                System.out.println("You have expressed that you were very satisfied with the customer service.");
            } else if (customerServiceSatisfaction >= 3) {
                System.out
                        .println("You have expressed that you were somewhat satisfied with the customer service.");
            } else {
                System.out.println("You have expressed that you were not satisfied with the customer service.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            rate.close();
        }
    }
}

// Batool
class IT {
    public void reportProblem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please describe the problem you encountered:");
        scanner.nextLine();
        System.out.println("Thank you for reporting the problem. Our IT department will look into it.");
        scanner.close();
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        try {
            System.out.println("Welcome to Saudi Arabia Trip Planner\n");
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH) + 1;
            int day = now.get(Calendar.DAY_OF_MONTH);
            System.out.println("Today's date: " + day + "/" + month + "/" + year + "\n");

            // Trip planning menu
            System.out.println("We can help you plan your trip to the following cities:");
            System.out.println("1. Riyadh");
            System.out.println("2. Jeddah");
            System.out.println("3. Abha");
            System.out.println("4. Alula");
            System.out.println("5. Eastern Province");

            int city = in.nextInt();
            PersonalInformation a = new PersonalInformation();
            a.addPeople(in);
            Cities obj;

            switch (city) {
                case 1:
                    obj = new Riyadh();
                    break;
                case 2:
                    obj = new Jeddah();
                    break;
                case 3:
                    obj = new Abha();
                    break;
                case 4:
                    obj = new Alula();
                    break;
                case 5:
                    obj = new EasternProvince();
                    break;
                default:
                    throw new InvalidCityException("Invalid city selected. Please select a valid city.");
            }

            System.out.print("Enter your budget in USD: ");
            double budget = in.nextDouble();
            double convertedBudget = a.convertBudgetToSAR();
            obj.setBudget(convertedBudget);
            if (convertedBudget < 0) {
                throw new InsufficientBudgetException("Budget is insufficient for the selected city.");
            }

            obj.printSchedule();

            // Rotating Schedule
            System.out.print("\nDo you want to rotate the schedule? (yes/no): ");
            String rotateChoice = in.next();
            if (rotateChoice.equalsIgnoreCase("yes")) {
                System.out.print("Enter the number of rotations: ");
                int rotations = in.nextInt();
                String[] rotatedSchedule = Cities.Rotate(obj.HriyadhScheduale, rotations);
                System.out.println("\nRotated Schedule:");
                Cities.PrintRotatedSchedule(rotatedSchedule);
            }

            // Additional Services
            AdditionalServices additionalServices = new AdditionalServices();
            additionalServices.inquireAboutAdditionalServices();

            // Payment
            System.out.print("\nEnter payment details:\nPayment amount: ");
            double paymentAmount = in.nextDouble();
            System.out.println("Select payment method:");
            System.out.println("1. Credit Card");
            System.out.println("2. PayPal");
            System.out.print("Enter payment method number: ");
            int paymentMethod = in.nextInt();
            if (paymentMethod < 1 || paymentMethod > 2) {
                throw new InvalidPaymentMethodException(
                        "Invalid payment method selected. Please select a valid payment method.");
            }
            PAYMENT payment = new PAYMENT(paymentAmount, paymentMethod);
            payment.processPayment();

            // Rating
            Rating rating = new Rating();
            rating.evaluateProgram();

            IT it = new IT();
            it.reportProblem();
        } catch (InputMismatchException e) {
            System.out.println("Input mismatch error: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("No such element error: " + e.getMessage());
        } catch (NoSuchFieldException e) {
            System.out.println("No such field error: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.out.println("No such method error: " + e.getMessage());
        } catch (InvalidCityException e) {
            System.out.println("Invalid city error: " + e.getMessage());
        } catch (InsufficientBudgetException e) {
            System.out.println("Insufficient budget error: " + e.getMessage());
        } catch (InvalidPaymentMethodException e) {
            System.out.println("Invalid payment method error: " + e.getMessage());
        } catch (ConcurrentModificationException e) {
            System.out.println("Concurrent modification error: " + e.getMessage());
        } finally {
            in.close();
        }
    }
}