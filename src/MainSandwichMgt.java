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
import net.bytebuddy.asm.Advice;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainSandwichMgt {

    private static ResourceBundle bundle = ResourceBundle.getBundle("MainSandwichStrings",Locale.getDefault());

    public static void main(String[] args) {
        UnitOfWork uow = new UnitOfWork();
        SandwichService sandwichService = new SandwichService(uow);
        PersonService personService = new PersonService(uow);

        Scanner scanner = new Scanner(System.in);

        System.out.println(bundle.getString("main.hello"));
        System.out.println(bundle.getString("main.wru"));
        String email = scanner.nextLine();

        try {
            Person officeManager =  personService.getPersonByEmail(email);
            do {
                try {
                    System.out.println(bundle.getString("main.question1"));
                    String action = scanner.nextLine();
                    if (action.equalsIgnoreCase(bundle.getString("main.stop"))) {
                        break;
                    } else if (action.substring(0, 1).equalsIgnoreCase(bundle.getString("main.action.add").substring(0, 1))) {
                        createNewSandwich(sandwichService, officeManager);
                    } else if (action.substring(0, 1).equalsIgnoreCase(bundle.getString("main.action.remove").substring(0, 1))) {
                        removeSandwich(sandwichService, officeManager);
                    } else {
                        System.out.println(bundle.getString("main.action.wrong"));
                    }

                } catch (SandwichNotFoundException | TypeNotImplementedException | SandwichAlreadyExistsException e) {
                    System.out.println(e.getMessage());
                }
            } while (true);
        } catch (PersonNotFoundException | NotAuthorizedException e) {
            System.out.println(e.getMessage());
            System.out.println(bundle.getString("main.notOfficeMgr"));
        }

    }

    private static void removeSandwich(SandwichService sandwichService, Person person) throws NotAuthorizedException, SandwichNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(sandwichService.printListOfAllSandwiches());
        do {
            System.out.println(bundle.getString("main.removeSandwich.question"));
            String sandwichToRemove = scanner.nextLine();
            if (bundle.getString("main.stop").equalsIgnoreCase(sandwichToRemove)) break;
            sandwichService.removeSandwich(person, sandwichToRemove);
        } while (true);
    }

    private static void createNewSandwich(SandwichService sandwichService, Person person) throws TypeNotImplementedException, SandwichAlreadyExistsException, NotAuthorizedException {
        Scanner scanner = new Scanner(System.in);
        List<String> lstTypes = List.of(
                bundle.getString("main.sandwich.type.meat"),
                bundle.getString("main.sandwich.type.chicken"),
                bundle.getString("main.sandwich.type.cheese"),
                bundle.getString("main.sandwich.type.fish"),
                bundle.getString("main.sandwich.type.special"),
                bundle.getString("main.sandwich.type.vegetarian")
        );
        String typeSandwich;
        do {
            System.out.println(bundle.getString("main.sandwich.add.question.type"));
            typeSandwich = scanner.nextLine();
        } while (!lstTypes.contains(typeSandwich));

        System.out.println(bundle.getString("main.sandwich.add.question.nameFR"));
        String frName = scanner.nextLine();
        System.out.println(bundle.getString("main.sandwich.add.question.nameNL"));
        String nlName = scanner.nextLine();
        String frDesc = "";
        String nlDesc = "";
        if (typeSandwich.equalsIgnoreCase(bundle.getString("main.sandwich.type.special")) || typeSandwich.equalsIgnoreCase(bundle.getString("main.sandwich.type.vegetarian"))) {
            System.out.println(bundle.getString("main.sandwich.add.question.descriptionFR"));
            frDesc = scanner.nextLine();
            System.out.println(bundle.getString("main.sandwich.add.question.descriptionNL"));
            nlDesc = scanner.nextLine();
        }
        System.out.println(bundle.getString("main.sandwich.add.question.price"));
        double price =Double.parseDouble(scanner.nextLine());
        Sandwich sandwich = switch (typeSandwich.toLowerCase()) {
            case "meat" -> new Meat(frName, nlName, price);
            case "chicken" -> new Chicken(frName, nlName, price);
            case "cheese" -> new Cheese(frName, nlName, price);
            case "fish" -> new Fish(frName, nlName, price);
            case "special" -> new Special(frName, nlName, frDesc, nlDesc, price);
            case "vegetarian" -> new Vegetarian(frName, nlName, frDesc, nlDesc, price);
            default -> throw new TypeNotImplementedException(typeSandwich);
        };
        sandwichService.addSandwich(person, sandwich);
    }
}
