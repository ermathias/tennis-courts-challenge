package com.tenniscourts.guests;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class GuestDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long name;

}
