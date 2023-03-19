package com.bangsil.bangsil.room.infrastructure;

import com.bangsil.bangsil.room.domain.RoomTemporaryStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTemporaryStorageRepository extends JpaRepository<RoomTemporaryStorage,Long> {
}
