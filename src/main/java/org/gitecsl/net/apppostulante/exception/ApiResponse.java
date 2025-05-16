package org.gitecsl.net.apppostulante.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String message;
    private T data;
    private Instant timestamp;
    private String status;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now();
        this.status = "success";
    }

    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMessage(message);
        response.setTimestamp(Instant.now());
        response.setStatus("error");
        return response;
    }
}
