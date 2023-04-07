package com.bangsil.bangsil.room.application;

import com.bangsil.bangsil.common.BaseResponse;
import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.room.domain.Room;
import com.bangsil.bangsil.room.domain.RoomImg;
import com.bangsil.bangsil.room.dto.RoomImgRequestDto;
import com.bangsil.bangsil.room.dto.RoomRequestDto;
import com.bangsil.bangsil.room.dto.RoomResponseDto;
import com.bangsil.bangsil.room.infrastructure.RoomImgRepository;
import com.bangsil.bangsil.room.infrastructure.RoomRepository;
import com.bangsil.bangsil.utils.s3.S3UploaderService;
import com.bangsil.bangsil.utils.s3.dto.S3UploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

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
    @Transactional
    public BaseResponse addRoom(RoomRequestDto roomRequestDto, List<MultipartFile> multipartFileList) {
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
            return new BaseResponse(BaseResponseStatus.ROOM_CREATE_FAILED);
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
