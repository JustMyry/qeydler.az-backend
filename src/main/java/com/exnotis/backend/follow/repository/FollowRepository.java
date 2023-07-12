package com.exnotis.backend.follow.repository;


import com.exnotis.backend.follow.model.FollowTable;
import com.exnotis.backend.quote.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/***
 * @Author: Exnotis Corporation.
 * @Target: Bu 'interface' 'FollowTable' ucun 'Jpa Repository' rolunu oynayir.
 * @Comment: Ileriki versiyalarda her metod ucun xususo 'SQL Query'si yazmaz lazim ola biler.
 * **/

@Repository
public interface FollowRepository extends JpaRepository<FollowTable, Long> {


    FollowTable findFollowTableById(String id);


    @Query(value = "SELECT * FROM follow_table u WHERE u.follower=:followerId AND u.following=:followingId", nativeQuery = true)
    FollowTable findFollowTableByFollowerAndFollowing(String followerId, String followingId);


    @Query(value = "SELECT * FROM follow_table u WHERE u.follower=:followerId AND u.is_active = true", nativeQuery = true)
    List<FollowTable> findFollowTablesByFollower(String followerId);


    @Query(value = "SELECT * FROM follow_table u WHERE u.following=:followingId AND u.is_active = true", nativeQuery = true)
    List<FollowTable> findFollowTablesByFollowing(String followingId);


    @Query(value = "SELECT * FROM follow_table u WHERE u.follower=:followerId AND u.is_active = true limit :limit offset :offset ", nativeQuery = true)
    List<FollowTable> findFollowTablesByFollowerWithLO(@Param("followerId") String userId, @Param("limit") int limit, @Param("offset") int offset);


    @Query(value = "SELECT * FROM follow_table u WHERE u.following=:following AND u.is_active = true limit :limit offset :offset ", nativeQuery = true)
    List<FollowTable> findFollowTablesByFollowingWithLO(@Param("following") String userId, @Param("limit") int limit, @Param("offset") int offset);

}
