package com.bangsil.bangsil.room.presentation;

import com.bangsil.bangsil.common.BaseResponse;
import com.bangsil.bangsil.room.application.RoomService;
import com.bangsil.bangsil.room.dto.RoomDto;
import com.bangsil.bangsil.utils.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/room")
    public ResponseEntity<Object> createRoom(HttpServletRequest httpServletRequest, @RequestBody RoomDto roomDto){
//        String token = jwtTokenProvider.getToken(httpServletRequest);
//        Long user_id = Long.valueOf(jwtTokenProvider.getUserPk(token));
        roomService.addRoom(roomDto);
        return new ResponseEntity(new BaseResponse(" "), HttpStatus.OK);
    }

    @GetMapping("/room/{room_id}")
    public ResponseEntity<Object> getRoom(@PathVariable Long room_id, HttpServletRequest httpServletRequest){
        RoomDto roomDto = roomService.getRoom(room_id);
        return new ResponseEntity(new BaseResponse(roomDto),HttpStatus.OK);
    }

    @PutMapping("/room/{room_id}")
    public ResponseEntity<Object> modifyRoom(HttpServletRequest httpServletRequest, @RequestBody RoomDto roomDto){
        return new ResponseEntity(new BaseResponse(" "),HttpStatus.OK);
    }
}
