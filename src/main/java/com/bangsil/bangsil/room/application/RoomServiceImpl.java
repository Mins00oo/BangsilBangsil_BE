package com.bangsil.bangsil.room.application;

import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.room.domain.Room;
import com.bangsil.bangsil.room.domain.RoomImg;
import com.bangsil.bangsil.room.dto.*;
import com.bangsil.bangsil.room.infrastructure.RoomImgRepository;
import com.bangsil.bangsil.room.infrastructure.RoomRepository;
import com.bangsil.bangsil.utils.s3.S3UploaderService;
import com.bangsil.bangsil.utils.s3.dto.S3UploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;

    private final S3UploaderService s3UploaderService;
    private final RoomImgRepository roomImgRepository;

    @Override
    public void addRoom(RoomRequestDto roomRequestDto, List<MultipartFile> multipartFileList) throws BaseException {
        Room room = roomRepository.save(roomRequestDto.toEntity(roomRequestDto));
        try {
            if (!(multipartFileList.size() == 1 && multipartFileList.get(0).isEmpty())) {
                for (MultipartFile multipartFile : multipartFileList) {
                    S3UploadDto s3UploadDto = s3UploaderService.upload(multipartFile, "bangsilbangsil", "roomImg");
                    RoomImgRequestDto roomImgRequestDto = new RoomImgRequestDto();
                    RoomImg roomImg = roomImgRepository.save(roomImgRequestDto.toEntity(room, s3UploadDto));
                }
            }
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.SUCCESS);
        }
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
