package ru.iaygi.api.tests.vladimir.tests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.Test;
import ru.iaygi.api.service.Specification;
import ru.iaygi.api.tests.vladimir.data.ResourceData;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Owner("vpakhomo")
public class SimpleTest {
    private static final String url = "https://reqres.in";

    @Test
    public void getResourceId() {
        int id = 2;
        String name = "fuchsia rose";
        int year = 2001;
        String color = "#C74375";
        String pantone_value = "17-2031";

        Specification.installSpecification(
                Specification.requestSpecification(url), Specification.responseSpecification200());

        var Data  =
                given()
                        .when()
                        .log().all()
                        .get("/api/unknown/2")
                        .then()
                        .log().all()
                        .extract()
                        .body()
                        .jsonPath()
                        .getObject("data", ResourceData.class);

        assertThat(Data).extracting("id", "name", "year", "color", "pantone_value")
                .contains(id, name, year, color, pantone_value);
    }
}
