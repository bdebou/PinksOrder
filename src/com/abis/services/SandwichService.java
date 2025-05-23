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

import java.util.List;

public class SandwichService {
    private final SandwichRepository sandwichRepository;
    private final PersonRepository personRepository;

    public SandwichService(UnitOfWork uow) {
        this.sandwichRepository = uow.getSandwichRepository();
        this.personRepository = uow.getPersonRepository();
    }

    public List<Sandwich> getAllSandwiches() {
        return this.sandwichRepository.getAll();
    }

    public void addSandwich(String email, Sandwich sandwich) throws PersonNotFoundException, SandwichAlreadyExistsException, NotAuthorizedException {
        this.addSandwich(this.personRepository.getPersonByEmail(email), sandwich);
    }

    public void addSandwich(Person person, Sandwich sandwich) throws SandwichAlreadyExistsException, NotAuthorizedException {
        if (this.amIAuthorized(person)) {
            this.sandwichRepository.addSandwich(sandwich);
        }
    }

    public void removeSandwich(String email, String nameOfSandwich) throws PersonNotFoundException, NotAuthorizedException, SandwichNotFoundException {
        this.removeSandwich(this.personRepository.getPersonByEmail(email), nameOfSandwich);
    }

    public void removeSandwich(Person person, String nameOfSandwich) throws NotAuthorizedException, SandwichNotFoundException {
        if (this.amIAuthorized(person)) {
            this.sandwichRepository.removeSandwich(nameOfSandwich);
        }
    }

    private boolean amIAuthorized(Person person) throws NotAuthorizedException {
        if (!(person instanceof OfficeManager)) {
            throw new NotAuthorizedException(String.format("<%s> is not authorized to add a sandwich", person.toString()));
        }
        return true;
    }

    public Sandwich getSandwichByName(String name) throws SandwichNotFoundException {
        return this.sandwichRepository.getSandwichByName(name);
    }

    public String printListOfAllSandwiches() {
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
        Person person = this.personRepository.getPersonByEmail(email);
        if (person instanceof OfficeManager) {
            return person;
        } else {
            throw new NotAuthorizedException(person);
        }
    }
}
