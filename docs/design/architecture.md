
a. Quality attribute scenarios –
Correctness: each player plays a card, with one of them being trump. The trump card wins the
hand. In any use case, the app should only allow play within the rules.
Usability: when turn changes, player is easily able to identify they should pass the device.
Maintainability: users want to add a new house rule. The software supports easy introduction,
having to change minimal code to introduce it.
Efficiency: the software should be able to run using very few resources, seeing it is a simple
application with very little UI.
Reliability: the software should be 100% available for the offline version, even if an issue occurs
with the online version.
b. Correctness is the most important attribute. If the system doesn’t function according to Euchre’s
rules, it will be very frustrating to play. Then, usability. It is very important that the system is
easily understandable to promote fast pace of play. Then, maintainability. The addition of the
house rules is a fairly important feature, and thus the code must be easily maintainable. Then,
Efficiency. While the code must be reasonably efficient, due to the fact that it is a simple
program, it is very likely to be efficient naturally. Finally, reliability. This design principle is not all
that important due to the fact that the online version is a later addition.
c. I believe both the MVC and 3 layer pattern would be applicable for this application. The UI could
serve as the presentation layer, and the game logic as the domain. Currently, the design would
likely not call for any interaction with files, but if it did, the database layer could be added. The
MVC pattern would work with the design because the views could be observers of the game
state, and thus be automatically notified when the state is changed.