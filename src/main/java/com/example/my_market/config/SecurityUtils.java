package com.example.my_market.config;

import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Handler;
import java.util.stream.Stream;

public class SecurityUtils {
    public static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null && Stream.of(HandlerHelper.RequestType.values())
                .anyMatch(requestType -> requestType.getIdentifier().equals(parameterValue));
    }
}
