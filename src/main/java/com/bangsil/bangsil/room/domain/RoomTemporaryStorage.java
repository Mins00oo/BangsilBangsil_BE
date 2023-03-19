package com.bangsil.bangsil.room.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class RoomTemporaryStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public RoomTemporaryStorage(Long id, Long user_id, String building_name, String room_number, int deposit, int maintenance_fee, int window_direction, Boolean bed, Boolean tv, Boolean washing_machine, Boolean dryer, Boolean refrigerator, Boolean microwave, Boolean gas_range, Boolean induction, Boolean air_conditioner, Boolean full_mirror, String add_residency, String add_detail_address, String add_mining, String add_mold, String add_deafening, Integer add_power_socket, Boolean add_leak, String add_bugs, Date add_move_date, Date add_visit_date) {
        this.id = id;
        this.user_id = user_id;
        this.building_name = building_name;
        this.room_number = room_number;
        this.deposit = deposit;
        this.maintenance_fee = maintenance_fee;
        this.window_direction = window_direction;
        this.bed = bed;
        this.tv = tv;
        this.washing_machine = washing_machine;
        this.dryer = dryer;
        this.refrigerator = refrigerator;
        this.microwave = microwave;
        this.gas_range = gas_range;
        this.induction = induction;
        this.air_conditioner = air_conditioner;
        this.full_mirror = full_mirror;
        this.add_residency = add_residency;
        this.add_detail_address = add_detail_address;
        this.add_mining = add_mining;
        this.add_mold = add_mold;
        this.add_deafening = add_deafening;
        this.add_power_socket = add_power_socket;
        this.add_leak = add_leak;
        this.add_bugs = add_bugs;
        this.add_move_date = add_move_date;
        this.add_visit_date = add_visit_date;
    }
}
