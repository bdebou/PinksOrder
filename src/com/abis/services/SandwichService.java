package com.abis.services;

import com.abis.models.actors.OfficeManager;
import com.abis.models.actors.Person;
import com.abis.models.sandwiches.Sandwich;
import com.abis.repositories.PersonRepository;
import com.abis.repositories.SandwichRepository;
import com.abis.repositories.UnitOfWork;
import com.abis.repositories.exceptions.PersonNotFoundException;
import com.abis.repositories.exceptions.SandwichAlreadyExistsException;
import com.abis.repositories.exceptions.SandwichNotFoundException;
import com.abis.services.exceptions.NotAuthorizedException;
import de.vandermeer.asciitable.AT_Row;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SandwichService {
    private static final Logger log = LoggerFactory.getLogger(SandwichService.class);
    private final SandwichRepository sandwichRepository;
    private final PersonRepository personRepository;

    public SandwichService(UnitOfWork uow) {
        this.sandwichRepository = uow.getSandwichRepository();
        this.personRepository = uow.getPersonRepository();
    }

    public List<Sandwich> getAllSandwiches() {
        log.debug("In getAllSandwiches()");
        return this.sandwichRepository.getAll();
    }

    public void addSandwich(String email, Sandwich sandwich) throws PersonNotFoundException, SandwichAlreadyExistsException, NotAuthorizedException {
        log.debug("In addSandwich(String email, Sandwich sandwich)");
        this.addSandwich(this.personRepository.getPersonByEmail(email), sandwich);
    }

    public void addSandwich(Person person, Sandwich sandwich) throws SandwichAlreadyExistsException, NotAuthorizedException {
        log.debug("In addSandwich(Person person, Sandwich sandwich)");
        if (this.amIAuthorized(person)) {
            this.sandwichRepository.addSandwich(sandwich);
        }
    }

    public void removeSandwich(String email, String nameOfSandwich) throws PersonNotFoundException, NotAuthorizedException, SandwichNotFoundException {
        log.debug("In removeSandwich(String email, String nameOfSandwich)");
        this.removeSandwich(this.personRepository.getPersonByEmail(email), nameOfSandwich);
    }

    public void removeSandwich(Person person, String nameOfSandwich) throws NotAuthorizedException, SandwichNotFoundException {
        log.debug("In removeSandwich(Person person, String nameOfSandwich)");
        if (this.amIAuthorized(person)) {
            this.sandwichRepository.removeSandwich(nameOfSandwich);
        }
    }

    private boolean amIAuthorized(Person person) throws NotAuthorizedException {
        log.debug("In amIAuthorized(Person person)");
        if (!(person instanceof OfficeManager)) {
            log.warn("<{}> is NOT authorized", person);
            throw new NotAuthorizedException(person);
        }
        log.info("<{}> is authorized", person);
        return true;
    }

    public Sandwich getSandwichByName(String name) throws SandwichNotFoundException {
        log.debug("In getSandwichByName(String name)");
        return this.sandwichRepository.getSandwichByName(name);
    }

    public String printListOfAllSandwiches() {
        log.debug("In rintListOfAllSandwiches()");
        AsciiTable at = new AsciiTable();
        at.addRule();
        AT_Row title = at.addRow("Type", "Naam", "Groeten Ja/Nee", "Grijs/Wit");
        title.setTextAlignment(TextAlignment.CENTER);
        at.addRule();
        for (Sandwich sandwich : this.sandwichRepository.getAll()) {
            at.addRow(sandwich.getClass().getSimpleName(), sandwich.getNameNL(), "", "");
        }
        at.addRule();
        return at.render();
    }

    public Person getOfficeManagerByEmail(String email) throws PersonNotFoundException, NotAuthorizedException {
        log.debug("In getOfficeManagerByEmail(String email)");
        Person person = this.personRepository.getPersonByEmail(email);
        if (this.amIAuthorized(person)) {
            return person;
        } else {
            return null;
        }
    }
}
