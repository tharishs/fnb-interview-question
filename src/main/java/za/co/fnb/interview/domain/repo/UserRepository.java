package za.co.fnb.interview.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.fnb.interview.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
