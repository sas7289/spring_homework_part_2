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
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.EntityManager;
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
    private final Session hibernateSession;

    public ReviewView(ReviewService reviewService,
                      HttpSession session,
                      Session hibernateSession) {
        this.session = session;
        this.hibernateSession = hibernateSession;
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
            review.setModerated(false);
            reviewService.saveReview(review);
            UI.getCurrent().getPage().reload();
        });

        if (currentProduct == null) {
            currentProduct = (Product) session.getAttribute("product");
        }


        add(Common.initNavigationPanel());
        add(currentReviewText);
        add(saveReviewButton);
        initReviews();
    }


    public void initReviews() {
        List<Review> reviews = reviewService.findAllByProduct(currentProduct);
        Boolean isModerator = currentUser.getRole().getName().equals("admin");
        for (Review review : reviews) {
            TextArea textArea = new TextArea();
            textArea.setValue(review.getText());
            textArea.setReadOnly(true);

            if(isModerator && !review.getModerated()) {
                add(textArea);
                add(new Button("Опубликовать", event -> {
                    review.setModerated(true);
                    reviewService.saveReview(review);
                    UI.getCurrent().getPage().reload();
                }));
            } else if (review.getModerated()){
                add(textArea);
            }
        }
    }
}
