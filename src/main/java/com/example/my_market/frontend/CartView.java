package com.example.my_market.frontend;

import com.example.my_market.entity.Cart;
import com.example.my_market.entity.User;
import com.example.my_market.service.CartService;
import com.example.my_market.service.OrderService;
import com.example.my_market.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Route("cart")
public class CartView extends VerticalLayout {
    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;


    public CartView(CartService cartService, OrderService orderService, HttpServletRequest request, UserService userService) {
        String userLogin = request.getUserPrincipal().getName();
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
//        List<Cart> carts = cartService.findAll();
//        List<Cart> carts = cartService.findAllByUserId(UUID.fromString("a521d594-8011-4447-9063-43ea8cbb9350"));
        List<Cart> carts = cartService.findByUserLogin(userLogin);
        Grid<Cart> grid = new Grid(Cart.class);
        grid.setItems(carts);
        grid.setSizeUndefined();
        grid.addColumn(Cart::getProduct);
        grid.setColumns("product.title", "quantity");

        add(new Button("Заказать", event -> {
            User currentUser = userService.findByLogin(userLogin).get();
            UUID orderId = orderService.save(currentUser);
            cartService.addOrderIdToItems(currentUser.getId(), orderId);
        }));

        add(grid);

    }


}
