package rental.it.infrastructure.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@JdbcTest
public class H2DatabaseTest {

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    public void beforeEach() {
        jdbc.execute("CREATE TABLE test(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255));");
        jdbc.execute("INSERT INTO test(name) VALUES('Anna')");
        jdbc.execute("INSERT INTO test(name) VALUES('John')");
    }

    @Test
    public void queryTestTable() {
        List<String> testNames = jdbc.query("SELECT name FROM test",
                (rs, rowNum) -> rs.getString("name"));

        assertThat(testNames, containsInAnyOrder("Anna", "John"));
    }
}