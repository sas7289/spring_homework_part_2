package com.example.my_market.service;

import com.example.my_market.entity.Cart;
import com.example.my_market.entity.Product;
import com.example.my_market.entity.User;
import com.example.my_market.repository.CartRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {
    private CartRepository cartRepository;
    private HttpServletRequest request;
    private EntityManager manager;

    public CartService(CartRepository cartRepository, HttpServletRequest request, EntityManager manager) {
        this.cartRepository = cartRepository;
        this.request = request;
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
    public Integer addProduct(Product product, User user) {
//        cartRepository.save(new Cart(product, quantity, user));
//        String userLogin = request.getRemoteUser();
        Cart cart = cartRepository.findByUserAndProduct(user, product);
        if (cart!= null) {
            cart.setQuantity(cart.getQuantity() + 1);
            manager.persist(cart);
            return cart.getQuantity();
        }
        Cart temp = new Cart(product, 1, user);
//        temp.setId(UUID.fromString("asdasd"));
        cartRepository.save(new Cart(product, 1, user));
        return temp.getQuantity();
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public List<Cart> findAllByUserId(UUID id) {
        return cartRepository.findAllByUserId(id);
    }

    public List<Cart> findByUserLogin(String login) {
        return cartRepository.findByUserLogin(login);
    }

//    public List<Object> customFindAll(UUID id) {
//        return cartRepository.customFindAll(id);
//    }

    @Transactional
    public Integer removeProduct(Product product, User user) {
        Cart cart = cartRepository.findByUserAndProduct(user, product);
        if (cart!= null) {

            cart.setQuantity(cart.getQuantity() > 0 ? cart.getQuantity() - 1 : 0);
            manager.persist(cart);
            return cart.getQuantity();
        }
        return 0;
    }

    public void addOrderIdToItems(UUID userId, UUID orderId) {
        cartRepository.updateOrderId(userId, orderId);
    }

    public Cart findByUserAndProduct(User user, Product product) {
        return cartRepository.findByUserAndProduct(user, product);
    }
}
