package com.example.sistemapedidos

// ------ CLASES DE DATOS (Avance 1) ------
data class Client(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
)

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double
)

data class OrderItem(
    val product: Product,
    val quantity: Int
) {
    val subtotal: Double
        get() = product.price * quantity
}

data class Order(
    val id: Int,
    val client: Client,
    val items: List<OrderItem>,
    val status: String // "Pendiente", "Enviado", "Entregado", "Cancelado"
) {
    val totalPedido: Double
        get() = items.sumOf { it.subtotal }
}

// ========== Funciones de reportes ==========

// 1. Calcular total vendido por categoría
fun totalVendidoPorCategoria(orders: List<Order>): Map<String, Double> {
    return orders
        .filter { it.status == "Entregado" }
        .flatMap { order -> order.items.map { item -> item.product.category to item.subtotal } }
        .groupBy { it.first }
        .mapValues { entry -> entry.value.sumOf { it.second } }
}

// 2. Calcular total vendido por cliente
fun totalVendidoPorCliente(orders: List<Order>): Map<String, Double> {
    return orders
        .filter { it.status == "Entregado" }
        .groupBy { it.client.name }
        .mapValues { entry -> entry.value.sumOf { it.totalPedido } }
}

// 3. Identificar cliente con mayor monto de compras
fun clienteMayorMonto(orders: List<Order>): Pair<String, Double>? {
    return totalVendidoPorCliente(orders)
        .maxByOrNull { it.value }
        ?.let { it.key to it.value }
}

// 4. Identificar producto más vendido (por cantidad de unidades)
fun productoMasVendido(orders: List<Order>): Pair<String, Int>? {
    return orders
        .filter { it.status == "Entregado" }
        .flatMap { it.items }
        .groupBy { it.product.name }
        .mapValues { entry -> entry.value.sumOf { it.quantity } }
        .maxByOrNull { it.value }
        ?.let { it.key to it.value }
}

// 5. Identificar categoría con mayor venta
fun categoriaMayorVenta(orders: List<Order>): Pair<String, Double>? {
    return totalVendidoPorCategoria(orders)
        .maxByOrNull { it.value }
        ?.let { it.key to it.value }
}

// 6. Calcular ticket promedio de pedidos entregados
fun ticketPromedio(orders: List<Order>): Double {
    val deliveredOrders = orders.filter { it.status == "Entregado" }
    return if (deliveredOrders.isNotEmpty()) {
        deliveredOrders.map { it.totalPedido }.average()
    } else 0.0
}

// 7. Top 5 productos más vendidos
fun top5ProductosMasVendidos(orders: List<Order>): List<Pair<String, Int>> {
    return orders
        .filter { it.status == "Entregado" }
        .flatMap { it.items }
        .groupBy { it.product.name }
        .mapValues { entry -> entry.value.sumOf { it.quantity } }
        .entries
        .sortedByDescending { it.value }
        .take(5)
        .map { it.key to it.value }
}

// 8. Top 3 clientes con mayor compra
fun top3ClientesMayorCompra(orders: List<Order>): List<Pair<String, Double>> {
    return totalVendidoPorCliente(orders)
        .entries
        .sortedByDescending { it.value }
        .take(3)
        .map { it.key to it.value }
}

// 9. Agrupar pedidos por estatus
fun agruparPedidosPorEstatus(orders: List<Order>): Map<String, List<Order>> {
    return orders.groupBy { it.status }
}

// 10. Agrupar productos por categoría
fun agruparProductosPorCategoria(products: List<Product>): Map<String, List<Product>> {
    return products.groupBy { it.category }
}

// 11. Calcular unidades vendidas por producto
fun unidadesVendidasPorProducto(orders: List<Order>): Map<String, Int> {
    return orders
        .filter { it.status == "Entregado" }
        .flatMap { it.items }
        .groupBy { it.product.name }
        .mapValues { entry -> entry.value.sumOf { it.quantity } }
}

// 12. Reporte general de ventas
fun reporteGeneralVentas(orders: List<Order>): Map<String, Any> {
    val delivered = orders.filter { it.status == "Entregado" }
    val totalVendido = delivered.sumOf { it.totalPedido }
    val totalPedidos = orders.size
    val entregados = delivered.size
    val cancelados = orders.count { it.status == "Cancelado" }
    val promedio = if (entregados > 0) totalVendido / entregados else 0.0

    return mapOf(
        "totalPedidos" to totalPedidos,
        "pedidosEntregados" to entregados,
        "pedidosCancelados" to cancelados,
        "totalVendido" to totalVendido,
        "promedioVenta" to promedio
    )
}

// 13. Reporte de pedidos cancelados
fun reportePedidosCancelados(orders: List<Order>): List<Order> {
    return orders.filter { it.status == "Cancelado" }
}

// 14. Reporte de productos no vendidos
fun reporteProductosNoVendidos(products: List<Product>, orders: List<Order>): List<Product> {
    val productosVendidos = orders
        .filter { it.status == "Entregado" }
        .flatMap { it.items }
        .map { it.product.name }
        .toSet()

    return products.filter { it.name !in productosVendidos }
}

// ========== FUNCIÓN PRINCIPAL ==========

fun main() {
    println("------ Avance 2 - Motos de reportes y estadisticas ------\n")
    // ========== DATOS DE PRUEBA (MÁS COMPLETOS) ==========

    // Clientes (7 clientes)
    val clients = listOf(
        Client(1, "Juan Pérez", "juan.perez@gmail.com", "555-1234"),
        Client(2, "María Gómez", "maria.gomez@gmail.com", "555-5678"),
        Client(3, "Carlos López", "carlos.lopez@gmail.com", "555-8765"),
        Client(4, "Ana Martínez", "ana.martinez@gmail.com", "555-4321"),
        Client(5, "Luis Fernández", "luis.fernandez@gmail.com", "555-9876"),
        Client(6, "Laura Rodríguez", "laura.rodriguez@gmail.com", "555-3456"),
        Client(7, "Pedro Sánchez", "pedro.sanchez@gmail.com", "555-6543")
    )

    // Productos (12 productos - Avance 1)
    val products = listOf(
        Product(1, "Laptop Gamer X1", "Electrónica", 15000.0),
        Product(2, "Mouse Inalámbrico", "Electrónica", 450.0),
        Product(3, "Teclado Mecánico", "Electrónica", 1200.0),
        Product(4, "Monitor 24 pulgadas", "Electrónica", 3200.0),
        Product(5, "Camiseta Deportiva", "Ropa", 350.0),
        Product(6, "Pantalón de mezclilla", "Ropa", 800.0),
        Product(7, "Zapatos Casual", "Ropa", 1200.0),
        Product(8, "Chaqueta Impermeable", "Ropa", 1800.0),
        Product(9, "Novela de ciencia ficción", "Libros", 280.0),
        Product(10, "Libro de programación Kotlin", "Libros", 550.0),
        Product(11, "Set de tazas", "Hogar", 320.0),
        Product(12, "Lámpara de escritorio", "Hogar", 650.0)
    )

    // Pedidos (7 pedidos - Avance 1)
    val orders = listOf(
        Order(
            id = 1,
            client = clients[0],
            items = listOf(
                OrderItem(products[0], 1),
                OrderItem(products[1], 2)
            ),
            status = "Entregado"
        ),
        Order(
            id = 2,
            client = clients[1],
            items = listOf(
                OrderItem(products[2], 1),
                OrderItem(products[3], 1)
            ),
            status = "Entregado"
        ),
        Order(
            id = 3,
            client = clients[2],
            items = listOf(
                OrderItem(products[5], 2),
                OrderItem(products[4], 1),
                OrderItem(products[6], 1)
            ),
            status = "Enviado"
        ),
        Order(
            id = 4,
            client = clients[3],
            items = listOf(
                OrderItem(products[8], 1),
                OrderItem(products[9], 1)
            ),
            status = "Pendiente"
        ),
        Order(
            id = 5,
            client = clients[4],
            items = listOf(
                OrderItem(products[10], 3),
                OrderItem(products[11], 1)
            ),
            status = "Entregado"
        ),
        Order(
            id = 6,
            client = clients[5],
            items = listOf(
                OrderItem(products[7], 1)
            ),
            status = "Cancelado"
        ),
        Order(
            id = 7,
            client = clients[6],
            items = listOf(
                OrderItem(products[1], 3),
                OrderItem(products[2], 1)
            ),
            status = "Pendiente"
        )
    )

    // ========== 1. REPORTES DE VENTAS POR CATEGORÍA Y CLIENTE ==========
    println("------ 1. Analisis de ventas por categoria y cliente ------")

    // Total vendido por categoría
    println("Total vendido por categoría:")
    totalVendidoPorCategoria(orders).forEach { (categoria, total) ->
        println("- $categoria: $${String.format("%.2f", total)}")
    }
    println()

    // Total vendido por cliente
    println("Total vendido por cliente:")
    totalVendidoPorCliente(orders).forEach { (cliente, total) ->
        println("- $cliente: $${String.format("%.2f", total)}")
    }
    println()

    // ========== 2. IDENTIFICACIÓN DE CLIENTE Y PRODUCTO DESTACADO ==========
    println("------ Identificacion de cliente y producto destacado ------")

    val mejorCliente = clienteMayorMonto(orders)
    mejorCliente?.let {
        println("Cliente con mayor monto de compras: ${it.first} - $${String.format("%.2f", it.second)}")
    }

    val mejorProducto = productoMasVendido(orders)
    mejorProducto?.let {
        println("- Producto más vendido: ${it.first} - ${it.second} unidades")
    }

    val mejorCategoria = categoriaMayorVenta(orders)
    mejorCategoria?.let {
        println("- Categoría con mayor venta: ${it.first} - $${String.format("%.2f", it.second)}")
    }
    println()

    // ========== 3. TICKET PROMEDIO ==========
    println("------ Ticket promedio ------")
    val ticketProm = ticketPromedio(orders)
    println("Ticket promedio de pedidos entregados: $${String.format("%.2f", ticketProm)}")
    println()

    // ========== 4. TOP 5 PRODUCTOS MÁS VENDIDOS ==========
    println("------ Top 5 productos mas vendidos ------")
    top5ProductosMasVendidos(orders).forEachIndexed { index, (producto, cantidad) ->
        println("   ${index + 1}. $producto - $cantidad unidades")
    }
    println()

    // ========== 5. TOP 3 CLIENTES CON MAYOR COMPRA ==========
    println("------ Top 3 clientes con mayor compra ------")
    top3ClientesMayorCompra(orders).forEachIndexed { index, (cliente, total) ->
        println("   ${index + 1}. $cliente - $${String.format("%.2f", total)}")
    }
    println()

    // ========== 6. AGRUPACIONES ==========
    println("------ Agrupaciones ------")
    // Agrupar pedidos por estatus
    println("Pedidos agrupados por estatus:")
    agruparPedidosPorEstatus(orders).forEach { (estatus, pedidos) ->
        println("- $estatus: ${pedidos.size} pedido(s)")
        pedidos.forEach { pedido ->
            println("  - Pedido #${pedido.id} | ${pedido.client.name} | $${String.format("%.2f", pedido.totalPedido)}")
        }
    }
    println()

    // Agrupar productos por categoría
    println("Productos agrupados por categoría:")
    agruparProductosPorCategoria(products).forEach { (categoria, productos) ->
        println("- $categoria: ${productos.size} producto(s)")
        productos.forEach { producto ->
            println("  - ${producto.name} | $${String.format("%.2f", producto.price)}")
        }
    }
    println()

    // ========== 7. UNIDADES VENDIDAS POR PRODUCTO ==========
    println("------ Unidades vendidas por prodcuto ------")
    unidadesVendidasPorProducto(orders).forEach { (producto, unidades) ->
        println("- $producto: $unidades unidad(es)")
    }
    println()

    // ========== 8. REPORTE GENERAL DE VENTAS ==========
    println("------ Reporte final de ventas ------")
    val reporteGeneral = reporteGeneralVentas(orders)
    println("ESTADISTICAS GENERALES:")
    println("  - Total de pedidos: ${reporteGeneral["totalPedidos"]}")
    println("  - Pedidos entregados: ${reporteGeneral["pedidosEntregados"]}")
    println("  - Pedidos cancelados: ${reporteGeneral["pedidosCancelados"]}")
    println("  - Total vendido: $${String.format("%.2f", reporteGeneral["totalVendido"] as Double)}")
    println("  - Promedio de venta: $${String.format("%.2f", reporteGeneral["promedioVenta"] as Double)}")
    println()

    // ========== 9. REPORTE DE PEDIDOS CANCELADOS ==========
    println("------ Reporte de pedidos cancelados ------")
    val cancelados = reportePedidosCancelados(orders)
    if (cancelados.isNotEmpty()) {
        println("Pedidos cancelados:")
        cancelados.forEach { pedido ->
            println("  - Pedido #${pedido.id} | ${pedido.client.name} | Total: $${String.format("%.2f", pedido.totalPedido)}")
        }
    } else {
        println("No hay pedidos cancelados")
    }
    println()

    // ========== 10. REPORTE DE PRODUCTOS NO VENDIDOS ==========
    println("------ Reporte de pedidos no vendidos ------")
    val noVendidos = reporteProductosNoVendidos(products, orders)
    if (noVendidos.isNotEmpty()) {
        println("Productos no vendidos:")
        noVendidos.forEach { producto ->
            println("  - ${producto.name} | ${producto.category} | $${String.format("%.2f", producto.price)}")
        }
    } else {
        println("Todos los productos han sido vendidos")
    }
    println()
    println("Reportes generados exitosamente")
} //Cerramos Main