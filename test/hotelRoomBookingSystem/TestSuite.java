package hotelRoomBookingSystem;
import org.junit.runners.Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {
		BookingTest.class,
		HotelRoomBookingIntegrationTest.class,
		WaitingListTest.class,
})
public class TestSuite {

}
