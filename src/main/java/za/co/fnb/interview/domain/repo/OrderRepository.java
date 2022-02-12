package za.co.fnb.interview.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.fnb.interview.domain.entity.Order;
import za.co.fnb.interview.domain.entity.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {


    List<Order> findAll();

    List<Order> findAllByReferenceIgnoreCase(String reference);

    List<Order> findAllByUser(User user);

    Long deleteAllByReferenceIgnoreCase(String reference);
}
