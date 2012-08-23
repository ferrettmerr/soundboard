Soundboard demo for TelTech.
==========

Make sure to have wireless on when testing the application so the phone has access to a data network.
4g and call works, 3g and call does not. This was the case with Verizon. I think AT&T can do 3g data and call.

The architecture of the application should have three major components
* Call Server

The call servers purpose is to transfer the call status to the mobile app via push notifications. This allows 
for a much nicer application with loading screens, ability to prevent user errors before they occur and 
access to features of the API's POST/GET callbacks.

* Android API wrapper around the TelAPI services. 

The API should mirror the python/ruby API's that currently exist.
The api should also provide a handler subclass to deal with callbacks from the Call Server. or errors
when calling.
examples:
	Bad to number
	call went to voicemail
	API not responding
	Call Server not responding

* Android App

Uses the Android API to connect to the TelAPI services and receives updates from the call server.

Comments:
LoginActivity:
The login should check to make sure the user has the capability to maintain a call and data connection at 
the same time. If the device cannot accomplish this, the application should warn the user and instruct them
on how to fix it.

SoundboardActivity
This activity comprises 90% of the application. Having MainActivity pass the form data
to the SoundboardActivity allows for a more decoupled application. This allows multiple entry points into
the SoundboardActivity easier then passing in a previously existing call and handler.

The sounds should be retreived from a server. I opted not to do it in this app as pulling data asyncronously 
and using handlers to update UI was used to make the API calls.

The data should be loaded through a HTTP call which means the data should come back using a handler. This 
was ommited since the data is hardcoded and a handler is used elsewhere in the application

Dream application:
The following are a list of features that I would love to implement in an application like this:

Landscape phones and tablets should show like the Novation Launchpad where each button is an image of the 
sound and a title. This grid view should be scrollable and each button rearrangeable by drag and drop.

User created and shareable custom boards where they can drag sounds from an organzied list/tab view that is
like the iOS facbook menu (Facebook app, main screen, hit menu in top left).

Be able to spoof the from number. Currently the from has to be set to the users device to pass the phone number
to the server for the call back.

Autoanswer phonecall:
There are ways to answer a phone call with root priveleges. Android 4.1 has recently locked down the auto answer
code. The easiest way to get around this is require root for android 4.1 and greater, and do it the old way < 4.1
