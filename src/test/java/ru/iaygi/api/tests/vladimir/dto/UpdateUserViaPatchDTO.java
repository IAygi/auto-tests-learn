package ru.iaygi.api.tests.vladimir.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.iaygi.api.service.StatusCodeCondition;

@Data
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserViaPatchDTO {
    private String name;
    private String job;
    private String updatedAt;
    public void shouldHave(StatusCodeCondition statusCodeCondition) {
    }
}
