(This rough guide was written for version 1.3.  Much new functionality has been added to future versions.  We could definitely use some updated documentation for the new functionality if anyone wishes to volunteer.)




# Getting Started #

Google Voice Java is an API.  You must first have a Google Voice Account and know how to hack some Java for this to be useful.

So far our tests of this library have worked well.  To do a simple command line test, enter the following in your commandline:

```

     java -jar {path}/google-voice-java-{version}.jar

```

This simply logs in, displays your auth key, fetches contents of your inbox and sentbox, and displays the HTML.  It runs the test.jar script.  Add the jar to your classpath and access the Voice.class to do whatever you need.





Now you must create a Voice object

```

import com.techventus.server.voice.Voice;

...

      Voice voice = new Voice(userName, pass);

...

```

Now, for example, to view the Inbox, call the function:

```

       voice.getInbox();

```


This function, and the other similar functions return raw HTML or XML code which must be parsed by you.  We may add some kind of parser in the future.  Since this project is FOSS, feel free to contribute one.



## Placing Calls ##


To place a call, you simply pass the number from which you wish to call (originNumber) and the number being called (destinationNumber).  Google Voice only allows you to use an Origin Number which has already been registered with them as associated with that Google Voice Account.

We havent tried otherwise, but would recommend against using special characters in the phone numbers.  However, we have tested using '+' with International calling and it works just fine.

```
...

       String originNumber = "2125551234";
 
       String destinationNumber = "4155557895";

       voice.call(originNumber, destinationNumber);

...
```


When this code is run, the originNumber should ring (assuming the registration checks out).




## Sending SMS Text Messages ##


Sending an SMS is also trivial.

```
...

       voice.sendSMS(destinationNumber, txt);

...
```



## Logging In ##

When creating the Voice object, your login details are stored in memory and the Google Client Login token or "auth" key is obtained.  This token is passed back on all subsequent calls.  If your programme runs long enough, that token will expire and any further calls to the Voice object will either result in an exception, or HTML for the Google Voice login screen.

In this case you can simply renew the auth token by calling

```
...

       voice. login();

...
```

and everything _should_ work again.

Indeed, that function is called in the background when you first create the Voice object.

If something is off and you trigger a CAPTCHA from Google Voice, you will probably have to log back into the account manually.  This API is not equipped to handle the CAPTCHA request in any meaningful way at this time.

## Enable/Disable Phones to Receive Calls ##

To enable or disable a phone, you must determine the integer identification of that phone.  Upon creating a successful Voice object, a list of Phone objects is created and populated from the information in your Google Voice account, phoneList.  Each Phone object in the phoneList has the following protected variables

```

      	protected String id;
	protected String number;
	protected String formattedNumber;
	protected String type;
	protected String name;
	protected String carrier;
	protected Boolean verified;

```

perhaps the easist way to determine the id of the phone you wish to enable or disable is to search the name variable of Phones in the list and then call the id variable.

Once the id is determined, enable or disable with:

```
      voice. phoneEnable(int ID);

      voice. phoneDisable(int ID);

```

A String is returned with Google's result output of this action.


## Other Callable Functions ##

A complete list of available functions to check to pull recent info from the Google Voice Account.  Again, it is up to you to do the parsing.  All functions return a String or throw an IOException.


```

        getGeneral()

        getInbox()

        getStarred()

        getRecent()

        getSpam()

        getRecorded()

        getPlaced()

        getReceived()
        
        getMissed()

        getSMS()


```

## Where to get help ##

You can subscribe to our mailing list at http://groups.google.com/group/google-voice-java and post your questions there.