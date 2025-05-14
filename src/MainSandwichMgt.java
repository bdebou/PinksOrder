import com.abis.models.actors.Instructor;
import com.abis.models.actors.OfficeManager;
import com.abis.models.actors.Person;
import com.abis.models.sandwiches.*;
import com.abis.repositories.UnitOfWork;
import com.abis.repositories.exceptions.PersonNotFoundException;
import com.abis.repositories.exceptions.SandwichAlreadyExistsException;
import com.abis.repositories.exceptions.SandwichNotFoundException;
import com.abis.repositories.exceptions.TypeNotImplementedException;
import com.abis.services.PersonService;
import com.abis.services.SandwichService;
import com.abis.services.exceptions.NotAuthorizedException;

import java.util.List;
import java.util.Scanner;

public class MainSandwichMgt {
    public static void main(String[] args) {
        UnitOfWork uow = new UnitOfWork();
        SandwichService sandwichService = new SandwichService(uow);
        PersonService personService = new PersonService(uow);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello, Who are you ?");
        System.out.println("Please provide your email address:");
        String email = scanner.nextLine();

        try {
            OfficeManager officeManager = (OfficeManager) personService.getPersonByEmail(email);
            do {
                try {
                    System.out.println("Do you want to Add or Remove a sandwich or stop to quit? (A/R)");
                    String action = scanner.nextLine();
                    if (action.equalsIgnoreCase("stop")) {
                        break;
                    } else if (action.substring(0, 1).equalsIgnoreCase("A")) {
                        createNewSandwich(sandwichService, officeManager);
                    } else if (action.substring(0, 1).equalsIgnoreCase("R")) {
                        removeSandwich(sandwichService, officeManager);
                    } else {
                        System.out.println("Wrong action, please retry");
                    }

                } catch (SandwichNotFoundException | TypeNotImplementedException | SandwichAlreadyExistsException e) {
                    System.out.println(e.getMessage());
                }
            } while (true);
        } catch (PersonNotFoundException | NotAuthorizedException e) {
            System.out.println(e.getMessage());
            System.out.println("You are not an OfficeManager");
        }

    }

    private static void removeSandwich(SandwichService sandwichService, Person person) throws NotAuthorizedException, SandwichNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(sandwichService.printListOfAllSandwiches());
        do {
            System.out.println("Which sandwich do you want to remove from the list above ? (or stop to quit)");
            String sandwichToRemove = scanner.nextLine();
            if ("stop".equalsIgnoreCase(sandwichToRemove)) break;
            sandwichService.removeSandwich(person, sandwichToRemove);
        } while (true);
    }

    private static void createNewSandwich(SandwichService sandwichService, Person person) throws TypeNotImplementedException, SandwichAlreadyExistsException, NotAuthorizedException {
        Scanner scanner = new Scanner(System.in);
        List<String> lstTypes = List.of("Meat", "Chicken", "Cheese", "Fish", "Special", "Vegetarian");
        String typeSandwich;
        do {
            System.out.println("""
                    Which kind of Sandwich ?:
                    - Meat
                    - Chicken
                    - Cheese
                    - Fish
                    - Special
                    - Vegetarian""");
            typeSandwich = scanner.nextLine();
        } while (!lstTypes.contains(typeSandwich));

        System.out.println("What is the French name of new sandwich ?");
        String frName = scanner.nextLine();
        System.out.println("What is the Dutch name of new sandwich ?");
        String nlName = scanner.nextLine();
        String frDesc = "";
        String nlDesc = "";
        if (typeSandwich.equalsIgnoreCase("Special") || typeSandwich.equalsIgnoreCase("Vegetarian")) {
            System.out.println("What is its French description ?");
            frDesc = scanner.nextLine();
            System.out.println("What is its Dutch description ?");
            nlDesc = scanner.nextLine();
        }
        System.out.println("What is its price ?");
        double price = scanner.nextDouble();
        Sandwich sandwich = switch (typeSandwich) {
            case "Meat" -> new Meat(frName, nlName, price);
            case "Chicken" -> new Chicken(frName, nlName, price);
            case "Cheese" -> new Cheese(frName, nlName, price);
            case "Fish" -> new Fish(frName, nlName, price);
            case "Special" -> new Special(frName, nlName, frDesc, nlDesc, price);
            case "Vegetarian" -> new Vegetarian(frName, nlName, frDesc, nlDesc, price);
            default -> throw new TypeNotImplementedException(typeSandwich);
        };
        sandwichService.addSandwich(person, sandwich);
    }
}
