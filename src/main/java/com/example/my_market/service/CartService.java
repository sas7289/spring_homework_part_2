package com.example.my_market.service;

import com.example.my_market.entity.Cart;
import com.example.my_market.entity.Product;
import com.example.my_market.entity.User;
import com.example.my_market.repository.CartRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {
    private CartRepository cartRepository;
    private HttpSession httpSession;
    private EntityManager manager;

    public CartService(CartRepository cartRepository, HttpSession httpSession, EntityManager manager) {
        this.cartRepository = cartRepository;
        this.httpSession = httpSession;
        this.manager = manager;
    }

//    public void addProduct(Product product, Integer quantity, User user) {
////        cartRepository.save(new Cart(product, quantity, user));
//        Cart cart = cartRepository.findByUserIdAndProductId(product.getId(), user.getId());
//        if (cart!= null) {
//            Integer quantity = cart.getQuantity();
//            cartRepository.s
//        }
//        cartRepository.save(new Cart(product, quantity, user));
//    }

    @Transactional
    public void addProduct(Product product, User user) {
//        cartRepository.save(new Cart(product, quantity, user));
        Cart cart = cartRepository.findByUserAndProduct(user, product);
        if (cart!= null) {
            cart.setQuantity(cart.getQuantity() + 1);
            manager.persist(cart);
            return;
        }
        Cart temp = new Cart(product, 1, user);
        temp.setId(UUID.fromString("asdasd"));
        cartRepository.save(new Cart(product, 1, user));
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public List<Cart> findAllByUserId(UUID id) {
        return cartRepository.findAllByUserId(id);
    }

//    public List<Object> customFindAll(UUID id) {
//        return cartRepository.customFindAll(id);
//    }

    public void removeProduct(Product product, User user) {
//        Cart cart = cartRepository.findByUserIdAndProductId(product.getId(), user.getId());
//        if (cart!= null) {
//            cart.setQuantity(cart.getQuantity() - 1);
//        }
    }

    public void addOrderIdToItems(UUID userId, UUID orderId) {
        cartRepository.updateOrderId(userId, orderId);
    }
}
