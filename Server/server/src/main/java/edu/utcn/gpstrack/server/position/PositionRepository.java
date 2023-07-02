package edu.utcn.gpstrack.server.position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Radu Miron
 * @version 1
 */
public interface PositionRepository extends JpaRepository<Position, Long> {
    @Transactional
    @Modifying
    @Query("DELETE from  Position p where p.terminalId = :terminalId")
    void delete(@Param("terminalId") String terminalId);

    @Query("SELECT p FROM Position p WHERE p.terminalId = :terminalId" +
            " AND p.createTime BETWEEN :startdate AND :enddate")
    List<Position> findByTerminalIdAndStartDateAndEndDate(@Param("terminalId") String terminalid, @Param("startdate") Date startDate,
                                                          @Param("enddate") Date endDate);

}
