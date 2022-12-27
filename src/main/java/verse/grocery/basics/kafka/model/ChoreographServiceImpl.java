/*
package verse.grocery.basics.kafka.model;

import verse.grocery.basics.kafka.model.Order;
import verse.grocery.basics.kafka.model.Payment;
import verse.grocery.basics.kafka.model.Transaction;
import verse.grocery.basics.kafka.model.User;
import verse.grocery.basics.kafka.orchestrator.plainjava.Status;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class ChoreographServiceImpl {

    @Autowired
    private KafkaTemplate<Integer, Payment> paymentKafkaTemplate;
    @Autowired
    private KafkaTemplate<Integer, Transaction> transactionKafkaTemplate;
    @Autowired
    private KafkaTemplate<Integer, Order> orderKafkaTemplate;

    @Scheduled(fixedDelay = 10000)
    public void sendDummyData() {
        User user = new User(1233);
        Order order = new Order(123, verse.grocery.basics.kafka.orchestrator.plainjava.Status.COMPLETE, user, 1122);
        orderKafkaTemplate.send("order", order);
        log.info("Order added to Queue: {}", order);
    }

    @KafkaListener(topics = "order", groupId = "grocery")
    @Transactional
    public Payment acceptOrder(Order order) {
        log.info("Order: {}", order);
        Payment payment = new Payment("ONLINE", Status.COM, order);
        paymentKafkaTemplate.send("payment", payment);
        return payment;
    }

    @KafkaListener(topics = "payment", groupId = "grocery")
    @Transactional
    public Transaction createTransaction(Payment payment) {
        log.info("Payment: {}", payment);
        Transaction transaction = new Transaction(1, Status.COMPLETED, payment);
        transactionKafkaTemplate.send("transaction", transaction);
        return transaction;
    }

    @KafkaListener(topics = "transaction", groupId = "grocery")
    @Transactional
    public void fullFillOrder(Transaction transaction) {
        log.info("Transaction: {}", transaction);
    }
}
*/
