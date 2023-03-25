package com.bangsil.bangsil.room.dto;

import lombok.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomAddOptionDto {

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
}
