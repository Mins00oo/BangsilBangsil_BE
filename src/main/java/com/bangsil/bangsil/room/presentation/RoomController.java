package com.bangsil.bangsil.room.presentation;

import com.bangsil.bangsil.common.BaseResponse;
import com.bangsil.bangsil.room.application.RoomService;
import com.bangsil.bangsil.room.dto.*;
import com.bangsil.bangsil.utils.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
public class RoomController {
    private final RoomService roomService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/room")
    public ResponseEntity<Object> createRoom(HttpServletRequest httpServletRequest, @RequestPart(value = "imgList", required = false) List<MultipartFile> multipartFileList, @RequestPart(value = "roomInfo") ObjectNode  objectNode) throws IOException {
//        String token = jwtTokenProvider.getToken(httpServletRequest);
//        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));
        ObjectMapper mapper = new ObjectMapper();
        RoomBasicDto roomBasicDto = mapper.treeToValue(objectNode.get("roomBasicDto"),RoomBasicDto.class);
        RoomOptionDto roomOptionDto = mapper.treeToValue(objectNode.get("roomOptionDto"),RoomOptionDto.class);
        RoomAddOptionDto roomAddOptionDto = mapper.treeToValue(objectNode.get("roomAddOptionDto"),RoomAddOptionDto.class);
        RoomRequestDto roomRequestDto = new RoomRequestDto(roomBasicDto, roomOptionDto, roomAddOptionDto);
        roomService.addRoom(roomRequestDto, multipartFileList);
        return new ResponseEntity(new BaseResponse(" "), HttpStatus.OK);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<Object> getRoom(@PathVariable Long roomId, HttpServletRequest httpServletRequest){
        RoomResponseDto roomResponseDto = roomService.getRoom(roomId);
        return new ResponseEntity(new BaseResponse(roomResponseDto),HttpStatus.OK);
    }

    @PutMapping("/room/{roomId}")
    public ResponseEntity<Object> modifyRoom(HttpServletRequest httpServletRequest, @RequestBody RoomRequestDto roomRequestDto, @PathVariable Long roomId){
        roomService.modifyRoom(roomRequestDto,roomId);
        return new ResponseEntity(new BaseResponse(" "),HttpStatus.OK);
    }
}
