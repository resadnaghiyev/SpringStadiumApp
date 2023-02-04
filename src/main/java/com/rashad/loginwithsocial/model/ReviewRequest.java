package com.rashad.loginwithsocial.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {

    @NotBlank(message = "required should not be empty")
    @Schema(example = "This is review")
    private String text;

    private List<String> usernames;
}
