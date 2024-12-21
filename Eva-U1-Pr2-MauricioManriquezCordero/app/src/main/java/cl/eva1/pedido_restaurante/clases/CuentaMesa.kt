package cl.eva1.pedido_restaurante.clases

class CuentaMesa(val mesa: Int) {
    private val _items = mutableListOf<ItemMesa>()
    var aceptaPropina: Boolean = true

    fun agregarItem(itemMesa: ItemMesa) {
        _items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int {
        return _items.sumOf { it.calcularSubtotal()}
    }

    fun calcularPropina(): Int {
        val totalSinPropina = calcularTotalSinPropina()
        return if (aceptaPropina) (totalSinPropina * 0.10).toInt() else 0
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}