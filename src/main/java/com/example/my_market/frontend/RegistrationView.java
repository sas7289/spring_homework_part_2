package com.example.my_market.frontend;

import com.example.my_market.entity.Role;
import com.example.my_market.entity.User;
import com.example.my_market.service.RoleService;
import com.example.my_market.service.UserService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.NavigationEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.NavigationRouteTarget;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Route("register")
public class RegistrationView extends VerticalLayout {
    private final UserService userService;
    private final Map<String, AbstractField> textFields;
    private final RoleService roleService;
    private final Label topLabel;

    {
        topLabel = new Label();
        topLabel.setVisible(false);
        setAlignItems(Alignment.CENTER);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        textFields = new LinkedHashMap<>();
        textFields.put("Login", new TextField("Логин"));
        textFields.put("Password", new PasswordField("Пароль"));
        textFields.put("RePassword", new PasswordField("Пароль (ещё раз)"));
        textFields.put("Last_name", new TextField("Фамилия"));
        textFields.put("Name", new TextField("Имя"));
        textFields.put("Middle_name", new TextField("Отчество"));
        textFields.put("Phone", new TextField("Телефон"));
    }

    public RegistrationView(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

        Button button = new Button("Зарегистрироваться");
        button.addClickListener(event -> {
            if (!checkNotNull()) {
                return;
            }
            User newUser = new User();
            Role role = roleService.getRole("user");
            newUser.setId(UUID.randomUUID());
            newUser.setLogin(((TextField) textFields.get("Login")).getValue());
            if (!((PasswordField) textFields.get("Password")).getValue().equals(((PasswordField) textFields.get("RePassword")).getValue())) {
                topLabel.setVisible(true);
                topLabel.setText("Пароли не совпадают!");
                return;
            }
            topLabel.setVisible(false);
            newUser.setPassword(BCrypt.hashpw(((PasswordField) textFields.get("Password")).getValue(), BCrypt.gensalt()));
            newUser.setName(((TextField) textFields.get("Name")).getValue());

            newUser.setMiddle_name(((TextField) textFields.get("Middle_name")).getValue());
            newUser.setLast_name(((TextField) textFields.get("Last_name")).getValue());
            newUser.setPhone(((TextField) textFields.get("Phone")).getValue());
            newUser.setRole(role);
            userService.saveUser(newUser);
            UI.getCurrent().navigate("login");
        });
        add(topLabel);
        add(textFields.values().toArray(new AbstractField[6]));
        add(button);
    }




    private boolean checkNotNull() {
        for (AbstractField field : textFields.values()) {
            if (field.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
