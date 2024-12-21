package cl.eva1.pedido_restaurante.clases

class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {
    fun calcularSubtotal(): Int {
        return cantidad * itemMenu.precio
    }
}