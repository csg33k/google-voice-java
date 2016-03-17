# Setting Up a Development Enviorment and Contributing #

Are you looking to start contributing to the google-voice-java project?  Or perhaps just hack some custom changes in on your own?  Here are a couple tips to help you get set up.

## Mailing List ##
The easiest way to get in contact with the contributers is through the mailing list located here: http://groups.google.com/group/google-voice-java

## Setting Up a Development Environment ##

### Downloading the Source Code ###

There are instructions on how to download the source here:

http://code.google.com/p/google-voice-java/source/checkout

For a one liner run the following command from your shell:

```
svn checkout http://google-voice-java.googlecode.com/svn/trunk/ google-voice-java-read-only
```

### Building from Source ###

Currently the project is developed in Eclipse, using its automated build tools.  If you don't already have it, you will need a copy of Eclipse from:

http://www.eclipse.org/downloads/

The IDE for Java Developers is fine.  You don't need to worry about the other versions offered.

Once you have Eclipse installed, start the IDE and do the following:

**File Menu > New > Java Project**Check the create project from existing source radio, and navigate to the folder you downloaded earlier.
**Make sure you are using Java 1.6 or greater!  You may have compile errors with a lower version.**Click finish.

Click the round green run button.  This will build the source and execute it.  You may need to specify, run as "Java Application" or a startup class.  Choose test/test.java.

If everything has gone according to plan, you will see:

```
Could not read the testProps, falling back to input. (null)
Enter Your Google Voice Username, eg user@gmail.com:
```

You have built from source!

We're working on Eclipse independent build tools for the project such as Maven and Ant.  If you prefer a different coding environment than Eclipse, you can wait for these scripts or write your own and contribute them.

### Contributing ###

Remember back when we told you how to download the source...  Well, it turns out that is read-only.  If you want to contribute back to the project, you will need committer status on the project and a different url.  You need a google account (We hope you can figure this out on your own!).  Once you have an account, request commit permission from the current project maintainer.  If you aren't too shady, you get commit access.  Then when you visit:

http://code.google.com/p/google-voice-java/source/checkout

you will see a new url that looks like this:

```
# Project members authenticate over HTTPS to allow committing changes.
svn checkout https://google-voice-java.googlecode.com/svn/trunk/ google-voice-java --username user.name@gmail.com

When prompted, enter your generated googlecode.com password.
```

If you checkout from that, you will be able to push upstream.

Happy hacking!