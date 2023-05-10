package com.bangsil.bangsil.room.dto;

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
}
