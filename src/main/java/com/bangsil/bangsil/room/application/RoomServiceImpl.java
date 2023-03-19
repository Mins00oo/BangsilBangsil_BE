package com.bangsil.bangsil.room.application;

import com.bangsil.bangsil.room.domain.Room;
import com.bangsil.bangsil.room.dto.RoomDto;
import com.bangsil.bangsil.room.infrastructure.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;

    @Override
    public void addRoom(RoomDto roomDto) {
        Room room = roomRepository.save(roomDto.toEntity(roomDto));
    }

    @Override
    public RoomDto getRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).get();
        RoomDto roomDto = modelMapper.map(room,RoomDto.class);
        return roomDto;
    }


}
