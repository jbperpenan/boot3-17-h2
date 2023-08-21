package jbp.jpmc.entity.seat;

import jbp.jpmc.entity.show.Show;
import jbp.jpmc.entity.show.ShowRepository;
import jbp.jpmc.entity.show.ShowValidator;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SeatValidator.class)
public class SeatValidatorTest {

    @Autowired
    private SeatValidator seatValidator;

    @MockBean
    private SeatRepository seatRepository;

    @MockBean
    private ShowRepository showRepository;

    @Mock
    private Seat seat;

    @Mock
    private Show show;

    @Test
    public void testIsValidSeatNumberFalse() {
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        when(seatRepository.findAllByShowNumberAndSeatNumberIn(1L,Arrays.asList("A1"))).thenReturn(seats);

        boolean result = seatValidator.isValidSeatNumber(1L, Arrays.asList("A1,A2"));
        assertEquals(false, result);
    }

    @Test
    public void testIsValidSeatNumberTrue() {
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        when(seatRepository.findAllByShowNumberAndSeatNumberIn(1L,Arrays.asList("A1"))).thenReturn(seats);

        boolean result = seatValidator.isValidSeatNumber(1L, Arrays.asList("A1"));
        assertEquals(true, result);
    }

    @Test
    public void testIsValidPhoneNumberForShowFalse() {
        when(seatRepository.countByShowNumberAndPhoneNumber(1L,"0932")).thenReturn(1L);

        boolean result = seatValidator.isValidPhoneNumberForShow(1L, "0932");
        assertEquals(false, result);
    }

    @Test
    public void testIsValidPhoneNumberForShowTrue() {
        when(seatRepository.countByShowNumberAndPhoneNumber(1L,"0932")).thenReturn(0L);

        boolean result = seatValidator.isValidPhoneNumberForShow(1L, "0932");
        assertEquals(true, result);
    }

    @Test
    public void testIsValidCancellationFalse1() {
        List<Seat> seats = new ArrayList<>();
        when(seatRepository.findByTicketNumberAndPhoneNumber("1","0932")).thenReturn(seats);

        boolean result = seatValidator.isValidCancellation("1", "0932");
        assertEquals(false, result);
    }

    @Test
    public void testIsValidCancellationFalse2() {
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        when(seat.getShowNumber()).thenReturn(1L);
        when(seatRepository.findByTicketNumberAndPhoneNumber("1","0932")).thenReturn(seats);
        when(showRepository.findByShowNumber(1L)).thenReturn(null);

        boolean result = seatValidator.isValidCancellation("1", "0932");
        assertEquals(false, result);
    }

    @Test
    public void testIsValidCancellationFalse3() {
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        when(seat.getShowNumber()).thenReturn(1L);
        when(seat.getBookedDateTime()).thenReturn(DateUtils.addMinutes(new Date(),-5));
        when(seatRepository.findByTicketNumberAndPhoneNumber("1","0932")).thenReturn(seats);
        when(showRepository.findByShowNumber(1L)).thenReturn(show);
        when(show.getCancellationWindow()).thenReturn(1);

        boolean result = seatValidator.isValidCancellation("1", "0932");
        assertEquals(false, result);
    }

    @Test
    public void testIsValidCancellationTrue() {
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        when(seat.getShowNumber()).thenReturn(1L);
        when(seat.getBookedDateTime()).thenReturn(DateUtils.addMinutes(new Date(),-5));
        when(seatRepository.findByTicketNumberAndPhoneNumber("1","0932")).thenReturn(seats);
        when(showRepository.findByShowNumber(1L)).thenReturn(show);
        when(show.getCancellationWindow()).thenReturn(10);

        boolean result = seatValidator.isValidCancellation("1", "0932");
        assertEquals(true, result);
    }
}