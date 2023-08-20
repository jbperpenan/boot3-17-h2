package jbp.jpmc.usertype.admin;

import jbp.jpmc.entity.show.Show;
import jbp.jpmc.entity.show.ShowService;
import jbp.jpmc.entity.show.ShowValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class AdminService {

    @Autowired
    private ShowService showService;

    @Autowired
    private ShowValidator showValidator;

    private Scanner input_scanner = new Scanner(System.in);

    public void adminViewShows(){
        System.out.println("========= LIST OF SHOWS ============");
        List<Show> shows = showService.getAllShows();
        shows.stream().forEach(s -> System.out.println(s.toString()));
        System.out.println("\n\n");
    }

    public void adminSetupShow(){
        System.out.println("========= CREATE NEW SHOW ========= \n");
        System.out.println("Sample Input (separated by white spaces) -> \'1 5 5 2\' means showNumber 1 with 5 rows, 5 seats and 2min cancellation window");
        System.out.print("Enter show details: ");
        String show = input_scanner.nextLine();

        Show newShow = showValidator.isValidNewShow(show);
        if( newShow != null){
            showService.createNewShow(newShow);
        }else{
            System.out.print("New show creation FAILED: "+show);
        }
    }
}
