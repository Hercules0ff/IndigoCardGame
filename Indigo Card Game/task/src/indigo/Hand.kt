package indigo

class Hand {
    private val hand = mutableListOf<String>()

    fun add(cards: List<String>) = cards.toCollection(hand)

    fun add(card: String) = hand.add(card)

    fun take(card: Int) = if (card in 1..hand.size) hand.removeAt(card - 1) else null

    fun isEmpty() = hand.isEmpty()

    fun size() = hand.size

    fun topCard() = if (hand.isNotEmpty()) hand.last() else ""

    fun cards() = hand.joinToString(" ")

    fun cardsNumbered() = hand.mapIndexed { index, card -> "${index+1})$card" }.joinToString(" ")

    fun shuffle() = hand.also { it.shuffled() }

    fun isWin(playerCard: String, topCard: String = topCard()) = topCard.isNotEmpty()
            && (topCard.last() == playerCard.last() || topCard.dropLast(1) == playerCard.dropLast(1))

    fun sumPoints() = hand.map { if (points.contains(it.dropLast(1))) 1 else 0 }.sum()

    fun move(topCard: String) = when {
        hand.size == 1 -> hand.first()
        topCard.isEmpty() -> randomCard()
        else -> bestCard(topCard)
    }.also { hand.remove(it) }

    private fun bestCard(topCard: String): String {
        val suitCandidate = hand.filter { it.last() == topCard.last() }
        val rankCandidate = hand.filter { it.first() == topCard.first() }

        return when {
            suitCandidate.isEmpty() && rankCandidate.isEmpty() -> randomCard()
            suitCandidate.size == 1 && rankCandidate.size == 1 && suitCandidate == rankCandidate -> suitCandidate.first()
            suitCandidate.size > 1 -> suitCandidate.random()
            rankCandidate.size > 1 -> rankCandidate.random()
            else -> (rankCandidate + suitCandidate).random()
        }
    }

    private fun randomCard(): String {
        val suitEquals = hand.groupBy { it.last() }
            .filter { it.value.size > 1 }.values.flatten()
        if (suitEquals.isNotEmpty()) return suitEquals.random()

        val rankEquals = hand.groupBy { it.first() }
            .filter { it.value.size > 1 }
            .values.flatten()
        if (rankEquals.isNotEmpty()) return rankEquals.random()

        return hand.random()
    }

    fun reset() {
        hand.clear()
    }

    companion object {
        const val DEAL_SIZE = 6
        const val TABLE_SIZE = 4
        val points = "A 10 J Q K".split(" ")

        fun create(cards: List<String>) = Hand().also { it.add(cards) }
    }



}