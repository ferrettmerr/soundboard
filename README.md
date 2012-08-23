# Soundboard demo for TelTech.

The phone running the application must have phone and data capablities at the same time:
* Wifi + Call
* 4G + Call
* AT&T may be able to transfer data and call at the same time (3G + Call).
* Verizon must use 4G + Call or Wifi + Call.

## Architecture
The following subsections describe the overall architecture of the soundboard application

### Call Server

The Call Server's purpose is to transfer the call status to the mobile app via push notifications 
and to connect the initial call back to the current phone. This allows for a much nicer application
with loading screen and access to features of the API's Web callbacks.

### Android API Wrapper Around the TelAPI Services 

The API should mirror the python/ruby API's that currently exists.
The API should also provide a handler subclass to deal with callbacks from the Call Server and errors
that may occur when calling.
examples:
* Bad to number
* call went to voicemail
* API not responding
* Call Server not responding

### Android App

Uses the Android API to connect to the TelAPI services and receives updates from the call server.

## Comments
### LoginActivity
The login should check to make sure the user has the capability to maintain a call and data connection at 
the same time. If the device cannot accomplish this, the application should warn the user and instruct them
on how to fix it.

### SoundboardActivity
This activity comprises most of the application. Having MainActivity pass the form data to the 
SoundboardActivity allows for a better decoupling of activities. This makes multiple entry points into
the SoundboardActivity easier then passing in a previously existing call and handler implementation.

The sounds should be retreived from a server. I opted not to do it in this app as pulling data asyncronously 
and using handlers to update the UI was used to make the API calls.


## Dream Application
The following are a list of features that I would love to implement in an application like this:

Landscape phones and tablets should show the sounds like the Novation Launchpad where each button 
is an image of the sound and a title. This grid view should be scrollable and each button rearrangeable 
by drag and drop.

User created and shareable custom boards where they can drag sounds from an organzied list/tab view that is
like the iOS facbook menu (Facebook app, main screen, hit menu in top left).

Be able to spoof the from number. Currently the from has to be set to the users device to pass the phone number
to the server for the call back.

Autoanswer phonecall:

There are ways to answer a phone call with root priveleges. Android 4.1 has recently locked down the auto answer
code. The easiest way to get around this is require root for android 4.1 and greater, and do it the old way < 4.1

Techy sidenote: The current way around this is to spoof the application by sending intents impersonating a headset.
Android 4.1 locked down the ability to "press" the physical headset button via intents.
