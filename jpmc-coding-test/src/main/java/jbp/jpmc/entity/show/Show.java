package jbp.jpmc.entity.show;


import jakarta.persistence.*;
import jbp.jpmc.entity.seat.Seat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
@Getter
@Setter
@Entity
public class Show {
    @Id
    private Long showNumber;
    private Integer rows;
    private Integer seats;
    private Integer cancellationWindow;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "showNumber")
    private List<Seat> seatNumbers;

    public Show(Long showNumber, Integer rows, Integer seats, Integer cancellationWindow) {
        this.showNumber = showNumber;
        this.rows = rows;
        this.seats = seats;
        this.cancellationWindow = cancellationWindow;
        this.seatNumbers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return
                "Show Number= " + showNumber +
                ", Rows= " + rows +
                ", Seats= " + seats +
                ", Cancellation Window (minutes)= " + cancellationWindow;
    }
}
