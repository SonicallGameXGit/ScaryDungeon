# ScaryDungeon - THE FIRST VERSION OF THE GAME
ScaryDungeon - 2D Platformer game, created in Java with LWJGL3 library
# 
## Installation (Versions: 0.0.01pre-alpha - 0.0.02pre-alpha)
  - Download zip of game in branch, named with game version
![How to download zip of game tutorial](/assets/images/readme/downloadziptutorial.png)
  - Unpack the zip content to folder, where game will be stored
![How to unpack zip tutorial](/assets/images/readme/unpackziptutorial.png)
  - Go to folder `\Build\`
![How to find folder with game](/assets/images/readme/findfolderwithgametutorial.png)
  - Run ScaryDungeon.jar file (Requires Java 17)
![How to run game](/assets/images/readme/gamefileruntutorial.png)
# 
## Controlls:
    - D / Right - Run right
    - A / Left - Run left
    - W / Space / Up - Jump

## Details:
###   Player
     - Player smooth run start & end
     - Dynamic speed of player run animation
     - Player added
###   Blocks
     - Brick block added
     - Ghost Skull block added
###   Visual
     - Camera smooth rotation
###   Sound
     - 3D sound system added
     - Added effect rack for sound source
     - Added effects [Distortion, Reverb]
     - Added music [Perfect Count]
###   Graphics:
     - Render in 3D
     - Fog added
###   Locations:
     - Shaders [assets/shaders]
     - Textures [assets/textures]
     - Sounds [assets/sounds]
###   Developer:
     - One developer working on it (Alex Ambartsumov / SonicallGameX)
     - Added shader refresh with F5 key
###   Core
     - Engine created with [Shaders, Window, Mouse & Keyboard input, Audio, render with vertex buffer objects]
     - Game created with [Scene shader, Collider, Time utility (using now only for player & camera motion smoothness), Blocks, Player, Material, Block types (class with all blocks), Texture setup (for adding animated textures feature), Map (to read 2d char array and transform it to engine-readable object & colliders list)]
