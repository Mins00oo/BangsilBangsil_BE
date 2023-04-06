package com.bangsil.bangsil.room.application;

import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.room.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RoomService {

    void addRoom(RoomRequestDto roomRequestDto, List<MultipartFile> multipartFileList) throws IOException, BaseException;

    RoomResponseDto getRoom(Long roomId);

    void modifyRoom(RoomRequestDto roomDto, Long roomId);

    List<RoomRequestDto> getRoomList(Long userId);
}
