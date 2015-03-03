var name = "Generalcommands";

//the function below is REQUIRED!!!
function getName(){ //return the name of the plugin, doesn't actually have to be a variable could just be 'return "test"', it would have the same affect, I just use a variable to make it easier to change and if I use it anywhere else in the code
return name;
}

//the function below is REQUIRED!!!
function run(){ //this is the run function, this gets called when the bot loads the plugin, this means at start up and when the reload command is called **EXTREMELY IMPORTANT** DO NOT ever ever ever use any method here that can only work when the bot is logged in, this will cause stuff to go wrong and cause lots of problems, don't do it (an example of this would be calling c.chat.sendMessage(); from this, BAD!
c.gui.addText("§4General commands plugin reloaded"); //add some text to the gui, the § symbol is how to tell the bot to color the text
return true; //return true to tell the bot we started up properly
}

//the function below is REQUIRED!!!
function getListener(){ //this function tells the bot what listeners we will be using, basically lets say we want to know when a sign gets changed, you would put "onSignUpdate" here and then create a function lower in the code called onSignUpdate() with the parameters of that event (those are listed in the tutorial). To list multiple events just seperate them with a comma NO SPACE! just a comma like bellow
return ""; //dont register anything
}

//this is a command, name this function the same name as what you called the command in getCommand
function under(command){ //you can do pretty much anything in here, this should only be called in game so calling game methods should be fine
    b = c.whandle.getWorld().getBlock(c.location).getRelative(0, -1, 0);
    if (b != null) {
        c.chat.sendMessage("Block is: " + b.getTypeId() + " and its meta data is: " + b.getMetaData());
    } else {
        c.chat.sendMessage("Failed :(");
    }  
}

function respawn(command){ //WARNING: this does NOT work and WILL throw an error
    try {
        buf = ByteStreams.newDataOutput();
        Packet.writeVarInt(buf, 22);
        buf.writeByte(0);
        c.net.sendPacket(buf, c.out);
        c.chat.sendMessage("Respawned! YOU DERP FACE!!!!!!");
    } catch (e) {
        c.gui.addText("Error in plugin " + getName() + " " + e.message);
    }
}

//this is once again a command, named the same as in the getCommand function
function version(command){
c.chat.sendMessage(c.versioninfo); //send the message
}

function reload(command){
c.ploader.reloadPlugins();
}

//the function below is REQUIRED!!!
function getCommand(){ //this is the function that registers the commands that your plugin will need. The format of these commands is nameofcommand;description,nameofcommand;description and so on, its important to note that there is no space between the delimiters (the commas and the semi colons) as placing a space there will cause errors
c.perms.register(1, "under");
c.perms.register(0, "version");
c.perms.register(1, "reload");
return "under;Prints information about the block under the bot,version;Returns the version of the bot,reload;Reloads all plugins";
}