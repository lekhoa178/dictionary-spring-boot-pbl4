package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.UserPacketInfo;
import com.pbl4.monolingo.entity.embeddable.UserPacketId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPacketInfoRepository extends JpaRepository<UserPacketInfo, UserPacketId> {
}
