package com.example.my_market.frontend;

import com.example.my_market.entity.Role;
import com.example.my_market.entity.User;
import com.example.my_market.service.RoleService;
import com.example.my_market.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.NavigationEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.NavigationRouteTarget;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Route("register")
public class RegistrationView extends VerticalLayout {
    private final UserService userService;
    private final Map<String, TextField> textFields;
    private final RoleService roleService;

    {
        textFields = new HashMap<>();
        textFields.put("Login", new TextField("Login"));
        textFields.put("Password", new TextField("Password"));
        textFields.put("Name", new TextField("name"));
        textFields.put("Middle_name", new TextField("middle_name"));
        textFields.put("Last_name", new TextField("last_name"));
        textFields.put("Phone", new TextField("phone"));

    }

    public RegistrationView(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
//        TextField login = new TextField();
//        login.setLabel("Login");
//        TextField password = new TextField();
//        password.setLabel("Password");
//        TextField name = new TextField();
//        name.setLabel("name");
//        TextField middle_name = new TextField();
//        middle_name.setLabel("middle_name");
//        TextField last_name = new TextField();
//        last_name.setLabel("last_name");
//        TextField phone = new TextField();
//        phone.setLabel("phone");
        Button button = new Button("Зарегистрироваться");
        button.addClickListener(event -> {
            if(!checkNotNull()) {
                return;
            }
            User newUser = new User();
            Role role = roleService.getRole("user");
            newUser.setId(UUID.randomUUID());
            newUser.setLogin(textFields.get("Login").getValue());
//            newUser.setPassword(textFields.get("Password").getValue());
            newUser.setPassword(BCrypt.hashpw(textFields.get("Password").getValue(), BCrypt.gensalt()));
            newUser.setName(textFields.get("Name").getValue());

            newUser.setMiddle_name(textFields.get("Middle_name").getValue());
            newUser.setLast_name(textFields.get("Last_name").getValue());
            newUser.setPhone(textFields.get("Phone").getValue());
            newUser.setRole(role);
//            newUser.setRole(new Role("user"));
            userService.saveUser(newUser);
            UI.getCurrent().navigate("login");
        });

        add(textFields.values().toArray(new TextField[6]));
        add(button);
    }


//    private void textFieldsInitialize() {
//        TextField login = new TextField();
//        login.setLabel("Login");
//        TextField password = new TextField();
//        password.setLabel("Password");
//        TextField name = new TextField();
//        name.setLabel("name");
//        TextField middle_name = new TextField();
//        middle_name.setLabel("middle_name");
//        TextField last_name = new TextField();
//        last_name.setLabel("last_name");
//        TextField phone = new TextField();
//        phone.setLabel("phone");
//    }


    private boolean checkNotNull() {
        for (TextField field : textFields.values()) {
            if(field.getValue().isBlank()) {
                return false;
            }
        }
        return true;
    }
}
