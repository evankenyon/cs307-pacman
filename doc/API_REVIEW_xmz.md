# API Review

### Takeaways:

* Methods that should go into constructors should go into constructors unless there's something else
  preventing that from happening. That is to say that methods that _need_ to be called should be
  guaranteed to be called (i.e. throw an error if not called, or set a default value).
* Methods in interfaces allow for possibility of different objects implementing that interface to
  implement them differently. Methods that we can only envision being implemented in one way should
  probably not go in an interface.
* Particularly, for us, we still need to add a lot of error cases and ensuring that the API's design
  allows the structure to be flexible. Most of our flexibility right now is not exposed through the
  API, instead handled in the Controller. Our Game API should expose methods to allow the Controller
  to change things in the Engine.
    * i.e. consider pulling changing movement schemes, win conditions, etc. up to the interface,
      potentially.

Collaborating NetIDs: sbl28, nqv2