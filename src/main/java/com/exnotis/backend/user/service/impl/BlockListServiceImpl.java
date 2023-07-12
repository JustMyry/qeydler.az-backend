package com.exnotis.backend.user.service.impl;

import com.exnotis.backend.exception.CustomException;
import com.exnotis.backend.user.model.BlockList;
import com.exnotis.backend.user.repository.BlockListRepository;
import com.exnotis.backend.user.service.BlockListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockListServiceImpl implements BlockListService {

    private final BlockListRepository repository;
    public BlockListServiceImpl(BlockListRepository repository){
        this.repository=repository;
    }

    @Override
    public List<BlockList> findBlockListsByBlockedBY(String blockedBY) {
        return repository.findBlockListsByBlockedBY(blockedBY);
    }

    @Override
    public void blockUser(String blocked, String blockedBY) {
        BlockList blockList = BlockList.builder()
                .blocked(blocked)
                .blockedBY(blockedBY)
                .build();
        repository.save(blockList);
    }

    @Override
    public void unBlockUser(String blocked, String blockedBY) {
        BlockList blockList = repository.findBlockListByBlockedBY(blockedBY);
        if(blockList==null)
            throw new CustomException("UnBlock request of " + blockedBY + " throws ex: Blocked user ( " + blocked + " ) is not exist in exnotis DB", 404);
        repository.delete(blockList);
    }


    @Override
    public boolean isUserBlocked(String blocked, String blockedBY) {
        return findBlockListsByBlockedBY(blockedBY).stream().anyMatch((s) -> s.getBlocked().equals(blocked));
    }
}
