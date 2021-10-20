package com.tenniscourts.guests;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class GuestDTO {
	private Long id;
	private String ipNumberUpdate;
	private Long userCreate;
	private Long userUpdate;
	private LocalDateTime dateUpdate;
	private String ipNumberCreate;
	private LocalDateTime dateCreate;
	private String name;

}
