package com.bangsil.bangsil.room.dto;

import com.bangsil.bangsil.room.domain.Room;
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

    public Room toEntity(final RoomRequestDto roomRequestDto){
        return Room.builder()
                .buildingName(roomRequestDto.getRoomBasicDto().getBuildingName())
                .roomNumber(roomRequestDto.getRoomBasicDto().getRoomNumber())
                .deposit(roomRequestDto.getRoomBasicDto().getDeposit())
                .maintenanceFee(roomRequestDto.getRoomBasicDto().getMaintenanceFee())
                .windowDirection(roomRequestDto.getRoomBasicDto().getWindowDirection())
                .bed(roomRequestDto.getRoomOptionDto().getBed())
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
