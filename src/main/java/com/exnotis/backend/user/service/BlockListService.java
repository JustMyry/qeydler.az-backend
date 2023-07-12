package com.exnotis.backend.user.service;

import com.exnotis.backend.user.model.BlockList;

import java.util.List;

public interface BlockListService {

    List<BlockList> findBlockListsByBlockedBY(String blockedBY);

    void blockUser(String blocked, String blockedBY);

    void unBlockUser(String blocked, String blockedBY);

    boolean isUserBlocked(String blocked, String blockedBY);

}
