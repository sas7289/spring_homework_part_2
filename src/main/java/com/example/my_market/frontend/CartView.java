package com.example.my_market.frontend;

import com.example.my_market.entity.Cart;
import com.example.my_market.entity.Product;
import com.example.my_market.entity.User;
import com.example.my_market.service.CartService;
import com.example.my_market.service.OrderService;
import com.example.my_market.service.ProductService;
import com.example.my_market.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Route("cart")
public class CartView extends VerticalLayout {
    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    private User user;



    public CartView(CartService cartService, OrderService orderService, ProductService productService, UserService userService) {
//        String userLogin = request.getUserPrincipal().getName();
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
        this.user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).get();
//        List<Cart> carts = cartService.findAll();
//        List<Cart> carts = cartService.findAllByUserId(UUID.fromString("a521d594-8011-4447-9063-43ea8cbb9350"));
        List<Cart> carts = cartService.findByUserLogin(user.getName());
        Grid<Cart> grid = new Grid(Cart.class);
        grid.setItems(carts);
        grid.setSizeUndefined();
        grid.addColumn(Cart::getProduct);
        grid.setColumns("product.title", "quantity");
        grid.addColumn(new ComponentRenderer(cart -> {
            Label countOfProduct = new Label(
                    (Cart) cart == null?
                            "0" : ((Cart) cart).getQuantity().toString());
            Button plusButton = new Button("+", event -> {
//                User user = userService.findByLogin("user_1").get();
                ((Cart) cart).setQuantity(((Cart) cart).getQuantity() + 1);
                countOfProduct.setText(((Cart) cart).getQuantity().toString());
            });

            Button minusButton = new Button("-", event -> {
//                User user = userService.findByLogin("user_1").get();
                ((Cart) cart).setQuantity(((Cart) cart).getQuantity() - 1);
                countOfProduct.setText(((Cart) cart).getQuantity().toString());
            });
            HorizontalLayout horizontalLayout = new HorizontalLayout(plusButton, minusButton, countOfProduct);
            horizontalLayout.setAlignItems(Alignment.CENTER);
            return horizontalLayout;
        }));


        add(Common.initNavigationPanel(), grid);

        add(new Button("Заказать", event -> {
            UUID orderId = orderService.save(user);
            cartService.addOrderIdToItems(user.getId(), orderId);
        }));
    }


}
