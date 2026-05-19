package api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BaseGetAllResponse<T>(
        Integer total,
        // allows to potentially use the same class for different API responses, not only lots
        @JsonAlias({"lots", "items", "results"})
        List<T> data
) {}
