package jbp.jpmc.entity.show;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowValidator {

    @Autowired
    private ShowService showService;

    public Show isValidNewShow(String showDetails){

        String[] updateInput = showDetails.trim().split( " ");
        if(updateInput.length == 4){
            try {
                Long showNumber = Long.valueOf(updateInput[0]);
                Integer rows = Integer.valueOf(updateInput[1]);
                Integer seats = Integer.valueOf(updateInput[2]);
                Integer min = Integer.valueOf(updateInput[3]);

                if(isValidShowNumber(showNumber) && isValidateRows(rows) && isValidateSeats(seats)){
                    return new Show(showNumber, rows,seats,min);
                }
                return null;

            }catch (Exception e ){
                System.out.println("Error creating new show: "+showDetails +"\nERROR: "+e.getMessage());
                return null;
            }
        } else {
            System.out.println("Invalid/incomplete update details: "+showDetails);
            return null;
        }
    }

    private boolean isValidShowNumber(Long showNumber) {
        if(showService.findShowByShowNumber(showNumber) != null){
            System.out.println("Invalid show number, already exists: "+showNumber);
            return false;
        }

        return true;
    }

    private boolean isValidateRows(Integer row) {
        if(row >= 1 && row <=26){
            return true;
        }
        System.out.println("Invalid number of rows value (only 1 to 26): "+row);
        return false;
    }

    private boolean isValidateSeats(Integer seat) {
        if(seat >= 1 && seat <=10){
            return true;
        }
        System.out.println("Invalid number of seats value (only 1 to 10): "+seat);
        return false;
    }
}
