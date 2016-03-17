# Changelog #

New in version 1.4

> Version 1.4 contains many upgrades. Some breaking changes may exist from 1.3 so test your code thoroughly.

  * The Phone objects created at startup and placed in the phoneList now contain an enabled/disabled boolean parameter corresponding to whether the phones are set to ring or not.

  * Call Presentation can now be toggled on and off

  * New Record Objects and a parser have been added for things like Voicemail, Call, SMS, Transcript


New in version 1.3

  * Calls to Google are now "spoofed" to appear as though they originate from Google Chrome.  This was done because in developing Google Voice Locations for the Android platform, it was observed that Google would not properly deliver webpage content to the API if it originated from a mobile device.  Spoofing solved the problem.  We have thus hard-coded the user-agent property to Chrome to make the behaviour consistent from every device.