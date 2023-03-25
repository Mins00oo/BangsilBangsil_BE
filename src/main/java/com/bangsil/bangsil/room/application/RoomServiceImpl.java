package com.bangsil.bangsil.room.application;

import com.bangsil.bangsil.room.domain.Room;
import com.bangsil.bangsil.room.dto.*;
import com.bangsil.bangsil.room.infrastructure.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;

    @Override
    public void addRoom(RoomRequestDto roomRequestDto) {
        Room room = roomRequestDto.toEntity(roomRequestDto);
        roomRepository.save(room);
    }

    @Override
    public RoomResponseDto getRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).get();
        RoomResponseDto roomResponseDto = new RoomResponseDto(room);
        return roomResponseDto;
    }

    @Override
    public void modifyRoom(RoomRequestDto roomRequestDto, Long roomId) {
        Room room = roomRepository.findById(roomId).get();
        room.modRoom(roomRequestDto);
        roomRepository.save(room);
    }

    @Override
    public List<RoomRequestDto> getRoomList(Long userId) {
        return null;
    }
}
