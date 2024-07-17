package hotelRoomBookingSystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class WaitingListTest {
	
	private WaitingList waitingList;
	private static User user;
	
	@Before
	public void setUp() {
		waitingList = new WaitingList();
		user = mock(User.class);	
	}
	
	private Object[] parametersForAddWaitingValidValue() {
        return new Object[] {
        		//test case 3.1
        		new Object[] { "VIP"}, // VIP member without exclusive reward
        		//test case 3.2
        		new Object[] { "Member"}, // Member with or without exclusive reward
        		//test case 3.3
        		new Object[] { "Normal"}, // Non-member
        };
    }
	@Test
	@Parameters(method="parametersForAddWaitingValidValue")
	public void testAddWaitingValidValue(String memberType) {
		when(user.getMemberType()).thenReturn(memberType);
		waitingList.addWaiting(user);
		List<User> actualList = waitingList.getWaiting(user.getMemberType()); 
		assertTrue(actualList.size() > 0);	
		assertTrue(actualList.contains(user));
	}
	
	
	private Object[] parametersForAddWaitingInvalidValue() {
		return new Object[] {
				//test case 3.4
        		new Object[] {null}, //null member type
        		//test case 3.5
        		new Object[] {""}, //empty member type
        		//test case 3.6
        		new Object[] {"InvalidType"}, //member type other than "VIP", "Member", "Normal"
        };
	}
    @Test(expected = IllegalArgumentException.class)
    @Parameters(method="parametersForAddWaitingInvalidValue")
    public void testAddWaitingInvalidValue(String memberType) {
    	when(user.getMemberType()).thenReturn(memberType);
		waitingList.addWaiting(user);
    }
    
	
	@Test
	public void testGetWaitingVIPValidValue() {
		//test case 4.1
		when(user.getMemberType()).thenReturn("VIP");
		// Adding the user to the VIP waiting list twice
		waitingList.addWaiting(user);
		waitingList.addWaiting(user);
		// Creating a list to hold the users added to the waiting list
		List<User> usersAdded = new ArrayList<User>();
		usersAdded.add(user);
		usersAdded.add(user);
		// Retrieving the list of waiting users with membership type "VIP"
		List<User> waitingUsers = waitingList.getWaiting("VIP");
		// Asserting that the list of waiting users matches the list of users added
		assertEquals(usersAdded, waitingUsers);
	}
	
	@Test
	public void testGetWaitingMemberValidValue() {
		//test case 4.2
		when(user.getMemberType()).thenReturn("Member");
		// Adding the user to the member waiting list twice
		waitingList.addWaiting(user);
		waitingList.addWaiting(user);
		// Creating a list to hold the users added to the waiting list
		List<User> usersAdded = new ArrayList<User>();
		usersAdded.add(user);
		usersAdded.add(user);
		// Retrieving the list of waiting users with membership type "Member"
		List<User> waitingUsers = waitingList.getWaiting("Member");
		// Asserting that the list of waiting users matches the list of users added
		assertEquals(usersAdded, waitingUsers);
	}
	
	@Test
	public void testGetWaitingNormalValidValue() {
		//test case 4.3
		when(user.getMemberType()).thenReturn("Normal");
		// Adding the user to the normal waiting list twice
		waitingList.addWaiting(user);
		waitingList.addWaiting(user);
		// Creating a list to hold the users added to the waiting list
		List<User> usersAdded = new ArrayList<User>();
		usersAdded.add(user);
		usersAdded.add(user);
		// Retrieving the list of waiting users with membership type "Normal"
		List<User> waitingUsers = waitingList.getWaiting("Normal");
		// Asserting that the list of waiting users matches the list of users added
		assertEquals(usersAdded, waitingUsers);
	}
	
	private Object[] parametersForGetWaitingInvalidValue() {
		return new Object[] {
				//test case 4.4
        		new Object[] {null}, //null member type
        		//test case 4.5
        		new Object[] {""}, //empty member type
        		//test case 4.6
        		new Object[] {"InvalidType"}, //member type other than "VIP", "Member", "Normal"
        };
    }
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method="parametersForGetWaitingInvalidValue")
	public void testGetWaitingInvalidValue(String memberType) {
		when(user.getMemberType()).thenReturn(memberType);
		waitingList.getWaiting(memberType);	 
	}
	
	private Object[] parametersForRemoveWaitingValidValue() {
        // Define different user data along with the expected waiting lists
        return new Object[] {
        		//test case 5.1
        		new Object[] {"VIP"}, //VIP member
        		//test case 5.2
        		new Object[] {"Member"}, //member with or without exclusive reward
        		//test case 5.3
        		new Object[] {"Normal"} //non-member
        };
    }
	@Test
	@Parameters(method="parametersForRemoveWaitingValidValue")
	public void testRemoveWaitingValidValue(String memberType) {
		when(user.getMemberType()).thenReturn(memberType);
		waitingList.addWaiting(user);
		waitingList.removeWaiting(user);
		// Asserting that the user is no longer in the waiting list for the specified member type
		assertTrue(!waitingList.getWaiting(memberType).contains(user));
	}
	
	
	private Object[] parametersForRemoveWaitingInvalidValue() {
        // Define different user data along with the expected waiting lists
        return new Object[] {
        		//test case 5.4
        		new Object[] {null}, //null member type
        		//test case 5.5
        		new Object[] {""}, //empty member type
        		//test case 5.6
        		new Object[] {"InvalidType"}, //member type other than "VIP", "Member", "Normal"
        };
    }
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method="parametersForRemoveWaitingInvalidValue")
	public void testRemoveWaitingInvalidValue(String memberType) {
		when(user.getMemberType()).thenReturn(memberType);
		waitingList.addWaiting(user);
		waitingList.removeWaiting(user);
	}
	
	
	
	
}