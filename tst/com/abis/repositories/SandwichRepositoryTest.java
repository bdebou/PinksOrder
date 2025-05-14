package com.abis.repositories;

import com.abis.models.actors.Instructor;
import com.abis.models.actors.OfficeManager;
import com.abis.models.actors.Person;
import com.abis.models.actors.Student;
import com.abis.models.sandwiches.Chicken;
import com.abis.models.sandwiches.Sandwich;
import com.abis.repositories.exceptions.SandwichAlreadyExistsException;
import com.abis.repositories.exceptions.SandwichNotFoundException;
import com.abis.services.SandwichService;
import com.abis.services.exceptions.NotAuthorizedException;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SandwichRepositoryTest {

    private static SandwichRepository sandwichRepository;

    @BeforeAll
    public static void init() {
        sandwichRepository = new FileSandwichRepository(Path.of("inputcsv/sandwiches.csv").toFile());
    }

    @Test
    public void getAllSandwichHasMoreThanOne() {
        List<Sandwich> lstSandwich = sandwichRepository.getAll();
        Assert.valueIsAtLeast(lstSandwich.size(), 1);
    }

    @Test
    public void getOneSandwichByItsName() throws SandwichNotFoundException {
        Sandwich sandwich = sandwichRepository.getSandwichByName("gouda");
        Assert.isNonEmpty(sandwich);
        assertEquals("gouda".toLowerCase(), sandwich.getNameFR().toLowerCase());
    }

    @Test
    public void getOneSandwichByItsNameButNotFound() {
        assertThrows(SandwichNotFoundException.class, () -> sandwichRepository.getSandwichByName("goudddaaaa"));
    }

    @Test
    public void addNewSandwichAlreadyExist() throws SandwichNotFoundException {
        Sandwich sandwichGouda = sandwichRepository.getSandwichByName("hesp");
        assertThrows(SandwichAlreadyExistsException.class, () -> sandwichRepository.addSandwich( sandwichGouda));
    }

    @Test
    public void addNewSandwichNotExistingByStudent() {
        Sandwich sandwich = new Chicken("Poulet à l'Andalouse", "Andalusische kip", 3.76D);
        assertThrows(NotAuthorizedException.class, () -> sandwichRepository.addSandwich(sandwich));
    }

    @Test
    public void addNewSandwichNotExistingByInstructor() {
        Sandwich sandwich = new Chicken("Poulet à l'Andalouse", "Andalusische kip", 3.76D);
        assertThrows(NotAuthorizedException.class, () -> sandwichRepository.addSandwich( sandwich));
    }

    @Test
    public void addNewSandwichNotExistingByOfficeManager() throws SandwichAlreadyExistsException, NotAuthorizedException, SandwichNotFoundException {
        Sandwich sandwichToAdd = new Chicken("Poulet à l'Andalouse", "Andalusische kip", 3.76D);
        sandwichRepository.addSandwich( sandwichToAdd);
        Sandwich sandwichAdded = sandwichRepository.getSandwichByName("Andalusische kip");
        assertEquals(sandwichToAdd, sandwichAdded);
    }

    @Test
    public void removeExistingSandwichByStudent() throws SandwichNotFoundException, NotAuthorizedException {
        assertThrows(NotAuthorizedException.class, () -> sandwichRepository.removeSandwich( "gouda"));
    }

    @Test
    public void removeExistingSandwichByInstructor() throws SandwichNotFoundException, NotAuthorizedException {
        assertThrows(NotAuthorizedException.class, () -> sandwichRepository.removeSandwich( "gouda"));
    }
    @Test
    public void removeExistingSandwichByOfficeManager() throws SandwichNotFoundException, NotAuthorizedException {
        sandwichRepository.removeSandwich( "gouda");
        assertThrows(SandwichNotFoundException.class, () -> sandwichRepository.getSandwichByName("gouda"));
    }

    @Test
    public void removeNonExistingSandwichByStudent() throws SandwichNotFoundException, NotAuthorizedException {
        assertThrows(NotAuthorizedException.class, () -> sandwichRepository.removeSandwich( "goudazzzz"));
    }

    @Test
    public void removeNonExistingSandwichByInstructor() throws SandwichNotFoundException, NotAuthorizedException {
        assertThrows(NotAuthorizedException.class, () -> sandwichRepository.removeSandwich( "goudazzzz"));
    }
    @Test
    public void removeNonExistingSandwichByOfficeManager() throws SandwichNotFoundException, NotAuthorizedException {
        assertThrows(SandwichNotFoundException.class, () -> sandwichRepository.removeSandwich( "goudazzzzz"));
    }
}
