package com.bangsil.bangsil.room.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class RoomImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private String room_origin_name;

    private String room_save_name;

    private String room_path;

    @Builder
    public RoomImg(Room room, String room_origin_name, String room_save_name, String room_path) {
        this.room = room;
        this.room_origin_name = room_origin_name;
        this.room_save_name = room_save_name;
        this.room_path = room_path;
    }
}
