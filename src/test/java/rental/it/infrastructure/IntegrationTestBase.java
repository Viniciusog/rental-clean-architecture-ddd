package rental.it.infrastructure;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public abstract class IntegrationTestBase {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SpringLiquibase liquibase;

    @BeforeEach
    void resetDatabase() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement  = connection.createStatement()) {
                statement.execute("DROP ALL OBJECTS;");
            }
        }
        liquibase.afterPropertiesSet();
    }
}