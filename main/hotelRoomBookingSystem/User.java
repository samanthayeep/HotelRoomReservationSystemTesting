package hotelRoomBookingSystem;

import java.util.*;

public class User 
{
	private String name;
	private String memberType;
	private boolean exclReward;
	private int numRoomsBooked; 
	
	public User(String name, String memberType, boolean exclReward, int numRoomsBooked)
	{
		this.name=name;
		this.memberType=memberType;
		this.exclReward=exclReward;
		this.numRoomsBooked=numRoomsBooked;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getMemberType() 
	{
		return memberType;
	}
	public boolean hasExclReward() 
	{
		return exclReward;
	}
	public void setExclReward(boolean exclReward) 
	{
	    this.exclReward = exclReward;
	}
	public int getNumRoomsBooked() 
	{
		return numRoomsBooked;
	}
	public void setNumRoomsBooked(int numRoomsBooked) 
	{
		this.numRoomsBooked=numRoomsBooked;
	}
	public void incrementRoomsBooked()
	{
		numRoomsBooked++;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return numRoomsBooked == user.numRoomsBooked &&
                Objects.equals(name, user.name) &&
                Objects.equals(memberType, user.memberType);
    }

	
}

