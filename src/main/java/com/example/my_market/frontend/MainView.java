package com.example.my_market.frontend;

import com.example.my_market.entity.Product;
import com.example.my_market.entity.User;
import com.example.my_market.service.CartService;
import com.example.my_market.service.ProductService;
import com.example.my_market.service.UserService;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.internal.KeyboardEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("main")
public class MainView extends VerticalLayout {
    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;


    public MainView(ProductService productService, CartService cartService, UserService userService) {
        this.productService = productService;
        this.cartService = cartService;
        this.userService = userService;

        Grid grid = new Grid<Product>(Product.class);
        List<Product> all = productService.findAll();
        grid.setItems(all);
        grid.setColumns("title", "quantity_in_stock", "price");
        grid.setSizeUndefined();
        grid.addColumn(new ComponentRenderer(pr -> {
            Button plusButton = new Button("+", event -> {
                User user = userService.findByLogin("user_1").get();
                Product product = productService.getById(((Product) pr).getId());
                cartService.addProduct(product, user);
            });

            Button minusButton = new Button("-", event -> {
                User user = userService.findByLogin("user_1").get();
                Product product = productService.getById(((Product) pr).getId());
                cartService.removeProduct(product, user);
            });
            return new HorizontalLayout(plusButton, minusButton);
        }));
        add(grid);
        /////////////
        Board board = new Board();
        Label label = new Label("Фильтры");
        TextField fieldOfMin = new TextField();
        TextField fieldOfMax = new TextField();
        TextField fieldOfTitle = new TextField();
        board.add(label, fieldOfMax, fieldOfMin, fieldOfTitle);
        add(board);

    }
}
