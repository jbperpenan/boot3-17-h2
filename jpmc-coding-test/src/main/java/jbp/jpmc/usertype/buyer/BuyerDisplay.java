package jbp.jpmc.usertype.buyer;

import jbp.jpmc.usertype.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class BuyerDisplay {

    @Autowired
    private BuyerService buyerService;

    private Scanner input_scanner = new Scanner(System.in);

    public void displayBuyer () {
        int choice = 99;
        do{
            System.out.println("\n\nSelect Buyer Task: \n [1] Check Shows \n [2] Book \n [3] Cancel \n [4] Exit");
            System.out.print("Enter number: ");

            choice = input_scanner.nextInt();
            switch (choice){
                case 1: buyerService.buyerViewShows();
                    break;
                case 2: buyerService.buyerBookShow();
                    break;
                case 3: buyerService.buyerCancelBook();
                    break;
                case 4: choice = 0;
                    break;
                default:
                    System.out.println("invalid choice: "+choice);
            }

        } while(choice != 0);
    }
}
