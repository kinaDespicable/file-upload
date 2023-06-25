package dev.dani.fileupload.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("timestamp")
    private Instant timestamp;

}