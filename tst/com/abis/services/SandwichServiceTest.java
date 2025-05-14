package com.abis.services;

import com.abis.models.actors.Instructor;
import com.abis.models.actors.OfficeManager;
import com.abis.models.actors.Person;
import com.abis.models.actors.Student;
import com.abis.models.sandwiches.Chicken;
import com.abis.models.sandwiches.Sandwich;
import com.abis.repositories.UnitOfWork;
import com.abis.repositories.exceptions.SandwichAlreadyExistsException;
import com.abis.repositories.exceptions.SandwichNotFoundException;
import com.abis.services.exceptions.NotAuthorizedException;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SandwichServiceTest {
    private static UnitOfWork uow;
    private SandwichService sandwichService;

    @BeforeAll
    public static void init() {
        uow = new UnitOfWork();
    }

    @BeforeEach
    public void initBeforeEach() {
        this.sandwichService = new SandwichService(uow);
    }

    @Test
    public void getAllSandwichHasMoreThanOne() {
        List<Sandwich> lstSandwich = sandwichService.getAllSandwiches();
        Assert.valueIsAtLeast(lstSandwich.size(), 1);
    }

    @Test
    public void getOneSandwichByItsName() throws SandwichNotFoundException {
        Sandwich sandwich = sandwichService.getSandwichByName("gouda");
        Assert.isNonEmpty(sandwich);
        assertEquals("gouda".toLowerCase(), sandwich.getNameFR().toLowerCase());
    }

    @Test
    public void getOneSandwichByItsNameButNotFound() {
        assertThrows(SandwichNotFoundException.class, () -> sandwichService.getSandwichByName("goudddaaaa"));
    }

    @Test
    public void addNewSandwichAlreadyExist() throws SandwichNotFoundException {
        Sandwich sandwichGouda = sandwichService.getSandwichByName("hesp");
        OfficeManager oMgr = new OfficeManager("Sandy", "Schillebeeckx", "sandy.Schillebeeckx@abis.com");
        assertThrows(SandwichAlreadyExistsException.class, () -> sandwichService.addSandwich(oMgr, sandwichGouda));
    }

    @Test
    public void addNewSandwichNotExistingByStudent() {
        Sandwich sandwich = new Chicken("Poulet à l'Andalouse", "Andalusische kip", 3.76D);
        Person student = new Student("John", "Doe", "john.doe@gmail.com", "KBC");
        assertThrows(NotAuthorizedException.class, () -> sandwichService.addSandwich(student, sandwich));
    }

    @Test
    public void addNewSandwichNotExistingByInstructor() {
        Sandwich sandwich = new Chicken("Poulet à l'Andalouse", "Andalusische kip", 3.76D);
        Person instructor = new Instructor("John", "Doe", "john.doe@gmail.com", "Java");
        assertThrows(NotAuthorizedException.class, () -> sandwichService.addSandwich(instructor, sandwich));
    }

    @Test
    public void addNewSandwichNotExistingByOfficeManager() throws SandwichAlreadyExistsException, NotAuthorizedException, SandwichNotFoundException {
        Sandwich sandwichToAdd = new Chicken("Poulet à l'Andalouse", "Andalusische kip", 3.76D);
        Person oMgr = new OfficeManager("John", "Doe", "john.doe@gmail.com");
        sandwichService.addSandwich(oMgr, sandwichToAdd);
        Sandwich sandwichAdded = sandwichService.getSandwichByName("Andalusische kip");
        assertEquals(sandwichToAdd, sandwichAdded);
    }

    @Test
    public void removeExistingSandwichByStudent() throws SandwichNotFoundException, NotAuthorizedException {
        Person person = new Student("John", "Doe", "john.doe@gmail.com", "ING");
        assertThrows(NotAuthorizedException.class, () -> sandwichService.removeSandwich(person, "gouda"));
    }

    @Test
    public void removeExistingSandwichByInstructor() throws SandwichNotFoundException, NotAuthorizedException {
        Person person = new Instructor("John", "Doe", "john.doe@gmail.com", "Java");
        assertThrows(NotAuthorizedException.class, () -> sandwichService.removeSandwich(person, "gouda"));
    }

    @Test
    public void removeExistingSandwichByOfficeManager() throws SandwichNotFoundException, NotAuthorizedException {
        Person person = new OfficeManager("John", "Doe", "john.doe@gmail.com");
        sandwichService.removeSandwich(person, "gouda");
        assertThrows(SandwichNotFoundException.class, () -> sandwichService.getSandwichByName("gouda"));
    }

    @Test
    public void removeNonExistingSandwichByStudent() throws SandwichNotFoundException, NotAuthorizedException {
        Person person = new Student("John", "Doe", "john.doe@gmail.com", "ING");
        assertThrows(NotAuthorizedException.class, () -> sandwichService.removeSandwich(person, "goudazzzz"));
    }

    @Test
    public void removeNonExistingSandwichByInstructor() throws SandwichNotFoundException, NotAuthorizedException {
        Person person = new Instructor("John", "Doe", "john.doe@gmail.com", "Java");
        assertThrows(NotAuthorizedException.class, () -> sandwichService.removeSandwich(person, "goudazzzz"));
    }

    @Test
    public void removeNonExistingSandwichByOfficeManager() throws SandwichNotFoundException, NotAuthorizedException {
        Person person = new OfficeManager("John", "Doe", "john.doe@gmail.com");
        assertThrows(SandwichNotFoundException.class, () -> sandwichService.removeSandwich(person, "goudazzzzz"));
    }
}
