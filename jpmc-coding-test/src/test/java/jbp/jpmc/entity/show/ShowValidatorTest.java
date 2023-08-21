package jbp.jpmc.entity.show;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ShowValidator.class)
public class ShowValidatorTest {

    @Autowired
    private ShowValidator showValidator;

    @MockBean
    private ShowService showService;

    @Mock
    private Show show;

    @Test
    public void testIsValidNewShow() {
        when(showService.findShowByShowNumber(1L)).thenReturn(null);

        Show show = showValidator.isValidNewShow("1 2 2 1");

        assertNotNull(show);
    }

    @Test
    public void testIsValidNewShowInvalidSeatValue() {
        when(showService.findShowByShowNumber(1L)).thenReturn(null);

        Show show = showValidator.isValidNewShow("1 11 11 1");
        assertNull(show);

        show = showValidator.isValidNewShow("1 26 0 1");
        assertNull(show);
    }

    @Test
    public void testIsValidNewShowInvalidRowValue() {
        when(showService.findShowByShowNumber(1L)).thenReturn(null);

        Show show = showValidator.isValidNewShow("1 27 2 1");
        assertNull(show);

        show = showValidator.isValidNewShow("1 0 2 1");
        assertNull(show);
    }

    @Test
    public void testIsInvalidNewShow() {
        Show show = showValidator.isValidNewShow("1 2 2 1 X");
        assertNull(show);
    }

    @Test
    public void testIsValidShowNumberTrue() {
        when(showService.findShowByShowNumber(1L)).thenReturn(null);

        boolean result = showValidator.isValidShowNumber(1L);
        assertEquals(true, result);
    }

    @Test
    public void testIsValidShowNumberFalse() {
        when(showService.findShowByShowNumber(1L)).thenReturn(show);

        boolean result = showValidator.isValidShowNumber(1L);
        assertEquals(false, result);
    }

    @Test
    public void testIsExistingShowFalse() {
        when(showService.findShowByShowNumber(1L)).thenReturn(null);

        boolean result = showValidator.isExistingShow(1L);
        assertEquals(false, result);
    }

    @Test
    public void testIsExistingShowTrue() {
        when(showService.findShowByShowNumber(1L)).thenReturn(show);

        boolean result = showValidator.isExistingShow(1L);
        assertEquals(true, result);
    }
}