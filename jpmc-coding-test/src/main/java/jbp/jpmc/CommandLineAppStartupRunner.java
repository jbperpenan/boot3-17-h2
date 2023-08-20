package jbp.jpmc;

import jbp.jpmc.usertype.admin.AdminDisplay;
import jbp.jpmc.entity.show.ShowService;
import jbp.jpmc.usertype.buyer.BuyerDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private ShowService showService;

    @Autowired
    private AdminDisplay adminDisplay ;

    @Autowired
    private BuyerDisplay buyerDisplay;

    private Scanner input_scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        int choice = 99;
        do{
            displayUserTypeSelect();
            System.out.print("Enter number: ");

            choice = input_scanner.nextInt();
            switch (choice){
                case 1: adminDisplay.displayAdmin();
                        break;
                case 2: buyerDisplay.displayBuyer();
                        break;
                case 3:System.out.println("App terminated...\n\n");
                       System.exit(0);
                default:
                    System.out.println("invalid choice");
            }

        } while(choice != 0);
    }

    private void displayUserTypeSelect () {
        System.out.println("\n\nSelect User Type: \n [1] Admin \n [2] Buyer \n [3] Exit");
    }
}