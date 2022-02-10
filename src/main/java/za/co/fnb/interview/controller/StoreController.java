package za.co.fnb.interview.controller;

import org.apache.catalina.Store;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.co.fnb.interview.domain.entity.Order;
import za.co.fnb.interview.domain.entity.Product;
import za.co.fnb.interview.domain.entity.User;
import za.co.fnb.interview.model.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class StoreController {

    private final StoreRestController storeController;

    public StoreController(StoreRestController storeController) {
        this.storeController = storeController;
    }

    @GetMapping("/products")
    public String showRegistrationForm(Model model) {
        model.addAttribute("products", storeController.getAllProducts());
        model.addAttribute("p1", new Product());
        return "products";
    }

    @PostMapping("/placeOrder")
    public String processRegister(@ModelAttribute Product p1, Model model) {
        CustomUserDetails principal = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("msg", storeController.placeOrder(p1.getId(), principal.getUser().getId()));
        return "orderplaced";
    }

}