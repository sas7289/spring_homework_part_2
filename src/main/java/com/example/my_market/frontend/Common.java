package com.example.my_market.frontend;

import com.example.my_market.entity.Product;
import com.example.my_market.entity.User;
import com.example.my_market.service.CartService;
import com.example.my_market.service.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.springframework.security.core.context.SecurityContextHolder;

public class Common {
    public static FormLayout initNavigationPanel() {
        Button routeToMain = new Button("Главная страница", event -> {
            UI.getCurrent().navigate("main");
        });
        Button routeToCart = new Button("Корзина", event -> {
            UI.getCurrent().navigate("cart");
        });
        HorizontalLayout navigateButtonsLayout = new HorizontalLayout();
        navigateButtonsLayout.add(routeToMain, routeToCart);

        HorizontalLayout logoutLayout = new HorizontalLayout();
        Button logoutButton = new Button("Выход", event -> {
            SecurityContextHolder.clearContext();
            UI.getCurrent().navigate("login");
        });

        logoutLayout.add(logoutButton);
        logoutLayout.setAlignItems(FlexComponent.Alignment.END);


        FormLayout headLayout = new FormLayout();
        headLayout.setWidthFull();
        headLayout.add(navigateButtonsLayout, logoutLayout);
        logoutLayout.setAlignSelf(FlexComponent.Alignment.END);
        return headLayout;
    }


}
