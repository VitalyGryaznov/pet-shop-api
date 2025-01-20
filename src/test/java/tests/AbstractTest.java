package tests;

import extensions.RequestSpecificationResolver;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

public class AbstractTest {
    protected RequestSpecification spec;

    @BeforeEach
    public void setUp() {
        spec = RequestSpecificationResolver.getRequestSpecification();
    }
}
