package com.bangsil.bangsil.room.dto;

import com.bangsil.bangsil.room.domain.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class RoomDto {

    private Long user_id;

    private String building_name;

    private String room_number;

    private int deposit;

    private int maintenance_fee;

    private int window_direction;

    private Boolean bed;

    private Boolean tv;

    private Boolean washing_machine;

    private Boolean dryer;

    private Boolean refrigerator;

    private Boolean microwave;

    private Boolean gas_range;

    private Boolean induction;

    private Boolean air_conditioner;

    private Boolean full_mirror;

    private String add_residency;

    private String add_detail_address;

    private String add_mining;

    private String add_mold;

    private String add_deafening;

    private Integer add_power_socket;

    private Boolean add_leak;

    private String add_bugs;

    private Date add_move_date; 

    private Date add_visit_date;

    public Room toEntity(final RoomDto roomDto){
        return Room.builder()
                .building_name(roomDto.getBuilding_name())
                .room_number(roomDto.getRoom_number())
                .deposit(roomDto.getDeposit())
                .maintenance_fee(roomDto.getMaintenance_fee())
                .window_direction(roomDto.getWindow_direction())
                .bed(roomDto.getBed())
                .tv(roomDto.getTv())
                .washing_machine(roomDto.getWashing_machine())
                .dryer(roomDto.getDryer())
                .refrigerator(roomDto.getRefrigerator())
                .microwave(roomDto.getMicrowave())
                .gas_range(roomDto.getGas_range())
                .induction(roomDto.getInduction())
                .air_conditioner(roomDto.getAir_conditioner())
                .full_mirror(roomDto.getFull_mirror())
                .add_residency(roomDto.getAdd_residency())
                .add_deafening(roomDto.getAdd_deafening())
                .add_power_socket(roomDto.getAdd_power_socket())
                .add_leak(roomDto.getAdd_leak())
                .add_bugs(roomDto.getAdd_bugs())
                .add_move_date(roomDto.getAdd_move_date())
                .add_visit_date(roomDto.getAdd_visit_date())
                .build();
    }
}
