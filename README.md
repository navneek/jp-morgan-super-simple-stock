# jp-morgan-super-simple-stock

# Requirements

1. The Global Beverage Corporation Exchange is a new stock market trading in drinks companies.
2. Provide the source code for an application that will:-
  a. Your company is building the object-oriented system to run that trading.
  b. You have been assigned to build part of the core object model for a limited phase 1

a. For a given stock,

1. Given any price as input, calculate the dividend yield
2. Given any price as input, calculate the P/E Ratio
3. Record a trade, with timestamp, quantity, buy or sell indicator and price
4. Calculate Volume Weighted Stock Price based on trades in past 5 minutes

b. Calculate the GBCE All Share Index using the geometric mean of the Volume Weighted Stock Price for all stocks

# Solution

The solutions is developed using Java, Spring, Maven

# The Source
The source code is mainly divided into three section

1. Model ( Represents all domain objects)
2. Service ( Represents all code business logic to solve the test )
3. Repository ( In memory database to store business objects )

The solution has a layored architecture and the loose coupling is achieved using Spring framework to make the whole application individually testable in parts

# The Test
There are test cases to test the business logic and assert the results against the expected acceptance criteria. 

They are written using Spring, jUnit and Mockito 




