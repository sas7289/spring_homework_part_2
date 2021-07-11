package com.example.my_market;

import com.example.my_market.entity.Cart;
import com.example.my_market.entity.Product;
import com.example.my_market.entity.User;
import com.example.my_market.repository.CartRepository;
import com.example.my_market.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;
import java.util.UUID;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    CartService cartService;

    @MockBean
    CartRepository cartRepository;

    @MockBean
    EntityManager manager;

    static User testUser;
    static Product testProduct;

    @BeforeAll
    public static void init() {
        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testProduct = new Product();
        testProduct.setId(UUID.randomUUID());
    }

    @Test
    public void testAddProductReturnQuantityWhenCartExist() {
        Cart testCart = new Cart();
        Product testProduct = null;
        testCart.setQuantity(3);
        Mockito.doReturn(testCart).when(cartRepository).findByUserAndProduct(testUser, testProduct);
        Mockito.doReturn("").when(cartRepository).save(testCart);
        Mockito.doNothing().when(manager).persist(testCart);

        Assertions.assertEquals(4, cartService.addProduct(testProduct, testUser));
    }

    @Test
    public void testAddProductReturnQuantityWhenCartNotExist() {
        Cart testCart = null;
        Product testProduct = null;
        Mockito.doReturn(testCart).when(cartRepository).findByUserAndProduct(testUser, testProduct);
        Mockito.doReturn("").when(cartRepository).save(testCart);
        Mockito.doNothing().when(manager).persist(testCart);

        Assertions.assertEquals(1, cartService.addProduct(testProduct, testUser));
    }

    @Test
    public void testRemoveProductReturnQuantityWhenCartExist() {
        Cart testCart = new Cart();
        Product testProduct = null;
        testCart.setQuantity(10);
        Mockito.doReturn(testCart).when(cartRepository).findByUserAndProduct(testUser, testProduct);
        Mockito.doReturn("").when(cartRepository).save(testCart);
        Mockito.doNothing().when(manager).persist(testCart);

        Assertions.assertEquals(9, cartService.removeProduct(testProduct, testUser));
    }



    @Test
    public void testRemoveProductReturnQuantityWhenCartNotExist() {
        Cart testCart = null;
        Product testProduct = null;
        Mockito.doReturn(testCart).when(cartRepository).findByUserAndProduct(testUser, testProduct);
        Mockito.doReturn("").when(cartRepository).save(testCart);
        Mockito.doNothing().when(manager).persist(testCart);

        Assertions.assertEquals(0, cartService.removeProduct(testProduct, testUser));
    }

}
