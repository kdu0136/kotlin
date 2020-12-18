package book_1

class Donut {
    companion object {
        val price = 100
    }
}

class CreditCard

class Payment(val creditCard: CreditCard, val amount: Int) {
    fun combine(payment: Payment): Payment =
        if (creditCard == payment.creditCard)
            Payment(creditCard, amount + payment.amount)
        else throw IllegalStateException("Cards don't match")

    companion object {
        fun groupByCard(payments: List<Payment>): List<Payment> =
            payments.groupBy { it.creditCard }
                .values
                .map { it.reduce { acc, payment -> acc.combine(payment) } }//t.reduce(Payment::combine) }
    }
}

class Purchase(val donuts: List<Donut>, val payment: Payment)

fun buyDonuts(quantity: Int = 1, creditCard: CreditCard): Purchase =
    Purchase(List(quantity) {
        Donut()
    }, Payment(creditCard, Donut.price * quantity))

