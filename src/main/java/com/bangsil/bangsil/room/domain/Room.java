package com.bangsil.bangsil.room.domain;

import com.bangsil.bangsil.room.dto.RoomRequestDto;
import com.bangsil.bangsil.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "deposit")
    private int deposit;

    @Column(name = "maintenance_fee")
    private int maintenanceFee;

    @Column(name = "window_direction")
    private int windowDirection;

    @Column(name = "bed")
    private Boolean bed;

    @Column(name = "tv")
    private Boolean tv;

    @Column(name = "washing_machine")
    private Boolean washingMachine;

    @Column(name = "dryer")
    private Boolean dryer;

    @Column(name = "refrigerator")
    private Boolean refrigerator;

    @Column(name = "microwave")
    private Boolean microwave;

    @Column(name = "gas_range")
    private Boolean gasRange;

    @Column(name = "induction")
    private Boolean induction;

    @Column(name = "air_conditioner")
    private Boolean airConditioner;

    @Column(name = "full_mirror")
    private Boolean fullMirror;

    @Column(name = "add_residency")
    private String addResidency;

    @Column(name = "add_detail_address")
    private String addDetailAddress;

    @Column(name = "add_mining")
    private String addMining;

    @Column(name = "add_mold")
    private String addMold;

    @Column(name = "add_deafening")
    private String addDeafening;

    @Column(name = "add_power_socket")
    private Integer addPowerSocket;

    @Column(name = "add_leak")
    private Boolean addLeak;

    @Column(name = "add_bugs")
    private String addBugs;

    @Column(name = "add_move_date")
    private Date addMoveDate;

    @Column(name = "add_visit_date")
    private Date addVisitDate;

    @Builder
    public Room(Long id, User user, String buildingName, String roomNumber, int deposit, int maintenanceFee, int windowDirection, Boolean bed, Boolean tv, Boolean washingMachine, Boolean dryer, Boolean refrigerator, Boolean microwave, Boolean gasRange, Boolean induction, Boolean airConditioner, Boolean fullMirror, String addResidency, String addDetailAddress, String addMining, String addMold, String addDeafening, Integer addPowerSocket, Boolean addLeak, String addBugs, Date addMoveDate, Date addVisitDate) {
        this.id = id;
        this.user = user;
        this.buildingName = buildingName;
        this.roomNumber = roomNumber;
        this.deposit = deposit;
        this.maintenanceFee = maintenanceFee;
        this.windowDirection = windowDirection;
        this.bed = bed;
        this.tv = tv;
        this.washingMachine = washingMachine;
        this.dryer = dryer;
        this.refrigerator = refrigerator;
        this.microwave = microwave;
        this.gasRange = gasRange;
        this.induction = induction;
        this.airConditioner = airConditioner;
        this.fullMirror = fullMirror;
        this.addResidency = addResidency;
        this.addDetailAddress = addDetailAddress;
        this.addMining = addMining;
        this.addMold = addMold;
        this.addDeafening = addDeafening;
        this.addPowerSocket = addPowerSocket;
        this.addLeak = addLeak;
        this.addBugs = addBugs;
        this.addMoveDate = addMoveDate;
        this.addVisitDate = addVisitDate;
    }

    @Builder
    public Room(Long id, User user, String buildingName, String roomNumber, int deposit, int maintenanceFee, int windowDirection, Boolean bed, Boolean tv, Boolean washingMachine, Boolean dryer, Boolean refrigerator, Boolean microwave, Boolean gasRange, Boolean induction, Boolean airConditioner, Boolean fullMirror) {
        this.id = id;
        this.user = user;
        this.buildingName = buildingName;
        this.roomNumber = roomNumber;
        this.deposit = deposit;
        this.maintenanceFee = maintenanceFee;
        this.windowDirection = windowDirection;
        this.bed = bed;
        this.tv = tv;
        this.washingMachine = washingMachine;
        this.dryer = dryer;
        this.refrigerator = refrigerator;
        this.microwave = microwave;
        this.gasRange = gasRange;
        this.induction = induction;
        this.airConditioner = airConditioner;
        this.fullMirror = fullMirror;
    }

    public void modRoom(RoomRequestDto roomRequestDto){
        this.buildingName = roomRequestDto.getRoomBasicDto().getBuildingName();
        this.roomNumber = roomRequestDto.getRoomBasicDto().getRoomNumber();
        this.deposit = roomRequestDto.getRoomBasicDto().getDeposit();
        this.maintenanceFee = roomRequestDto.getRoomBasicDto().getMaintenanceFee();
        this.windowDirection = roomRequestDto.getRoomBasicDto().getWindowDirection();
        this.bed = roomRequestDto.getRoomOptionDto().getBed();
        this.tv = roomRequestDto.getRoomOptionDto().getTv();
        this.washingMachine = roomRequestDto.getRoomOptionDto().getWashingMachine();
        this.dryer = roomRequestDto.getRoomOptionDto().getDryer();
        this.refrigerator = roomRequestDto.getRoomOptionDto().getRefrigerator();
        this.microwave = roomRequestDto.getRoomOptionDto().getMicrowave();
        this.gasRange = roomRequestDto.getRoomOptionDto().getGasRange();
        this.induction = roomRequestDto.getRoomOptionDto().getInduction();
        this.airConditioner = roomRequestDto.getRoomOptionDto().getAirConditioner();
        this.fullMirror = roomRequestDto.getRoomOptionDto().getFullMirror();
        this.addResidency = roomRequestDto.getRoomAddOptionDto().getAddResidency();
        this.addDetailAddress = roomRequestDto.getRoomAddOptionDto().getAddDetailAddress();
        this.addMining = roomRequestDto.getRoomAddOptionDto().getAddMining();
        this.addMold = roomRequestDto.getRoomAddOptionDto().getAddMold();
        this.addDeafening = roomRequestDto.getRoomAddOptionDto().getAddDeafening();
        this.addPowerSocket = roomRequestDto.getRoomAddOptionDto().getAddPowerSocket();
        this.addLeak = roomRequestDto.getRoomAddOptionDto().getAddLeak();
        this.addBugs = roomRequestDto.getRoomAddOptionDto().getAddBugs();
        this.addMoveDate = roomRequestDto.getRoomAddOptionDto().getAddMoveDate();
        this.addVisitDate = roomRequestDto.getRoomAddOptionDto().getAddVisitDate();
    }
}
