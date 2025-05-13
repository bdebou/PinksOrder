package com.abis.services;

import com.abis.models.Order;
import com.abis.models.actors.Person;
import com.abis.models.sandwiches.HasDescription;
import com.abis.models.sandwiches.Normal;
import com.abis.models.sandwiches.Sandwich;
import com.abis.repositories.OrderRepository;
import com.abis.repositories.UnitOfWork;

public class OrderService {
    public UnitOfWork unitOfWork;

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
//    public String printOutOrder() {
//        OrderRepository orderRepository = this.unitOfWork.getOrderRepository();
//
//        AsciiTable at = new AsciiTable();
//        at.addRule();
//        AT_Row title = at.addRow(null, null, "Broodjes (Pinky's)");
//        title.setTextAlignment(TextAlignment.LEFT);
//        for (Order order : orderRepository.getAll()) {
//            at.addRow(null, null, String.format("Naam: %s", order.getOrderingPerson()));
//            at.addRow(null, null, String.format("Training: %s", order.getCourse()));
//            for (Sandwich sandwich : order.getSandwiches()) {
//                if (sandwich instanceof Normal normalSandwich) {
//                    at.addRow(normalSandwich.getNameNL(), normalSandwich.isSalad() ? "X" : "", normalSandwich.getKind().toString());
//                } else if (sandwich instanceof HasDescription specialSandwich) {
//                    at.addRow( String.format("%s (%s)", specialSandwich.getNameNL(), specialSandwich.getNlDescription()), null,specialSandwich.getKind().toString());
//                }
//            }
//        }
//        at.addRule();
//        return at.render();
//    }

    public void registerNewOrder(Order order) {
//TODO Write code to register new order in repository, don't forget to check if are not OfficeManager
    }
}
