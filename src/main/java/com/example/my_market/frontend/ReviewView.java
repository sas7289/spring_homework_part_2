package com.example.my_market.frontend;

import com.example.my_market.config.CustomDetails;
import com.example.my_market.entity.Product;
import com.example.my_market.entity.Review;
import com.example.my_market.entity.User;
import com.example.my_market.service.ReviewService;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Route("Review")
public class ReviewView extends VerticalLayout {
    private final HttpSession session;
    private final ReviewService reviewService;
    private final Authentication authentication;
    private Product currentProduct;
    private final User currentUser;
    private Grid<Review> mainGrid;

    public ReviewView(ReviewService reviewService, HttpSession session) {
        this.session = session;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        this.reviewService = reviewService;
        this.currentProduct = (Product) ComponentUtil.getData(UI.getCurrent(), "product");
        if (currentProduct != null) {
            session.setAttribute("product", currentProduct);
        }
        this.currentUser = ((CustomDetails) authentication.getPrincipal()).getUser();
        TextArea currentReviewText = new TextArea("Ваш отзыв");
        currentReviewText.setSizeFull();
        Button saveReviewButton = new Button("Сохранить отзыв", event -> {
            Review review = new Review();
            review.setProduct(currentProduct);
            review.setUser(currentUser);
            review.setText(currentReviewText.getValue());
            review.setId(UUID.randomUUID());
            reviewService.saveReview(review);
            UI.getCurrent().getPage().reload();
        });

        if (currentProduct == null) {
            currentProduct = (Product) session.getAttribute("product");
        }

        initReviews();
//        List<Review> allByProduct = reviewService.findAllByProduct(currentProduct);
//        mainGrid = new Grid<>(Review.class);
//        mainGrid.setItems(allByProduct);
//        mainGrid.setColumns("text");
//        mainGrid.getColumnByKey("text").setHeader("Отзывы");
//        add(currentReviewText);
//        add(saveReviewButton);
//        add(mainGrid);
    }


    private void initReviews() {
        List<Review> reviews = reviewService.findAllByProduct(currentProduct);
        for (Review review : reviews) {
            TextArea textArea = new TextArea();
            textArea.setValue(review.getText());
            add(textArea);
        }
    }
}
