package jbp.jpmc.entity.seat;

import jbp.jpmc.entity.show.ShowValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static jbp.jpmc.entity.seat.SeatStatus.BOOKED;
import static jbp.jpmc.entity.seat.SeatStatus.OPEN;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowValidator showValidator;

    @Autowired
    private SeatValidator seatValidator;

    public void cancelSeatBookings(String cancellationDetails){
        String[] cancellationInput = cancellationDetails.trim().split(StringUtils.SPACE);
        if(cancellationInput.length == 2){
            try {
                String ticketNumber = cancellationInput[0];
                String phoneNumber = cancellationInput[1];

                if(seatValidator.isValidCancellation(ticketNumber, phoneNumber)){
                    List<Seat> seatsToUpdate = seatRepository.findByTicketNumberAndPhoneNumber(ticketNumber, phoneNumber);
                    seatsToUpdate.stream().forEach(s -> updateSeatsForCancellation(s));

                    seatRepository.saveAll(seatsToUpdate);
                    System.out.println("Booking cancellation COMPLETED");
                }

            }catch (Exception e ){
                System.out.println("Error booking cancellation : "+cancellationDetails +"\nERROR: "+e.getMessage());
            }
        } else {
            System.out.println("Invalid/incomplete cancellation details: "+cancellationDetails);
        }
    }

    private void updateSeatsForCancellation(Seat seat){
        seat.setBookedDateTime(null);
        seat.setTicketNumber(null);
        seat.setPhoneNumber(null);
        seat.setStatus(OPEN);
    }

    public List<Seat> getAvailableSeatsByShow(Long showNumber){
        return seatRepository.findByShowNumberAndStatus(showNumber, OPEN);
    }

    public List<Seat> getAllAvailableSeats(){
        return seatRepository.findByStatus(OPEN);
    }

    public String bookSeat(String bookDetails) {
        String[] updateInput = bookDetails.trim().split( StringUtils.SPACE);
        if(updateInput.length == 3){
            try {
                Long showNumber = Long.valueOf(updateInput[0]);
                String phoneNumber = updateInput[1];
                String[] seats = updateInput[2].toUpperCase().split(",");

                if(showValidator.isExistingShow(showNumber)
                        && seatValidator.isValidSeatNumber(showNumber, Arrays.asList(seats))
                        && seatValidator.isValidPhoneNumberForShow(showNumber, phoneNumber)){
                    long ctr = seatRepository.countByTicketNumberIsNotNull()+1;
                    List<Seat> seatsToUpdate = seatRepository.findAllByShowNumberAndSeatNumberIn(showNumber, Arrays.asList(seats));
                    seatsToUpdate.stream().forEach(s -> updateSeatForBooking(s, phoneNumber, ctr));

                    seatRepository.saveAll(seatsToUpdate);
                    return String.valueOf(ctr);
                }
                return null;

            }catch (Exception e ){
                System.out.println("Error booking seat: "+bookDetails +"\nERROR: "+e.getMessage());
                return null;
            }
        } else {
            System.out.println("Invalid/incomplete update details: "+bookDetails);
            return null;
        }
    }

    private void updateSeatForBooking(Seat seat, String phoneNumber, long ctr){
        seat.setPhoneNumber(phoneNumber);
        seat.setStatus(BOOKED);
        seat.setTicketNumber(String.valueOf(ctr));
        seat.setBookedDateTime(new Date());
    }
}
