package jbp.jpmc.entity.seat;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

import static jbp.jpmc.entity.seat.SeatStatus.OPEN;

@NoArgsConstructor
@Data
@Getter
@Setter
@Entity
public class Seat {
    @Id
    @GeneratedValue
    private UUID uuid;
    private Long showNumber;
    private String ticketNumber;
    private String phoneNumber;
    private String seatNumber;
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.seatStatus = OPEN;
    }

    @Override
    public String toString() {
        if(this.ticketNumber == null)
            this.ticketNumber =  "<empty>";

        if(this.phoneNumber == null)
            this.phoneNumber =  "<empty>";

        return "\tSeat {" +
                " Ticket Number= " + ticketNumber +
                ", Phone Number= " + phoneNumber +
                ", Seat Number= " + seatNumber +
                ", Status= " + seatStatus +
                " }";
    }
}
