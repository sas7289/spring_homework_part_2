package com.example.my_market.frontend;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Navigation {
    public static HorizontalLayout initNavigationPanel() {
        HorizontalLayout navigationPanel = new HorizontalLayout();
        Button routeToMain = new Button("Главная страница", event -> {
            UI.getCurrent().navigate("main");
        });
        Button routeToCart = new Button("Корзина", event -> {
            UI.getCurrent().navigate("cart");
        });
        navigationPanel.add(routeToMain, routeToCart);
        navigationPanel.setAlignItems(FlexComponent.Alignment.CENTER);
        return navigationPanel;
    }
}
