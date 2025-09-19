# 🎟️ Event Ticket Booking App

A full-stack web application for booking event tickets with simulated payments using **Razorpay Payment Gateway**. This project is built with a **React (Frontend)**, a **Spring Boot (Backend)**, and uses **MySQL** for persistent data storage.

---

## 🚀 Features

* **Browse and Book Events**: View a list of available events and select the number of tickets you wish to purchase.
* **Integrated Payments**: A seamless checkout experience using **Razorpay Checkout** to handle payments.
* **Secure Backend**: The Spring Boot backend handles the entire payment lifecycle, from creating the order to verifying the payment signature for a secure transaction.
* **Confirmed Bookings**: All successful bookings are securely stored in a **MySQL** database.

---

## 🛠️ Tech Stack

* **Frontend**: React.js
* **Backend**: Spring Boot (Java)
* **Database**: MySQL
* **Payment Gateway**: Razorpay

---

## ⚙️ Setup Instructions

Follow these steps to get the application running on your local machine.

### 1️⃣ Backend (Spring Boot)

1.  Navigate to the `backend` directory.
    ```bash
    cd backend
    ```
2.  Run the application. It will automatically start the server on port `8080`.
    ```bash
    mvn spring-boot:run
    ```
3.  Configure your database and Razorpay keys. Open `src/main/resources/application.properties` and update the placeholders with your credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/eventdb
    spring.datasource.username=root
    spring.datasource.password=yourpassword

    razorpay.key_id=your_test_key
    razorpay.key_secret=your_test_secret
    ```

### 2️⃣ Frontend (React)

1.  Navigate to the `frontend` directory.
    ```bash
    cd frontend
    ```
2.  Install the required dependencies.
    ```bash
    npm install
    ```
3.  Start the development server. The frontend will be available on `http://localhost:3000`.
    ```bash
    npm start
    ```
    Make sure to update the API base URL in the frontend code to point to your Spring Boot backend (e.g., `http://localhost:8080`).

---

## 💳 Razorpay Payment Flow

1.  The user selects an event and the number of tickets.
2.  The frontend sends a booking request to the backend.
3.  The backend creates an **Order** using the Razorpay API.
4.  The frontend opens the Razorpay Checkout popup using the test keys.
5.  The user makes a payment using a test card or UPI option provided by Razorpay.
6.  Razorpay returns a success or failure response.
7.  The backend then verifies the payment's signature to ensure its authenticity.
8.  On a successful payment, the booking is confirmed and stored in the MySQL database.


---

## 📌 Future Enhancements

* **User Authentication**: Implement JWT-based authentication for user logins.
* **Email/SMS Confirmation**: Automatically send ticket confirmation via email or SMS.
* **Admin Panel**: Build a dedicated admin dashboard for event management.
* **Seat Selection**: Introduce a seat selection system for a more granular booking experience.
