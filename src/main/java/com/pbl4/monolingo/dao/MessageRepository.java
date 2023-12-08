package com.pbl4.monolingo.dao;


import com.pbl4.monolingo.entity.Message;
import gov.nih.nlm.nls.lvg.Util.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAccountIdAndReceiverAccountId(Integer senderId,Integer receivedId);
    Page<Message> findBySenderAccountIdAndReceiverAccountId(Integer senderId, Integer receivedId, Pageable pageable);

}
