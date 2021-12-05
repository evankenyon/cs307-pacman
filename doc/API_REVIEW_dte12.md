# Part 1

1. The View API has a View class for each model agent, so each model agent communicates with the
   corresponding front end agent. Within the View API, there is only method in the view API,
   updateAgent(), which is called by the consumer for each model/view agent pair when the agent
   needs to be updated.
2. The API starts with the AgentView super class for all view agents. Then, the Stationary and
   Movable agents are extended from that. Finally, each individual agent view extends from
   stationary or movable super class.
3. Additional subclasses can be created for additional agents by extending the respective movable or
   stationary super class.
4. Errors will occur if a data file is inputted with an agent that is not created in the model or
   view. For example, if the data file tries to make a dinosaur on the grid, it will throw an
   invalid file error since it cannot create a dinosaur agent.

# Part 2

1. This API is easy to learn because there is only one method for the AgentView super class and one
   method in each of the stationary or movable agent view subclasses. These are called when the
   agents change.
2. This API is readable because the user only needs to call the required methods, and there are very
   few methods to call, leading to much less confusion.
3. The API is hard to misuse since you user cannot mix up methods easily and the method naming makes
   it obvious which methods to use where.
4. The API design is good because it uses abstractions to follow the open-closed principle and has
   very few methods to have readable code. Good is measured by how easy the API is to use. 