var name = "test";
var pos1 = "";
var pos2 = "";
var imp = 0;

//the function below is REQUIRED!!!
function getName(){ //return the name of the plugin, doesn't actually have to be a variable could just be 'return "test"', it would have the same affect, I just use a variable to make it easier to change and if I use it anywhere else in the code
return name;
}

//the function below is REQUIRED!!!
function run(){ //this is the run function, this gets called when the bot loads the plugin, this means at start up and when the reload command is called **EXTREMELY IMPORTANT** DO NOT ever ever ever use any method here that can only work when the bot is logged in, this will cause stuff to go wrong and cause lots of problems, don't do it (an example of this would be calling c.chat.sendMessage(); from this, BAD!
c.gui.addText("§4woder's awesome test"); //add some text to the gui, the § symbol is how to tell the bot to color the text
c.gui.addText("§5lololoawdawd"); //same thing
imp = 0; //set a variable to 0, this was for something I was trying to do, but that thing isn't supported at the moment. For now its just a variable being set.
return true; //return true to tell the bot we started up properly
}

//the function below is REQUIRED!!!
function getListener(){ //this function tells the bot what listeners we will be using, basically lets say we want to know when a sign gets changed, you would put "onSignUpdate" here and then create a function lower in the code called onSignUpdate() with the parameters of that event (those are listed in the tutorial). To list multiple events just seperate them with a comma NO SPACE! just a comma like bellow
return "onSignUpdate,onBlockChange"; //tell the bot that we are registering onSignUpdate and onBlockChange
}

function onBlockChange(x,y,z,bid,meta){ //this function is an event, this will get called when the event fires, in this case when a block is changed.
 c.chat.sendMessage("plugin says: " + x + "," + y + "," + z + " and is now block id: " + bid + " meta: " + meta); //This will send a message showing the chat our parameters, note that you will probably want to remove this as it puts a message everytime a block changes
}

function onSignUpdate(x,y,z,l1,l2,l3,l4){ //this is once again an event, it will fire once a sign is placed or changed
 c.chat.sendMessage("Sign at " + x + "," + y + "," + z + " now says: " + l1 + ";" + l2 + ";" + l3 + ";" + l4); //This will send a message in the chat showcasing parameters
}

//this is a command, name this function the same name as what you called the command in getCommand
function pick(command){ //you can do pretty much anything in here, this should only be called in game so calling game methods should be fine
var picked = pickup(); //this calls our pickup method to randomly select a pickup line.
c.chat.sendMessage(picked); //this will then send the line to the chat
}

//this is once again a command, named the same as in the getCommand function
function importe(command){
c.chat.sendMessage("Place two dirt blocks to select the voxel"); //send the message, please note the whole voxel thing doesn't currently work.
imp = 1; //change that variable, the problem at the moment is that the code interpretor seems to forget all the variables you set, working on a fix to this
}

//the function below is REQUIRED!!!
function getCommand(){ //this is the function that registers the commands that your plugin will need. The format of these commands is nameofcommand;description,nameofcommand;description and so on, its important to note that there is no space between the delimiters (the commas and the semi colons) as placing a space there will cause errors
return "pick;a command to test,importe;used to import";
}

function pickup(){ //this is just a regular javascript function that is used by another part of the code
var pickup = new Array();
pickup[1] = "Is your name Google? Because you have everything I've been searching for.";
pickup[2] = "Isn't your e-mail address beautifulgirl@mydreams.com?";
pickup[3] = "Are you an angel, because your texture mapping is divine!";
var line = "";
var rando = Math.floor(Math.random() * 3) + 1
line = pickup[rando];
return line;
}