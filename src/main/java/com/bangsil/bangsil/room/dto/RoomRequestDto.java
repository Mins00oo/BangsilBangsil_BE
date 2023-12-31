package com.bangsil.bangsil.room.dto;

import com.bangsil.bangsil.room.domain.Room;
import com.bangsil.bangsil.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDto {

    private RoomBasicDto roomBasicDto;

    private RoomOptionDto roomOptionDto;

    private RoomAddOptionDto roomAddOptionDto;

    private User user;

    public Room toEntity(final RoomRequestDto roomRequestDto){
        return Room.builder()
                .user(roomRequestDto.getUser())
                .buildingName(roomRequestDto.getRoomBasicDto().getBuildingName())
                .roomNumber(roomRequestDto.getRoomBasicDto().getRoomNumber())
                .deposit(roomRequestDto.getRoomBasicDto().getDeposit())
                .monthlyFee(roomRequestDto.getRoomBasicDto().getMonthlyFee())
                .maintenanceFee(roomRequestDto.getRoomBasicDto().getMaintenanceFee())
                .windowDirection(roomRequestDto.getRoomBasicDto().getWindowDirection())
                .roomSize(roomRequestDto.getRoomBasicDto().getRoomSize())
                .bed(roomRequestDto.getRoomOptionDto().getBed())
                .desk(roomRequestDto.getRoomOptionDto().getDesk())
                .tv(roomRequestDto.getRoomOptionDto().getTv())
                .washingMachine(roomRequestDto.getRoomOptionDto().getWashingMachine())
                .dryer(roomRequestDto.getRoomOptionDto().getDryer())
                .refrigerator(roomRequestDto.getRoomOptionDto().getRefrigerator())
                .microwave(roomRequestDto.getRoomOptionDto().getMicrowave())
                .gasRange(roomRequestDto.getRoomOptionDto().getGasRange())
                .induction(roomRequestDto.getRoomOptionDto().getInduction())
                .airConditioner(roomRequestDto.getRoomOptionDto().getAirConditioner())
                .fullMirror(roomRequestDto.getRoomOptionDto().getFullMirror())
                .addResidency(roomRequestDto.getRoomAddOptionDto().getAddResidency())
                .addDeafening(roomRequestDto.getRoomAddOptionDto().getAddDeafening())
                .addPowerSocket(roomRequestDto.getRoomAddOptionDto().getAddPowerSocket())
                .addLeak(roomRequestDto.getRoomAddOptionDto().getAddLeak())
                .addBugs(roomRequestDto.getRoomAddOptionDto().getAddBugs())
                .addMoveDate(roomRequestDto.getRoomAddOptionDto().getAddMoveDate())
                .addVisitDate(roomRequestDto.getRoomAddOptionDto().getAddVisitDate())
                .build();
    }
}
