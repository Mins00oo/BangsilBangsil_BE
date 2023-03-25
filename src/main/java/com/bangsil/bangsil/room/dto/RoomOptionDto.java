package com.bangsil.bangsil.room.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomOptionDto {
    private Boolean bed;

    private Boolean tv;

    private Boolean washingMachine;

    private Boolean dryer;

    private Boolean refrigerator;

    private Boolean microwave;

    private Boolean gasRange;

    private Boolean induction;

    private Boolean airConditioner;

    private Boolean fullMirror;
}
