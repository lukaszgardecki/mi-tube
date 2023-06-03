# MI Tube

<p align="center">
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-1.PNG" width="85%"/>
</p>

MI Tube is the website project dedicated to the movies.
Appearance is based on the [IMDb](https://www.imdb.com/) and the Polish-language site [Filmweb](https://www.filmweb.pl/).
The application was created for learning purposes. The main part of project was created during the [JavaStart](https://javastart.pl/) [Spring course](https://javastart.pl/kurs/spring) and then
was gradually developed.

## Technologies and libraries
Project is created with:
- [Java 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
- [Spring Boot 6](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Hibernate](https://hibernate.org/)
- [Maven](https://maven.apache.org/)
- [Thymeleaf](https://www.thymeleaf.org/)
- [Liquibase](https://www.liquibase.org/)
- [H2](https://www.h2database.com/html/main.html)
- [MySQL](https://www.mysql.com/)
- [BCrypt](https://en.wikipedia.org/wiki/Bcrypt)
- [Docker](https://www.docker.com/)

## Run with Docker
- [Install Docker Compose](https://docs.docker.com/compose/install/)

- Clone the project:
```bash
git clone https://github.com/lukaszgardecki/mi-tube.git
```
- Go to the project directory:
```bash
cd mi-tube
```
- Run all containers:
```bash
docker-compose up -d
```
- Please be patient. It may take a while.
- MI Tube is available at: `http://localhost:8080`
- You can log in to the account:
  <br>&nbsp; *(The list of created accounts is in the `src/main/resources/db/testdata/0004_users.sql` file.)*
```
ADMIN:
email: admin@example.com
password: adminpass

EDITOR:
email: editor@example.com
password: editorpass

USER:
email: user@example.com
password: userpass
```

## Features

### Rating system

MI Tube allows users to rate the movies on a scale of 1 to 10. Each logged in user can rate a movie only once and re-rating overwrites the previous one. All ratings are stored in a database.
<p align="center">
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-18.PNG" width="30%"/>
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-17.PNG" width="30%"/>
</p>


### Movie info

There's information about movies available to everyone. You do not have to be logged in to see it! Besides a title, description or a genre, the movie site shows a main rating, statistics, YouTube trailer and a poster image.
<p align="center">
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-7.PNG" width="40%"/>
</p>

### Top movies

You can display a list of 10 top rated movies. The most popular ones are at the top of the list. Also, if you want to know which movies are the highest-grossing, just click on the "Top Box Office" navigation button.
<p align="center">
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-10.PNG" width="20%"/>
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-19.PNG" width="50%"/>
</p>

### Browse movies by genre

You can browse movies by genre. Click on the "Genre" navigation button to see the list of the movies.

### Registration / Login

You can create an account through the registration form. Login and registration forms has the CRSF protection provided by Spring Security. Remember me cookie is valid for 30 days.
<p align="center">
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-12.PNG" width="45%"/>
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-13.PNG" width="45%"/>
</p>

### Password encryption using BCrypt

Passwords are stored in a database in an encrypted format using the BCrypt algorithm.
<p align="center">
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-16.PNG"/>
</p>

### User account management

Each user can change their personal data, i.e. first name, last name, email, password and an avatar. You have to confirm your current password if you want to change one. Otherwise, the password won't be changed.
<p align="center">
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-4.PNG" width="65%"/>
</p>

### Role-based authorization with Spring Security

There are 3 basic roles in MI Tube such as a user, editor and an admin. User is the role with the lowest level of access and allows to rate the movies. Editor can also rate the movies and he's granted access to content managing. Admin is the role with the highest level of access. He can manage account settings and has access to all resources.
<p align="center">
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-14.PNG" width="45%"/>
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-15.PNG" width="45%"/>
</p>

### Admin panel

Editors and Admins has access to "Admin Panel", that's the place where they can manage the movies and genres. They can add, edit and delete the movies and the genres at the current stage of application development.
<p align="center">
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-5.PNG" width="70%"/>
</p>
<p align="center">
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-6.PNG" width="45%"/>
  <img src="https://github.com/lukaszgardecki/screenshots/blob/main/mi-tube/mi-tube-8.PNG" width="45%"/>
</p>

## Status

Project is in progress with some active features and another improvements to make.


## To do

- Movie comments (admin can edit them)
- Movie browser
- Public user's account (rated movies, added comments etc.)
- Email account activation after registration
- Adding movies by all users (+editor/admin acceptation)
- Admin mode: users management
- Make the website more responsive
- Internationalization
- Unit tests