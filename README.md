# breakout-clone
## Yet another Breakout clone

Just the umpteenth breakout clone.

## How to play
The code has been compiled with Java 17 so, having that, to run the game `java -jar $JAR` is enough, where *$JAR* is,
possibly, the [latest release](https://github.com/ukitinu/breakout-clone/releases/latest).  
Nothing else is necessary: if no [breakout_clone.properties](./breakout_clone.properties) file is found,
it creates a default one and exits afterwards.

The game generates two rows of bricks per level, with brick types picked randomly (with some limits/requirements).  
Technically, the placement of every object and text is dynamic, depending on the window's size (maybe I'll make it
configurable in the future).

The score is increased destroying bricks (how much depends on the brick type) and clearing levels with all lives.
Losing a life resets the level AND the score, losing all lives resets the whole game. When a level is cleared, a life
is restored.

Hitting the ball with the paddle's sides inverts the y-velocity only, hitting it with the center, also inverts the x-velocity.


## Configuration
THe *log.* options control the info being logged, useless when playing, kind of useful when debugging.  
The two *fps.* configs are probably best left alone, I tinkered with them in the past and 60/80 seems to be the best values.  
I haven't really tried to change the *max_lives* option, but it should work since it is treated dynamically in the code.  
The *max_level* config was useful when debugging, and may be increased/decreased to have more/less of a challenge.  
The *start_speed* config defines the starting **vertical** speed of the ball, which is also increased level by level
according to *mod_speed*.


## A comment on the code
I started this project because I wanted to create a simple game to test some ideas.  
Some mechanics are implemented as generically as possible, which is probably one of the reasons (together with a 
not-very-good game thread/loop) why the game has got plenty of issues, especially in the physics/movement department.  
When I started coding it, I thought that OOP was the best way to organise and develop a game. With time, I realised my
mistake and moved away a bit from OOP, which is why the code is not as tidy as I would like.


