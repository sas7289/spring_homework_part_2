package com.example.my_market.frontend;

import com.example.my_market.entity.Cart;
import com.example.my_market.service.CartService;
import com.example.my_market.service.OrderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.UUID;

@Route("cart")
public class CartView extends VerticalLayout {
    private final CartService cartService;
    private final OrderService orderService;


    public CartView(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
//        List<Cart> carts = cartService.findAll();
        List<Cart> carts = cartService.findAllByUserId(UUID.fromString("51ef560b-7596-43e6-bfb5-3adbdcf317bd"));
        Grid<Cart> grid = new Grid(Cart.class);
        grid.setItems(carts);
        grid.setSizeUndefined();
        grid.addColumn(Cart::getProduct);
        grid.setColumns("product.title", "quantity");

        add(new Button("Заказать", event -> {
            UUID orderId = orderService.save(UUID.fromString("51ef560b-7596-43e6-bfb5-3adbdcf317bd"));
            cartService.addOrderIdToItems(UUID.fromString("51ef560b-7596-43e6-bfb5-3adbdcf317bd"), orderId);
        }));

        add(grid);

    }


}
