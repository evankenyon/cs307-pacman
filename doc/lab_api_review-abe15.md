#Abe15 API Review
##Part 1
1) How does your API encapsulate your implementation decisions?
The Agent API encapsulates the implementation decisions by providing an interfact to call "step" on each agent. This methods implementation varies among agents and includes the individual implementation logic for what an agent does each cycle. It also encapsulates an agents data by only making direction and position information available.
2) What abstractions is your API built on?
3) What about your API's design is intended to be flexible?
The interpretation of the term agent is intended to be flexible. It could be something that moves, something that is static, something edible, something controllable, etc.
4) What exceptions (error cases) might occur in your API and how are they addressed?
N

##Part 2
5) How do you justify that your API is easy to learn?
The API and its methods are extremely well-named, commented, and concise enough to be a quick learn for virtually anybody with CS experience.
6) How do you justify that API leads to readable code?
The API clearly defines all primary functions of an agent that must be incorporated, thus, any implementations will follow the single responsibility principle and lead to less-repeated code.
7) How do you justify that API is hard to misuse?
8) Why do you think your API design is good (also define what your measure of good is)?
I think our API is decent, given the knowledge of the game and its implementation I have now there are some revisions I would've made in regard to making rule interfaces rather than agent interfaces and making agent classes synonymous. 
