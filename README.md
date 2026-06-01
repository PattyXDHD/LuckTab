
# LuckTab - TabPrefix for LuckPerms

This plugin allows you to add a prefix to the tab list and in chat. The tab list is displayed in sorted order.


## SpigotMC

https://www.spigotmc.org/resources/lucktab.80192/
## Commands & Permissions

```bash
 Commands:
- /lucktab help (lucktab.admin)
- /lucktab reload (lucktab.admin)
- /lucktab resetall (lucktab.admin)

 Chat Formate:
- lucktab.chat.format.color
- lucktab.chat.format.magic
- lucktab.chat.format.bold
- lucktab.chat.format.strikethrough
- lucktab.chat.format.underline
- lucktab.chat.format.italic
```

## General Usage:

To assign a prefix to a group:<br>
/lp group default meta setprefix 100 "Your Prefix§e"<br>
-> the *100* is the weight of the Prefix. You can ignore that.<br>
To assign a chat-prefix to a group:<br>
/lp group default meta set chatprefix "Your Chat-Prefix§e"<br>
<br>
To assign a suffix to a group:<br>
/lp group default meta setsuffix 100 "Your Suffix"<br>
-> the *100* is the weight of the Suffix. You can ignore that.<br>
To assign a chat-suffix to a group:<br>
/lp group default meta set chatsuffix "Your Chat-Suffix"<br>
<br>
To sort the groups in the player list, you need to set the group weight:<br>
/lp group default setweight 300<br>
(300=onBottom, 200=inMiddle, 100=onTop)<br>
-> higher number = lower position<br>


## example commands:
```bash
/lp group default meta setprefix 100 "&7Player &7» &7"
/lp group default setweight 300

/lp group premium meta setprefix 100 "&6Premium &7» &6"
/lp group premiumsetweight 200

/lp group admin meta setprefix 100 "&4Admin &7» &4"
/lp group adminsetweight 100

/lp group admin meta set defaultchatcolor "&b&l"
```


## config.yml

```yaml
#     _               _    _____     _
#    | |   _   _  ___| | _|_   _|_ _| |__
#    | |  | | | |/ __| |/ / | |/ _` | '_ \
#    | |__| |_| | (__|   <  | | (_| | |_) |
#    |_____\__,_|\___|_|\_\ |_|\__,_|_.__/
#    by PattyXDHD

# usage of the ChatEvent (disable it if you use another ChatPlugin)
useChatFormat: true

# Color permissions below
useColorTranslate: true

# patchHolder -> %name%, %message%, %ping%, %displayName%, %group%
chatFormat: '%name% &8» &7%message%'

# If no prefix has been set for the chat, it simply uses the one from the tab list.
usePrefixFromTablist: true
# If no prefix has been set for the chat, it simply uses the one from the tab list.
useSuffixFromTablist: true

# update automatically the Tablist
autoUpdate: true
```
## Screenshots

![App Screenshot](https://www.spigotmc.org/attachments/cmd_9adqdnsjhe-png.963333/)<br>
![App Screenshot](https://www.spigotmc.org/attachments/javaw_jduzlwixxf-png.963334/)<br>
![App Screenshot](https://www.spigotmc.org/attachments/lucktab_screenshot_3-png.963335/)<br>
![App Screenshot](https://www.spigotmc.org/attachments/javaw_orpacn5zef-png.963336/)<br>



## YouTube Video zum Plugin

https://www.youtube.com/watch?v=7YmF1HowyMs

![App Screenshot](https://img.youtube.com/vi/7YmF1HowyMs/maxresdefault.jpg)
