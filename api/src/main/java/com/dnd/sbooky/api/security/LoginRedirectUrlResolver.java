package com.dnd.sbooky.api.security;

import static com.dnd.sbooky.api.security.EnvironmentConstants.STATE_DELIMITER;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginRedirectUrlResolver {

    private static final String STATE_PARAM = "state";
    private static final String SUCCESS_PATH = "/auth/callback";
    private static final String FAILURE_PATH = "/login";

    private final LoginRedirectProperties redirectProperties;

    public String resolveRedirectUrl(HttpServletRequest request, boolean isFailure) {
        Environment environment = resolveEnvironment(request);
        String baseUrl = getBaseUrlForEnvironment(environment);

        return isFailure ? baseUrl + FAILURE_PATH : baseUrl + SUCCESS_PATH;
    }

    private Environment resolveEnvironment(HttpServletRequest request) {
        String state = request.getParameter(STATE_PARAM);
        if (state == null || state.isEmpty()) {
            throw new IllegalArgumentException("State parameter is missing!");
        }

        try {
            String decodedState = new String(Base64.getDecoder().decode(state));

            String[] stateParts = decodedState.split(STATE_DELIMITER);

            if (stateParts.length != 2) {
                throw new IllegalArgumentException("Invalid state parameter format!");
            }

            return Environment.fromString(stateParts[0]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid state parameter encoding");
        }
    }

    private String getBaseUrlForEnvironment(Environment environment) {
        return switch (environment) {
            case LOCAL -> redirectProperties.getLocal();
            case DEV -> redirectProperties.getDev();
            case PROD -> redirectProperties.getProd();
        };
    }
}
