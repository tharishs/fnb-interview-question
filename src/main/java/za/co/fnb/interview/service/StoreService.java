package za.co.fnb.interview.service;

import za.co.fnb.interview.domain.entity.Order;
import za.co.fnb.interview.domain.entity.Product;
import za.co.fnb.interview.domain.entity.User;

import java.util.List;

public interface StoreService {
    List<Product> getProducts(Integer id);

    List<Order> getAllOrders();

    List<User> getUsers(Long id);

    List<Order> getOrder(String reference);

    String placeOrder(Integer productId, Long customerId);

    String payOrder(String reference);
}
