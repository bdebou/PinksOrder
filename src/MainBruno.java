import com.abis.models.actors.OfficeManager;
import com.abis.models.actors.Person;
import com.abis.models.sandwiches.Chicken;
import com.abis.repositories.FileSandwichRepository;
import com.abis.repositories.SandwichRepository;
import com.abis.repositories.UnitOfWork;
import com.abis.repositories.exceptions.SandwichAlreadyExistsException;
import com.abis.services.OrderService;
import com.abis.services.SandwichService;
import com.abis.services.exceptions.NotAuthorizedException;

import java.nio.file.Path;

public class MainBruno {
    public static void main(String[] args) {
        UnitOfWork uow = new UnitOfWork();

        uow.getSandwichRepository().getAll().stream().forEach(System.out::println);

        OrderService orderService = new OrderService(uow);
        System.out.println(orderService.printOutOrder());

        SandwichRepository sandwichRepository = new FileSandwichRepository(Path.of("inputcsv/input.csv").toFile());

        Person officeMgr = new OfficeManager("Sandy", "Schillebeeckx", "sandy.Schillebeeckx@abis.com");
        SandwichService sandwichService = new SandwichService(uow);
        try {
            sandwichService.addSandwich(officeMgr, new Chicken("Poulet à l'andalouse", "Andalusische kip", 3.7D));
        } catch (SandwichAlreadyExistsException | NotAuthorizedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------");
        uow.getSandwichRepository().getAll().stream().forEach(System.out::println);
        System.out.println("-----------------");
        sandwichRepository.getAll().stream().forEach(System.out::println);
        System.out.println("-----------------");

        try {
            sandwichService.removeSandwich(officeMgr, "jambon");
        } catch (NotAuthorizedException e) {
            throw new RuntimeException(e);
        }
        uow.getSandwichRepository().getAll().stream().forEach(System.out::println);

//            Person studentOne = new Student();
//            Person instructor = new Instructor();
//            orderService.setNewOrder(studentOne);
//            orderService.setNewOrder(instructor);
//            orderService.setNewOrder(officeMgr);
    }
}
