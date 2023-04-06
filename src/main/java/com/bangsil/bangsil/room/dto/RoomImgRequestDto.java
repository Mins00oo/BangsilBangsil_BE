package com.bangsil.bangsil.room.dto;

import com.bangsil.bangsil.room.domain.Room;
import com.bangsil.bangsil.room.domain.RoomImg;
import com.bangsil.bangsil.utils.s3.dto.S3UploadDto;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomImgRequestDto {

    private Room room;

    private String room_origin_name;

    private String room_save_name;

    private String room_path;

    public RoomImg toEntity(final Room room, S3UploadDto s3UploadDto){
        return RoomImg.builder()
                .room(room)
                .room_origin_name(s3UploadDto.getOriginName())
                .room_save_name(s3UploadDto.getSaveName())
                .room_path(s3UploadDto.getImgUrl())
                .build();
    }
}
