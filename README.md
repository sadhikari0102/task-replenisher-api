It is the backend of the Walmart Task Replenisher Application which can be deployed on any cloud platform and function as an independent api.

Currently, the backend itself is deployed at url : http://18.218.226.1:8080/

Below are the specifications which needs to be taken care of before the api functionalities are utilized:

1. Assuming that MySql server is already installed in the running system, start mysql service:
	
	mysql -u root -p password

2. Either change the sql creadentials to:
	
	username - root
	
	password - password
		
	OR

   Make changes in application.properties file inside path 'task-replenisher-api/src/main/resources' and update the mysql user credentials

3. Run following command on mysql prompt to create a database schema:
	
	mysql>CREATE DATABASE task_replenisher

4. Inside file application.properties(from step 2), change

	spring.jpa.hibernate.ddl-auto=update
		
	TO
	
	spring.jpa.hibernate.ddl-auto=create

5. Build the project by running 'mvn clean install' at the root folder and run the jar inside the target using:
	
	java -jar task-replenisher-api-0.0.1-SNAPSHOT.jar

6. This will create the tables in the database.

7. Run below command on sql prompt to load initial role data:
	
	insert into role values('ADMIN'),('BUSINESS'),('INDIVIDUAL');

8. Again, inside file application.properties, revert
	
	spring.jpa.hibernate.ddl-auto=create
	
	TO
	
	spring.jpa.hibernate.ddl-auto=update

9. Build and run again using step 5.

Once the jar has been executed, the service is up and running at http://localhost:8080/ and can be consumed using any REST client.


Below are the functionalities that can be achieved with the given api:

1. Create an admin user to start a new list replenisher. Admin will only be responsible for creation of user who'll actually use the system to create and manage tasks.

2. Admin can create either BUSINESS user or INDIVIDUAL contributors as the system users, assigning them username and passwords.

3. BUSINESS users or INDIVIDUAL contributors can login using their credentials to maintain tasks.

4. BUSINESS users can create tasks and assign it to self or other users(BUSINESS/INDIVIDUAL).

5. INDIVIDUAL users can create tasks and assign it to self only.

6. BUSINESS users/INDIVIDUAL contributors can update the task details including priority and status. Every time a status changes, STARTTIME and ENDTIME is updated accordingly.

7. BUSINESS users can view all the tasks currently inside the system in a sorted order of status and priority, while INDIVIDUAL users can only view the tasks assigned to them in a sorted order of status and oriority.

8. The recurring task functinality using task template is left for future development due to time constraint.

Below are the possible requests which can be made :
1. 
	Usage	:	Create admin user, who in turn is responsible for creating any system user.
	
	Request Type	:	POST
	
	URL	:	http://localhost:8080/admin
	
	RequestBody	:	{        
		"name":"admin",
		"password":"pass",
		"role":{"role":"ADMIN"}
	}
	
	Value constraints	:	role can only be ADMIN
	
	Authentication	:	N/A

2. 
	Usage	:	Create system users. This can only be done by existing admin, hence use the admin credentials in authorization.
	
	Request Type	:	POST
	
	URL	:	http://localhost:8080/authorized/users
	
	RequestBody	:	{
		"name":"business2",
		"password":"pass",
		"role":{"role":"BUSINESS"}
	}
	
	Value constraints	:	role can either be ADMIN, BUSINESS or INDIVIDUAL, and is mandatory
	
	Authentication	:	Basic Auth
	
				username : admin
				
				password : pass"
				
	Note	:	auth credentials will be same as the ones created in request 1

3. 
	Usage	:	Get list of admin and system users. To be run by admin, hence admin's credentials
	
	Request Type	:	GET
	
	URL	:	http://localhost:8080/authorized/users
	
	RequestBody	:	N/A
	
	Value constraints	:	
	
	Authentication	:	Basic Auth
	
				username : admin
				
				password : pass"
				
	Note	:	auth credentials will be same as the ones created in request 1

4. 
	Usage	:	Get tasks in the database. This can either be used by BUSINESS or INDIVIDUAL user, which are identified based on their respective credentials in the basic-authorization. BUSINESS USERS can access all the tasks in the system, while INDIVIDUAL users can only see the tasks assigned to them.
	
	Request Type	:	GET
	
	URL	:	http://localhost:8080/authorized/tasks/
	
	RequestBody	:	N/A
	
	Value constraints	:	
	
	Authentication	:	Basic Auth
	
				username : business2
				
				password : pass
				
	Note	:	Auth credentials of any BUSINESS or INDIVIDUAL user

5. 
	Usage	:	Create task in the database and assign it to an existing user (BUSINESS OR INDIVIDUAL). BUSINESS user can assign it to any system user, while INDIVIDUAL can only assign it to themselves.
	
	Request Type	:	POST
	
	URL	:	http://localhost:8080/authorized/tasks/
	
	RequestBody	:	"{
		"description": "dance",
		"status": "CREATED",
		"priority": 4
		"assignedTo": {
		            "name": "individual2"
		        }
		}
		
	Value constraints	:	
	Status can be either CREATED/ASSIGNED/STARTED/FINISHED. If the status is left empty, it is automatically saved as CREATED or ASSIGNED based on whether assignedTo is empty or not.
	
	Priority is mandatory and can range between 1 and 10.
					
	Description is mandatory
	
	Authentication	:	Basic Auth
	
				username : business1
				
				password : pass"
				
	Note	:	Auth credentials of any BUSINESS or INDIVIDUAL user

6. 
	Usage	:	Update task using specific task id in the url. Only the updated parameter needs to be supplied in the request body. If the status of the task changes, STARTTIME and ENDTIME are updated accordingly.
	
	Request Type	:	PUT
	
	URL	:	http://localhost:8080/authorized/tasks/1
	
	RequestBody	:	{        "status":"STARTED"
			}
			
	Value constraints	:	Status when changes to STARTED, STARTTIME becomes current time.
	
	Status when changes to FINISHED, ENDTIME becomes current time.
	
	Status when changes to CREATED/ASSIGNED, STARTTIME and ENDTIME become null.
					
	Authentication	:	Basic Auth
	
				username : business1
				
				password : pass
				
	Note	:	Auth credentials of any BUSINESS or INDIVIDUAL user



