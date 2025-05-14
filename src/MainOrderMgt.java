import com.abis.models.Order;
import com.abis.models.actors.Person;
import com.abis.models.actors.Student;
import com.abis.models.enums.BreadType;
import com.abis.models.sandwiches.*;
import com.abis.repositories.UnitOfWork;
import com.abis.repositories.exceptions.PersonAlreadyExistsException;
import com.abis.repositories.exceptions.PersonNotFoundException;
import com.abis.repositories.exceptions.SandwichNotFoundException;
import com.abis.services.OrderService;
import com.abis.services.PersonService;
import com.abis.services.SandwichService;
import com.abis.models.exceptions.MaxSandwichesReachedException;

import java.nio.file.Path;
import java.util.Scanner;

public class MainOrderMgt {
    public static void main(String[] args) {
        UnitOfWork uow = new UnitOfWork();
        OrderService orderService = new OrderService(uow);
        SandwichService sandwichService = new SandwichService(uow);
        PersonService personService = new PersonService(uow);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello and welcome to order your sandwich !");
        System.out.println("Please enter the Session name ?");
        String sessionName = scanner.nextLine();
        do {
            System.out.println("What is the email address of student ?");
            String emailStudent = scanner.nextLine();
            Person student;
            try {
                student = personService.getPersonByEmail(emailStudent);
            } catch (PersonNotFoundException e) {
                student = createNewPerson(emailStudent);
                try {
                    personService.addNewStudent(student);
                } catch (PersonAlreadyExistsException ex) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println(sandwichService.printListOfAllSandwiches());
            Order order = new Order(sessionName, student);
            do {
                System.out.println("Which sandwich do you want ? (Please enter correct name OR stop)");
                String sandName = scanner.nextLine();
                if (sandName.equalsIgnoreCase("stop")) break;
                try {
                    Sandwich sandwich = sandwichService.getSandwichByName(sandName);
                    if (sandwich instanceof Normal normalSandwich) {
                        System.out.println("Do you want vegetables? (Y/N)");
                        normalSandwich.setSalad(scanner.nextLine().equalsIgnoreCase("y"));
                    }
                    System.out.println("Which kind of bread? Grey or White");
                    String kindBread = scanner.nextLine();
                    if (kindBread.substring(0, 1).equalsIgnoreCase("g")) {
                        sandwich.setKind(BreadType.GREY);
                    } else {
                        sandwich.setKind(BreadType.WHITE);
                    }
                    order.addSandwich(sandwich);
                } catch (SandwichNotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (MaxSandwichesReachedException e) {
                    System.out.println(e.getMessage());
                    break;
                }

            } while (true);
            orderService.registerNewOrder(order);
            System.out.println("Do you have another student to register ? (yes or No)");
            String nextStudent = scanner.nextLine();
            if (nextStudent.substring(0, 1).equalsIgnoreCase("n")) break;
        } while (true);

        System.out.println(orderService.printOutOrder());
//        System.out.println(orderService.printOutOrderASCII());
        String pathOrderFile = "C:\\temp\\javacourses\\BDB_out.txt";
        orderService.printOutOrderASCII(Path.of(pathOrderFile).toFile());
        System.out.println("The order has been saved under <" + pathOrderFile + ">");

    }

    private static Person createNewPerson(String email) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the firstname of Student ?");
        String firstname = scanner.nextLine();
        System.out.println("What is the lastname of student ?");
        String lastName = scanner.nextLine();

        return new Student(firstname, lastName, email, null);
    }
}