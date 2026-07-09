package com.example.sistemapedidos

// Sistema de pedidos - Avance 2

// ------ CLASES DE DATOS ------
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
    val status: String // Pendiente, Enviado, Entregado o Cancelado
) {
    val totalPedido: Double
        get() = items.sumOf { it.subtotal }
}

data class OrderSummary(
    val clientName: String,
    val total: Double,
    val status: String
)

data class SalesStats(
    val totalPedidos: Int,
    val pedidosEntregados: Int,
    val pedidosCancelados: Int,
    val totalVendido: Double,
    val promedioVenta: Double
)

val ESTATUS_VALIDOS = listOf("Pendiente", "Enviado", "Entregado", "Cancelado")

// ------ DATOS DE PRUEBA ------
fun cargarClientes(): List<Client> {
    return listOf(
        Client(1, "Juan Perez", "juan.perez@gmail.com", "555-1234"),
        Client(2, "Maria Gomez", "maria.gomez@gmail.com", "555-5678"),
        Client(3, "Carlos Lopez", "carlos.lopez@gmail.com", "555-8765"),
        Client(4, "Ana Martinez", "ana.martinez@gmail.com", "555-4321"),
        Client(5, "Luis Fernandez", "luis.fernandez@gmail.com", "555-9876"),
        Client(6, "Laura Rodriguez", "laura.rodriguez@gmail.com", "555-3456"),
        Client(7, "Pedro Sanchez", "pedro.sanchez@gmail.com", "555-6543")
    )
}

fun cargarProductos(): List<Product> {
    return listOf(
        Product(1, "Laptop Gamer X1", "Electronica", 15000.0),
        Product(2, "Mouse inalambrico", "Electronica", 450.0),
        Product(3, "Teclado mecanico", "Electronica", 1200.0),
        Product(4, "Monitor 24 pulgadas", "Electronica", 3200.0),
        Product(5, "Camiseta deportiva", "Ropa", 350.0),
        Product(6, "Pantalon de mezclilla", "Ropa", 800.0),
        Product(7, "Zapatos casual", "Ropa", 1200.0),
        Product(8, "Chaqueta impermeable", "Ropa", 1800.0),
        Product(9, "Novela de ciencia ficcion", "Libros", 280.0),
        Product(10, "Libro de programacion Kotlin", "Libros", 550.0),
        Product(11, "Set de tazas", "Hogar", 320.0),
        Product(12, "Lampara de escritorio", "Hogar", 650.0),
        Product(13, "Bocina bluetooth", "Electronica", 950.0),
        Product(14, "Termo de acero", "Hogar", 450.0),
        Product(15, "Mochila urbana", "Ropa", 900.0)
    )
}

fun cargarPedidos(clients: List<Client>, products: List<Product>): List<Order> {
    return listOf(
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
                OrderItem(products[3], 1),
                OrderItem(products[1], 1)
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
        ),
        Order(
            id = 8,
            client = clients[0],
            items = listOf(
                OrderItem(products[9], 2),
                OrderItem(products[8], 1)
            ),
            status = "Entregado"
        ),
        Order(
            id = 9,
            client = clients[1],
            items = listOf(
                OrderItem(products[4], 4),
                OrderItem(products[6], 1)
            ),
            status = "Entregado"
        ),
        Order(
            id = 10,
            client = clients[3],
            items = listOf(
                OrderItem(products[12], 1)
            ),
            status = "Cancelado"
        ),
        Order(
            id = 11,
            client = clients[5],
            items = listOf(
                OrderItem(products[13], 2),
                OrderItem(products[10], 1)
            ),
            status = "Entregado"
        ),
        Order(
            id = 12,
            client = clients[2],
            items = listOf(
                OrderItem(products[3], 1),
                OrderItem(products[1], 1)
            ),
            status = "Enviado"
        )
    )
}

// ------ FUNCIONES DE LA ENTREGA 1 ------
fun filtrarPedidosPorEstatus(orders: List<Order>, status: String): List<Order> {
    return orders.filter { it.status.equals(status, ignoreCase = true) }
}

fun filtrarProductosPorCategoria(products: List<Product>, category: String): List<Product> {
    return products.filter { it.category.equals(category, ignoreCase = true) }
}

fun buscarProductosPorNombre(products: List<Product>, text: String): List<Product> {
    return products.filter { it.name.contains(text, ignoreCase = true) }
}

fun ordenarProductosPorPrecio(products: List<Product>): List<Product> {
    return products.sortedBy { it.price }
}

fun ordenarPedidosPorMontoTotal(orders: List<Order>): List<Order> {
    return orders.sortedByDescending { it.totalPedido }
}

fun totalVendidoEntregado(orders: List<Order>): Double {
    return orders
        .filter { it.status == "Entregado" }
        .sumOf { it.totalPedido }
}

fun contarPedidosPorEstatus(orders: List<Order>): Map<String, Int> {
    return ESTATUS_VALIDOS.associateWith { status ->
        orders.count { it.status == status }
    }
}

fun productosConPrecioMayorA(products: List<Product>, price: Double): List<Product> {
    return products.filter { it.price > price }
}

fun clientesPorPedido(orders: List<Order>): List<Pair<Int, Client>> {
    return orders.map { it.id to it.client }
}

fun resumenPedidos(orders: List<Order>): List<OrderSummary> {
    return orders.map { order ->
        OrderSummary(
            clientName = order.client.name,
            total = order.totalPedido,
            status = order.status
        )
    }
}

// ------ FUNCIONES DE LA ENTREGA 2 ------
fun totalVendidoPorCategoria(orders: List<Order>): Map<String, Double> {
    return orders
        .filter { it.status == "Entregado" }
        .flatMap { order -> order.items.map { item -> item.product.category to item.subtotal } }
        .groupBy { it.first }
        .mapValues { entry -> entry.value.sumOf { it.second } }
}

fun totalVendidoPorCliente(orders: List<Order>): Map<String, Double> {
    return orders
        .filter { it.status == "Entregado" }
        .groupBy { it.client.name }
        .mapValues { entry -> entry.value.sumOf { it.totalPedido } }
}

fun clienteMayorMonto(orders: List<Order>): Pair<String, Double>? {
    return totalVendidoPorCliente(orders)
        .maxByOrNull { it.value }
        ?.let { it.key to it.value }
}

fun productoMasVendido(orders: List<Order>): Pair<String, Int>? {
    return unidadesVendidasPorProducto(orders)
        .maxByOrNull { it.value }
        ?.let { it.key to it.value }
}

fun categoriaMayorVenta(orders: List<Order>): Pair<String, Double>? {
    return totalVendidoPorCategoria(orders)
        .maxByOrNull { it.value }
        ?.let { it.key to it.value }
}

fun ticketPromedio(orders: List<Order>): Double {
    val deliveredOrders = orders.filter { it.status == "Entregado" }
    return if (deliveredOrders.isNotEmpty()) {
        deliveredOrders.map { it.totalPedido }.average()
    } else {
        0.0
    }
}

fun top5ProductosMasVendidos(orders: List<Order>): List<Pair<String, Int>> {
    return unidadesVendidasPorProducto(orders)
        .entries
        .sortedByDescending { it.value }
        .take(5)
        .map { it.key to it.value }
}

fun top3ClientesMayorCompra(orders: List<Order>): List<Pair<String, Double>> {
    return totalVendidoPorCliente(orders)
        .entries
        .sortedByDescending { it.value }
        .take(3)
        .map { it.key to it.value }
}

fun agruparPedidosPorEstatus(orders: List<Order>): Map<String, List<Order>> {
    return orders.groupBy { it.status }
}

fun agruparProductosPorCategoria(products: List<Product>): Map<String, List<Product>> {
    return products.groupBy { it.category }
}

fun unidadesVendidasPorProducto(orders: List<Order>): Map<String, Int> {
    return orders
        .filter { it.status == "Entregado" }
        .flatMap { it.items }
        .groupBy { it.product.name }
        .mapValues { entry -> entry.value.sumOf { it.quantity } }
}

fun reporteGeneralVentas(orders: List<Order>): SalesStats {
    val delivered = orders.filter { it.status == "Entregado" }
    val totalVendido = delivered.sumOf { it.totalPedido }
    val entregados = delivered.size

    return SalesStats(
        totalPedidos = orders.size,
        pedidosEntregados = entregados,
        pedidosCancelados = orders.count { it.status == "Cancelado" },
        totalVendido = totalVendido,
        promedioVenta = if (entregados > 0) totalVendido / entregados else 0.0
    )
}

fun reportePedidosCancelados(orders: List<Order>): List<Order> {
    return orders.filter { it.status == "Cancelado" }
}

fun reporteProductosNoVendidos(products: List<Product>, orders: List<Order>): List<Product> {
    val productosVendidos = orders
        .filter { it.status == "Entregado" }
        .flatMap { it.items }
        .map { it.product.id }
        .toSet()

    return products.filter { it.id !in productosVendidos }
}

// ------ FUNCIONES DE IMPRESION ------
fun dinero(amount: Double): String {
    return "$${String.format("%.2f", amount)}"
}

fun imprimirPedido(order: Order) {
    println("Pedido #${order.id} | Cliente: ${order.client.name} | Estatus: ${order.status} | Total: ${dinero(order.totalPedido)}")
}

fun imprimirProducto(product: Product) {
    println("- ${product.name} | Categoria: ${product.category} | Precio: ${dinero(product.price)}")
}

fun imprimirSeccion(title: String) {
    println()
    println("===== $title =====")
}

fun main() {
    val clients = cargarClientes()
    val products = cargarProductos()
    val orders = cargarPedidos(clients, products)

    println("Sistema de procesamiento funcional de pedidos")
    println("Programa de consola en Kotlin")

    // ----- ENTREGA 1 -----
    imprimirSeccion("ENTREGA 1 - Procesamiento funcional de pedidos")

    imprimirSeccion("1. Todos los pedidos registrados")
    orders.forEach { imprimirPedido(it) }

    imprimirSeccion("2. Todos los productos disponibles")
    products.forEach { imprimirProducto(it) }

    imprimirSeccion("3. Pedidos filtrados por estatus: Entregado")
    filtrarPedidosPorEstatus(orders, "Entregado").forEach { imprimirPedido(it) }

    imprimirSeccion("4. Productos filtrados por categoria: Electronica")
    filtrarProductosPorCategoria(products, "Electronica").forEach { imprimirProducto(it) }

    imprimirSeccion("5. Buscar productos por nombre: Libro")
    buscarProductosPorNombre(products, "Libro").forEach { imprimirProducto(it) }

    imprimirSeccion("6. Productos ordenados por precio de menor a mayor")
    ordenarProductosPorPrecio(products).forEach { imprimirProducto(it) }

    imprimirSeccion("7. Pedidos ordenados por monto total")
    ordenarPedidosPorMontoTotal(orders).forEach { imprimirPedido(it) }

    imprimirSeccion("8. Total vendido considerando solo pedidos entregados")
    println("Total vendido: ${dinero(totalVendidoEntregado(orders))}")

    imprimirSeccion("9. Cantidad de pedidos por estatus")
    contarPedidosPorEstatus(orders).forEach { (status, total) ->
        println("- $status: $total pedido(s)")
    }

    imprimirSeccion("10. Productos con precio mayor a ${dinero(1000.0)}")
    productosConPrecioMayorA(products, 1000.0).forEach { imprimirProducto(it) }

    imprimirSeccion("11. Cliente asociado a cada pedido")
    clientesPorPedido(orders).forEach { (orderId, client) ->
        println("Pedido #$orderId -> ${client.name} | ${client.email}")
    }

    imprimirSeccion("12. Lista resumida de pedidos")
    resumenPedidos(orders).forEach { summary ->
        println("- Cliente: ${summary.clientName} | Total: ${dinero(summary.total)} | Estatus: ${summary.status}")
    }

    // ----- ENTREGA 2 -----
    imprimirSeccion("ENTREGA 2 - Motor de reportes y estadisticas")

    imprimirSeccion("1. Total vendido por categoria")
    totalVendidoPorCategoria(orders).forEach { (category, total) ->
        println("- $category: ${dinero(total)}")
    }

    imprimirSeccion("2. Total vendido por cliente")
    totalVendidoPorCliente(orders).forEach { (clientName, total) ->
        println("- $clientName: ${dinero(total)}")
    }

    imprimirSeccion("3. Cliente con mayor monto de compras")
    clienteMayorMonto(orders)?.let { (clientName, total) ->
        println("$clientName - ${dinero(total)}")
    }

    imprimirSeccion("4. Producto mas vendido")
    productoMasVendido(orders)?.let { (productName, quantity) ->
        println("$productName - $quantity unidad(es)")
    }

    imprimirSeccion("5. Categoria con mayor venta")
    categoriaMayorVenta(orders)?.let { (category, total) ->
        println("$category - ${dinero(total)}")
    }

    imprimirSeccion("6. Ticket promedio de pedidos entregados")
    println("Ticket promedio: ${dinero(ticketPromedio(orders))}")

    imprimirSeccion("7. Top 5 productos mas vendidos")
    top5ProductosMasVendidos(orders).forEachIndexed { index, (productName, quantity) ->
        println("${index + 1}. $productName - $quantity unidad(es)")
    }

    imprimirSeccion("8. Top 3 clientes con mayor compra")
    top3ClientesMayorCompra(orders).forEachIndexed { index, (clientName, total) ->
        println("${index + 1}. $clientName - ${dinero(total)}")
    }

    imprimirSeccion("9. Pedidos agrupados por estatus")
    agruparPedidosPorEstatus(orders).forEach { (status, statusOrders) ->
        println("- $status: ${statusOrders.size} pedido(s)")
        statusOrders.forEach { order ->
            println("  Pedido #${order.id} | ${order.client.name} | ${dinero(order.totalPedido)}")
        }
    }

    imprimirSeccion("10. Productos agrupados por categoria")
    agruparProductosPorCategoria(products).forEach { (category, categoryProducts) ->
        println("- $category: ${categoryProducts.size} producto(s)")
        categoryProducts.forEach { product ->
            println("  ${product.name} | ${dinero(product.price)}")
        }
    }

    imprimirSeccion("11. Unidades vendidas por producto")
    unidadesVendidasPorProducto(orders).forEach { (productName, quantity) ->
        println("- $productName: $quantity unidad(es)")
    }

    imprimirSeccion("12. Reporte general de ventas")
    val stats = reporteGeneralVentas(orders)
    println("Total de pedidos: ${stats.totalPedidos}")
    println("Pedidos entregados: ${stats.pedidosEntregados}")
    println("Pedidos cancelados: ${stats.pedidosCancelados}")
    println("Total vendido: ${dinero(stats.totalVendido)}")
    println("Promedio de venta: ${dinero(stats.promedioVenta)}")

    imprimirSeccion("13. Reporte de pedidos cancelados")
    reportePedidosCancelados(orders).forEach { imprimirPedido(it) }

    imprimirSeccion("14. Reporte de productos no vendidos")
    reporteProductosNoVendidos(products, orders).forEach { imprimirProducto(it) }

    println()
    println("Reportes generados correctamente.")
}
