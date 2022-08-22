package me.potato.udemywebflux.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MultiplyRequestDto {
    private Integer first;
    private Integer second;
}
