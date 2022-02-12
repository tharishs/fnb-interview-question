package za.co.fnb.interview.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import za.co.fnb.interview.domain.entity.Order;
import za.co.fnb.interview.domain.entity.Product;
import za.co.fnb.interview.model.CustomUserDetails;

@Controller
public class StoreController {

    private final StoreRestController storeController;

    public StoreController(StoreRestController storeController) {
        this.storeController = storeController;
    }

    @GetMapping("/products")
    public String showAllProducts(Model model) {
        model.addAttribute("products", storeController.getAllProducts());
        model.addAttribute("p1", new Product());
        return "products";
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@ModelAttribute Product p1, Model model) {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("msg", storeController.placeOrder(p1.getId(), principal.getUser().getId()));
        return "order_placed";
    }

    @PostMapping("/payNow")
    public String payNow(@ModelAttribute Order o1, Model model) {
        model.addAttribute("msg", storeController.payOrder(o1.getReference()));
        return "order_placed";
    }

    @PostMapping("/deleteOrder")
    public String deleteOrder(@ModelAttribute Order o1, Model model) {
        model.addAttribute("msg", storeController.deleteOrder(o1.getReference()));
        return "order_placed";
    }

    @GetMapping("/orders")
    public String showCustomerOrders(Model model) {

        model.addAttribute("orders", storeController.getAllLoggedInUserOrders());
        model.addAttribute("o1", new Order());
        return "orders";
    }

    @GetMapping("/home")
    public String showCustomerOrders() {
        return "home_page";
    }

    @PostMapping("/invoice")
    public String invoice(Model model, @ModelAttribute Order o1) {
        model.addAttribute("orders", storeController.getOrder(o1.getReference()));
        return "invoice";
    }

}