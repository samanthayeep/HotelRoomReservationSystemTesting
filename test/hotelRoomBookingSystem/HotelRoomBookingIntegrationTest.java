package hotelRoomBookingSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.inOrder;

@RunWith(JUnitParamsRunner.class)
public class HotelRoomBookingIntegrationTest {
	static Booking booking;
	static WaitingList waitingList;
	static Printer printerMock;
	static Room roomMock;
	
	@Before
	public static void setupForAllTest() {
		waitingList = new WaitingList();
		roomMock = mock(Room.class);
		printerMock = mock(Printer.class);
	}
	
	public static List<Object[]> loadTestDataFromFile() throws IOException {
        List<Object[]> testData = new ArrayList<>();
        
        String fileName = "setBookingValidData.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                // Extracting user information
                String name = parts[0];
                String membership = parts[1];
                boolean exclusiveReward = Boolean.parseBoolean(parts[2]);
                int roomRequested = Integer.parseInt(parts[3]);

                // Extracting room availability and allocation
                int[] roomAvailable = new int[]{Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6])};
                int[] roomAllocated = new int[]{Integer.parseInt(parts[7]), Integer.parseInt(parts[8]), Integer.parseInt(parts[9])};
                boolean addToWaitingList = Boolean.parseBoolean(parts[10]);
                testData.add(new Object[]{new User(name, membership, exclusiveReward, roomRequested), roomAvailable, roomAllocated, addToWaitingList});
                
        }
        }catch(IOException e) {
        	System.out.println(e);
        }

        return testData;
    }

	@Parameters(method ="loadTestDataFromFile")
	@Test
	public void testSetBookingValid(User user, int[] roomAvailable, int[] roomExpected, boolean addToWaitingList) {
		booking = new Booking(user);
		when(roomMock.checkRoom(anyString())).thenAnswer(invocation -> {
            String roomType = invocation.getArgument(0);
            if (roomType.equals("VIP")) {
                return roomAvailable[0]--;
            } else if (roomType.equals("Deluxe")) {
                return roomAvailable[1]--;
            } else if (roomType.equals("Standard")) {
                return roomAvailable[2]--;
            }
            return 0; // Default value if room type is not recognized
        });
        booking.setBooking(waitingList, roomMock);
        verify(roomMock, times(roomExpected[0])).setVipRoom(-1);
        verify(roomMock, times(roomExpected[1])).setDeluxeRoom(-1);
        verify(roomMock, times(roomExpected[2])).setStandardRoom(-1);
        if (addToWaitingList) {
            assertTrue(waitingList.getWaiting(user.getMemberType()).contains(user));
        } else {
        	//to verify addWaiting(user) never invoked during test execution
        	assertFalse(waitingList.getWaiting(user.getMemberType()).contains(user));
        }
	}
	
	private static Object[] parametersForSetBookingInvalid() {
		int[] roomAvailable = {3,3,3}; 
		User user1 = new User("Alice", "VIP", false, 0);
		User user2 = new User("Alice", "VIP", true, 1);
		User user3 = new User("Alice", "VIP", true, 3);
		User user4 = new User("Alice", "VIP", false, 4);
		
		User user5 = new User("Alice", "Member", true, 0);
		User user6 = new User("Alice", "Member", false, 0);
		User user7 = new User("Alice", "Member", true, 3);
		User user8 = new User("Alice", "Member", false, 3);
		
		User user9 = new User("Alice", "Normal", false, 0);
		User user10 = new User("Alice", "Normal", true, 1);
		User user11= new User("Alice", "Normal", false, 2);
		
		return new Object[] {
				//VIP member restriction
				//VIP member request 0 room
				new Object[] {user1, roomAvailable},
				//VIP request with exclusive reward
				new Object[] {user2, roomAvailable},
				new Object[] {user3, roomAvailable},
				//VIP member request 4 rooms
				new Object[] {user4, roomAvailable},
				
				//Member restriction
				//Member with exclusive reward request 0 room 
				new Object[] {user5, roomAvailable},
				//Member without exclusive reward request 0 room 
				new Object[] {user6, roomAvailable},
				//Member with exclusive reward request 3 room 
				new Object[] {user7, roomAvailable},
				//Member without exclusive reward request 3 room 
				new Object[] {user8, roomAvailable},			
				
				//Non-member restriction
				//Non-member request 0 room 
				new Object[] {user9, roomAvailable},
				//Non-member with exclusive reward request 1 room 
				new Object[] {user10, roomAvailable},
				//Non-member request 2 room 
				new Object[] {user11, roomAvailable},
			
		};
	}
	@Parameters(method = "parametersForSetBookingInvalid")
	@Test(expected = IllegalArgumentException.class)
	public static void testSetBookingInvalid(User user, int[] roomAvailable) {
		when(roomMock.checkRoom(anyString())).thenAnswer(invocation -> {
            String roomType = invocation.getArgument(0);
            if (roomType.equals("VIP")) {
                return roomAvailable[0]--;
            } else if (roomType.equals("Deluxe")) {
                return roomAvailable[1]--;
            } else if (roomType.equals("Standard")) {
                return roomAvailable[2]--;
            }
            return 0; // Default value if room type is not recognized
        });
		booking = new Booking(user);
		booking.setBooking(waitingList, roomMock);
	}
	private static Object[] parametersForCancelBookingValid() {
		User user1 = new User("Alice", "VIP", false, 3);
		User user2 = new User("Alice", "VIP", false, 1);
		User user3 = new User("Eve", "Member", false, 2);
		User user4 = new User("Eve", "Member", false, 1);
		User user5 = new User("Bob", "Normal", false, 1);
		//user has booking but not in waiting list
		int[] roomAvailable1 = {3, 3, 3};
		int[] roomAllocated1 = {3, 0, 0};
		
		int[] roomAvailable2 = {3, 3, 3};
		int[] roomAllocated2 = {1, 0, 0};
		
		int[] roomAvailable3 = {3, 3, 3};
		int[] roomAllocated3 = {0, 2, 0};
		
		int[] roomAvailable4 = {3, 3, 3};
		int[] roomAllocated4 = {0, 1, 0};
		
		int[] roomAvailable5 = {3, 3, 3};
		int[] roomAllocated5 = {0, 0, 1};
		
		//user has booking and is in waiting list
		int[] roomAvailable6 = {1, 0, 0};
		int[] roomAllocated6 = {1, 0, 0};
		
		int[] roomAvailable7 = {0, 1, 0};
		int[] roomAllocated7 = {0, 1, 0};
		
		//user has no booking but is in waiting list
		int[] roomAvailable8 = {0, 0, 0};
		int[] roomAllocated8 = {0, 0, 0};
		
		
		
		return new Object[] {
				//user has booking but not in waiting list cancel booking
				new Object[] {user1, roomAvailable1, roomAllocated1},
				new Object[] {user2, roomAvailable2, roomAllocated2},
				new Object[] {user3, roomAvailable3, roomAllocated3},
				new Object[] {user4, roomAvailable4, roomAllocated4},
				new Object[] {user5, roomAvailable5, roomAllocated5},
				//user has booking and also in waiting list cancel booking
				new Object[] {user1, roomAvailable6, roomAllocated6},
				new Object[] {user3, roomAvailable7, roomAllocated7},
				//user has no booking and in waiitng list cancel booking
				new Object[] {user1, roomAvailable8, roomAllocated8},
				new Object[] {user2, roomAvailable8, roomAllocated8},
				new Object[] {user3, roomAvailable8, roomAllocated8},
				new Object[] {user4, roomAvailable8, roomAllocated8},
				new Object[] {user5, roomAvailable8, roomAllocated8},
		};
	}
	
	
	
	@Test
	@Parameters(method = "parametersForCancelBookingValid")
	public static void testCancelBookingValid(User userToCancel, 
			int[] roomAvailable, int[] roomAllocated) {
		booking = new Booking(userToCancel);
		when(roomMock.checkRoom(anyString())).thenAnswer(invocation -> {
            String roomType = invocation.getArgument(0);
            if (roomType.equals("VIP")) {
                return roomAvailable[0]--;
            } else if (roomType.equals("Deluxe")) {
                return roomAvailable[1]--;
            } else if (roomType.equals("Standard")) {
                return roomAvailable[2]--;
            }
            return 0; // Default value if room type is not recognized
        });
		
		booking.setBooking(waitingList, roomMock);
		booking.cancelBooking(waitingList, roomMock, userToCancel);
		
		//verify that the waiting list is empty
		assertFalse(waitingList.getWaiting(userToCancel.getMemberType()).contains(userToCancel));
		
	}
	
	private static Object[] paramtersForCancelBookingInvalid() {
		User user1 = new User("Alice", "VIP", false, 3);
		User user2 = new User("Eve", "Member", false, 2);
		
		return new Object[] {
				//user not found in booking and waiting list
				new Object[] {user1, user2},
		};
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method="paramtersForCancelBookingInvalid")
	public static void testCancelBookingInvalid(User user1, User user2) {
		booking = new Booking(user1);
		booking.setBooking(waitingList, roomMock);
		booking.cancelBooking(waitingList, roomMock, user2);
		
	}
	
	
	
	private static Object[] parametersForAddWaitingValid() {
		User user1 = new User("Alice", "VIP", false, 3);
		User user2 = new User("Bob", "Member", false, 1);
		User user3 = new User("Eve", "Member", true, 2);
		User user4 = new User("Felix", "Normal", false, 1);
	
		 return new Object[] {
		    	new Object[] {user1},
		    	new Object[] {user2},
		    	new Object[] {user3},
		    	new Object[] {user4},
		 };
	}
	
	@Test
	@Parameters(method="parametersForAddWaitingValid")
	public void testAddWaitingValid(User user) {
		waitingList.addWaiting(user);
		List<User> actualList = waitingList.getWaiting(user.getMemberType()); 
		assertTrue(actualList.size() > 0);	
		assertTrue(actualList.contains(user));
	}
	
	private static Object[] parametersForAddWaitingInvalid() {
		User user1 = new User("Alice", null, false, 3);
		User user2 = new User("Bob", "", false, 3);
		User user3 = new User("Eve", "InvalidType", false, 1);
		
		 return new Object[] {
		    	new Object[] {user1}, //null memberType user
		    	new Object[] {user2}, //empty memberType user
		    	new Object[] {user3}, //invalid memberType user
		 };
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method="parametersForAddWaitingInvalid")
	public void testAddWaitingInvalid(User user) {
		waitingList.addWaiting(user);	
	}
	 
	private static Object[] parametersForGetWaitingVIPValid() {
		User user1 = new User("Alice", "VIP", false, 3);
		User user2 = new User("Bob", "VIP", false, 1);
		
		 return new Object[] {
		    	new Object[] {user1, user2},
		 };
	}
	@Test
	@Parameters(method="parametersForGetWaitingVIPValid")
	public void testGetWaitingVIPValid(User user1, User user2) {
		waitingList.addWaiting(user1);
		waitingList.addWaiting(user2);
		List<User> usersAdded = new ArrayList<User>();
		usersAdded.add(user1);
		usersAdded.add(user2);
		List<User> waitingUsers = waitingList.getWaiting("VIP");
		assertEquals(usersAdded, waitingUsers);
	}
	
	private static Object[] parametersForGetWaitingMemberValid() {
		User user1 = new User("Bob", "Member", false, 1);
		User user2 = new User("Eve", "Member", true, 2);
		String memberType1 = "Member";
		
		 return new Object[] {
		    	new Object[] {user1, user2},
		 };
	}
	@Test
	@Parameters(method="parametersForGetWaitingMemberValid")
	public void testGetWaitingMemberValid(User user1, User user2) {
		waitingList.addWaiting(user1);
		waitingList.addWaiting(user2);
		List<User> usersAdded = new ArrayList<User>();
		usersAdded.add(user1);
		usersAdded.add(user2);
		List<User> waitingUsers = waitingList.getWaiting("Member");
		assertEquals(usersAdded, waitingUsers);
	}
	
	private static Object[] parametersForGetWaitingNormalValid() {
		User user1 = new User("Eve", "Normal", false, 2);
		User user2 = new User("Felix", "Normal", false, 1);
		
		 return new Object[] {
		    	new Object[] {user1, user2},
		 };
	}
	@Test
	@Parameters(method="parametersForGetWaitingNormalValid")
	public void testGetWaitingNormalValid(User user1, User user2) {
		waitingList.addWaiting(user1);
		waitingList.addWaiting(user2);
		List<User> usersAdded = new ArrayList<User>();
		usersAdded.add(user1);
		usersAdded.add(user2);
		List<User> waitingUsers = waitingList.getWaiting("Normal");
		assertEquals(usersAdded, waitingUsers);
	}
	
	private static Object[] parametersForGetWaitingInvalid() {
		User user1 = new User("Alice", null, false, 3);
		User user2 = new User("Bob", "", false, 3);
		User user3 = new User("Eve", "InvalidType", false, 1);

		
		 return new Object[] {
		    	new Object[] {user1}, //user with null memberType
		    	new Object[] {user2}, //user with empty memberType
		    	new Object[] {user3}, //user with invalid memberType
		 };
	}
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method="parametersForGetWaitingInvalid")
	public void testGetWaitingInvalid(User user) {
		waitingList.getWaiting(user.getMemberType()); 	
	}
	
	private static Object[] parametersForRemoveWaitingValid() {
	
		User user1 = new User("Alice", "VIP", false, 3);
		User user2 = new User("Bob", "Member", false, 1);
		User user3 = new User("Eve", "Member", true, 2);
		User user4 = new User("Felix", "Normal", false, 1);
		
		 return new Object[] {
		    	new Object[] {user1},
		    	new Object[] {user2},
		    	new Object[] {user3},
		    	new Object[] {user4},
		 };
	}
	@Test
	@Parameters(method="parametersForRemoveWaitingValid")
	public void testRemoveWaitingValid(User user) {
		waitingList.addWaiting(user);
		waitingList.removeWaiting(user);
		List<User> actualList = waitingList.getWaiting(user.getMemberType()); 
		assertTrue(!actualList.contains(user));	
	}
	
	private static Object[] parametersForRemoveWaitingInvalid() {
		User user1 = new User("Alice", null, false, 3);
		User user2 = new User("Bob", "", false, 3);
		User user3 = new User("Eve", "InvalidType", false, 1);
		
		 return new Object[] {
		    	new Object[] {user1}, //add user into null memberType
		    	new Object[] {user2}, //add user into empty memberType
		    	new Object[] {user3}, //add user into invalid memberType
		 };
	}
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method="parametersForRemoveWaitingInvalid")
	public void testRemoveWaitingInvalid(User user) {
		waitingList.addWaiting(user);
		waitingList.removeWaiting(user);	
	}
}

