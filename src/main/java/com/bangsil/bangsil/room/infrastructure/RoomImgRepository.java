package com.bangsil.bangsil.room.infrastructure;

import com.bangsil.bangsil.room.domain.Room;
import com.bangsil.bangsil.room.domain.RoomImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomImgRepository extends JpaRepository<RoomImg, Long> {
    List<RoomImg> findByRoom_Id(Long roomId);
}
