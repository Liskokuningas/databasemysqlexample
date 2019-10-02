How to get MySQL database connected to start.vaadin.com - project
=================================================================

This tutorial mainly concentrates on how to wire your web application to `MySQL` database so you can Create, Read, Update and Delete (CRUD) information in database via `Vaadin` web application. Idea is to show you how to connect a template downloaded from https://start.vaadin.com to a database so you can replicate it to your own needs. This is not about the best practices (for `Vaadin`, `Spring` or `MySQL`) but to get you started fast.

== 1. Get started with start.vaadin.com

Start by downloading a starter project from https://start.vaadin.com – page. For this example get the default project with `MasterDetail` – view. Default project also has the `DashBoard` – view. Feel free to add new views as you please but make sure there the `MasterDetail` – view is there. Download the project with a `name` and `Java Group ID` you want. In this example project name is `databasemysqlexample` and the `Java Group ID` is `com.example.mysql`. Using the same helps if you want to copy-paste code or entire class but it is also a good exercise trying to figure needed things by yourself. 
For the Technology Stack select `Spring Boot`. It is required for this tutorial. 

== 2. Open the downloaded project in your favorite IDE

Download, unzip and open the project in your favorite Integrated Development Environment (IDE). If you have troubles with IDE or setting up the environment check out quick start guide (by Marcus Hellberg) from https://vaadin.com/tutorials/vaadin-quick-start. It has also a list of Vaadin prerequisites (https://vaadin.com/tutorials/vaadin-quick-start#_prerequisites) needed.

There is also more detailed tutorials by Alejandro Duarte for three major IDEs: +
https://vaadin.com/tutorials/import-maven-project-intellij-idea +
https://vaadin.com/tutorials/import-maven-project-eclipse +
https://vaadin.com/tutorials/import-maven-project-netbeans

At this point you probably want to get your brand new `Vaadin` project online. Once the project is open in the selected IDE you can start it (since the nice people running https://start.vaadin.com have made the project ready-to-run). `Vaadin` application with `Spring Boot` can be run with `mvn spring-boot:run` – command or run as an Application. If you have `Maven` installed in the local computer just open the project folder root and type `mvn spring-boot:run`.

Alejandros tutorials (listed above) have quite extensive instructions on how to do this for each of the IDEs (check under title `Running Maven goals`). 

NOTE: Before checking Alejandros tutorials: In the tutorials `Maven goal` is `jetty:run`, but since we use `Spring Boot` the `Maven goal` is `spring-boot:run`, NOT `jetty:run`. Follow the instructions but replace `jetty:run` with `spring-boot:run` and you should be set for success.

If you run the application for the first time prepare to wait some time: `Vaadin` uses `npm` package system. Downside is that in the first run you have to download quite a lot of code from the internet (from `npm` repository). If you get bored during download you can have a look at why `Vaadin` uses `npm` (https://vaadin.com/blog/bower-and-npm-in-vaadin-14- and https://vaadin.com/blog/all-vaadin-components-are-now-available-on-npm ) 

Ok, now we assume that everything is downloaded and local web server has started with your brand new application. Console should show text lines something like this:
....
INFO 8152 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''

INFO 8152 --- [  restartedMain] c.e.m.application.spring.Application     : Started Application in 25.931 seconds (JVM running for 26.81)
....

That means that the `Tomcat` webserver is up and running and offering your application in the address http://localhost:8080. Open web browser and go to that address. You should see your application there. Have a look, surf around, click some lists items and buttons and see what happens.

== 3. Database manager and setting up the database

To wire `MySQL` to your application you need a `MySQL` database running locally or running online in cloud. `MySQL` is an `Oracle` product (https://www.mysql.com/). `MariaDB` is a free and open source drop-in replacement for `MySQL` (https://mariadb.org/). You can also get `MySQL` database from `Google` (https://cloud.google.com/sql/) or running as an add-on in `Heroku` (https://elements.heroku.com/search/addons?q=sql) just to mention a couple. If you need help with setting up a database server have a look at (for example) `MariaDB` instructions (https://mariadb.com/get-started-with-mariadb/).

Before going further you should have a `MySQL` (or corresponding database such as `MariaDB`) database server up and running. If running database locally you need a database manager such as `HeidiSQL` (https://www.heidisql.com/) or Oracle `MySQL Workbench` (https://www.mysql.com/products/workbench/). Using a command line for database management is just as fine.

== 4. Connecting Vaadin application to database

Ok, lets assume everything is in order and we can continue wiring database to our `Vaadin` application.
First you must enter the database address and credentials (`username` / `password`) for the database. *Don’t use `root` as a user*. This tutorial is not about the best practices but using `root` user for database access is just not right and should be done ever never. If you decide to ignore this warning and to do it anyways you can skip the steps where we create a new user and grant privileges to that user. Root already has enough privileges to do anything. 

Open your IDE with `Vaadin` project. Select the `application.properties` – file and open it. +
Add `MySQL` server address and credentials to `application.properties`:

....
spring.datasource.url = jdbc:mysql://localhost:3306/<database name>
spring.datasource.username = <username>
spring.datasource.password = <password>
....

In this example we use `dummydata` as a placeholder for everything: `database`, `username` and `password`. +
For the `sprint.datasource.url` we use the localhost with the port `3306`. This is the default port for `MySQL` (and `MariaDB`) but be aware that it can also be something else. This is the case especially if you use cloud based `MySQL`. The ending `/dummydata` in the address refers to the database name we are going to create.  

....
spring.datasource.url = jdbc:mysql://localhost:3306/dummydata
spring.datasource.username = dummydata
spring.datasource.password = dummydata
....

Next we need to make sure that the project contains `spring-boot-starter-jdbc` and `mysql-connector-java` dependencies. Here is where `Maven` kicks in. Add those dependencies to your `POM.XML` under the element tag `<dependencies>`. Once added you might need to update the project. This can be done in `Eclipse` by right clicking the project, selecting `Maven` and `Update project…`. Select your project and click `ok`. Wait for a second for the `Maven` to download dependencies and we are good to go. Usually this is done when you next time run the application but it usually does not hurt to do things up front.

Add dependencies to `POM.XML`:
....
<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>        
<dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
</dependency>
....

== 5. Preparing the database

If you already have the needed database, user (with privileges), table with columns and the data, you can skip any or all of the following steps. After this tutorial when you start creating your own new views with data, you only need the table with columns and to grant the privileges for that user to that table. There is a way to grant all rights to the user but I’m not going to show how since it is a bit dangerous if you ever go live with the application. Do it with your own risk. 

You need to be logged in to the Database manager (of your choice, command line or with GUI) as a `root` or similar `admin role` that has rights to create new users, databases and tables.

Log in to the `database manager` as `admin` or `root`. +
In the `database manager`, create a `new database`:

....
CREATE DATABASE dummydata;
....

In the `database manager`, create a new user with password:

....
CREATE USER 'dummydata'@'localhost' IDENTIFIED BY 'dummydata';
....

In the `database manager`, grant the user `dummyuser` all rights to database `dummydata`:

....
GRANT ALL PRIVILEGES ON dummydata.* TO 'dummydata'@'localhost';
....

In the `database manager`, create a table that contains fields for `Masterdata` view in application. `Masterdata` uses the `Employee` – class that can be found from the `Employee.java` – file. 

....
USE dummydata; CREATE TABLE IF NOT EXISTS employees (firstname VARCHAR(1000), lastname VARCHAR(1000), title VARCHAR(1000), email VARCHAR(1000), notes VARCHAR(1000) );
....

In the `database manager`, insert some employee data:

....
USE dummydata;
INSERT INTO employees VALUES ('Dev', 'Eloper', 'developer', 'dev.eloper@dontsendthis.org', 'Backend developer');
INSERT INTO employees VALUES ('Front', 'Ender', 'developer', 'front.ender@dontsendthis.org', 'Frontend developer');
INSERT INTO employees VALUES ('Wo', 'Manager', 'manager', 'wo.manager@dontsendthis.org', 'PO and team leader');
....

Steps below are optional and only to check that things work. +
In the `database manager`, logout from `admin` or `root` user. +
In the `database manager`, login as a `dummydata` user. +
In the `database manager`, select everything from the table `employees` in the `dummydata` database:

....
SELECT firstname, lastname, email, title FROM dummydata.employees
....

If you see a list of employees inserted in the step above, you are ready with the database.

== 6. Wiring the database to the application

So far we have been doing mostly configurations. Now we can start coding. First start the application with instructions given above (if you need to refresh your memory more extensively check the chapter `Open the downloaded project in your favorite IDE` in this tutorial). 

NOTE: You can use the command `mvn spring-boot:run` or `run as Application` (mainclass: `com.example.mysql.application.spring.Application` in this example). 


===== If you got a console error something like this:

....
***************************
APPLICATION FAILED TO START
***************************

Description:

The Tomcat connector configured to listen on port 8080 failed to start. The port may already be in use or the connector may be misconfigured.

Action:

Verify the connector's configuration, identify and stop any process that's listening on port 8080, or configure this application to listen on another port.
....

Don’t worry, all is not lost; that just means that some application is already using the `port 8080`. If you started your own application and did not remember to terminate it before re-starting the application that culprit was actually you. So, you need to find that `Console` tab that runs your application and terminate that one. In `Eclipse` look for a red box – icon that is hinted `Terminate`. There is also a button with a hint `Remove all terminated Launches` that deletes `Console` tabs that are not running anything. After pressing that one you can find your Console tab that runs actually runs the application. 

When application starts (hopefully) use a browser and open http://localhost:8080/masterdetail address. +
You should see a list of employees and their information details. At this moment list is still populated from code, not from the database.

Follow the steps to connect the view (more specifically the `Grid` element) to the database:

Open `MasterDetalView.java` - file in IDE: +
Locate `afterNavigation` - method. In this method the `grid` (called `employees`) gets populated by a list of employees. +
List of employees is currently fetched from the `BackendService` `service` object. 

....
@Override
public void afterNavigation(AfterNavigationEvent event) {
    employees.setItems(service.getEmployees());
}
....

Create a file called `EmployeeService.java` into the same package as `BackendService.java` - file. +
We will be using also the already existing class `Employee` (from `Employee.java` - file). +
Do the following steps:

1) Add the annonation `@Component` before `EmployeeService` - class declaration.

....
@Component
public class EmployeeService {
....

2) Add the JdbcTemplate - variable to the class. +
3) Add `@Autowired` annotation before variable declaration. +

....
@Autowired
private JdbcTemplate jdbcTemplate;
....

4) Add a function to get all employees from database:

....
public List<Employee> findAll() {
    try {
        return jdbcTemplate.query("SELECT firstname, lastname, email, title FROM employees", 
            (rs, rowNum) -> new Employee(rs.getString("firstname"), rs.getString("lastname"), 
                rs.getString("email"), rs.getString("title")));
    } catch (Exception e) {
        return new ArrayList<Employee>();	
    }
}
....

When the function `findAll` is called, it uses the `@Autowired` `jdbcTemplate` variable and executes a `SELECT` query. Query then returns the rows found and for each of them it creates an instance of the `Employee` - class. Those employees are added to a list and returned to the calling party. Most of the work is done by `JdbcTemplate`. You need to just make sure that the `Employee` - class has a constructor matching the info fetch with a query and read using the `rs.get<>` - methods. 

NOTE: `Rowmapper` (doing the employees list for you) has methods like `getString`, `getDouble`, `getInt` and `getDate` that reflect the data received from database.
For example if you have a datetime field in database, you probably better to use `getDate()` method instead of `getString()`.

Now go back to the `MasterDetalView.java` - file in IDE: +
Add a variable for the newly created `EmployeeService` - class. You can have a look how `BackendService` declaration is done. +
Don't forget the `@Autowired` annotiation.

....
@Autowired
private EmployeeService employeeService;
....

At this point you better check that the Application is still running and works fine (as before we started to add the code). Check instructions given earlier in this tutorial if you need help. +
If everything still works like before we can continue in IDE with the `MasterDetalView.java` – file. + 
Now we change the source for the list that populates the `Grid` element in the `MasterDetail`  - view.

Go to the `afterNavigation` - method (that we already had a look at) in `MaterDetailView.java` - file. 
Replace the call to `service.getEmployees()` - method with a call to the newly created `EmployeeService` instances `employeeService.findAll()` - method. You can either remove the line or comment it out adding // at the beginning of the line.

....
//employees.setItems(service.getEmployees()); 
employees.setItems(employeeService.findAll());
....

Now `employees` - grid should be populated with the data from database.

Refresh the browser and check that this is the case. The longer list should now be replaced with just the three employees we added to the database. 
Select a row from the list. `Form` - element next to the list should be populated automatically with the selected employee info. Now the employees list is connected to the database instead of hard coded list of employees. You can verify that by going to the `database manager` and adding some more lines with the `INSERT` – statement you already used. Those new lines should appear to the page after you refresh the window. 

NOTE: If the list did not change there is a couple of things you can try to fix it. Go back to the IDE and make sure you have saved all the modified files. Make sure (from 
the console view) that the server has re-started (it might take some time). If the application still shows old employees stop the server and restart the application.

== 7. Saving an employee information

Next we will create a code to insert and update employees. In this example they both work from the same `Save` - button. To know if the press is an update or insert we will check if the given emails address is already in the database we are going to update. If the email address is new then it is an insert. 

NOTE: Now you might think should there be a save and insert buttons or is the email address the correct key to use determining a new employee. You are probably having a better solution in your mind already and that is very good news for you. This tutorial is not about best practices but about how to get your database connected to you `Vaadin` example application. You can fix it to your liking later on.

We start by creating a method to save the employee to database. Add `saveEmployee` - method to `EmployeeService` - class. It should take `employee` as an argument and it returns an `int` value (the number of employees updated/inserted in database). 

....
public int saveEmployee(Employee employee) {
    List<Employee> employees = this.findByEmail(employee.getEmail());
        if ( employees.size() > 0 ) {
            return updateEmployee(employee);
        } else {
            return insertEmployee(employee);
        }
}
....

You might notice that the method `saveEmployee` is using new methods called `findByEmail`, `updateEmployee` and `insertEmployee`. You need to create those methods to `EmployeeService` - class. `findByEmail` method is used to check if that email is already in use in the database. It returns a list of employees with that email address. It should always be 0 or 1 but if you have already entered same email address many times to the database it can be more than 1. In that case all the rows with that email are going to get updated at the same time. If the email address is already in the database (list size is more than 0) we will call the `updateEmployee` - method. Otherwise we will insert a new employee by calling `insertEmployee` - method. 

....
public List<Employee> findByEmail(String email) {    	
    try {
        return jdbcTemplate.query("SELECT firstname, lastname, email, title FROM employees WHERE email = ?",
        new Object[]{email}, (rs, rowNum) -> new Employee(rs.getString("firstname"), rs.getString("lastname"), 
        rs.getString("email"), rs.getString("title")));
    } catch (Exception e) {
        return new ArrayList<Employee>();	
    }    	
}
....

....
private int insertEmployee(Employee employee) {
    try {
        return jdbcTemplate.update("INSERT INTO employees VALUES (?, ?, ?, ?, ?)",
        employee.getFirstname(), employee.getLastname(), employee.getTitle(), employee.getEmail(), "");
    } catch (Exception e) {
        return 0;
    }
}
....

....
private int updateEmployee(Employee employee) {
    try {
        return jdbcTemplate.update("UPDATE employees SET lastname = ?, firstname = ? WHERE email = ?",
         employee.getLastname(), employee.getFirstname(), employee.getEmail());
    } catch (Exception e) {
        return 0;
    }
}
....

Now that we have setup the `EmployeeService` - class we need to connect the user interface (the `Save` - button) to work. Open the `MasterDetailView.java` - file (with `MasterDetailView` - class). In the constructor of that class you can find a line of code that is run when the `Save` button is pressed. In the example it is something like this:

....
save.addClickListener(e -> {
    Notification.show("Not implemented");
});
....

NOTE: A constructor is a special method in the class that is run when an instance of that class is created. If you are not sure what a constructor is have a look from the internet or from a book. 

This code means that when the `Save` button is clicked it runs the code `Notification.show("Not implemented");`. We are going to replace showing of the notification with actually saving (or inserting) the employees information to the database. Like this:

....
save.addClickListener(e -> {
    Employee employee = binder.getBean();
    if ( employeeService.saveEmployee(employee) > 0) {
        employees.setItems(employeeService.findAll());
    } else {
        Notification.show("Save error");
    }
});
....

This block of code uses the `binder` to get the employees information that is currently shown in the form with employee information (not the list, but the fields on the right side). Then it calls the `saveEmployee` - method of the `EmployeeService` – class to save the employee. If the `saveEmployee` - method returns a value that is more than 0 we update the list of employees with `setItem` command. Otherwise a notification with a text `Save error` is shown to the user.

There is one more step you need to do: find the `populateForm` - method in the `MasterDetailView` - class. +
Check that it exists and if yes change the following line of code:

....
binder.readBean(value);    	
....
to
....
binder.setBean(value); 
....

This is an important step and without the `binder` being set using `setBean` method we are not able to get values from the `binder` with `binder.getBean()` command when pressing the `Save` - button. Find out the difference between these commands by yourself. 

Now we should be all clear to test the application. Remember to save every file in the IDE and run the application. When the application is started go to the `MasterDetail` page, select an employee from the list, change the first and the last name of that employee and press the `Save` - button. If everything went well the input fields should be cleared and the name of that employee should change while the email remains the same. 

Now take another line from the list and change the names AND the email address (but use a completely new email address that is not already in the list). Then press `Save` -  button. A new line should appear the list. Now have a play with the list, select a line and change the email address to something that already is on the list but for another person.

What happens when you change some else email address to match that one? You should see that the logic is not there for the user. Try to figure out why those things happens and how could you prevent that. Also selecting an employee from the list and then pressing `Clear` button empties the information of that employee from the list. It does not delete the employee or save the cleared data but not quite the outcome the end user would expect.

== 8. Adding an employee information

You might have noticed already that if you press the `Clear` - button and then try to enter completely new employee information this does not work. Same thing happens when reloading the page and start inserting employee info without first selecting an employee. Why is that?

So, `Vaadin` helps you out with using the binders. You bind the employee information input fields to that particular employee. You can return the information from all the fields with a simple `binder.getBean` command instead of having to ask the value from each and every field separately. The application binds the employee to input fields when you select it from the list. This is done in the constructor of the `MasterDetailView` - class with the following line:

....
employees.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));
....

It means that when a single line from the list is selected we call the `populateForm` – method. If you want to know what happens in more detail have a look at the `populateForm` – method again. From there you can find the added `binder.setBean(value);` line of code. This binds the selected employee information to the fields.

First we will handle the problem that when you refresh the page (without selecting an employee from the list), fill the employee information to the empty fields and press `save`, nothing happens. The root cause for this is that the input fields are not bound by the binder to any employee. When you press the `Save` - button and the application runs the code `Employee employee = binder.getBean();` in the click listener of the `Save` - button. Because `binder` is not set with `setBean` command it returns a `null`. +
`Null` is then passed to `employeeService.saveEmployee` command as a parameter. That `null` is then used in the `saveEmployee` – method with `employee.getEmail()` but since `null` does not have a method `getEmail` it throws a `null exception` and the code execution is stopped. You can check this from the console of your IDE or where ever you log out the error messages from the application. 

NOTE: If you don't know what does `null` mean take a break from this tutorial and learn about it before continuing. 

We can handle the not existing binding in many ways; one would be to check if the employee bean received from the binder is `null` and create a new employee bean that we pass along. This would require us to get all the information from the input fields. Now we have only a couple but if there would be tens of input fields this might be a lot of work. 

Instead we will set the binder with an empty employee bean straight from the start. This empty bean is then replaced be a new one if an employee is selected from the list.
Find the constructor of the `MasterDetailView` – class (we have discussed this earlier already). Find the line of code `binder.bindInstanceFields(this);` and add the following line of code after that:

....
binder.setBean(new Employee());
....

This line of code binds a newly created employee to the input fields. If you want to initialize the default employee with values you can use the `Employee` - class constructor that takes first name, last name, email address and title as a parameter. Title has not been used during this tutorial at all but at this case you need to provide that also. As an exercise you can add a new constructor to `Employee` class without the title. If you decide to use the constructor with initial values as names and email address after you refresh the the browser you can see the given values already in the input fields.

Now if you run the application, refresh the browser, enter last name, first name and email address and press `Save` - button an employee with given information is created. Of course if you enter already existing email address then it is not inserted but updated. But you already knew this from before. 

Now we take a look at the second problem with inserting new employees. If you select an employee from the list and press save or clear the input fields are cleared. If you now try to add a new employee information and press the `save` - button you get the same `null pointer exception`. Why is that? 

This is because when you hit `Clear` - button it calls the code `employees.asSingleSelect().clear()` that clears the selections from the employees list. When that happens a line of code `populateForm(event.getValue())` gets called. Since the selected value is cleared by the first command `populateForm` method gets a parameter `null` (since nothing is selected). In `populateForm` method the code `binder.setBean(value);` sets binder to value of `null`. This is the same situation as we used to have when refreshing the page and starting to add an employee without first clicking a line in the list. Setting the binder to `null` happens also when you hit `Save` - button. The chain of commands is a bit longer but the end result is the same as with the `Clear` - button. 

We can prevent this in many ways. Again this tutorial is not about best practices but to show you how to get started fast. With this in mind we are going to use the following solution; since both our problems (pressing the `clear` - button and the `save` - button) lead us to the `populateForm` - method we will place the fix there. When the method is run we’ll check if the parameter `Employee value` is `null`. If it is not `null` we continue as before. If it is `null` then we will create an employee and bind that one. Basically the same thing we did before in the constructor. Add the following code to the `populateForm` method:

....
if ( value == null ) {
    value = new Employee();
}
....

Remember to add it before the code line:
....
binder.setBean(value);
....

Now the binder should always have an employee bean bound to it and those `null pointer exceptions` should end. This means that you can insert new employees to empty fields. That problem with employee info getting updated if the email address is already in use is still there but that is another story.

== 9. Removing an employee

CRUD stands for Create, Read, Update and Delete. We already have Create (insert), Read (select) and Update (update) but we are missing Delete. This requires us the place a new button to the user interface and creating a couple of methods to delete something from the database.

First we start with the needed methods. Since we have `saveEmployee` - method we could also have `deleteEmployee` - method. Open the `EmployeeService` - class and add the following method:

....
public int deleteEmployee(Employee employee) {
    try {
        return jdbcTemplate.update("DELETE FROM employees WHERE email = ?", employee.getEmail());
    } catch (Exception e) {
        return 0;
    }
}
....

This deletes from database all the lines that have the same email address as the given employee. This means that if you have several employees with the same email address they are all deleted. Sounds dangerous but as you remember we agreed to use email as the key and there should be only one employee with the same email address in the database.

Now we have to place the delete button to the user interface. The correct place for the delete button can cause discussion among developers, designers and users. At this point we just place the button to the UI (user interface) and do not discuss the placing in any depth.

Delete button should appear after the input fields in the right side of the screen. Same area where `Clean` and `Save` - buttons are. First add the button declaration as a variable to the `MasterDetailView` – class (the same way as `save` and `clear` buttons are done).

....
private Button delete = new Button("Delete");
....

Open the `MasterDetailView` - class and locate the `createButtonLayout` - method. We will use theming to show the button that it is used to remove something. Add a line of code to the `createButtonLayout`:

....
delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
....

Logical place for the code is before or after `cancel.addThemeVariants` and `save.addThemeVariants` commands. Then add the button to form by adding the button to the parameters of the `buttonLayout.add` command:

....
buttonLayout.add(delete, cancel, save);
....

The order of the buttons in the parameters determinates the order of the buttons in the UI. Placing `delete` as the first parameter also renders it as the first button from the left. When you run the application the `Delete` - button should be shown next to the `Cancel` and `save` - buttons.

Pressing the `delete` - button does currently nothing. We need to add an event listener to it. We also need to add the code that is run when the `click event` happens. Do this by locating the constructor of the `MasterDetailView` - class. Add the following code in to the constructor. Logical place is near where `save` and `cancel` - buttons event listeners are defined. You already made modifications to the `save` - button event listener.

....
delete.addClickListener(e -> {
    Employee employee = binder.getBean();
    if ( employeeService.deleteEmployee(employee) > 0) {
        employees.setItems(employeeService.findAll());
    } else {
        Notification.show("Delete error");
    }				
});
....

When the `Delete` - button is clicked we get the employee that is currently bound to the input fields and pass it as a parameter to `deleteEmployee`- method. We already coded that method and know that it takes the email address of the employee and removes all employees with that email address from the database.

If you run the applications you can select an employee from the list and removed it (from the database and following from the list) by pressing the `Delete` button. If there is nothing selected or given email address is not found you should see a small notification (at the lower right corner of the screen) that says `Delete error`. 

Now have a go with your application. Remove all the employees from the list, add couple new ones, modify existing employees and so on. Once you have removed all the employees it should not be possible to add any more employees with duplicate email address. 

== 10. What to do next?

Adding the CRUD operations to a `Vaadin` application allows you to continue further down the rabbit hole of application development. There are still a couple of things you can do here like figuring out how to make sure the user won't accidentally try to add a new email address that is already in use or how to prevent the user to change the email address of an existing  employee. We also have `title` and `password` field that are not in use. I’m not going to help you with those ideas here but hopefully you now have an idea how to continue. 

Thank you for reading this short tutorial. I warmly welcome you to the world of application development using `Vaadin`!

== 11. Troubles ahead? 

Something things just don't work as planned. If you don't see the list of employees in http://localhost:8080/masterdetail you better double check everything.

If the list is empty, but everything works fine otherwise there is probably something wrong with the database connection. We created a try-catch for the database operation, so you can either debug the code or remove the try-catch. In this case having things in try-catch prevents the possible error from being shown to the user. Removing the try-catch shows the exception on the browser window. This is not the optimal solution but if you are having troubles with debugging you can go with this option.

If you have a problem with `SQL` query, you will probably see something like:

....
There was an exception while trying to navigate to 'masterdetail' with the exception message 'StatementCallback; bad SQL grammar [SELECT firstname, lastname, email, title FROM employeess]; nested exception is java.sql.SQLSyntaxErrorException: Table 'dummydata.employeess' doesn't exist'
....
or
....
There was an exception while trying to navigate to 'masterdetail' with the exception message 'StatementCallback; bad SQL grammar [SELECT firstname, last_name, email, title FROM employees]; nested exception is java.sql.SQLSyntaxErrorException: Unknown column 'last_name' in 'field list''
....

Fix is to check the query text to match the table and column names you created in the beginning.

If there is a problem with `application.properties` definitions you might get an exception:
....
There was an exception while trying to navigate to 'masterdetail' with the exception message 'Failed to obtain JDBC Connection; nested exception is java.sql.SQLSyntaxErrorException: Unknown database 'dummydata2''
....

Problem is a wrong database name (is `dummydata2`, should be `dummydata`). Check the name of database you have created (or have already) and see that they match.

Another one could be:
....
There was an exception while trying to navigate to 'masterdetail' with the exception message 'Failed to obtain JDBC Connection; nested exception is java.sql.SQLException: Access denied for user 'dummydata2'@'localhost' (using password: YES)'
....

This can be caused by wrong username or password. +
It can also be a case when the user does not have the correct privileges.

In all the cases double check the steps and your code.  
