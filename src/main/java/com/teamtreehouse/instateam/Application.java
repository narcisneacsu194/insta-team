package com.teamtreehouse.instateam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
/*
* This is the class where the main method application resides.
* The main method deploys this spring application, using the static method run of the SpringApplication class.
* This project actually is an application for project development project management.
* You have roles, collaborators, and project entities.
* A project consists of a name, description, and displays the roles needed to be filled, and along those the collaborators assigned to these positions.
* A role in the project may or may not be filled by any collaborator.
* A collaborator can have only one role.
* A collaborator can work at multiple projects at once, and a role can be needed at multiple projects at once.
* You can add, edit, delete existing roles, collaborators, and projects.
* Technologies that I used:
* -Application Deployment: Spring Boot
* -Java ORM Framework: Hibernate
* -Database engine: H2
* -Front-end engine: Thymeleaf
* What I would change to this project ? Instead of H2 database engine, I would use a real world alternative like MySQL, PostgreSQL etc.
* The other thing I would change, is that instead of using Thymeleaf as my dynamic view, I would replace it with AngularJS,
* as it becomes more and more used in the real world projects.
* I would also want to integrate this application within a cloud computing service, like Microsoft Azure.
* One final thing to add would be that I used Tomcat DBCP (Database Connection Pool) for having a faster and more resource friendly
* connectivity to the database.
*/
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
