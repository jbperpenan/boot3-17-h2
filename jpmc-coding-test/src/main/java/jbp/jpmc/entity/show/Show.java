package jbp.jpmc.entity.show;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Data
@Getter
@Setter
@Entity
public class Show {
    @Id
    @GeneratedValue
    private UUID uuid;
    private Long showNumber;
    private Integer rows;
    private Integer seats;
    private Integer cancellationWindow;

    public Show(Long showNumber, Integer rows, Integer seats, Integer cancellationWindow) {
        this.showNumber = showNumber;
        this.rows = rows;
        this.seats = seats;
        this.cancellationWindow = cancellationWindow;
    }

    @Override
    public String toString() {
        return
                "Show Number=" + showNumber +
                ", Rows=" + rows +
                ", Seats=" + seats +
                ", Cancellation Window (min)=" + cancellationWindow;
    }
}
