#### **Part 1**

1. How does your API encapsulate your implementation decisions?
    1. Need to not give data structures used by JsonParser away, can use similar getters that we
       used for cell society
2. What abstractions is your API built on?
    2. My API does not employ many abstractions since inheritance hierarchies/interfaces would not
       be as appropriate for what is needed
3. What about your API's design is intended to be flexible?
    3. It seemed pretty flexible overall, specifically allowing the user to upload either a user
       preferences file or starting config file without any difference in the methods called from
       the API user. Also, if a significantly different layout for the starting config was
       introduced, a new class could be created that could be swapped out with the current starting
       config parser pretty easily.
4. What exceptions (error cases) might occur in your API and how are they addressed?
    4. I should create a specific exception type for parsing exceptions, but besides this throwing
       exceptions and catching them is good and good to have the messages in props files. I also
       need to do logging.

#### **Part 2**

1. How do you justify that your API is easy to learn?
    1. Need to rename methods named uploadFile, updatePressedKey. However, besides this, the methods
       themselves are very active, so the users of the API do not need to do much to leverage the
       power of my API.
2. How do you justify that API leads to readable code?
    2. Need to use private helper methods for longer methods. However, most of the work that the
       users of the API need to do is done in the API itself, which leads to not
3. How do you justify that API is hard to misuse?
    3. Seems hard to misuse since methods do what they are expected and no clean up/worry about the
       internals is necessary on user side of things
4. Why do you think your API design is good (also define what your measure of good is)?
    4. My measure of good is that the API is active and the user does not need to worry about the
       internals. Based on that measure, my API design is overall good since very active and user
       does not need to pay attention to what is going on underneath the hood.