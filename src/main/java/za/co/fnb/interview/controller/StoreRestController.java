package za.co.fnb.interview.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.co.fnb.interview.domain.entity.Order;
import za.co.fnb.interview.domain.entity.Product;
import za.co.fnb.interview.domain.entity.User;
import za.co.fnb.interview.service.StoreService;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/store", produces = MediaType.APPLICATION_JSON_VALUE)
public class StoreRestController {

    private final StoreService storeService;

    public StoreRestController(StoreService storeService) {
        this.storeService = storeService;
    }


    @PostMapping("/order/place/{productId}/customer/{customerId}")
    public String placeOrder(@PathVariable Integer productId,
                             @PathVariable Long customerId) {

        return storeService.placeOrder(productId, customerId);

    }

    @PostMapping("/order/pay/{reference}")
    public String payOrder(@PathVariable String reference) {

        return storeService.payOrder(reference);

    }

    @DeleteMapping("/order/delete/{reference}")
    public String deleteOrder(@PathVariable String reference) {

        return storeService.deleteOrder(reference);

    }

    @GetMapping("/get/all/users")
    public List<User> getAllUsers() {
        return storeService.getUsers(null);

    }

    @GetMapping("/get/all/orders")
    public List<Order> getAllOrders() {
        return storeService.getAllOrders();

    }

    @GetMapping("/get/all/products")
    public List<Product> getAllProducts() {
        return storeService.getProducts(null);

    }

    @GetMapping("/get/user/{id}")
    public List<User> getUser(@PathVariable Long id) {
        return storeService.getUsers(id);

    }

    @GetMapping("/get/order/{reference}")
    public List<Order> getOrder(@PathVariable String reference) {
        return storeService.getLoggedInUserOrders(reference);

    }

    @GetMapping("/get/user/order")
    public List<Order> getAllLoggedInUserOrders() {
        return storeService.getLoggedInUserOrders();

    }

    @GetMapping("/get/product/{id}")
    public List<Product> getProduct(@PathVariable Integer id) {
        return storeService.getProducts(id);

    }


}


