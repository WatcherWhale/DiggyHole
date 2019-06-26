# <img src="assets/Banner.png" alt="" height="150" />

## Installation
1. Install WorldEdit to your server
2. Download the `DiggyHole.jar` file and place it in 
your `plugins` folder.
3. Now start your server and DiggyHole will be fully installed.

## Configuration
When you first start your server you will notice that a new
folder is created in the `plugins` folder.
This folder has the name `DiggyHole`. In this folder you find
a file names `config.yml`. This file contains the following
configuration

```yaml
# config.yml
MinPlayers: 4
Countdown: 15
EffectTime: 500
WinPoints: 10
chance:
  diamondOffset: 2.0
  coal: 1.0
  iron: 1.5
  gold: 1.0
  emerald: 0.3
  redstone: 0.5
  lapis: 2.0
  magma: 0.3
```

* **MinPlayers**: The minimum amount of players
needed to start a game

* **CountDown**: The time (in seconds) players need 
to wait before the game starts when the 
minimum amount of players is reached.

* **EffectTime**: The time (in ticks `20 ticks = 1 second`)
a PotionEffect lasts.

* **WinPoints**: The amount of points a player needs to score
to win the game.

* **chance.diamondMultiplier**: This number increases the chance
diamonds occur. The chance that a diamond occurs is calculated
as follows 
`chance= WinPoints * (Players * multiplier + stabilizer)/(Volume of the arena)`

* **chance.***: The chance (in %) the specified ore occurs.

## Creating an Arena
1. Make a selection with WorldEdit of how big your playable
arena should be.
**Warning:** The arena gets a grass block top and barrier walls
around itself. This is not part of the selection.

2. Now perform the command `/dh create <arena name>`.
This will create an arena.

3. Now go to the place where your waiting area is and type 
`/dh set <arena name> lobby`.

4. Locate yourself where the arena is and type 
`/dh set <arena name> arena`

Now you created an arena.

## Commands & Permissions

| Command                             | Description                    | Permission         |
| ----------------------------------- | ------------------------------ | ------------------ |
| `/dh help`                          | Get a list of all commands.    | `diggyhole.player` |
| `/dh join <arena name>`             | Join the specified arena.      | `diggyhole.player` |
| `/dh leave`                         | Leave the game.                | `diggyhole.player` |
| `/dh create <arena name>`           | Create an arena.               | `diggyhole.admin`  |
| `/dh set <arena name> <lobby/arena>`| Set a special location.        | `diggyhole.admin`  |
| `/dh region <arena name>`           | Change the region of an arena. | `diggyhole.admin`  |
| `/dh reload`                        | Reload all the config files.   | `diggyhole.admin`  |
