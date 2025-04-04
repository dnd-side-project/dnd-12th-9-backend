package com.dnd.sbooky.api.security;

import static com.dnd.sbooky.api.security.TokenConstants.QUERY_PARAM;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private static final String AUTHORIZATION_REQUEST_BASE_URI = "/api/login";
    private final OAuth2AuthorizationRequestResolver defaultAuthorizationRequestResolver;
    private final RedirectProperties redirectProperties;

    public CustomAuthorizationRequestResolver(
            ClientRegistrationRepository clientRegistrationRepository,
            RedirectProperties redirectProperties) {
        this.defaultAuthorizationRequestResolver =
                new DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository, AUTHORIZATION_REQUEST_BASE_URI);
        this.redirectProperties = redirectProperties;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest authorizationRequest =
                defaultAuthorizationRequestResolver.resolve(request);
        return customizeAuthorizationRequest(request, authorizationRequest);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(
            HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest authorizationRequest =
                defaultAuthorizationRequestResolver.resolve(request, clientRegistrationId);
        return customizeAuthorizationRequest(request, authorizationRequest);
    }

    private OAuth2AuthorizationRequest customizeAuthorizationRequest(
            HttpServletRequest request, OAuth2AuthorizationRequest authorizationRequest) {

        if (authorizationRequest == null) {
            return null;
        }

        String environment = request.getParameter(QUERY_PARAM);
        String redirectUri;

        if ("local".equals(environment)) {
            redirectUri = redirectProperties.getLocal();
        } else if ("dev".equals(environment)) {
            redirectUri = redirectProperties.getDev();
        } else if ("prod".equals(environment)) {
            redirectUri = redirectProperties.getProd();
        } else {
            redirectUri = redirectProperties.getLocal();
        }

        request.getSession().setAttribute(QUERY_PARAM, redirectUri);

        return authorizationRequest;
    }
}
