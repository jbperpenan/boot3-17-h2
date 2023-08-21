package jbp.jpmc.usertype.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class AdminDisplay {

    @Autowired
    private AdminService adminService;

    private Scanner input_scanner = new Scanner(System.in);

    public void displayAdmin () {
        int choice = 99;
        do{
            System.out.println("\n\nSelect Admin Task: \n [1] Setup Show \n [2] View Shows \n [3] Exit");
            System.out.print("Enter number: ");

            choice = input_scanner.nextInt();
            switch (choice){
                case 1: adminService.adminSetupShow();
                    break;
                case 2: adminService.adminViewShows();
                    break;
                case 3: choice = 0;
                    break;
                default:
                    System.out.println("invalid choice: "+choice);
            }

        } while(choice != 0);
    }
}
