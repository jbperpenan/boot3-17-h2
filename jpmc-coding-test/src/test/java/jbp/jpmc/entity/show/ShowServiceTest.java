package jbp.jpmc.entity.show;

import jbp.jpmc.entity.seat.Seat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static jbp.jpmc.entity.seat.SeatStatus.OPEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ShowService.class)
public class ShowServiceTest {

    @Autowired
    private ShowService showService;

    @MockBean
    private ShowRepository showRepository;

    @Mock
    private Show show;

    @Mock
    private Seat seat;

    @Test
    public void testGetAllShows() {
        List<Show> expectedList = new ArrayList<>();
        expectedList.add(show);

        when(showRepository.findAll()).thenReturn(expectedList);

        List<Show> output = showService.getAllShows();

        assertNotNull(output);
        assertEquals(expectedList.size(), output.size());
        assertNotNull(output.get(0));
    }

    @Test
    public void testCreateNewShow() {
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        when(show.getRows()).thenReturn(2);
        when(show.getSeats()).thenReturn(2);
        when(show.getSeatNumbers()).thenReturn(seats);

        when(seat.getSeatNumber()).thenReturn("1A");
        when(seat.getStatus()).thenReturn(OPEN);

        showService.createNewShow(show);
        assertEquals(seats.size(), show.getSeatNumbers().size());

        Seat seat = show.getSeatNumbers().get(0);
        assertEquals(this.seat.getSeatNumber(), seat.getSeatNumber());
        assertEquals(this.seat.getStatus(), seat.getStatus());

        verify(showRepository).save(show);
    }


    @Test
    public void testFindShowByShowNumber() {
        when(showRepository.findByShowNumber(1L)).thenReturn(show);

        Show show = showService.findShowByShowNumber(1L);
        assertNotNull(show);
        verify(showRepository).findByShowNumber(1L);
    }
}