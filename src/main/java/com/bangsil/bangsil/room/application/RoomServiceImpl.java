package com.bangsil.bangsil.room.application;

import com.bangsil.bangsil.common.BaseResponse;
import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.room.domain.Room;
import com.bangsil.bangsil.room.domain.RoomImg;
import com.bangsil.bangsil.room.dto.*;
import com.bangsil.bangsil.room.infrastructure.RoomImgRepository;
import com.bangsil.bangsil.room.infrastructure.RoomRepository;
import com.bangsil.bangsil.user.domain.User;
import com.bangsil.bangsil.user.infrastructure.UserRepository;
import com.bangsil.bangsil.utils.s3.S3UploaderService;
import com.bangsil.bangsil.utils.s3.dto.S3UploadDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bangsil.bangsil.common.BaseResponseStatus.ROOM_CREATE_FAILED;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoomServiceImpl implements RoomService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;

    private final S3UploaderService s3UploaderService;
    private final RoomImgRepository roomImgRepository;

    @Override
    @Transactional
    public BaseResponse addRoom(ObjectNode objectNode, List<MultipartFile> multipartFileList,Long userId) throws JsonProcessingException, BaseException {
        ObjectMapper mapper = new ObjectMapper();
        RoomBasicDto roomBasicDto = mapper.treeToValue(objectNode.get("roomBasicDto"),RoomBasicDto.class);
        RoomOptionDto roomOptionDto = mapper.treeToValue(objectNode.get("roomOptionDto"),RoomOptionDto.class);
        RoomAddOptionDto roomAddOptionDto = mapper.treeToValue(objectNode.get("roomAddOptionDto"),RoomAddOptionDto.class);
        User user = userRepository.findById(userId).orElseThrow(()-> new BaseException(ROOM_CREATE_FAILED));
        RoomRequestDto roomRequestDto = new RoomRequestDto(roomBasicDto, roomOptionDto, roomAddOptionDto, user);
        try {
            Room room = roomRepository.save(roomRequestDto.toEntity(roomRequestDto));
            if(!(multipartFileList == null )) {
                if (!(multipartFileList.size() == 1 && multipartFileList.get(0).isEmpty())) {
                    for (MultipartFile multipartFile : multipartFileList) {
                        S3UploadDto s3UploadDto = s3UploaderService.upload(multipartFile, "bangsilbangsil", "roomImg");
                        RoomImgRequestDto roomImgRequestDto = new RoomImgRequestDto();
                        RoomImg roomImg = roomImgRepository.save(roomImgRequestDto.toEntity(room, s3UploadDto));
                    }
                    return new BaseResponse(BaseResponseStatus.SUCCESS);
                }else {
                    return new BaseResponse(BaseResponseStatus.SUCCESS_NULLPOINT);
                }
            }else {
                return new BaseResponse(BaseResponseStatus.SUCCESS_NULLPOINT);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new BaseResponse(ROOM_CREATE_FAILED);
        }
    }

    @Override
    public BaseResponse getRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).get();
        List<RoomImg> roomImgList = roomImgRepository.findByRoom_Id(roomId);
        RoomResponseDto roomResponseDto = new RoomResponseDto(room);
        return new BaseResponse(BaseResponseStatus.SUCCESS,roomResponseDto);
    }

    @Override
    public BaseResponse modifyRoom(RoomRequestDto roomRequestDto, Long roomId) {
        Room room = roomRepository.findById(roomId).get();
        room.modRoom(roomRequestDto);
        roomRepository.save(room);
        return new BaseResponse(BaseResponseStatus.SUCCESS);
    }

    @Override
    public BaseResponse getRoomList(Long userId) {
        List<Room> roomList = roomRepository.findByUser_Id(userId);
        List<RoomResponseDto> roomResponseDtoList = new ArrayList<>();
        for(Room room : roomList){
            RoomResponseDto roomResponseDto = new RoomResponseDto(room);
            roomResponseDtoList.add(roomResponseDto);
        }
        return new BaseResponse(BaseResponseStatus.SUCCESS,roomResponseDtoList);
    }
}
