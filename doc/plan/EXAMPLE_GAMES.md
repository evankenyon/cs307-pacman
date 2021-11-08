# Example games

1. Vanilla Pacman
    1. Pacman moves around the map, eating dots (which give him points), power ups that let him eat
       ghosts (for points), and fruits (optional, but give him more points than dots). 4 Ghosts
       chase Pacman, trying to eat him. He has a certain number of lives, and he loses one each time
       a ghost eats him. Game ends when all dots are eaten or Pacman loses all of his lives. There
       are walls that he cannot go through.
2. User controls ghosts vs user controlling pac man
    1. Play as one of the ghosts to beat Pacman.
    2. Can have a variation with multiple pacmans and you gain points by eating pacmen or eating
       pacmen quickly (more points for shorter pac man lives)
3. Super Pac Man
    1. Pacman eats keys, keys open doors with fruits behind them.
    2. There are no regular pellets.
    3. “Super” pellet which makes pacman invincible, he can eat doors, he’s bigger and faster (for
       some predefined amount of time)
    4. Everything else is the same as Vanilla Pacman

### Explain why you chose them and clearly identify their functional commonalities and differences:

* We chose the original Pacman since this is the foundation for our project. We chose the second
variation since this was an obvious, not too difficult extension that will help us think about some
basic design (such as how we can have both Pacman and the ghosts potentially be user controlled). We
chose Super Pac Man as our third example game since there were key differences that seemed to us
that they would be easy to implement if we set up our design well (i.e. a key is another item that
Pacman could eat, eating fruit is now required, and there’s a new type of powerup). We believe this
will help us develop important abstractions for different game items.

* Vanilla Pacman and the 2nd option we chose are very similar. The main differences being that the
user controls a ghost instead of Pacman in the 2nd option, all the foundational rules from Vanilla
Pacman stay the same. Vanilla Pacman/the 2nd option still have a good amount in common with Super
Pac Man, albeit with some key differences. Pacman will eat keys instead of regular pellets. These
keys will open doors. There is a new pellet, the super pellet, which makes pacman invincible, allows
him to eat doors, and makes him bigger and faster. These are the key differences, and everything
else is the same as Vanilla Pacman (ex. There are still the regular power up pellets that allow
Pacman to eat the ghosts). The door can be thought of as a type of wall, the key as a type of edible
object, and the fruit can still be thought of as a type of edible object.
