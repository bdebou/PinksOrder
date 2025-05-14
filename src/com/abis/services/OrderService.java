package com.abis.services;

import com.abis.models.Order;
import com.abis.models.sandwiches.HasDescription;
import com.abis.models.sandwiches.Normal;
import com.abis.models.sandwiches.Sandwich;
import com.abis.repositories.OrderRepository;
import com.abis.repositories.UnitOfWork;
import de.vandermeer.asciitable.AT_Row;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OrderService {
    private final UnitOfWork unitOfWork;

    public OrderService(UnitOfWork uow) {
        this.unitOfWork = uow;
    }

    public String printOutOrder() {
        OrderRepository orderRepository = this.unitOfWork.getOrderRepository();

        StringBuilder sb = new StringBuilder();
        sb.append("Broodjes (Pinky's)").append(System.lineSeparator());
        for (Order order : orderRepository.getAll()) {
            sb.append(String.format("Naam: %s", order.getOrderingPerson())).append(System.lineSeparator())
                    .append(String.format("Training: %s", order.getCourse())).append(System.lineSeparator());
            for (Sandwich sandwich : order.getSandwiches()) {
                if (sandwich instanceof Normal normalSandwich) {
                    sb.append(sandwich.getNameNL());
                    if (normalSandwich.isSalad()) {
                        sb.append(" met sla ");
                    }
                } else if (sandwich instanceof HasDescription specialSandwich) {
                    sb.append(String.format("%s (%s)", specialSandwich.getNameNL(), specialSandwich.getNlDescription()));
                }
                sb.append(String.format(" %s brood", sandwich.getKind().toString().toLowerCase()))
                        .append(System.lineSeparator());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void printOutOrderASCII(File outputFile) {
        File tmpFile = null;
        boolean bSaved = false;
        try {
            tmpFile = File.createTempFile("order", null);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile, StandardCharsets.UTF_8))) {
                bw.write(this.printOutOrderASCII());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (outputFile.exists()) outputFile.delete();
            bSaved = tmpFile.renameTo(outputFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (!bSaved && tmpFile != null && tmpFile.exists()) {
                tmpFile.delete();
            }
        }
    }

    public String printOutOrderASCII() {

        OrderRepository orderRepository = this.unitOfWork.getOrderRepository();

        AsciiTable at = new AsciiTable();
        at.addRule();
        AT_Row title = at.addRow( null, "Broodjes (Pinky's)");
        title.setTextAlignment(TextAlignment.LEFT);
        at.addRule();
        for (Order order : orderRepository.getAll()) {
            at.addRow( null, String.format("Naam: %s", order.getOrderingPerson()));
            at.addRow( null, String.format("Training: %s", order.getCourse()));
            for (Sandwich sandwich : order.getSandwiches()) {
                at.addRule();
                if (sandwich instanceof Normal normalSandwich) {
                    at.addRow(String.format("%s (%s)", normalSandwich.getNameNL(), normalSandwich.isSalad() ? "met sla" : "zonder sla"), normalSandwich.getKind().toString());
                } else if (sandwich instanceof HasDescription specialSandwich) {
                    at.addRow( String.format("%s (%s)", specialSandwich.getNameNL(), specialSandwich.getNlDescription()), specialSandwich.getKind().toString());
                }
            }
            at.addRule();
        }
        return at.render();
    }

    public void registerNewOrder(Order order) {
//TODO Write code to register new order in repository, don't forget to check if are not OfficeManager
        this.unitOfWork.getOrderRepository().addOrder(order);
        this.unitOfWork.getOrderRepository().writeOrderHistory(order);
    }
}
