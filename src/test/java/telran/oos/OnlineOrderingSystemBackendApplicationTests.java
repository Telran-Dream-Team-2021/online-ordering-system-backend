package telran.oos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@AutoConfigureMockMvc
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@SpringBootTest
class OnlineOrderingSystemBackendApplicationTests {
//    ObjectMapper mapper = new ObjectMapper();
//    @Autowired
//    MockMvc mockMvc;
    @Test
    void contextLoads() {
//        assertNotNull(mockMvc);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        String dateString = date.format(formatter);
        System.out.println(dateString);
        LocalDateTime secondDate = LocalDateTime.parse(dateString, formatter);

        System.out.println(secondDate);
    }

}
