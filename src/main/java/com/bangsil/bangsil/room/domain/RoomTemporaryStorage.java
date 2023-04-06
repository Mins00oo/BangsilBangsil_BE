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

    private Long userId;

    private String buildingName;

    private String roomNumber;

    private int deposit;

    private int maintenanceFee;

    private int windowDirection;

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

    private String addResidency;

    private String addDetailAddress;

    private String addMining;

    private String addMold;

    private String addDeafening;

    private Integer addPowerSocket;

    private Boolean addLeak;

    private String addBugs;

    private Date addMoveDate;

    private Date addVisitDate;

    public RoomTemporaryStorage(Long id, Long userId, String buildingName, String roomNumber, int deposit, int maintenanceFee, int windowDirection, Boolean bed, Boolean tv, Boolean washingMachine, Boolean dryer, Boolean refrigerator, Boolean microwave, Boolean gasRange, Boolean induction, Boolean airConditioner, Boolean fullMirror, String addResidency, String addDetailAddress, String addMining, String addMold, String addDeafening, Integer addPowerSocket, Boolean addLeak, String addBugs, Date addMoveDate, Date addVisitDate) {
        this.id = id;
        this.userId = userId;
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
}
