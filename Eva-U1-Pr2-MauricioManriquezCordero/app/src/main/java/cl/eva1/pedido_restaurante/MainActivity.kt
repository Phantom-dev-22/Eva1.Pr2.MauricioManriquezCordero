package cl.eva1.pedido_restaurante

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Switch
import android.widget.TextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // Referencias de los elementos del layout
    private lateinit var etPastelDeChocloCantidad: EditText
    private lateinit var etCazuelaCantidad: EditText
    private lateinit var etPastelDeChocloValor: TextView
    private lateinit var etCazuelaValor: TextView
    private lateinit var switchPropina: Switch

    private lateinit var tvSubtotal: TextView
    private lateinit var tvPropina: TextView
    private lateinit var tvTotal: TextView

    private var valorUnitarioPastelDeChoclo = 12000.0
    private var valorUnitarioCazuela = 10000.0
    private var cantidadPastelDeChoclo = 0
    private var cantidadCazuela = 0
    private var propina = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar las vistas
        etPastelDeChocloCantidad = findViewById(R.id.etPastelDeChocloCantidad)
        etCazuelaCantidad = findViewById(R.id.etCazuelaCantidad)
        etPastelDeChocloValor = findViewById(R.id.editTextNumberDecimal)
        etCazuelaValor = findViewById(R.id.editTextNumberDecimal2)
        switchPropina = findViewById(R.id.switchPropina)

        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvPropina = findViewById(R.id.tvPropina)
        tvTotal = findViewById(R.id.tvTotal)

        // Mostrar valores unitarios iniciales con formato
        etPastelDeChocloValor.text = formatCurrency(valorUnitarioPastelDeChoclo)
        etCazuelaValor.text = formatCurrency(valorUnitarioCazuela)

        // Configurar el evento de cambio de cantidad
        etPastelDeChocloCantidad.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                actualizarValorPorCantidad()
                calcularTotales()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etCazuelaCantidad.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                actualizarValorPorCantidad()
                calcularTotales()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Configurar el evento de cambio del switch de propina
        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            calcularTotales()
        }

        // Inicializar la interfaz con los valores actuales
        actualizarValorPorCantidad()
        calcularTotales()
    }

    // Función para calcular los valores por cantidad
    private fun actualizarValorPorCantidad() {
        cantidadPastelDeChoclo = etPastelDeChocloCantidad.text.toString().toIntOrNull() ?: 0
        cantidadCazuela = etCazuelaCantidad.text.toString().toIntOrNull() ?: 0

        // Calcular y mostrar los valores totales por producto
        val totalPastelDeChoclo = cantidadPastelDeChoclo * valorUnitarioPastelDeChoclo
        val totalCazuela = cantidadCazuela * valorUnitarioCazuela

        etPastelDeChocloValor.text = formatCurrency(totalPastelDeChoclo)
        etCazuelaValor.text = formatCurrency(totalCazuela)
    }

    // Función para calcular el subtotal, propina y total
    private fun calcularTotales() {
        val totalPastelDeChoclo = cantidadPastelDeChoclo * valorUnitarioPastelDeChoclo
        val totalCazuela = cantidadCazuela * valorUnitarioCazuela

        // Calcular el subtotal
        val subtotal = totalPastelDeChoclo + totalCazuela

        // Mostrar el subtotal con formato
        tvSubtotal.text = "Comida: ${formatCurrency(subtotal)}"

        // Calcular la propina (10% si el switch está activado)
        propina = if (switchPropina.isChecked) {
            subtotal * 0.10
        } else {
            0.0
        }

        // Mostrar la propina con formato
        tvPropina.text = "Propina: ${formatCurrency(propina)}"

        // Calcular el total
        val total = subtotal + propina

        // Mostrar el total con formato
        tvTotal.text = "Total: ${formatCurrency(total)}"
    }

    // Función para formatear valores como pesos chilenos
    private fun formatCurrency(value: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
        return format.format(value)
    }
}
