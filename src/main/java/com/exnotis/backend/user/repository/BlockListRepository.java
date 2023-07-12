package com.exnotis.backend.user.repository;

import com.exnotis.backend.user.model.BlockList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockListRepository extends JpaRepository<BlockList, Long> {

    List<BlockList> findBlockListsByBlockedBY(String blockedBY);

    BlockList findBlockListByBlockedBY(String blockedBY);

}
