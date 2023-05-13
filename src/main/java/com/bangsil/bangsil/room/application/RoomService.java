package com.bangsil.bangsil.room.application;

import com.bangsil.bangsil.common.BaseResponse;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.room.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RoomService {

    BaseResponse addRoom(ObjectNode objectNode, List<MultipartFile> multipartFileList,Long userId) throws JsonProcessingException, BaseException;

    BaseResponse getRoom(Long roomId);

    BaseResponse modifyRoom(RoomRequestDto roomDto, Long roomId);

    BaseResponse getRoomList(Long userId);
}
