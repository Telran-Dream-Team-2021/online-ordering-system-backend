package telran.oos.api;

import telran.oos.api.dto.Roles;

public interface ApiConstants {
    String API_MAPPING = "/api/v1";
    String LOGIN_MAPPING = API_MAPPING + "/login";
    String USER_MAPPING = API_MAPPING + "/users";
    String PRODUCT_MAPPING = "/products";

    String ADMIN = Roles.ROLE_ADMIN.toString();
    String USER = Roles.ROLE_USER.toString();
}
