package com.bangsil.bangsil.room.dto;

import com.bangsil.bangsil.room.domain.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class RoomDetailResponseDto {
    private Long id;

    private RoomBasicDto roomBasicDto;

    private RoomOptionDto roomOptionDto;

    private RoomAddOptionDto roomAddOptionDto;

    private List<RoomImgResponseDto> roomImgResponseDtoList;

    public RoomDetailResponseDto(Room room, List<RoomImgResponseDto> roomImgResponseDtoList){
        this.id = room.getId();
        this.roomBasicDto = RoomBasicDto.builder()
                .userId(room.getUser().getId())
                .buildingName(room.getBuildingName())
                .roomNumber(room.getRoomNumber())
                .deposit(room.getDeposit())
                .monthlyFee(room.getMonthlyFee())
                .maintenanceFee(room.getMaintenanceFee())
                .windowDirection(room.getWindowDirection())
                .roomSize(room.getRoomSize())
                .build();
        this.roomOptionDto = RoomOptionDto.builder()
                .bed(room.getBed())
                .desk(room.getDesk())
                .tv(room.getTv())
                .washingMachine(room.getWashingMachine())
                .dryer(room.getDryer())
                .refrigerator(room.getRefrigerator())
                .microwave(room.getMicrowave())
                .gasRange(room.getGasRange())
                .induction(room.getInduction())
                .airConditioner(room.getAirConditioner())
                .fullMirror(room.getFullMirror())
                .build();
        this.roomAddOptionDto = RoomAddOptionDto.builder()
                .addResidency(room.getAddResidency())
                .addDetailAddress(room.getAddDetailAddress())
                .addMining(room.getAddMining())
                .addMold(room.getAddMold())
                .addDeafening(room.getAddDeafening())
                .addPowerSocket(room.getAddPowerSocket())
                .addLeak(room.getAddLeak())
                .addBugs(room.getAddBugs())
                .addMoveDate(room.getAddMoveDate())
                .addVisitDate(room.getAddVisitDate())
                .build();
        this.roomImgResponseDtoList = roomImgResponseDtoList;
    }
}
