package MobileBankingApplicationCRUD;

import java.util.Random;

public class BookingIdGenerator {
    public static String generateBookingId() {
        Random random = new Random();
        long random12DigitNumber = 100000000000L + (long)(random.nextDouble() * 899999999999L);
        return String.valueOf(random12DigitNumber);
    }
}
