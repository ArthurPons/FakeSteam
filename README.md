# FakeSteam
A video game store for a class about web application's architecture.

## TODO

- Add image upload in /inscription
- ~~Hash pwd~~
- Code webservices
- Primefaces (API JSF)

- ~~Finish DAOs~~

Thomas
	
	~~comment~~
	~~game~~
	~~user~~
	~~historic~~
	~~rating~~

Arthur

	~~gameisofgenre~~
	~~genre~~
	~~gameisonconsole~~
	~~console~~
	~~userowngame~~

### Namming conventions

#### Servlets

- Form submit : Form + Class.java
- DB interaction : DbForm + Class.java

#### View

- jsp : form + Class.jsp
- jsf : form + Class.xhtml

#### Bean

- Class.java

#### DAO

- Interface : Class + Dao.java
- Implementation : Class + Dao + impl.java

#### REST

- Class + Rest.java

#### URL

- Insertion in DB table : /add + class
- Deletion in DB table : /del + class
- Update in DB table : /udpdate + class
- REST urls : /rest/class/get/(null|idNumber)

#### Forms

Servlet declared in web.xmk and mapped to an URL
When URL is accessed, the JSF (xhtml) page configured in the servlet is displayed
When the form is submitted, it calls submit method in corresponding bean (comment form calls comment.submit())
submit() directly calls the DAO and inserts data in DB

## Technologies
The project will be written in java using the [Eclipse IDE](https://eclipse.org/). The application server will be [Wildfly](wildfly.org).

## Amazing Logo
![alt text](http://www.ponsarth.fr/FakeSteam.png "Logo Title Text 1")

## Useful info

Everything concerning the project : https://webapplis.utc.fr/sr03/

Git [cheat sheet](https://services.github.com/on-demand/downloads/github-git-cheat-sheet.pdf)

[Edmodo](https://www.edmodo.com/home)
