package com.exnotis.backend.quote.repository;


import com.exnotis.backend.quote.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {


    Quote findQuoteByAuthorUserId(String authorUserId);

    Quote findQuoteById(Long id);

    @Query(value = "SELECT * FROM quote u WHERE u.author_user_id=:userId AND u.is_active = true limit :limit offset :offset ", nativeQuery = true)
    public List<Quote> findAllByAuthorUserId(@Param("userId") String userId, @Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM quote limit :limit offset :offset", nativeQuery = true)
    public List<Quote> findAll(@Param("limit") int limit, @Param("offset") int offset);

    List<Quote> findAllByAuthorUserId(String authorUserId);

}
//@Query(value = "SELECT * FROM tutorials t WHERE t.published=true", nativeQuery = true)

//@Query(value = "SELECT * FROM product order by id desc limit :limit", nativeQuery = true)
//public List<Product> findTopN(@Param("limit") int limit);