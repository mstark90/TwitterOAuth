# Introduction
An example webapp I wrote quickly for a friend to demonstrate how OAuth works and how to handle it in a webapp.

It does not use the Spring Framework, instead using simple Servlets as to minimize the complexity, time to understand, and time to write the example. Some of the code might be shoddy, but if so likely wasn't the focus of this webapp.

# Usage
You will need to edit the src/main/java/com/michaelstark/twitteroauth/LoginServlet.java file and edit this little nugget here:

    builder.setOAuthConsumerKey("<consumer key goes here>")
               .setOAuthConsumerSecret("<consumer secret goes here>");

Here you will enter in your Twitter consumer key and consumer secret which will allow you to make requests against Twitter's APIs, including logging in via OAuth.

This webapp was designed to run under Apache Tomcat with a JDK of version 1.7 or higher. It is also a Maven project, so it should open without issue in most modern Java IDEs. It was built using NetBeans 8.2 on a machine running macOS Sierra, but is general enough where it shouldn't matter.

This project also uses Twitter4J when accessing Twitter APIs to prevent reinventing the wheel when accessing Twitter APIs using java.net.URLConnection instances everywhere.
