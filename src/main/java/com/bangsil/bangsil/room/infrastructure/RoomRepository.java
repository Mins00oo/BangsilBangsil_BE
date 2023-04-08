package com.bangsil.bangsil.room.infrastructure;

import com.bangsil.bangsil.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findById(Long roomId);

    List<Room> findByUser_Id(Long userId);
}
