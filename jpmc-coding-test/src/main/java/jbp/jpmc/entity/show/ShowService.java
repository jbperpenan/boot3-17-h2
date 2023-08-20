package jbp.jpmc.entity.show;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    public List<Show> getAllShows(){
        List<Show> result = new ArrayList<>();
        showRepository.findAll().iterator().forEachRemaining(result::add);

        return result;
    }

    public void createNewShow(Show newShow) {

        showRepository.save(newShow);

        System.out.println("New Show successfully created.");
    }

    public Show findShowByShowNumber(Long showNumber) {
        return showRepository.findByShowNumber(showNumber);
    }
}
