
Please write down the purpose of the product, a brief description of the product features, and
the target users. Also, write the initial list of use cases as much as you can. You may change or
complete the use cases later if you see fit.
My project is a pass and play implementation of the card game euchre.
The project should support a fully functional implementation of euchre, adhering to the rules
listed here:
The Pack
Trim the deck to create a deck consisting of only values 9 through ace. (removing all values 2
through 8).
Object of the Game
The goal is to win at least three tricks. If the side that fixed the trump fails to get three tricks, it
is said to be "euchred." Winning all five tricks is called a "march."
Rank of Cards
1
The highest trump is the jack of the trump suit, called the "right bower." The second-highest
trump is the jack of the other suit of the same color called the "left bower." (Example: If
diamonds are trumps, the right bower is J♦ and the left bower is J♥.) The remaining trumps,
and also the plain suits, rank as follows: A (high), K, Q, J, 10, 9.
Card Values/scoring
The following shows all scoring situations:
Partnership making trump wins 3 or 4 tricks – 1 point
Partnership making trump wins 5 tricks – 2 points
Lone hand wins 3 or 4 tricks – 1 point
Lone hand wins 5 tricks – 4 points
Partnership or lone hand is euchred, opponents score 2 points
The first player or partnership to score 10 points wins the game.
The Deal
The cards are dealt clockwise, to the left, beginning with the player to the left of the dealer.
Each player receives five cards. The dealer may give a round of three at a time, then a round of
two at a time, or may give two, then three; but the dealer must adhere to whichever
distribution plan he begins with. After the first deal, the deal passes to the player on the
dealer's left.
On completing the deal, the dealer places the rest of the pack in the center of the table and
turns the top card face up. Should the card turned up be accepted as trump by any player, the
dealer has the right to exchange the turned up card for another card in their hand.
Making the Trump
Beginning with the player to the left of the dealer, each player passes or accepts the turn-up as
trump.
The dealer signifies refusal of the turn-up by removing the card from the top and placing it (face
up) partially underneath the pack; this is called "turning it down."
2
If all four players pass in the first round, each player in turn, starting with the player to the
dealer's left, has the option of passing again or of naming the trump suit. The rejected suit may
not be named.
If all four players pass in the second round, the cards are gathered and shuffled, and the next
dealer deals. Once the trump is fixed, either by acceptance of the turn-up or by the naming of
another suit, the turn-up is rejected, the bidding ends and play begins.
Playing Alone
If the player who fixes the trump suit believes it will be to their side's advantage to play without
the help of their partner's cards, the player exercises this option by declaring "alone" distinctly
at the time of making the trump. This player's partner then turns their cards face down and
does not participate in the play.
The Play
The opening lead is made by the player to the dealer's left, or if this player's partner is playing
alone, it is made by the player across from the dealer. If possible, each player must follow suit
to a lead. If unable to follow suit, the player may trump or discard any card. A trick is won by
the highest card of the suit led, or, if it contains trumps, by the highest trump. The winner of a
trick leads next.
Use cases:
A group of four players can play an entire game of euchre according to the rules above.
There are a variety of house rules frequently added to euchre. These rules should be able to be
added/removed easily.
Users may only have a group of fewer than 4 people. Thus, they may need a bot algorithm to fill
the roles of absent players.
The players playing alongside bots may require bots of different skill levels. There should be
support for the addition of additional bot algorithms.
Support for online play may be called for in the future, so make sure program could be
augmented to support that.