This is an early mock-up of the planned DiMe People Finder website.

It reads a local XDI database from file **graph.xdi** and displays it in the form of a simple people list.

### How to build

First, you need to build the main [XDI2](http://github.com/projectdanube/xdi2) project.

Just run

    mvn clean install jetty:run

Then the people list is available at

	http://localhost:7401/people

And the XDI interface is available at

	http://localhost:7401/xdi
