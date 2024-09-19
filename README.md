# 1. INTRODUCTION:
## 1.1. Motivation:
Online ordering food websites have revolutionized the food industry, providing customers with a convenient and hassle-free way to order their favorite meals. These websites allow customers to browse menus, select items, customize orders, and pay securely online from the comfort of their own homes or offices.

Although website creation is no longer a new topic, it is a hot topic to exploit new functions. In this thesis, I created a website that is both simple and effective and serves as a platform to practice and strengthen my skills and knowledge. The findings from this thesis also serve as a resource for further development in the field.

## 1.2. Problem Statement:
The problem  for this project is how to ensure security for users so that customers can safely place orders and pay online. Besides, choosing the necessary functions for development helps users who are not familiar with technology to place orders and manage orders easily.

## 1.3. Scope:
The scope of the website includes creating a fully functional and responsive website that enables customers to browse menu items, place orders, and make payments online. The website should also provide restaurant owners with an easy-to-use backend system for managing orders, updating menu items, and tracking sales.

# 2. LITERATURE REVIEW
## 2.1. Similar Application/System
The platform is referenced by the design and function of famous websites such as Grab Food, Gloria Food and Normal Website(Pizza Hut, The Pizza Company, …)


## 2.2. Platform and Tool Review
Programming language: HTML/ CSS/ Java Script/ SpringBoot / Java

Database management system: MySQL to export and import database

Tools: InteliJ

# 3. SYSTEM DESIGN
## 3.1. System Requirement Specification
### 3.1.1. Functional Requirements
•	Responsive design: The website should be optimized for use on all devices, including desktops, laptops, tablets, and smartphones, ensuring that customers can access the site from anywhere.
•	User-friendly interface: The website should have a clean, intuitive, and user-friendly interface that is easy to navigate and understand, making it simple for customers to place orders.
•	Online payment system: Customers should be able to make secure online payments through a reliable payment gateway, ensuring that transactions are fast, safe, and secure.
•	Customizable menu: The website should allow restaurants to customize their menu items and update them in real time, ensuring that customers always have access to the latest offerings.
•	Order tracking: The website should provide customers with real-time updates on their order status, including estimated delivery time and tracking information.
•	Integration with third-party services: The website should be able to integrate with third-party services, such as Google Maps by using Geolocation API, to provide accurate location information and directions to customers.
•	Data security: The website should provide robust security measures to ensure that customer data is protected from unauthorized access, data breaches, and other cyber threats.


### 3.1.2 Requirement Analysis
As a web application catering to the food industry, my platform serves three distinct user groups: customers, managers, and delivery persons. Each group has unique needs and requirements that my platform aims to fulfill.
For customers, the website provides a streamlined and convenient way to order food online. Customers can sign up, sign in, and browse the restaurant's menu, complete with prices, descriptions, and images. Once they've selected their desired items, customers can customize them as per their preferences and add them to their cart. When they're ready to pay, the web application accepts various payment methods. Throughout the process, customers can track their order status and receive notifications for updates. After receiving their order, customers can rate and review the restaurant and the food.
For Admin, our platform offers an efficient way to manage their restaurant's online presence. Admin can create an account, sign in, and add and edit menu items, prices, and descriptions. They can view the orders received and their status, allowing them to plan and coordinate their operations effectively. They can also update the order status and notify the customer of any changes, improving communication and customer satisfaction. Finally, managers can view customer ratings and reviews and respond to them, demonstrating their commitment to customer service and feedback.
For delivery persons, our platform facilitates smooth and timely deliveries. Delivery persons can create an account, sign in, and view the orders assigned to them and their details. They can update the order status "delivered," keeping the customer and the manager informed of their progress. By using our platform, delivery persons can optimize their routes, reduce delivery times, and improve their efficiency.

### 3.1.3 Non-functional Requirements
- Security
- Response time
- Throughput
- Scalability
- Availability
- Maintainability
- Reliability
- Portability

## 3.2 System Design Specification
### 3.2.1 Use-case Diagram
![image](https://github.com/user-attachments/assets/14fe139c-92ae-4d9f-acf2-1dd908ea6b45)
<p align="center"><b>Figure 3.1: Use-case Diagram</b></p>



### 3.2.2 Sequence Diagram
![image](https://github.com/user-attachments/assets/5b4d58c1-1113-49ac-b54f-220835772813)
<p align="center"><b>Figure 3.2: Sequence Diagram: Adding Food</b></p>

![image](https://github.com/user-attachments/assets/f14fddc2-036c-4532-afc1-60704c40b079)
<p align="center"><b>Figure 3.2: Sequence Diagram: Placing Order</b></p>

![image](https://github.com/user-attachments/assets/4d8e5315-677d-4aec-b964-7c0cc029a931)
<p align="center"><b>Figure 3.3: Sequence Diagram: Delivering Order</b></p>



### 3.2.3 Entity-Relationship Diagram
![image](https://github.com/user-attachments/assets/4c2b8721-8eea-4fb4-9bb9-4e978166ceca)
<p align="center"><b>Figure 3.4: Entity-Relationship Diagram</b></p>



### 3.2.4 Relational-Schema Diagram
![image](https://github.com/user-attachments/assets/c4eb359e-e336-475f-859b-375d591a8f2b)
<p align="center"><b>Figure 3.5: Relational-Schema Diagram</b></p>



# 4. SYSTEM IMPLEMENTATION
![image](https://github.com/user-attachments/assets/3a85b360-2640-4296-8972-8056dc153a7b)
<p align="center"><b>Figure 4.1: Login</b></p><br><br>

![image](https://github.com/user-attachments/assets/3a85b360-2640-4296-8972-8056dc153a7b)
<p align="center"><b>Figure 4.2: Register</b></p><br><br>

![image](https://github.com/user-attachments/assets/b121f1e7-718f-4d45-a96d-f73915e40fe1)
<p align="center"><b>Figure 4.3: Home-page</b></p><br><br>

When we click "Menu" button, it will go to "Food" section
![image](https://github.com/user-attachments/assets/d6e273fd-821b-4242-8a07-74e46c7c81f7)
<p align="center"><b>Figure 4.4: Home-page:"Menu section"</b></p><br><br>

![image](https://github.com/user-attachments/assets/b2b5790b-6317-492f-95ca-cbb6c818e20e)
<p align="center"><b>Figure 4.5: Check Out</b></p><br><br>

![image](https://github.com/user-attachments/assets/1e09c57d-e432-491a-ae45-ccc2a8b50bae)
<p align="center"><b>Figure 4.6: Place order</b></p><br><br>

![image](https://github.com/user-attachments/assets/fd2fa1c7-6bb5-4a50-9444-56ee55b2df72)
<p align="center"><b>Figure 4.7: Payment Online</b></p><br><br>

![image](https://github.com/user-attachments/assets/153de51a-e44d-4cf8-bb43-1f9fe4725f74)
<p align="center"><b>Figure 4.8: Admin Dashboard</b></p><br><br>

![image](https://github.com/user-attachments/assets/626d0712-27b2-4dc1-89ba-6b047f15d9f6)
<p align="center"><b>Figure 4.9: Admin Adding Food</b></p><br><br>

![image](https://github.com/user-attachments/assets/913067df-2c53-4dff-92d5-6da5bcd19b49)
<p align="center"><b>Figure 4.10: Admin Oder List</b></p><br><br>

![image](https://github.com/user-attachments/assets/dc1fb4ee-7242-4ac8-ae64-cb73dd23afce)
<p align="center"><b>Figure 4.11: DeliveryPerson Oder List</b></p><br><br>


# 5. CONCLUSION AND DISCUSSION
## 5.1 Strength and Weakness

	User interface:
•	Pros: Friendly interface with all elements clearly laid out so that users can easily identify, manage and manage application content and data.
•	Cons: Plain color background can be a bit boring for user experience who like something colorful.
	Strength
•	Multiple platforms:
•	Get customer's location: 
•	Food and Shipper Ratings: 
•	Food Management: 
•	Order Tracking: T
	Limitation
•	Limited payment methods: 
•	Delivery times are not estimated: 
•	No notifications about new orders: 

## 5.3 Future Work

1. Real-time Messaging Between Customers and Owners
2. Home Page and Dashboard Pagination
3. Order notification system: 
4. Calculate delivery time


