# breakout-clone
## Yet another Breakout clone

Just the umpteenth breakout clone.


## A short comment
I started this project because I wanted to create a simple game to test some ideas.  
Some mechanics are implemented as generically as possible, which is probably one of the reasons (together with a 
not-very-good game thread) why the game has got plenty of issues, especially in the physics department.  
When I started coding it, I thought that OOP was the best way to organise and develop a game. With time, I realised my
mistake and moved away a bit from OOP, which is why the code is not as tidy as I would like.

## How to play
The code has been compiled with Java 17 so, having that, to run the game `java -jar $JAR` is enough, where *$JAR* is,
possibly, the [latest release](https://github.com/ukitinu/breakout-clone/releases/latest).  
Nothing else is necessary: if no [breakout_clone.properties](./breakout_clone.properties) file is found,
it creates a default one and exits afterwards.
