package telran.oos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import telran.oos.api.dto.*;
import telran.oos.jpa.entity.User;
import telran.oos.service.implementation.OrderServiceImpl;
import telran.oos.service.implementation.ProductServiceImpl;
import telran.oos.service.implementation.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static telran.oos.api.ApiConstants.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OosOrderTest {
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserServiceImpl users;
    @Autowired
    ProductServiceImpl products;
    @Autowired
    OrderServiceImpl orders;




    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
    }

    @Test @Order(1)
    void creatingUsers() throws Exception {
        AuthRequestDto user1 = new AuthRequestDto("first@mail.ru", "1234", "");
            mockMvc.perform(
                    MockMvcRequestBuilders.post(LOGIN_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(user1))
            );

        User user = users.read("first@mail.ru");
        System.out.println(user.getId());
        assertTrue("first@mail.ru".equals(user.getEmail()));
    }

    @Test @Order(2)
    void creatingProducts(){
        products.create(new ProductDto(
                1l,
                "Cake",
                "Cakes",
                "some description",
                "image/url",
                100f,
                MeasurementUnit.PIECES,
                true
        ));

        assertTrue("Cake".equals(products.read(1l).getName()));
    }

    @Test @Order(3)
    void creatingOrders() throws Exception {
        List<OrderItemDto> items = new ArrayList<>();
        items.add(new OrderItemDto(1l, 1l, 1l, 100f, 5));
        OrderDto order = new OrderDto(
                1l,
                items,
                1l,
                "wall-street",
                OrderStatus.created,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post(ORDER_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(order))
        );

        System.out.println(orders.getAll().size());
        orders.getAll().forEach(o-> System.out.println(o));


    }
}
