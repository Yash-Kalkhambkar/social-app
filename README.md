# SocialApp - Full Stack Social Media Platform

A complete social media web application developed as part of my internship project. This application demonstrates full-stack development skills using modern Java frameworks and web technologies, featuring user authentication, social interactions, and a responsive user interface.

## Overview

SocialApp is a comprehensive social media platform that allows users to connect, share content, and interact with each other's posts. The application showcases key concepts in web development including secure authentication, RESTful API design, database management, and frontend-backend integration.

## Key Features

### 🔐 User Management
- **Secure Registration & Login**: JWT-based authentication system
- **Profile Customization**: Users can update their bio and profile pictures
- **Session Management**: Automatic token handling and secure logout

### 📝 Content Creation
- **Post Creation**: Support for both text and image posts
- **Tagging System**: Organize content with custom tags
- **Rich Text Support**: Enhanced posting experience with formatting options

### 💬 Social Interactions
- **Like System**: Express appreciation for posts
- **Comment Feature**: Engage in discussions on posts
- **Real-time Updates**: Dynamic content loading without page refresh

### 🎨 User Experience
- **Responsive Design**: Works seamlessly across desktop and mobile devices
- **Clean Interface**: Intuitive and user-friendly design
- **Fast Performance**: Optimized loading and smooth interactions

## Technical Stack

### Backend Technologies
- **Java 17**: Latest LTS version for robust backend development
- **Spring Boot 3.x**: Rapid application development framework
- **Spring Security**: Comprehensive security framework
- **Spring Data JPA**: Simplified database operations
- **JWT Authentication**: Stateless authentication mechanism
- **H2 Database**: In-memory database for development and testing

### Frontend Technologies
- **HTML5**: Modern markup for semantic structure
- **CSS3**: Advanced styling with flexbox and grid layouts
- **Vanilla JavaScript**: Pure JavaScript for dynamic functionality
- **Responsive Design**: Mobile-first approach

## Installation & Setup

### Prerequisites
Make sure you have the following installed:
- Java 17 or higher
- Maven 3.6+
- Git

### Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/Yash-Kalkhambkar/social-app.git
   cd social-app
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Main application: `http://localhost:8080`
   - H2 Database console: `http://localhost:8080/h2-console`

### Database Configuration

The application uses H2 in-memory database with the following default settings:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
```

## API Documentation

### Authentication Endpoints
- `POST /api/auth/register` - Create a new user account
- `POST /api/auth/login` - Authenticate user and receive JWT token

### Post Management
- `GET /api/posts` - Retrieve all posts
- `POST /api/posts` - Create a new post (requires authentication)
- `POST /api/posts/{id}/like` - Like/unlike a post (requires authentication)

### Comment System
- `GET /api/posts/{id}/comments` - Get all comments for a specific post
- `POST /api/posts/{id}/comment` - Add a comment to a post (requires authentication)

### User Profile
- `GET /api/users/profile` - Get current user's profile information
- `PUT /api/users/profile` - Update user profile (requires authentication)

## Project Structure

```
src/
├── main/
│   ├── java/com/socialapp/
│   │   ├── controller/     # REST API controllers
│   │   ├── model/          # Entity classes
│   │   ├── repository/     # Data access layer
│   │   ├── service/        # Business logic
│   │   ├── security/       # JWT and security configuration
│   │   └── SocialAppApplication.java
│   └── resources/
│       ├── static/         # Frontend assets (HTML, CSS, JS)
│       └── application.properties
```

## Learning Outcomes

Through this project, I gained hands-on experience with:
- **Full-stack Development**: Integrating frontend and backend technologies
- **Spring Framework**: Understanding dependency injection and MVC architecture
- **Security Implementation**: JWT authentication and authorization
- **Database Design**: Entity relationships and JPA operations
- **RESTful API Design**: Creating clean and maintainable endpoints
- **Frontend Development**: Responsive design and JavaScript DOM manipulation

## Future Enhancements

Potential improvements and features to implement:
- [ ] Real-time notifications
- [ ] Image upload functionality
- [ ] User following system
- [ ] Advanced search and filtering
- [ ] Email verification
- [ ] Password reset functionality
- [ ] Dark mode support

## Contributing

This project is open for contributions and suggestions. Feel free to:
1. Fork the repository
2. Create a feature branch
3. Submit a pull request with detailed description

## Acknowledgments

This project was developed during my internship to demonstrate practical application of full-stack development concepts. Special thanks to my mentors and the open-source community for their valuable resources and guidance.

---

**Developed by:** Yash Kalkhambkar  
**Institution:** MIT ADT University  
**Program:** Second Year, Computer Science & Engineering  
**LinkedIn:** [Connect with me](https://linkedin.com/in/yash-kalkhambkar)

**Repository:** [https://github.com/Yash-Kalkhambkar/social-app.git](https://github.com/Yash-Kalkhambkar/social-app.git)
