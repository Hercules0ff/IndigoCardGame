package indigo

class Player(val hand: Hand, private val name: String = "Computer") {
    private var cards = 0
    private var score = 0

    fun win(table: Hand) {
        score += table.sumPoints()
        cards += table.size()
        table.reset()
        lastWinner = this
        println("$name wins cards")
    }

    fun printScore(computer: Player) {
        println("Score: $name $score - ${computer.name} ${computer.score}")
        println("Cards: $name $cards - ${computer.name} ${computer.cards}")
    }

    fun finalScore(table: Hand, computer: Player, isPlayerTurn: Boolean) {
        if (!table.isEmpty()) {
            val cardsWinner = lastWinner ?: if (isPlayerTurn) this else computer

            cardsWinner.score += table.sumPoints()
            cardsWinner.cards += table.size()
        }

        val playerWins = cards > computer.cards || (cards == computer.cards && isPlayerTurn)

        if (playerWins) score += 3 else computer.score += 3
        printScore(computer)
    }

    companion object {
        var lastWinner: Player? = null
    }

}