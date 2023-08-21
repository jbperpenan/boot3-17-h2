package jbp.jpmc.entity.seat;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeatRepository extends CrudRepository<Seat, Long> {
    List<Seat> findByShowNumberAndStatus(Long showNumber, SeatStatus seatStatus);
    List<Seat> findByStatus(SeatStatus seatStatus);
    List<Seat> findAllByShowNumberAndSeatNumberIn(Long showNumber, List<String> seatNumbers);
    long countByTicketNumberIsNotNull();
    long countByShowNumberAndPhoneNumber(Long showNumber, String phoneNumber);
    List<Seat> findByTicketNumberAndPhoneNumber(String ticketNumber, String phoneNumber);
}

