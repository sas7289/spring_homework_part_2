package com.example.my_market.frontend;

import com.example.my_market.entity.Product;
import com.example.my_market.entity.User;
import com.example.my_market.service.CartService;
import com.example.my_market.service.ProductService;
import com.example.my_market.service.UserService;
import com.example.my_market.util.specification.ProductSpecification;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Route("main")
public class MainView extends VerticalLayout {
    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final Grid<Product> mainGrid;

    private final Authentication authentication;
    private User user;


    public MainView(ProductService productService, CartService cartService, UserService userService) {
        this.productService = productService;
        this.cartService = cartService;
        this.userService = userService;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();



        initUser();
        mainGrid = new Grid<Product>(Product.class);
        mainGridInit(productService, cartService);


        add(Common.initNavigationPanel(), mainGrid);

        VerticalLayout filterLayout = filterLayoutInit();

        add(filterLayout);

    }




    private VerticalLayout filterLayoutInit() {
        HorizontalLayout labelOfFilters = new HorizontalLayout(new Label("Фильтры"));
        HorizontalLayout filterByMinPrice = new HorizontalLayout(new Label("Минимальная цена"), new TextField());
        HorizontalLayout filterByMaxPrice = new HorizontalLayout(new Label("Максимальная цена"), new TextField());
        HorizontalLayout filterByTitle = new HorizontalLayout(new Label("Название"), new TextField());

        VerticalLayout verticalLayout = new VerticalLayout(
                labelOfFilters,
                filterByMinPrice,
                filterByMaxPrice,
                filterByTitle,
                new Button("Отфильтровать", event -> {
                    productFilter(filterByMinPrice, filterByMaxPrice, filterByTitle);
                })
        );
        return verticalLayout;
    }

    private void mainGridInit(ProductService productService, CartService cartService) {
        List<Product> all = productService.findAll();
        mainGrid.setItems(all);
        mainGrid.setColumns("title", "price", "quantity_in_stock");
        mainGrid.setSizeUndefined();
        mainGrid.addColumn(new ComponentRenderer(item -> {
            Label countOfProduct = new Label(
                    cartService.findByUserAndProduct(user, (Product)item) == null?
                    "0" : cartService.findByUserAndProduct(user, (Product)item).getQuantity().toString());
            Button plusButton = new Button("+", event -> {
//                User user = userService.findByLogin("user_1").get();
                Product product = productService.getById(((Product) item).getId());
                Integer productQuantity = cartService.addProduct(product, user);
                countOfProduct.setText(productQuantity.toString());
            });

            Button minusButton = new Button("-", event -> {
//                User user = userService.findByLogin("user_1").get();
                Product product = productService.getById(((Product) item).getId());
                Integer productQuantity = cartService.removeProduct(product, user);
                countOfProduct.setText(productQuantity.toString());
            });

            Button addReview = new Button("Оставить отзыв", event -> {
                ComponentUtil.setData(UI.getCurrent(), "product", item);
                UI.getCurrent().navigate("Review");
            });
            HorizontalLayout horizontalLayout = new HorizontalLayout(plusButton, minusButton, countOfProduct, addReview);
            horizontalLayout.setAlignItems(Alignment.CENTER);
            return horizontalLayout;
        }));
    }

    private void productFilter(HorizontalLayout minPriceFilter, HorizontalLayout maxPriceFilter, HorizontalLayout labelFilter) {
        Specification<Product> totalSpecifications = Specification.where(null);
        String minPrice = ((TextField) minPriceFilter.getComponentAt(1)).getValue();
        String maxPrice = ((TextField) maxPriceFilter.getComponentAt(1)).getValue();
        String title = ((TextField) labelFilter.getComponentAt(1)).getValue();
        if (StringUtils.isNoneEmpty(minPrice)) {
            totalSpecifications = totalSpecifications.and(ProductSpecification.productGreaterOrEqualsThen(Integer.valueOf(minPrice)));
        }
        if (StringUtils.isNoneEmpty(maxPrice)) {
            totalSpecifications = totalSpecifications.and(ProductSpecification.productLessOrEquals(Integer.valueOf(maxPrice)));
        }
        if (StringUtils.isNoneEmpty(title)) {
            totalSpecifications = totalSpecifications.and(ProductSpecification.productLikeTitle(title.toLowerCase()));
        }
        mainGrid.setItems(productService.findAll(totalSpecifications));
    }


    private void initUser() {
        this.user = userService.findByLogin(authentication.getName()).get();
    }
}
