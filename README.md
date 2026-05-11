
# LuckTab - TabPrefix für LuckPerms

Mit diesem Plugin kann man in der Tablist und um Chat den Prefix hinzufügen. Die Tablist wird sortiert dargestellt.


## SpigotMC

https://www.spigotmc.org/resources/lucktab.80192/
## Commands & Permissions

```bash
- Commands:
- /lucktabreload (lucktab.reload)

- Chat Formate:
- lucktab.chat.format.color
- lucktab.chat.format.magic
- lucktab.chat.format.bold
- lucktab.chat.format.strikethrough
- lucktab.chat.format.underline
- lucktab.chat.format.italic
```
## config.yml

```yaml
#     _               _    _____     _
#    | |   _   _  ___| | _|_   _|_ _| |__
#    | |  | | | |/ __| |/ / | |/ _` | '_ \
#    | |__| |_| | (__|   <  | | (_| | |_) |
#    |_____\__,_|\___|_|\_\ |_|\__,_|_.__/
#    by PattyXDHD

useChatFormat: true
useColorTranslate: true
# patchholder -> %chatPrefix%, %playerName%, %message%, %ping%, %displayName%, %group%
chatFormat: '%chatPrefix%%playerName% &8» &7%message%'
# update automatically the Tablist
autoUpdate: true
# format -> "group;tabPrefix;chatPrefix"
prefixes:
 - 'owner;&4Owner &7» &4;&4Owner &7- &4'
  - 'default;&7Player » ;&7Player - '
```
## Screenshots

![App Screenshot](https://www.spigotmc.org/attachments/lucktab_screenshot_1-png.524419/)<br>
![App Screenshot](https://www.spigotmc.org/attachments/lucktab_screenshot_2-png.524420/)<br>
![App Screenshot](https://www.spigotmc.org/attachments/lucktab_screenshot_3-png.524421/)<br>



## YouTube Video zum Plugin

https://www.youtube.com/watch?v=7YmF1HowyMs

![App Screenshot](https://img.youtube.com/vi/7YmF1HowyMs/maxresdefault.jpg)
