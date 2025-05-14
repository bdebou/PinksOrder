import com.abis.models.Order;
import com.abis.models.actors.Person;
import com.abis.models.sandwiches.Sandwich;
import com.abis.repositories.FileOrderRepository;
import com.abis.repositories.FilePersonRepository;
import com.abis.repositories.FileSandwichRepository;
import com.abis.repositories.exceptions.OrderNotFoundException;
import com.abis.repositories.exceptions.PersonNotFoundException;
import com.abis.repositories.exceptions.SandwichNotFoundException;
import com.abis.services.OrderService;
import exception.MaxSandwichesReachedException;

import java.nio.file.Path;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws PersonNotFoundException, SandwichNotFoundException, MaxSandwichesReachedException, OrderNotFoundException {
        FileSandwichRepository fileSandwichRepository = new FileSandwichRepository(Path.of("inputcsv/sandwiches.csv").toFile());
        FilePersonRepository filePersonRepository = new FilePersonRepository();
        FileOrderRepository fileOrderRepository= new FileOrderRepository();




        Order order1 = new Order("JAVA", filePersonRepository.getPersonByName("Raghunath","Singh"));
        order1.addSandwich(fileSandwichRepository.getSandwichByName("Brie"));
        order1.addSandwich(fileSandwichRepository.getSandwichByName("Brie"));

        Order order2 = new Order("Python", filePersonRepository.getPersonByName("Bruno","Deboubers"));
        order2.addSandwich(fileSandwichRepository.getSandwichByName("Martino"));
        order2.addSandwich(fileSandwichRepository.getSandwichByName("Salami"));

        Order order3 = new Order("C++ Advanced", filePersonRepository.getPersonByName("Hans","Decat"));
        order3.addSandwich(fileSandwichRepository.getSandwichByName("Maya"));


        Order order4 = new Order("Kotlin", filePersonRepository.getPersonByName("Tushar","Pandey"));
        order4.addSandwich(fileSandwichRepository.getSandwichByName("Gouda"));


        Order order5 = new Order("Javascript", filePersonRepository.getPersonByName("Sandy","Schillebeeckx"));
        order5.addSandwich(fileSandwichRepository.getSandwichByName("Brie"));




        //List<Order> orders = fileOrderRepository.getAll();
        //orders.forEach(System.out::println);

        fileOrderRepository.addOrder(order1);
        fileOrderRepository.writeOrderHistory(order1);
        fileOrderRepository.addOrder(order2);
        fileOrderRepository.writeOrderHistory(order2);
        fileOrderRepository.addOrder(order3);
        fileOrderRepository.writeOrderHistory(order3);
        fileOrderRepository.addOrder(order4);
        fileOrderRepository.writeOrderHistory(order4);
        fileOrderRepository.addOrder(order5);
        fileOrderRepository.writeOrderHistory(order5);

        Order order6 = fileOrderRepository.getByPerson(filePersonRepository.getPersonByName("Raghunath", "Singh"));
        fileOrderRepository.printOrderRepo();












    }

}