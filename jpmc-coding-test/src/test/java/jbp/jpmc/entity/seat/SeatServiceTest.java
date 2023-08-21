package jbp.jpmc.entity.seat;

import jbp.jpmc.entity.show.ShowValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jbp.jpmc.entity.seat.SeatStatus.BOOKED;
import static jbp.jpmc.entity.seat.SeatStatus.OPEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SeatService.class)
public class SeatServiceTest {

    @Autowired
    private SeatService seatService;

    @MockBean
    private SeatRepository seatRepository;

    @MockBean
    private ShowValidator showValidator;

    @MockBean
    private SeatValidator seatValidator;

    @Mock
    private Seat seat;

    @Test
    public void testValidBookSeat() {
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        when(seatRepository.countByTicketNumberIsNotNull()).thenReturn(0L);
        when(showValidator.isExistingShow(1L)).thenReturn(true);
        when(seatValidator.isValidSeatNumber(1L,Arrays.asList("A1"))).thenReturn(true);
        when(seatValidator.isValidPhoneNumberForShow(1L,"09321437651")).thenReturn(true);
        when(seatRepository.findAllByShowNumberAndSeatNumberIn(1L,Arrays.asList("A1"))).thenReturn(seats);

        String output = seatService.bookSeat("1 09321437651 A1");

        assertEquals("1", output);

        verify(showValidator, atMostOnce()).isExistingShow(1L);
        verify(seatValidator, atMostOnce()).isValidSeatNumber(1L, Arrays.asList("A1"));
        verify(seatValidator, atMostOnce()).isValidPhoneNumberForShow(1L,"09321437651");
        verify(seatRepository, atMostOnce()).saveAll(seats);
        verify(seatRepository, atMostOnce()).countByTicketNumberIsNotNull();

        verify(seat, atMostOnce()).setPhoneNumber("09321437651");
        verify(seat, atMostOnce()).setStatus(BOOKED);
        verify(seat, atMostOnce()).setTicketNumber("1");
    }

    @Test
    public void testInvalidBookSeat() {
        seatService.bookSeat("1 09321437651 A1 X");

        verify(showValidator, never()).isExistingShow(1L);
        verify(seatValidator, never()).isValidSeatNumber(1L, Arrays.asList("A1"));
        verify(seatValidator, never()).isValidPhoneNumberForShow(1L,"09321437651");
    }

    @Test
    public void testValidCancelSeatBookings() {
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        when(seatRepository.findByTicketNumberAndPhoneNumber("1","0932")).thenReturn(seats);
        when(seatValidator.isValidCancellation("1","0932")).thenReturn(true);

        seatService.cancelSeatBookings("1 0932");

        verify(seatValidator).isValidCancellation("1","0932");
        verify(seat, atMostOnce()).setBookedDateTime(null);
        verify(seat, atMostOnce()).setTicketNumber(null);
        verify(seat, atMostOnce()).setPhoneNumber(null);
        verify(seat, atMostOnce()).setStatus(OPEN);
        verify(seatRepository, atMostOnce()).saveAll(seats);
    }

    @Test
    public void testInvalidCancelSeatBookings() {
        when(seatValidator.isValidCancellation("1","0932")).thenReturn(false);

        seatService.cancelSeatBookings("1 0932");

        verify(seatValidator).isValidCancellation("1","0932");
    }

    @Test
    public void testInvalidParamCancelSeatBookings() {

        seatService.cancelSeatBookings("1 0932 123");

        verify(seatValidator, never()).isValidCancellation("1","0932");
    }

    @Test
    public void testGetAvailableSeatsByShow() {
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        when(seatRepository.findByShowNumberAndStatus(1L, OPEN)).thenReturn(seats);

        List<Seat> seatsOutput = seatService.getAvailableSeatsByShow(1L);

        assertNotNull(seatsOutput);
        assertEquals(seats.size(), seatsOutput.size());
        assertNotNull(seatsOutput.get(0));
        verify(seatRepository).findByShowNumberAndStatus(1L,OPEN);
    }

    @Test
    public void testGetAllAvailableSeats() {
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        when(seatRepository.findByStatus(OPEN)).thenReturn(seats);

        List<Seat> seatsOutput = seatService.getAllAvailableSeats();

        assertNotNull(seatsOutput);
        assertEquals(seats.size(), seatsOutput.size());
        assertNotNull(seatsOutput.get(0));
        verify(seatRepository).findByStatus(OPEN);
    }
}