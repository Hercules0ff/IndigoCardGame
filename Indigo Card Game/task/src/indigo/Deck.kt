package indigo

class Deck {
    private val _deck = mutableListOf<String>()
    val cards = _deck

    init {
        val ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        val suits = listOf("♦", "♥", "♠", "♣")
        for (rank in ranks) {
            for (suit in suits) {
                _deck.add(rank + suit)
            }
        }
        _deck.shuffle()
    }

    fun getCards(num: Int) = if (_deck.size >= num) (1..num).map { _deck.removeLast() } else emptyList()

    fun getHand(num: Int) = Hand.create(getCards(num))

    fun isEmpty() = _deck.isEmpty()

}