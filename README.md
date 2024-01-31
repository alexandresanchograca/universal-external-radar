# universal-external-radar

A simple java based 2D and 3D radar using libdgx. 
Made as an external companion application to single-player games. 

You can stream location data of objects to the application and render it in a 2D or 3D way and change the scale of which they are displayed.

Runs on any desktop platform and can also be easily adapted to also run in Android or iOS with the installation of the proper libgdx sdk's.

Comunication is done via sockets. The protocol used currently is TCP. 
This interprocess communication method allows you to transmit information across computers in the same or different networks.
Switch to UDP protocol if you want more performance, since I think this protocol should be the one used in this type of applications to have less delay in the information displayed.

