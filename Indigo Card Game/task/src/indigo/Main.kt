package indigo

const val ORDER_QUESTION = "Play first?"

fun main() {
    val deck = Deck()
    val table = deck.getHand(Hand.TABLE_SIZE)
    val player = Player(deck.getHand(Hand.DEAL_SIZE), "Player")
    val computer = Player(deck.getHand(Hand.DEAL_SIZE))
    var isPlayerTurn: Boolean
    var exit = false

    println("Indigo Card Game")
    isPlayerTurn = getPlayerTurn()
    println("Initial cards on the table: " + table.cards())

    while(!exit) {
        cardsOnTheTable(table)
        if (deckIsEmpty(player.hand, computer.hand, deck)) break
        if (isPlayerTurn) exit = playerPlays(player, computer, table) else computerPlays(computer, player, table)
        isPlayerTurn = !isPlayerTurn
    }
    if(!exit) player.finalScore(table, computer, isPlayerTurn)
    println("Game Over")
}

private fun playerPlays(player: Player, computer: Player, table: Hand): Boolean {
    println("Cards in hand: ${player.hand.cardsNumbered()}")

    while (true) {
        println("Choose a card to play (1-${player.hand.size()}):")
        val action = readln()
        if (action.lowercase() == "exit") {
            return true
        }
        val card = player.hand.take(action.toIntOrNull() ?: continue) ?: continue
        if( isWin(player, card, table)) player.printScore(computer)
        return false
    }
}

fun deckIsEmpty(playerHand: Hand, computerHand: Hand, deck: Deck): Boolean{
    if (playerHand.isEmpty() && computerHand.isEmpty() && deck.isEmpty()) return true
    if (playerHand.isEmpty() && computerHand.isEmpty() ) {
        playerHand.add(deck.getCards(Hand.DEAL_SIZE))
        computerHand.add(deck.getCards(Hand.DEAL_SIZE))
    }
    return false
}

private fun computerPlays(computer: Player, player: Player, table: Hand) {
    println(computer.hand.cards())
    val card = computer.hand.move(table.topCard())
    println("Computer plays $card")
    if (isWin(computer, card, table)) player.printScore(computer)
}

private fun getPlayerTurn(): Boolean {
    while (true) {
        println(ORDER_QUESTION)
        when (readln()) {
            "yes" -> return true
            "no" -> return false
        }
    }
}

private fun cardsOnTheTable(table: Hand) =
    if (table.isEmpty()) {
        println("\nNo cards on the table")
    } else {
        println("\n${table.size()} cards on the table, and the top card is ${table.topCard()}")
    }

private fun isWin(player: Player, card:String, table: Hand): Boolean {
    return table.isWin(card).also { isWin ->
        table.add(card)
        if (isWin) player.win(table)
    }
}






