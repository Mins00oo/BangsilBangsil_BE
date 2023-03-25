package com.bangsil.bangsil.room.application;

import com.bangsil.bangsil.room.dto.*;

import java.util.List;

public interface RoomService {

    void addRoom(RoomRequestDto roomRequestDto);

    RoomResponseDto getRoom(Long roomId);

    void modifyRoom(RoomRequestDto roomDto, Long roomId);

    List<RoomRequestDto> getRoomList(Long userId);
}
