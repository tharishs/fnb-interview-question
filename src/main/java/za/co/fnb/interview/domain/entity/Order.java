package za.co.fnb.interview.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;

    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "date", nullable = false)
    private LocalDateTime dateTime;
}

