package jbp.jpmc.entity.show;

import jbp.jpmc.entity.seat.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static jbp.jpmc.entity.seat.SeatStatus.OPEN;


@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    public List<Show> getAllShows(){
        List<Show> result = new ArrayList<>();
        showRepository.findAll().iterator().forEachRemaining(result::add);

        return result;
    }

    public void createNewShow(Show newShow) {
        initSeatNumbers(newShow);
        showRepository.save(newShow);

        System.out.println("New Show successfully created.");
    }

    private void initSeatNumbers(Show newShow){
        List<Seat> seats = new ArrayList<>();
        char tmp = 'A';
        for(int i=1; i<=newShow.getRows(); i++){
            for(int j=1; j<=newShow.getSeats(); j++){
                seats.add(new Seat(String.valueOf(tmp)+j));
            }
            tmp++;
        }
        newShow.getSeatNumbers().addAll(seats);
    }

    public Show findShowByShowNumber(Long showNumber) {
        return showRepository.findByShowNumber(showNumber);
    }
}
