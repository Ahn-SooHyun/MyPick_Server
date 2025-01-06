package kr.co.MyPick_server.DTO.idPWFound;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class IDFoundReq {

    @NotBlank(message = "Name cannot be empty.")
    @Pattern(regexp = "^[가-힣a-zA-Z ]+$", message = "Name must contain only letters and spaces.")
    private String name;

    @NotNull(message = "Birth date cannot be empty.")
    @Past(message = "Birth date must be a past date.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

}
