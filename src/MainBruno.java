import com.abis.models.Order;
import com.abis.models.actors.OfficeManager;
import com.abis.models.actors.Person;
import com.abis.models.sandwiches.Chicken;
import com.abis.models.sandwiches.Normal;
import com.abis.repositories.UnitOfWork;
import com.abis.repositories.exceptions.PersonNotFoundException;
import com.abis.repositories.exceptions.SandwichAlreadyExistsException;
import com.abis.repositories.exceptions.SandwichNotFoundException;
import com.abis.services.OrderService;
import com.abis.services.PersonService;
import com.abis.services.SandwichService;
import com.abis.services.exceptions.NotAuthorizedException;
import exception.MaxSandwichesReachedException;

public class MainBruno {
    public static void main(String[] args) {
        UnitOfWork uow = new UnitOfWork();
        OrderService orderService = new OrderService(uow);
        SandwichService sandwichService = new SandwichService(uow);
        PersonService personService = new PersonService(uow);

        System.out.println(sandwichService.printListOfAllSandwiches());
        try {
            Person bruno = personService.getPersonByEmail("bruno.deboubers@ing.com");
            Person raghunath = personService.getPersonByName("Raghunath", "Singh");
            Person instructor = personService.getPersonByName("Sandy", "Schillebeeckx");

            Order order = new Order("Java", bruno);
            try {
                Normal normalSand = (Normal) sandwichService.getSandwichByName("Gouda");
                normalSand.setSalad(true);

                order.addSandwich(normalSand);
                order.addSandwich(sandwichService.getSandwichByName("maya"));
                order.addSandwich(sandwichService.getSandwichByName("rosbif"));
            } catch (MaxSandwichesReachedException | SandwichNotFoundException e) {
                System.out.println(e.getMessage());
            }
            orderService.registerNewOrder(order);
            System.out.println(orderService.printOutOrder());

        } catch (PersonNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Person officeMgr = new OfficeManager("Sandy", "Schillebeeckx", "sandy.Schillebeeckx@abis.com");
        try {
            sandwichService.addSandwich(officeMgr, new Chicken("Poulet à l'andalouse", "Andalusische kip", 3.7D));
        } catch (SandwichAlreadyExistsException | NotAuthorizedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------");
        System.out.println(sandwichService.printListOfAllSandwiches());

        try {
            sandwichService.removeSandwich(officeMgr, "jambon");
        } catch (NotAuthorizedException | SandwichNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(sandwichService.printListOfAllSandwiches());
    }
}