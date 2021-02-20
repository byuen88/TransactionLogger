# CPSC 210 Personal Project

## Transaction Logger


### What will the Application do?
The personal project that I have decided to create for this term is an application that allows you to log your **daily 
spendings** onto a Transaction Logger. 

Here are its features:
- It will keep track of how much money you spend in a day, the transaction's name and what the money was spent on 
(e.g. groceries, entertainment, etc).
- It will allow you to either add a transaction to the current date automatically or allow you to manually enter a 
specific date.
- It will make sure that the date you enter is a valid date, otherwise, the application will bring you back to the 
main menu.
- It will show you a pie chart of what transaction type your money was spent on.
- You will be able to set a monthly spending limit. If you are ever to spend more than this amount, a warning will 
pop-up.
- It will calculate how much you have spent in a year, a month, or a day.
- It will calculate how much you spend for each transaction type and show it in the form of numbers or in percentages.
- It will display all transactions you have made on a day.

### Who is this application for?
This application is suited for anybody who really wants keep themselves **organized**. Specifically, people who would 
like to keep their **finances** in check by logging their transactions all in one place so that they are more aware of 
how much they are spending. This application is also suited for people who are trying to budget. This application would
allow them to make sure they do not spend over a monthly transaction limit by giving out warnings whenever the limit is
exceeded.

### Why I chose this project?
I ultimately chose this project as I thought it would be incredibly useful to me as I often don't think about how much 
money I spend every month and as a result, I **overspend**. This application would keep me organized, and seeing how
much I spend each month would be a great reminder to be more mindful of how much money I have spending. I also chose to 
do this project because I believed I would learn a lot from coding it from scratch without any assistance.

## User Stories

- As a user, I want to be able to log every spending I make on the corresponding day that the transaction occurred and 
add it to the application. 
- As a user, I want to be able to either manually enter the date of the transaction or choose to add the transaction
to the current date.
- As a user, I want to be able to specify the name of the transaction and what kind of transaction it was (for example,
if the transaction is in the grocery category or if it was in the entertainment category).
- As a user, I want to be able to see how much money I have spent in a month, day, 
and the total amount of money I have spent so far in a year.
- As a user, I want to be able to set up a monthly spending limit.
- As a user, I want to be able to see how much money I have spent on each transaction type.
- As a user, I want to be able to save my transaction logger to file
- As a user, I want to be able to load my transaction logger from file
- As a user, when I select the quit option from the application menu, I want to be reminded to save my to-do list to 
file and have the option to do so or not.

## Phase 4: Task 2
I chose to make my Date class robust. I made the isValidDate method robust by making it throw an InvalidDateException
if the date passed in is invalid. 

## Phase 4: Task 3
Looking at my UML class design, it is clear my code possesses a significant amount of coupling. If I was given more
time to work on my project, these are the things that I would change:
- Create a new abstract Action superclass in my actions package. This class would represent an action and would contain 
the duplicated code in my current action classes. 
- Every class in the current actions package would then extend this new abstract class.

As the course progressed, I learned new things and new approaches to coding which can be applied to my design.
- Apply the Observer design pattern to my project to reduce the coupling.