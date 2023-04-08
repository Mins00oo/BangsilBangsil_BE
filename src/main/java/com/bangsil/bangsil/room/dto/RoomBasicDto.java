package com.bangsil.bangsil.room.dto;

import com.bangsil.bangsil.user.domain.User;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomBasicDto {

    private Long userId;

    private String buildingName;

    private String roomNumber;

    private Integer deposit;

    private Integer monthlyFee;

    private Integer maintenanceFee;

    private String windowDirection;

    private Integer roomSize;
}
