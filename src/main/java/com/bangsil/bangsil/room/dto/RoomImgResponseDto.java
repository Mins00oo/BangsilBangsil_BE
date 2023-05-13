package com.bangsil.bangsil.room.dto;

import com.bangsil.bangsil.room.domain.RoomImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomImgResponseDto {

    private String room_origin_name;

    private String room_save_name;

    private String room_path;

    public RoomImgResponseDto(RoomImg roomImg){
        this.room_origin_name = roomImg.getRoom_origin_name();
        this.room_save_name = roomImg.getRoom_save_name();
        this.room_path = roomImg.getRoom_path();
    }
}
