## Product Requirements Document: The EquiScheduler - Riding Lesson Planner

**1. Introduction**

The EquiScheduler is a comprehensive and user-friendly platform designed to streamline the scheduling and management of a riding school. This document outlines the product requirements for the initial version of the EquiScheduler, focusing on core functionalities for both administrative staff and riders. The system aims to replace manual scheduling methods, reduce administrative overhead, and provide a seamless booking experience for clients. By centralizing the management of teachers, horses, and schedules, EquiScheduler will enhance efficiency, minimize booking conflicts, and improve overall communication within the riding school.

**2. User Personas**

**a) Admin Persona: "Sarah," the Stable Manager**

* **Bio:** Sarah is the busy manager of a bustling riding stable. She is responsible for coordinating a team of riding instructors, managing the health and schedules of the stable's horses, and ensuring a smooth experience for all clients. She is moderately tech-savvy but requires a system that is intuitive and easy to learn.
* **Goals:**
    * To have a centralized system to manage all aspects of lesson scheduling.
    * To easily track instructor availability and horse workload.
    * To reduce time spent on manual booking and administrative tasks.
    * To minimize double bookings and scheduling conflicts.
    * To provide clients with a simple and transparent way to book lessons.
* **Frustrations:**
    * The current paper-based or spreadsheet system is prone to errors and is time-consuming to maintain.
    * Communicating schedule changes to instructors and clients is a constant challenge.
    * It's difficult to get a clear overview of the week's or month's schedule at a glance.

**b) User Persona: "Alex," the Avid Rider**

* **Bio:** Alex is a passionate equestrian who takes regular riding lessons. They have a busy work schedule and need a flexible and convenient way to book their lessons. Alex is comfortable with technology and expects a modern, mobile-friendly booking experience.
* **Goals:**
    * To easily see when their preferred instructor is available.
    * To book lessons at a time that fits their schedule.
    * To be able to cancel or reschedule lessons with reasonable notice.
    * To have a clear record of their upcoming and past lessons.
* **Frustrations:**
    * Playing "phone tag" with the stable to book a lesson.
    * Not knowing the instructor's availability without calling or visiting.
    * The inconvenience of having to cancel a lesson well in advance due to rigid policies.

**3. Admin Features**

### 3.1. Instructor Management

* **Description:** This feature allows the admin to create, view, update, and delete riding instructor profiles. Each profile will contain essential information for scheduling and contact purposes.
* **User Stories:**
    * As an admin, I want to add new riding instructors to the system by entering their name, contact information, and their general availability.
    * As an admin, I want to be able to modify an instructor's details, such as their available hours per day and the days of the week they work.
    * As an admin, I want to define the standard slot length for lessons offered by each instructor (e.g., 30, 45, or 60 minutes).
    * As an admin, I want to be able to delete an instructor who is no longer with the stable.
* **Acceptance Criteria:**
    * The system must allow for the creation of a new instructor with fields for first name, last name, email, phone number, and a notes section.
    * An availability matrix (days of the week and time ranges) must be configurable for each instructor.
    * The admin must be able to set a default lesson slot duration for each instructor.
    * A list of all instructors must be viewable, with options to edit or delete each entry.

### 3.2. Horse Management

* **Description:** This feature enables the admin to manage the stable's horses, including their availability for lessons. This is crucial for preventing overworking the animals.
* **User Stories:**
    * As an admin, I want to add new horses to the system, including their name, breed, and any relevant notes about their temperament or skill level.
    * As an admin, I want to set the maximum number of hours a horse can be ridden per day to ensure their well-being.
    * As an admin, I want to view a list of all horses and be able to edit their details or remove them from the system if they are no longer at the stable.
* **Acceptance Criteria:**
    * The system must allow for the creation of a new horse with fields for name, breed, age, and a notes section.
    * A field to input the maximum daily working hours for each horse is required.
    * A comprehensive list of all horses must be available, with options to edit or delete each record.

### 3.3. Schedule Management

* **Description:** This core feature allows the admin to create, view, and modify the master schedule for the riding school. This includes creating bookable lesson slots and blocking out time for other activities.
* **User Stories:**
    * As an admin, I want to generate a weekly or monthly schedule based on instructor and horse availability.
    * As an admin, I want to be able to manually create bookable lesson slots for a specific teacher and, optionally, a specific horse.
    * As an admin, I want to block out non-bookable time slots for an instructor (e.g., for training, vet appointments) or a horse (e.g., for rest, medical care).
    * As an admin, I want to easily view the schedule by week, month, a specific trainer, or a specific horse.
    * As an admin, I want to be able to delete or modify existing bookable and non-bookable slots.
* **Acceptance Criteria:**
    * The system must provide a calendar view with daily, weekly, and monthly display options.
    * The calendar must be filterable by instructor and horse.
    * Admins must be able to click on a time slot to create a new bookable lesson, assigning an instructor and a horse.
    * Admins must be able to create "unavailable" blocks of time for both instructors and horses.
    * Existing schedule entries must be editable and deletable.

### 3.4. User Management

* **Description:** This feature allows the admin to manage user accounts, specifically for associating them with instructor roles.
* **User Stories:**
    * As an admin, I want to create new user accounts.
    * As an admin, I want to assign the "teacher" role to specific user accounts, giving them the appropriate permissions.
    * As an admin, I want to be able to delete user accounts.
* **Acceptance Criteria:**
    * The system must allow for the creation of user accounts with a username and password.
    * There must be a mechanism to assign a "teacher" role to a user.
    * Admins must have the ability to view a list of all users and delete them.

### 3.5. Student Management

* **Description:** This feature allows the admin to manage student records for riders like Alex.
* **User Stories:**
    * As an admin, I want to create and update student contact information.
    * As an admin, I want to delete student profiles when they are no longer active.
* **Acceptance Criteria:**
    * The system must support a student profile with first name, last name, email, and phone number.
    * A list of all students must be viewable with edit and delete options.

**4. User Features**

### 4.1. Calendar Viewing

* **Description:** This feature allows registered users (riders) to view the lesson availability of each instructor.
* **User Stories:**
    * As a user, I want to be able to select a riding teacher and view their calendar to see available lesson slots.
* **Acceptance Criteria:**
    * Users must be able to access a public-facing or login-required calendar.
    * The calendar should clearly display the available time slots for each instructor.
    * The view should be intuitive and easy to navigate on both desktop and mobile devices.

### 4.2. Lesson Booking

* **Description:** This feature enables users to book available lesson slots directly through the platform.
* **User Stories:**
    * As a user, I want to be able to click on an available time slot and book a lesson for myself.
    * As a user, I want to receive a confirmation of my booking via email.
* **Acceptance Criteria:**
    * Users must be able to select an available time slot from an instructor's calendar.
    * Upon selection, a confirmation step should appear before finalizing the booking.
    * Once a booking is confirmed, the time slot should no longer show as available.
    * An automated email confirmation with the lesson details (date, time, instructor) should be sent to the user.

### 4.3. Booking Cancellation

* **Description:** This feature allows users to cancel their booked lessons up to a specified time in advance.
* **User Stories:**
    * As a user, I want to be able to view my upcoming bookings and have the option to cancel a lesson.
    * As a user, I want to be able to cancel a lesson up to one week before the scheduled time.
* **Acceptance Criteria:**
    * Users must have a personal dashboard or section to view their scheduled lessons.
    * A "Cancel" button should be available for each upcoming lesson.
    * The system must enforce the one-week cancellation policy. If the cancellation window has passed, the option should be disabled or an appropriate message displayed.
    * Upon successful cancellation, the time slot should become available again in the instructor's calendar.

**5. Future Considerations**

The initial version of the EquiScheduler will focus on the core functionalities outlined above. However, the following features are envisioned for future releases to further enhance the platform's capabilities:

* **Online Payments:** Integration with a payment gateway to allow users to pay for lessons at the time of booking.
* **Group Lessons:** Functionality to schedule and book group lessons with multiple participants.
* **Waiting Lists:** Allow users to join a waiting list for fully booked time slots and be notified if a spot becomes available.
* **Automated Reminders:** Send automated email or SMS reminders to users about their upcoming lessons.
* **Reporting and Analytics:** Provide admins with reports on booking trends, instructor performance, and horse utilization.
* **Mobile App:** A dedicated mobile application for both admins and users for an optimized on-the-go experience.