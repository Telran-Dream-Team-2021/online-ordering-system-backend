package telran.oos.api;

import telran.oos.api.dto.Roles;

public interface ApiConstants {
    String API_MAPPING = "/api/v1";
    String LOGIN_MAPPING = API_MAPPING + "/login";
    String USER_MAPPING = API_MAPPING + "/users";
    String PRODUCT_MAPPING = API_MAPPING + "/products";
    String CATEGORY_MAPPING = API_MAPPING + "/categories";
    String BASKET_MAPPING = API_MAPPING + "/baskets";

    /* WebSockets configuration */
    String WEBSOCKET_MAPPING = "/websocket-oos/v1/**";
    String WEBSOCKET_BROKER_MAPPING = "/topics";
    String WEBSOCKET_PRODUCT_THEME = WEBSOCKET_BROKER_MAPPING + "/products";
    String WEBSOCKET_BASKET_THEME = WEBSOCKET_BROKER_MAPPING + "/baskets";

    /*  products constraints */
    int MIN_PRODUCT_NAME_LENGTH = 3;
    int MAX_PRODUCT_NAME_LENGTH = 30;
    int MIN_CATEGORY_NAME_LENGTH = 1;
    int MAX_CATEGORY_NAME_LENGTH = 255;
    int MIN_DESCRIPTION_LENGTH = 100;
    int MAX_DESCRIPTION_LENGTH = 1000;
    int MIN_PRODUCT_PRICE = 1;
    int MAX_PRODUCT_PRICE = 10_000;

    String ADMIN = Roles.ROLE_ADMIN.toString();
    String USER = Roles.ROLE_USER.toString();
}
