package za.co.fnb.interview.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import za.co.fnb.interview.domain.entity.Order;
import za.co.fnb.interview.domain.entity.Product;
import za.co.fnb.interview.domain.entity.User;
import za.co.fnb.interview.domain.repo.OrderRepository;
import za.co.fnb.interview.domain.repo.ProductRepository;
import za.co.fnb.interview.domain.repo.UserRepository;
import za.co.fnb.interview.model.CustomUserDetails;
import za.co.fnb.interview.service.StoreService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public StoreServiceImpl(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Product> getProducts(Integer id) {

        if (id == null) {
            return productRepository.findAll();
        }

        return productRepository.findById(id).map(Collections::singletonList).orElseGet(ArrayList::new);

    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<User> getUsers(Long id) {

        if (id == null) {
            return userRepository.findAll();
        }

        return userRepository.findById(id).map(Collections::singletonList).orElseGet(ArrayList::new);

    }

    @Override
    public List<Order> getLoggedInUserOrders(String reference) {
        return orderRepository.findAllByReferenceIgnoreCase(reference);
    }

    @Override
    public String placeOrder(Integer productId, Long customerId) {

        Order order = new Order();
        order.setIsPaid(false);
        order.setProduct(productRepository.getById(productId));
        order.setUser(userRepository.getById(customerId));
        String reference = UUID.randomUUID().toString();
        order.setReference(reference);
        order.setDateTime(LocalDateTime.now());
        orderRepository.save(order);

        return String.format("Order has been placed. Reference: [%s] - Please use this when making payment", reference);
    }

    @Override
    public String payOrder(String reference) {

        List<Order> orders = orderRepository.findAllByReferenceIgnoreCase(reference.trim());

        List<Product> collect = orders.stream().map(Order::getProduct).filter(product -> product.getStock() <= 0).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            return "No stock. Order not placed";
        }

         /*
            TODO: This is where we set up payment processor component - determine if successful or not and handle appropriately
         */

        List<Product> products = orders.stream().map(Order::getProduct).collect(Collectors.toList());
        products.forEach(product -> product.setStock(product.getStock() - 1));

        productRepository.saveAll(products);

        orders.forEach(order -> order.setIsPaid(true));
        orderRepository.saveAll(orders);

        /*
            TODO: This is where we set up another component (e.g. CAMEL) which can invoke an event and place on queue.
         */

        return "Successfully paid for order. Will be shipped soon";
    }

    @Override
    public List<Order> getLoggedInUserOrders() {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderRepository.findAllByUser(principal.getUser());
    }

    @Override
    @Transactional
    public String deleteOrder(String reference) {
        orderRepository.deleteAllByReferenceIgnoreCase(reference);
        return "Successfully removed order";
    }
}
