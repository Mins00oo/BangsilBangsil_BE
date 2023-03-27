package com.bangsil.bangsil.room.dto;

import com.bangsil.bangsil.user.domain.User;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomBasicDto {

    private User userId;

    private String buildingName;

    private String roomNumber;

    private int deposit;

    private int monthlyFee;

    private int maintenanceFee;

    private int windowDirection;

    private int roomSize;
}
