package com.bangsil.bangsil.room.application;

import com.bangsil.bangsil.room.domain.Room;
import com.bangsil.bangsil.room.dto.RoomDto;

public interface RoomService {

    void addRoom(RoomDto roomDto);

    RoomDto getRoom(Long roomId);
}
