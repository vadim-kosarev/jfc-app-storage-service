package dev.vk.jfc.app.storage.appstorage.cmd;

import dev.vk.jfc.app.storage.appstorage.entities.Address;
import dev.vk.jfc.app.storage.appstorage.entities.ImageDataItem;
import dev.vk.jfc.app.storage.appstorage.entities.Order;
import dev.vk.jfc.app.storage.appstorage.repository.AddressRepository;
import dev.vk.jfc.app.storage.appstorage.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TestCommand02 implements CommandLineRunner {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void run(String... args) throws Exception {
        runTest_01();

    }


    private void runTest_01() {
        // create Order object
        Order order = new Order();

        order.setOrderTrackingNumber("1000");
        order.setStatus("COMPLETED");
        order.setTotalPrice(new BigDecimal(2000));
        order.setTotalQuantity(5);

        Address billingAddress = new Address();
        billingAddress.setStreet("kothrud");
        billingAddress.setCity("pune");
        billingAddress.setState("Maharashra");
        billingAddress.setCountry("India");
        billingAddress.setZipCode("11048");

        order.setBillingAddress(billingAddress);
        billingAddress.setOrder(order);
        // save both order and address ( Cascade type - ALL)
        orderRepository.save(order);
    }
}
