package com.exnotis.backend.like.repository;


import com.exnotis.backend.like.model.QuoteLike;
import com.exnotis.backend.quote.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteLikeRepository extends JpaRepository<QuoteLike, Long> {

    QuoteLike findQuoteLikeById(Long id);


    @Query(value = "SELECT * FROM quote_like u WHERE u.liked_quote_id = :likeQuoteId limit :limit offset :offset ", nativeQuery = true)
    public List<QuoteLike> findAllByLikedQuoteIdWithLimit(@Param("likeQuoteId") Long likeQuoteId, @Param("limit") int limit, @Param("offset") int offset);


    @Query(value = "SELECT * FROM quote_like u WHERE u.liked_quote_id = :likeQuoteId", nativeQuery = true)
    public List<QuoteLike> findAllByLikedQuoteId(@Param("likeQuoteId") Long likeQuoteId);

}
