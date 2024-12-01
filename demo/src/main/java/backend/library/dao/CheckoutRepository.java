package backend.library.dao;

import backend.library.entity.Checkout;
import org.hibernate.annotations.Check;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout,Long> {
    Checkout findByUserEmailAndBookId(String userEmail, long bookId);

    List<Check> findBooksByUserEmail(String userEmail);

}
