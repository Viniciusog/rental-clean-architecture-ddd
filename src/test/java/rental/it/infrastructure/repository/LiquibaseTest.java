package rental.it.infrastructure.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@JdbcTest
public class LiquibaseTest {

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    public void beforeEach() {
        jdbc.execute("INSERT INTO customer(name, email, active)  " +
                "VALUES ('Liquibase', 'liquibase@server.com', true)");
    }

    @Test
    public void queryTestTable() {
        String name = jdbc.queryForObject("""
                SELECT name FROM customer WHERE email = 'liquibase@server.com'
                """, String.class);

        assertThat(name, is("Liquibase"));
    }
}