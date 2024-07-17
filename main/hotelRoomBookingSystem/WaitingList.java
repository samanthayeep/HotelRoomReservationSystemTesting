package hotelRoomBookingSystem;
import java.util.*;

public class WaitingList {
	List<User> vipWaiting;
	List<User> memberWaiting;
	List<User> normalWaiting;
	
    public WaitingList() {
      vipWaiting = new ArrayList<>();
      memberWaiting = new ArrayList<>();
      normalWaiting = new ArrayList<>();
    }

    public void addWaiting(User user) {
    	if(user.getMemberType() == null)
            throw new IllegalArgumentException("Invalid member type.");
        
        switch (user.getMemberType()) {
            case "VIP":
                vipWaiting.add(user);
                break;
            case "Member":
                memberWaiting.add(user);
                break;
            case "Normal":
                normalWaiting.add(user);
                break;
             default:
            	 throw new IllegalArgumentException("Invalid user type.");
        }
    }

  
    public List<User> getWaiting(String member_Type) {
    	if(member_Type == null)
            throw new IllegalArgumentException("Invalid member type.");
        
        switch (member_Type) {
            case "VIP":
                return vipWaiting;
            case "Member":
                return memberWaiting;
            case "Normal":
                return normalWaiting;
            default:
                throw new IllegalArgumentException("Invalid user type.");
        }
    }

   
    public void removeWaiting(User user) {
    	if(user.getMemberType() == null)
            throw new IllegalArgumentException("Invalid member type.");
        
        boolean removed = false;
        switch (user.getMemberType()) {
            case "VIP":
                removed = vipWaiting.remove(user);
                break;
            case "Member":
                removed = memberWaiting.remove(user);
                break;
            case "Normal":
                removed = normalWaiting.remove(user);
                break;
        }

        if (!removed) {
            throw new IllegalArgumentException("User not found in the waiting list.");
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WaitingList)) {
            return false;
        }
        WaitingList other = (WaitingList) obj;
        return listsAreEqual(this.vipWaiting, other.vipWaiting)
                && listsAreEqual(this.memberWaiting, other.memberWaiting)
                && listsAreEqual(this.normalWaiting, other.normalWaiting);
        }

        private boolean listsAreEqual(List<User> list1, List<User> list2) {
            if (list1 == null || list2 == null) {
                return list1 == list2; // Both null or both non-null
            }
            if (list1.size() != list2.size()) {
                return false;
            }
            for (int i = 0; i < list1.size(); i++) {
                if (!list1.get(i).equals(list2.get(i))) {
                    return false;
                }
            }
            return true;
            
        }



}
    



