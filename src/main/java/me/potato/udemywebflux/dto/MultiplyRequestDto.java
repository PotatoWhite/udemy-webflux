package me.potato.udemywebflux.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MultiplyRequestDto {
    private Integer first;
    private Integer second;
}
