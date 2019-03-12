# Website Tests
This is a Maven Java project, so you will need to install it to your computer https://maven.apache.org/install.html 

e2e tests written in  [Selenide](https://selenide.org/) framework

# Test cases
You can find test cases in [Google Doc](https://docs.google.com/spreadsheets/d/1O98qGYwbqhGLZoUuARdoLkbdouyQekho_NmzuKQIjOc/edit?usp=sharing)

## Run tests
 In terminal (or CI) run from root directory of this project:
> mvn test

if you want to run your tests in other browsers you can use
>mvn test -Dselenide.browser=opera

> Important Note:
> There is a known issue with MainStatsPageTest.checkConsoleErrorsWarnings test which is failing in Firefox which still don't support console errors https://github.com/mozilla/geckodriver/issues/284 and https://github.com/SeleniumHQ/selenium/issues/1161

Also you can try to use headless mode for Chrome like
>mvn test -Dselenide.browser=chrome -Dselenide.headless=true

> Important Note: Some stats related tests failing in headleses mode cause they have some protection against it. Browser size, accepting insecure certificates or user agent change didn't help out  

or connect to grid
>mvn test Dselenide.remote=http://localhost:5678/wd/hub

## Test Flows
you can find test flows image in root directory `TestFlows.png`file

## Found bugs
you can find bugs in [Google Doc](https://docs.google.com/spreadsheets/d/1O98qGYwbqhGLZoUuARdoLkbdouyQekho_NmzuKQIjOc/edit?pli=1#gid=1524612191)