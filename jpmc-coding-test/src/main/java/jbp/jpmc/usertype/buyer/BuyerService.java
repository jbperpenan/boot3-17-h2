package jbp.jpmc.usertype.buyer;

import jbp.jpmc.entity.seat.Seat;
import jbp.jpmc.entity.seat.SeatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class BuyerService {

    @Autowired
    private SeatService seatService;

    public void buyerCancelBook() {
        Scanner input_scanner = new Scanner(System.in);
        System.out.println("========= Cancel Booked Seats ============");
        System.out.println("Sample Input (separated by white spaces) -> \'1 09321437651\' means cancel booking for ticket number 1 with 09321437651 phone number");
        System.out.print("Cancellation details: ");
        String cancellationDetails = input_scanner.nextLine();

        if (!StringUtils.isEmpty(cancellationDetails)){
            seatService.cancelSeatBookings(cancellationDetails);
        }
    }

    public void buyerViewShows() {
        Scanner input_scanner = new Scanner(System.in);
        System.out.println("========= Available Seats ============");
        System.out.print("Enter show number (leave blank for all shows): ");
        String showNumber = input_scanner.nextLine();

        List<Seat> seats = new ArrayList<>();
        if (!StringUtils.isEmpty(showNumber)){
            seats.addAll(seatService.getAvailableSeatsByShow(Long.valueOf(showNumber)));
        } else{
            seats.addAll(seatService.getAllAvailableSeats());
        }

        if(seats.size() == 0){
            System.out.println("No available seats.\n");
        } else{
            for(Seat s: seats) {
                if(s != null){
                    System.out.println(s.toString());
                    System.out.println("<------------------------------------------->\n");
                }
            }
        }
    }

    public void buyerBookSeats() {
        Scanner input_scanner = new Scanner(System.in);
        System.out.println("========= Book Seats ============");
        System.out.println("Sample Input -> \'1 09321437651 A1,A2,A3\' means show number 1, 09321437651 as phone number, A1,A2,A3(comma separated) as seats numbers");
        System.out.print("Booking details: ");
        String book = input_scanner.nextLine();

        String ticketNumber = seatService.bookSeat(book);
        if(StringUtils.isEmpty(ticketNumber)){
            System.out.println("Seat booking FAILED: "+book);
        }else{
            System.out.println("Seat booking COMPLETED, with ticket number "+ticketNumber);
        }
        System.out.println("<------------------------------------------->\n\n");
    }
}
