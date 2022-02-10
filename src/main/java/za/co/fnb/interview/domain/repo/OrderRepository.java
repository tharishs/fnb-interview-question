package za.co.fnb.interview.domain.repo;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.fnb.interview.domain.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {


    List<Order> findAll();

    List<Order> findAllByReferenceIgnoreCase(String reference);

}
