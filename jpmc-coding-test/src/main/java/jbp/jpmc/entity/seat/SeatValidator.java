package jbp.jpmc.entity.seat;

import jbp.jpmc.entity.show.Show;
import jbp.jpmc.entity.show.ShowRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SeatValidator {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowRepository showRepository;

    public boolean isValidSeatNumber(Long showNumber, List<String> seatNumbers) {
        List<Seat> seatsToUpdate = seatRepository.findAllByShowNumberAndSeatNumberIn(showNumber, seatNumbers);
        if(seatsToUpdate.size() != seatNumbers.size()){
            StringBuilder seats = new StringBuilder();
            seatNumbers.stream().forEach(s -> seats.append(s+" "));
            System.out.println("Invalid seat number found, please check: "+seats.toString());
            return false;
        }

        return true;
    }

    public boolean isValidPhoneNumberForShow(Long showNumber, String phoneNumber){
        long ctr = seatRepository.countByShowNumberAndPhoneNumber(showNumber, phoneNumber);
        if(ctr != 0){
            System.out.println("Phone number: "+phoneNumber+" already exists for show number: "+showNumber);
            return false;
        }
        return true;
    }

    public boolean isValidCancellation(String ticketNumber, String phoneNumber){
        List<Seat> seats = seatRepository.findByTicketNumberAndPhoneNumber(ticketNumber, phoneNumber);
        if(seats.size() != 0){
            Seat seat = seats.get(0);
            Show show = showRepository.findByShowNumber(seat.getShowNumber());

            if(show != null && (show.getCancellationWindow() != null || show.getCancellationWindow() != 0)){
                long window = show.getCancellationWindow().longValue();
                Date bookedDateTime = seat.getBookedDateTime();
                Date now = new Date();

                long diffInMillies = Math.abs(now.getTime() - bookedDateTime.getTime());
                long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if(diff > window){
                    System.out.println("Invalid cancellation, beyond window time in minutes: "+window+" with time minutes difference: "+diff);
                }else{
                    return true;
                }

            }else{
                return true;
            }
        }else{
            System.out.println("No booking found with ticket Number: "+ticketNumber+" and phone number: "+phoneNumber);
            return false;
        }
        return false;
    }
}
