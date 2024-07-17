package hotelRoomBookingSystem;

import static org.junit.Assert.*;
import org.junit.*;
import junitparams.*;
import java.util.*;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class BookingTest {
	private static Booking booking;
    private static User userMock;
    private static Room roomMock;
    private static WaitingList waitingListMock;

	
	@Before
    public static void setUp() {
        userMock = mock(User.class);
        roomMock = mock(Room.class);
        waitingListMock = mock(WaitingList.class);
        booking = new Booking(userMock);
    }
	
	public static Object[] parametersForBookingTestValid() {
		//using BA for all user, vip=1,3; member=1,2; non-member=1
		//vip request 1 rooms & all room available 
		int[] roomAvailable1 = {3,3,3}; 
		int[] roomAllocated1 = {1,0,0}; //how many times the set room is called

		//vip request 3 rooms & all room available 
		int[] roomAvailable2 = {3,3,3}; 
		int[] roomAllocated2 = {3,0,0};
		
		//vip request 1 rooms & vip room not available 
		int[] roomAvailable3 = {0,3,3}; 
		int[] roomAllocated3 = {0,1,0};
		
		//vip request 3 rooms , vip room not available 
		int[] roomAvailable4 = {0,3,3}; 
		int[] roomAllocated4 = {0,3,0};
		
		//vip request 1 rooms , vip & deluxe room not available 
		int[] roomAvailable5 = {0,0,3}; 
		int[] roomAllocated5 = {0,0,1};
		
		//vip request 3 rooms , vip & deluxe room not available 
		int[] roomAvailable6 = {0,0,3}; 
		int[] roomAllocated6 = {0,0,3};
		
		//member with exclusive reward request 1 room,all room available
		int[] roomAvailable7 = {3,3,3}; 
		int[] roomAllocated7 = {1,0,0};
		
		//member with exclusive reward request 2 rooms,all room available
		int[] roomAvailable8 = {3,3,3}; 
		int[] roomAllocated8 = {1,1,0};
		
		//member with exclusive reward request 1 room,vip room not available
		int[] roomAvailable9 = {0,3,3}; 
		int[] roomAllocated9 = {0,1,0};
 
		//member with exclusive reward request 2 room,vip room not available
		int[] roomAvailable10 = {0,3,3}; 
		int[] roomAllocated10 = {0,2,0};
		
		//member with exclusive reward request 1 room,vip & deluxe room not available
		int[] roomAvailable11 = {0,0,3}; 
		int[] roomAllocated11 = {0,0,1};
		
		//member with exclusive reward request 2 room,vip & deluxe room not available
		int[] roomAvailable12 = {0,0,3}; 
		int[] roomAllocated12 = {0,0,2};
		
		//member without exclusive reward request 1 room,all room available
		int[] roomAvailable13 = {3,3,3}; 
		int[] roomAllocated13 = {0,1,0};
		
		//member without exclusive reward request 2 room,all room available
		int[] roomAvailable14 = {3,3,3}; 
		int[] roomAllocated14 = {0,2,0};
		
		//member without exclusive reward request 1 room,vip room not available
		int[] roomAvailable15 = {0,3,3}; 
		int[] roomAllocated15 = {0,1,0};
		
		//member without exclusive reward request 2 room,vip room not available
		int[] roomAvailable16 = {0,3,3}; 
		int[] roomAllocated16 = {0,2,0};
		
		//member without exclusive reward request 1 room,vip & deluxe room not available
		int[] roomAvailable17 = {0,0,3}; 
		int[] roomAllocated17 = {0,0,1};
		
		//member without exclusive reward request 2 room,vip & deluxe room not available
		int[] roomAvailable18 = {0,0,3}; 
		int[] roomAllocated18 = {0,0,2};
		
		//non member request for 1 room , all room available
		int[] roomAvailable19 = {3,3,3}; 
		int[] roomAllocated19 = {0,0,1};
		
		//non member request for 1 room , vip room not available
		int[] roomAvailable20 = {0,3,3}; 
		int[] roomAllocated20 = {0,0,1};

		//non member request for 1 room , vip & deluxe room not available
		int[] roomAvailable21 = {0,0,3}; 
		int[] roomAllocated21 = {0,0,1};

		return new Object[] {
				//1.3.1.1 - vip request 1 rooms & all room available  
				new Object[] {"VIP",false, 1, roomAvailable1, roomAllocated1},
				//1.3.1.2 - vip request 3 rooms & all room available 
				new Object[] {"VIP",false, 3, roomAvailable2, roomAllocated2},
				//1.3.2.1 - vip request 1 rooms & vip room not available 
				new Object[] {"VIP",false, 1, roomAvailable3, roomAllocated3},
				//1.3.2.2 - vip request 3 rooms , vip room not available 
				new Object[] {"VIP",false, 3, roomAvailable4, roomAllocated4},
				//1.3.3.1 - vip request 1 rooms , vip & deluxe room not available 
				new Object[] {"VIP",false, 1, roomAvailable5, roomAllocated5},
				//1.3.3.2 - vip request 3 rooms , vip & deluxe room not available 
				new Object[] {"VIP",false, 3, roomAvailable6, roomAllocated6},
				//1.3.5.1 - member with exclusive reward request 1 room,all room available
				new Object[] {"Member",true, 1, roomAvailable7, roomAllocated7},
				//1.3.5.2 - member with exclusive reward request 2 rooms,all room available
				new Object[] {"Member",true, 2, roomAvailable8, roomAllocated8},
				//1.3.6.1 - member with exclusive reward request 1 room,vip room not available
				new Object[] {"Member",true, 1, roomAvailable9, roomAllocated9},
				//1.3.6.2 - member with exclusive reward request 2 room,vip room not available
				new Object[] {"Member",true, 2, roomAvailable10, roomAllocated10},
				//1.3.7.1 - member with exclusive reward request 1 room,vip & deluxe room not available
				new Object[] {"Member",true, 1, roomAvailable11, roomAllocated11},
				//1.3.7.2 - member with exclusive reward request 2 room,vip & deluxe room not available
				new Object[] {"Member",true, 2, roomAvailable12, roomAllocated12},
				//1.3.9.1 - member without exclusive reward request 1 room,all room available
				new Object[] {"Member",false, 1, roomAvailable13, roomAllocated13},
				//1.3.9.2 - member without exclusive reward request 2 room,all room available
				new Object[] {"Member",false, 2, roomAvailable14, roomAllocated14},
				//1.3.10.1 - member without exclusive reward request 1 room,vip room not available
				new Object[] {"Member",false, 1, roomAvailable15, roomAllocated15},
				//1.3.10.2 - member without exclusive reward request 2 room,vip room not available
				new Object[] {"Member",false, 2, roomAvailable16, roomAllocated16},
				//1.3.11.1 - member without exclusive reward request 1 room,vip & deluxe room not available
				new Object[] {"Member",false, 1, roomAvailable17, roomAllocated17},
				//1.3.11.2 - member without exclusive reward request 2 room,vip & deluxe room not available
				new Object[] {"Member",false, 2, roomAvailable18, roomAllocated18},
			   //1.3.13 - non member request for 1 room , all room available
				new Object[] {"Normal",false, 1, roomAvailable19, roomAllocated19},
				//1.3.14 - non member request for 1 room , vip room not available
				new Object[] {"Normal",false, 1, roomAvailable20, roomAllocated20},
				//1.3.15 - non member request for 1 room , vip & deluxe room not available
				new Object[] {"Normal",false, 1, roomAvailable21, roomAllocated21},
				
		};
	}
	
	@Parameters(method = "parametersForBookingTestValid")
	@Test
	public static void testBookingTestValid(String memberType, boolean hasExclReward, int roomsBooked, int[] roomAvailable, int[] roomAllocated) {
		when(userMock.getMemberType()).thenReturn(memberType);
		when(userMock.hasExclReward()).thenReturn(hasExclReward).thenReturn(false);
		when(userMock.getNumRoomsBooked()).thenReturn(roomsBooked);
		
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
        
        
        booking.setBooking(waitingListMock, roomMock);
        verify(roomMock, times(roomAllocated[0])).setVipRoom(-1);
        verify(roomMock, times(roomAllocated[1])).setDeluxeRoom(-1);
        verify(roomMock, times(roomAllocated[2])).setStandardRoom(-1);
	}

	public static Object[] parametersBookingTestRoomsAvailabilityandAddedIntoWL() {
		// No rooms available of any room type & No room allocated
		int[] roomAvailable1 = {0, 0, 0}; 
		int[] roomAllocated1= {0,0,0};
		
	    //room available and allocated
		//vip book 2, only 1 vip room available
		int[] roomAvailable2 = {1, 0, 0}; 
		int[] roomAllocated2= {1,0,0};
		
		//vip book 3, only 2 vip room available
		int[] roomAvailable3 = {2, 0, 0}; 
		int[] roomAllocated3= {2,0,0};
		
		//vip book 2, only 1 deluxe room available
		int[] roomAvailable4 = {0, 1, 0}; 
		int[] roomAllocated4= {0,1,0};
		
		//vip book 3, only 2 deluxe room available
		int[] roomAvailable5 = {0, 2, 0}; 
		int[] roomAllocated5= {0,2,0};
		
		//vip book 2, only 1 standard room available
		int[] roomAvailable6 = {0, 0, 1}; 
		int[] roomAllocated6= {0,0,1};
		
		//vip book 3, only 2 standard room available
		int[] roomAvailable7 = {0, 0, 2}; 
		int[] roomAllocated7= {0,0,2};
		
		//member book 2 with exclusive reward, only 1 vip room available
		int[] roomAvailable8 = {1, 0, 0}; 
		int[] roomAllocated8= {1,0,0};
		
		//member book 2 with exclusive reward, only 1 deluxe room available
		int[] roomAvailable9 = {0, 1, 0}; 
		int[] roomAllocated9= {0,1,0};
		
		//member book 2 with exclusive reward, only 1 standard room available
		int[] roomAvailable10 = {0, 0, 1}; 
		int[] roomAllocated10= {0,0,1};
		
		//member book 2 without exclusive reward, only 1 vip and 1 deluxe room available,no room allocated
		int[] roomAvailable11 = {1, 1, 0}; 
		int[] roomAllocated11= {0,1,0};
		
		//member book 2 without exclusive reward, only 1 deluxe room available
		int[] roomAvailable12 = {0, 1, 0}; 
		int[] roomAllocated12= {0,1,0};
		
		//member book 2 without exclusive reward, only 1 standard room available
		int[] roomAvailable13 = {0, 0, 1}; 
		int[] roomAllocated13= {0,0,1};
		
		
		//non member book 1 room, only 1 vip room available
		int[] roomAvailable14 = {1, 0, 0}; 
		int[] roomAllocated14= {0,0,0};
		
		//non member book 1 room, only 1 deluxe room available
		int[] roomAvailable15 = {0, 1, 0}; 
		int[] roomAllocated15= {0,0,0};
		
		//non member book 1 room, only 1 standard room available, return false
		int[] roomAvailable16 = {0,0, 1}; 
		int[] roomAllocated16= {0,0,1};
		
		

	    
	    return new Object[] {
	    	//no room available & directly placed into waiting list
	        //1.3.4 - VIP with no rooms available, added into waiting list
	        new Object[] {"VIP", false, 1, roomAvailable1,roomAllocated1, true},
	        // 1.3.8 - Member with exclusive reward , no rooms available, added into waiting list
	        new Object[] {"Member", true, 1, roomAvailable1, roomAllocated1, true},
	        // 1.3.12 - Member without exclusive reward , no rooms available, added into waiting list
	        new Object[] {"Member", false, 1, roomAvailable1, roomAllocated1, true},
	        //1.3.16 - Non-member with no rooms available, added into waiting list
	        new Object[] {"Normal", false, 1, roomAvailable1,roomAllocated1, true},
	        
	        //room available but not enough for requirement, room allocated and also placed into waiting list 
	        //VIP with not enough rooms available, room VIP allocated & added into waiting list
	        //1.4.1 - vip book 2, only 1 vip room available
	        new Object[] {"VIP", false, 2, roomAvailable2,roomAllocated2, true},   
	        //1.4.2 - vip book 3, only 2 vip room available
	        new Object[] {"VIP", false, 3, roomAvailable3,roomAllocated3, true},   
	        //VIP with not enough rooms available, room deluxe allocated & added into waiting list
	        //1.4.3 - vip book 2, only 1 deluxe room available
	        new Object[] {"VIP", false, 2, roomAvailable4,roomAllocated4, true}, 
	        //1.4.4 -vip book 3, only 2 deluxe room available
	        new Object[] {"VIP", false, 3, roomAvailable5,roomAllocated5, true},   
	        //1.4.5 -vip book 2, only 1 standard room available
	        new Object[] {"VIP", false, 2, roomAvailable6,roomAllocated6, true},   
	        //1.4.6 -vip book 3, only 2 standard room available
	        new Object[] {"VIP", false, 3, roomAvailable7,roomAllocated7, true},   
	        
	        //Member with exclusive reward and not enough rooms available, room allocated & added into waiting list
	        //1.5.1 - member book 2 with exclusive reward, only 1 vip room available
	        new Object[] {"Member", true, 2, roomAvailable8,roomAllocated8, true},   
	        //1.5.2 - member book 2 with exclusive reward, only 1 deluxe room available
	        new Object[] {"Member", true, 2, roomAvailable9,roomAllocated9, true},   
	        //1.5.3 - member book 2 with exclusive reward, only 1 standard room available
	        new Object[] {"Member", true, 2, roomAvailable10,roomAllocated10, true},  
        
	        //Member without exclusive reward and not enough rooms available, room allocated & added into waiting list
	        //1.5.4 - member book 2 without exclusive reward, only 1 vip and 1 deluxe room available,only 1 deluxe room allocated
	        new Object[] {"Member", false, 2, roomAvailable11,roomAllocated11, true},  
	        //1.5.5 - member book 2 without exclusive reward, only 1 deluxe room available
	        new Object[] {"Member", false, 2, roomAvailable12,roomAllocated12, true}, 
			//1.5.6 - member book 2 without exclusive reward, only 1 standard room available
	        new Object[] {"Member", false, 2, roomAvailable13,roomAllocated13, true}, 
	        
	        //Non-member with not enough rooms available, room allocated & added into waiting list
			//1.6.1 - non member book 1 room, only 1 vip room available
	        new Object[] {"Normal", false, 1, roomAvailable14,roomAllocated14, true},
			//1.6.2 - non member book 1 room, only 1 deluxe room available
	        new Object[] {"Normal", false, 1, roomAvailable15,roomAllocated15, true},
			//1.6.3 - non member book 1 room, only 1 standard room available not added to waiting list
	        new Object[] {"Normal", false, 1, roomAvailable16,roomAllocated16, false},

	        
	    };
	}

    @Parameters(method = "parametersBookingTestRoomsAvailabilityandAddedIntoWL")
    @Test
    public void testBookingWithNoRoomsAvailableAndAddedIntoWL(String memberType, boolean hasExclReward, 
    		int roomsBooked, int[] roomAvailable, int[] roomAllocated, boolean addToWaitingList) {
    	
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.hasExclReward()).thenReturn(hasExclReward);
        when(userMock.getNumRoomsBooked()).thenReturn(roomsBooked);
        

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
        
        booking.setBooking(waitingListMock, roomMock);
        verify(roomMock, times(roomAllocated[0])).setVipRoom(-1);
        verify(roomMock, times(roomAllocated[1])).setDeluxeRoom(-1);
        verify(roomMock, times(roomAllocated[2])).setStandardRoom(-1);

        if (addToWaitingList) {
            verify(waitingListMock).addWaiting(userMock);
        } else {
        	//to verify addWaiting(user) never invoked during test execution
            verify(waitingListMock, never()).addWaiting(userMock);
        }
    }

	
	
	public static Object[] parametersForBookingTestInvalid() {
		int[] roomAvailable = {3,3,3}; 
		
		return new Object[] {
				//VIP member restriction
				//1.1.1 - VIP member request 0 room
				new Object[] {"VIP",false, 0, roomAvailable},
				//1.1.1 - VIP member request 4 rooms
				new Object[] {"VIP",false, 4, roomAvailable},
				//1.1.2 - VIP request with exclusive reward
				new Object[] {"VIP",true, 1, roomAvailable},
				new Object[] {"VIP",true, 3, roomAvailable},
				
				
				//Member restriction
				//1.2.1 - Member with exclusive reward request 0 room 
				new Object[] {"Member",true, 0, roomAvailable},
				//1.2.1 - Member with exclusive reward request 3 room 
				new Object[] {"Member",true, 3, roomAvailable},
				//1.1.3 - Member without exclusive reward request 0 room 
				new Object[] {"Member",false, 0, roomAvailable},
				//1.1.3 - Member without exclusive reward request 3 room 
				new Object[] {"Member",false, 3, roomAvailable},			
				
				//Non-member restriction
				//1.2.2 - Non-member request 0 room 
				new Object[] {"Normal",false, 0, roomAvailable},
				//1.2.2 - Non-member request 2 room 
				new Object[] {"Normal",false, 2, roomAvailable},
				//1.2.3 - Non-member with exclusive reward request 1 room 
				new Object[] {"Normal",true, 1, roomAvailable},

			
		};
	}
	
	@Parameters(method = "parametersForBookingTestInvalid")
	@Test(expected = IllegalArgumentException.class)
	public static void testBookingInvalid(String memberType, boolean hasExclReward, int roomsBooked, int[] roomAvailable) {
		when(userMock.getMemberType()).thenReturn(memberType);
		when(userMock.hasExclReward()).thenReturn(hasExclReward).thenReturn(false);
		when(userMock.getNumRoomsBooked()).thenReturn(roomsBooked);
		
        when(roomMock.checkRoom("VIP")).thenReturn(roomAvailable[0]--);
        when(roomMock.checkRoom("Deluxe")).thenReturn(roomAvailable[1]--);
        when(roomMock.checkRoom("Standard")).thenReturn(roomAvailable[2]--);
        
        
        booking.setBooking(waitingListMock, roomMock);
	}
	
	public static Object[] parametersForCancelBookingValidV2() {
		
		return new Object[] {
				//user has booking and is in waitinglist
				//test case 2.2
				new Object[] {"Alice", "VIP", "Alice", true, true},
				//test case 2.3
				new Object[] {"Alice", "Member", "Alice", true, true},
				//test case 2.4
				new Object[] {"Alice", "Normal", "Alice", true, true},
				//user has booking and is not in waitinglist
				//test case 2.5
				new Object[] {"Alice", "VIP", "Alice", true, false},
				//test case 2.6
				new Object[] {"Alice", "Member", "Alice", true, false},
				//test case 2.7
				new Object[] {"Alice", "Normal", "Alice", true, false},
				//user has no booking and is in waitinglist
				//test case 2.8
				new Object[] {"Alice", "VIP", "Bob", false, true},
				//test case 2.9
				new Object[] {"Alice", "Member", "Bob", false, true},
				//test case 2.10
				new Object[] {"Alice", "Normal", "Bob", false, true},
		
				
	};
	}
	
	@Test
	@Parameters(method ="parametersForCancelBookingValidV2")
	public static void testCancelBookingValidV2(String userToCancelname, String memberType, String bookingName
			,boolean foundInBooking, boolean foundInWaiting) {
		User userToCancelMock = mock(User.class);
		//when the user has no booking, name is different from the user name in booking
		when(userToCancelMock.getName()).thenReturn(userToCancelname);
		when(userToCancelMock.getMemberType()).thenReturn(memberType);
		when(userMock.getName()).thenReturn(bookingName);
		
		//when user is in the waiting list, return a list that contains the user
		List<User> waitingListArray = new ArrayList<User>();
		if(foundInWaiting) {
			waitingListArray.add(userToCancelMock);
		}
		when(waitingListMock.getWaiting(memberType)).thenReturn(waitingListArray);
		booking.cancelBooking(waitingListMock, roomMock, userToCancelMock);
		
		if(foundInBooking) {
			//verify that the room available is reset
			verify(roomMock, times(1)).setVipRoom(anyInt());
			verify(roomMock, times(1)).setDeluxeRoom(anyInt());
			verify(roomMock, times(1)).setStandardRoom(anyInt());
		}
		
		
		if(foundInWaiting) {
			//verify that the user is removed from waiting list
			verify(waitingListMock, times(1)).removeWaiting(userToCancelMock);
		}
		
		
	}
	
	public static Object[] parametersForCancelBookingInvalid() {
		
		return new Object[] {
				//test case 2.1
				new Object[] {"Alice", "unknown", "Bob"},
				//user has no booking and is not in waitinglist
				//test case 2.11
				new Object[] {"Alice", "VIP", "Bob"},
				//test case 2.12
				new Object[] {"Alice", "Member", "Bob"},
				//test case 2.13
				new Object[] {"Alice", "Normal", "Bob"},
	};
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method ="parametersForCancelBookingInvalid")
	public static void testCancelBookingInvalid(String userToCancelname, String memberType, String bookingName) {
		User userToCancelMock = mock(User.class);
		//when the user has no booking, name is different from the user name in booking
		when(userToCancelMock.getName()).thenReturn(userToCancelname);
		when(userToCancelMock.getMemberType()).thenReturn(memberType);
		when(userMock.getName()).thenReturn(bookingName);
		
		//when user is not in the waiting list, return an empty waiting list
		List<User> waitingListArray = new ArrayList<User>();
		when(waitingListMock.getWaiting(memberType)).thenReturn(waitingListArray);
		
		booking.cancelBooking(waitingListMock, roomMock, userToCancelMock);
		
	}
	
	
}

	
	