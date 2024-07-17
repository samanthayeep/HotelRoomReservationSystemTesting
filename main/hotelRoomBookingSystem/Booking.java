package hotelRoomBookingSystem;

import java.util.*;

public class Booking {
	private Room roomsAllocated;
	private User user;
	
    public Booking(User user) {
    	this.roomsAllocated = new Room();
    	this.user = user;
    }
    
    public Room getRoomsAllocated() {
    	return roomsAllocated;
    }
    
    public User getUser() {
    	return user;
    }
    
	public Room setBooking(WaitingList waitingList, Room rooms) {
		if (rooms.getVipRoom() <0 || rooms.getDeluxeRoom() <0 || rooms.getStandardRoom() <0)
			throw new IllegalArgumentException("Invalid available room numbers.");
	
		if (!Arrays.asList("VIP", "Member", "Normal").contains(user.getMemberType())) {
            throw new IllegalArgumentException("Invalid user type.");
        }
        
        if((user.getMemberType().equals("VIP") ) && user.hasExclReward()) {
        	throw new IllegalArgumentException("VIP member does not have exclusive reward.");
        }
        
        if((user.getMemberType().equals("Normal")) && user.hasExclReward()) {
        	throw new IllegalArgumentException("Non-member does not have exclusive reward.");
        }
        
		int numRoomsBooked = user.getNumRoomsBooked();
		 if (numRoomsBooked <= 0) {
	            throw new IllegalArgumentException("Invalid number of rooms to book. Must be a positive integer.");
	        }
		switch (user.getMemberType()) {
	        case "VIP":
	            if (numRoomsBooked > 3) { 
	                throw new IllegalArgumentException("VIP user cannot book more than 3 rooms");
	            }
	            break;
	        case "Member":
	            if (numRoomsBooked > 2) {
	                throw new IllegalArgumentException("Member cannot book more than 2 rooms");
	            }
	            break;
	        case "Normal":
	            if (numRoomsBooked > 1) {
	                throw new IllegalArgumentException("Normal user cannot book more than 1 room");
	            }
	            break;
	        default:
	        	throw new IllegalArgumentException("Invalid user type: " + user.getMemberType());
    }
		
		roomsAllocated = new Room(0,0,0);
			for (int i = 0; i < numRoomsBooked; i++) {
	        	  if (user.getMemberType().equals("VIP")) {
		                if (rooms.checkRoom("VIP")>0) {
		                    roomsAllocated.setVipRoom(1);
		                    rooms.setVipRoom(-1);
		        
		                } else if (rooms.checkRoom("Deluxe")>0) {
		                	roomsAllocated.setDeluxeRoom(1);
		                	rooms.setDeluxeRoom(-1);	
		                    
		                } else if (rooms.checkRoom("Standard")>0) {
		                	roomsAllocated.setStandardRoom(1);
		                	rooms.setStandardRoom(-1);
		                    
		                }else if(!waitingList.getWaiting("VIP").contains(user)) {
		                	waitingList.addWaiting(user);
		                }
	        	}
	
		        else if (user.getMemberType().equals("Member")) {
	                if (rooms.checkRoom("VIP") >0 && user.hasExclReward()) {
	                	roomsAllocated.setVipRoom(1);
	                    rooms.setVipRoom(-1);	
	                    user.setExclReward(false);
	                } 
	                
	                else if (rooms.checkRoom("Deluxe")>0) {
	                	roomsAllocated.setDeluxeRoom(1);
	                	rooms.setDeluxeRoom(-1);		
	                } 
	                
	                else if (rooms.checkRoom("Standard")>0) {
	                	roomsAllocated.setStandardRoom(1);
	                	rooms.setStandardRoom(-1);
		                }
	                else if(!waitingList.getWaiting("Member").contains(user))
	                	waitingList.addWaiting(user);
		        
		        } 
		        
		        else if (user.getMemberType().equals("Normal")) {
		            if (rooms.checkRoom("Standard")>0) {
		            	roomsAllocated.setStandardRoom(1);
	                	rooms.setStandardRoom(-1);
		            }
		            else if(!waitingList.getWaiting("Normal").contains(user))
		            	waitingList.addWaiting(user);
		        }
	          }
		
	    return rooms;
	}
	
	public Room cancelBooking(WaitingList waitingList, Room rooms, User userToCancel) {
		if(userToCancel.getMemberType() == null || userToCancel.getMemberType().isEmpty() || 
				!Arrays.asList("VIP", "Member", "Normal").contains(userToCancel.getMemberType())) {
			 throw new IllegalArgumentException("Invalid user type.");
		}
		boolean userFound = false;
        if (user.getName().equals(userToCancel.getName())) {
        	userFound = true;
            rooms.setVipRoom(roomsAllocated.getVipRoom());
    		rooms.setDeluxeRoom(roomsAllocated.getDeluxeRoom());
    		rooms.setStandardRoom(roomsAllocated.getStandardRoom());
    		roomsAllocated.clearRoom();
    		
        }
	    
		boolean userInWaitingList = false;
		List<User> userToRemove = waitingList.getWaiting(userToCancel.getMemberType());
		if(userToRemove!= null) {
			for(User user:userToRemove) {
				if(user.getName().equals(userToCancel.getName())) {
					 userInWaitingList = true;
				}
			}
		}
		if(userInWaitingList) {
			waitingList.removeWaiting(userToCancel);
		}

	    if (!userFound && !userInWaitingList) {
	        throw new IllegalArgumentException("User is not found.");
	    }

	    return rooms;
}
}



