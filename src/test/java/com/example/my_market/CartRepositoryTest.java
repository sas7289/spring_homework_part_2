package com.example.my_market;

import com.example.my_market.entity.*;
import com.example.my_market.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@DataJpaTest
@Profile("test")
@ContextConfiguration(classes = {Cart.class, User.class, Product.class, CartRepository.class})
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void cartTest() {
        Role testRole = new Role();
        testRole.setId(UUID.randomUUID());
        testRole.setName("testRole");

        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setRole(testRole);
        testUser.setName("name");
        testUser.setMiddle_name("middleName");
        testUser.setLast_name("lastName");
        testUser.setLogin("login");
        testUser.setPassword("pass");
        testUser.setPhone("777");

        Order testOrder = new Order();
        testOrder.setId(UUID.randomUUID());
        testOrder.setUser(testUser);
        testOrder.setDate_of_creation(new Timestamp(System.currentTimeMillis()));

        Product testProduct = new Product();
        testProduct.setId(UUID.randomUUID());
        testProduct.setPrice(111);
        testProduct.setTitle("title");
        testProduct.setQuantity_in_stock(10);

        Cart testCart = new Cart();
        testCart.setQuantity(3);
        testCart.setId(UUID.randomUUID());;
        testCart.setUser(testUser);
        testCart.setProduct(testProduct);
        testCart.setOrder(testOrder);

        entityManager.persist(testCart);
        List<Cart> all = cartRepository.findAll();


    }
}
