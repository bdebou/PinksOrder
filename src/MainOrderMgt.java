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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainOrderMgt {
    private static ResourceBundle bundle = ResourceBundle.getBundle("MainOrderStrings", Locale.getDefault());

    public static void main(String[] args) {

        UnitOfWork uow = new UnitOfWork();
        OrderService orderService = new OrderService(uow);
        SandwichService sandwichService = new SandwichService(uow);
        PersonService personService = new PersonService(uow);

        Scanner scanner = new Scanner(System.in);

        System.out.println(bundle.getString("main.hello"));
        System.out.println(bundle.getString("main.question.sessionName"));
        String sessionName = scanner.nextLine();
        do {
            System.out.println(bundle.getString("main.question.student.email"));
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
                System.out.println(bundle.getString("main.question.order.sandwich"));
                String sandName = scanner.nextLine();
                if (sandName.equalsIgnoreCase(bundle.getString("main.stop"))) break;
                try {
                    Sandwich sandwich = sandwichService.getSandwichByName(sandName);
                    if (sandwich instanceof Normal normalSandwich) {
                        System.out.println(bundle.getString("main.question.order.vegetables"));
                        normalSandwich.setSalad(scanner.nextLine().substring(0, 1).equalsIgnoreCase(bundle.getString("main.answer.yes").substring(0, 1)));
                    }
                    System.out.println(bundle.getString("main.question.order.kindBread"));
                    String kindBread = scanner.nextLine();
                    if (kindBread.substring(0, 1).equalsIgnoreCase(bundle.getString("main.answer.order.kindBread.grey").substring(0, 1))) {
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
            System.out.println(bundle.getString("main.question.nextStudent"));
            String nextStudent = scanner.nextLine();
            if (nextStudent.substring(0, 1).equalsIgnoreCase(bundle.getString("main.answer.no").substring(0, 1))) break;
        } while (true);

        System.out.println(orderService.printOutOrder());
//        System.out.println(orderService.printOutOrderASCII());
        String pathOrderFile = "C:\\temp\\javacourses\\BDB_out.txt";
        orderService.printOutOrderASCII(Path.of(pathOrderFile).toFile());
        System.out.println(String.format(bundle.getString("main.info.savedLocation"), pathOrderFile));

    }

    private static Person createNewPerson(String email) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(bundle.getString("main.question.student.firstName"));
        String firstname = scanner.nextLine();
        System.out.println(bundle.getString("main.question.student.lastName"));
        String lastName = scanner.nextLine();

        return new Student(firstname, lastName, email, null);
    }
}