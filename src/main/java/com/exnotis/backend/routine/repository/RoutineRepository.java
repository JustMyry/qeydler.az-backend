package com.exnotis.backend.routine.repository;


import com.exnotis.backend.routine.model.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {


    Routine findRoutineById(Long id);

    @Query(value = "SELECT * FROM routine u WHERE u.owner_id=:userId AND u.is_active = true AND u.routine_day=:date AND u.is_active =true limit :limit offset :offset ", nativeQuery = true)
    public List<Routine> findRoutinesByDayAndOwnerId(@Param("userId") String userId, @Param("date") LocalDate date, @Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM routine u WHERE u.owner_id=:userId AND u.is_active = true AND u.routine_day=:date AND u.is_active =true AND u.privacy =:privacy", nativeQuery = true)
    public List<Routine> findRoutinesByDayAndOwnerIdAndPrivacy(@Param("userId") String userId, @Param("date") LocalDate date, @Param("privacy") String privacy);


}
