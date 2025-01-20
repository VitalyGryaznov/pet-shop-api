package extensions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.config;
import static io.restassured.config.HttpClientConfig.httpClientConfig;
import static io.restassured.filter.log.LogDetail.ALL;

public class RequestSpecificationResolver {

    private static RequestSpecification spec;

    static {
        List<Filter> filtersList = new ArrayList<>();
        filtersList.add(new AllureRestAssured());
        filtersList.add(new RequestLoggingFilter(ALL));
        filtersList.add(new ResponseLoggingFilter(ALL));

        spec = new RequestSpecBuilder()
                .setConfig(config()
                        .httpClient(httpClientConfig()
                                .setParam("http.connection.timeout", 60000)
                                .setParam("http.socket.timeout", 60000)
                        )
                        .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                                .jackson2ObjectMapperFactory((cls, charset) -> {
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    objectMapper.findAndRegisterModules();
                                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                                    objectMapper.enable(SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE);
                                    return objectMapper;
                                })
                        )
                )
                .setBaseUri("https://petstore.swagger.io/v2")
                .addFilters(filtersList)
                .setRelaxedHTTPSValidation()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification getRequestSpecification() {
        return spec;
    }
}
