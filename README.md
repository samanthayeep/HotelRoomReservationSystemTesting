**Hotel Room Booking System**

This project is a Hotel Room Booking System developed as part of the UECS2354 Software Testing course for the 202401 assignment. The system supports various functionalities including booking and cancellation of hotel rooms, handling of different member types (VIP, Normal, Non-member), and managing waiting lists.

**Project Overview**

The Hotel Room Booking System allows users to book rooms based on their membership type (VIP, Normal, or Non-member). The system includes functionalities for:

- Allocating rooms based on membership type and availability.
- Handling exclusive rewards for VIP and Normal members.
- Managing waiting lists when rooms are fully booked.
- Cancelling bookings and removing users from waiting lists.

**Features**

**VIP Member:**
- Can book up to 3 rooms.
- Room allocation: VIP -> Deluxe -> Standard -> VIP Waiting List.
- Handles scenarios when rooms are partially available.

**Normal Member:**
- Can book up to 2 rooms.
- Room allocation: Deluxe -> Standard -> Member Waiting List.
- Exclusive rewards allow booking VIP rooms if available.

**Non-member:**
- Can book 1 room.
- Room allocation: Standard -> Normal Waiting List.

**Common:**
- Users can cancel bookings.
- Users on waiting lists are removed if bookings are cancelled.

**Setup Instructions**

1. Clone the Repository:
- git clone https://github.com/samanthayeep/HotelRoomReservationSystemTesting.git
- cd HotelRoomReservationSystemTesting

2. Import the Project:
- Open Eclipse or any Java IDE.
- Import the cloned repository as an existing project.

3. Set Up JAR Files:
- Place all JAR files (jUnitParams & Mockito) in C:\jar_files.

**Usage**

1. Run Application Code:
- Implement the User, Booking, and WaitingList classes.
- Use the setBooking() method to allocate rooms or add to waiting lists.
- Use the cancelBooking() method to cancel bookings and update waiting lists.

2. Run Test Code:
- Write test cases using the jUnit Framework and Mockito.
- Execute the tests to ensure the application works as expected.

**Test Plan**

The test plan includes scope, objectives, test basis, features to be tested, and not to be tested, test conditions, and entry/exit criteria. For detailed test plan information, refer to the test_plan.md file.

**Test Design**

The test design includes decision tables and detailed test cases. For each condition, rules are identified and referenced in test cases. For detailed test design information, refer to the test_design.md file.

**Application Code**

The application code includes:

- User class
- Booking class
- WaitingList class
The setBooking() method determines room allocation, and the cancelBooking() method handles booking cancellations.

**Test Code**

The test code uses the jUnit Framework and Mockito to verify the functionality of the methods. Test cases are designed to cover all scenarios, using techniques like boundary value analysis and equivalence partitioning.

**Assumptions**

- Additional assumptions made for the application are documented in the assumptions.md file.
- Status/error checking is implemented to make the program robust.
- Code is organized with meaningful variable names and liberal use of comments.


