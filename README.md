# SistemaPedidos

Proyecto de consola en Kotlin realizado en Android Studio/Gradle para las entregas del sistema de pedidos.

## Como ejecutar

Desde la carpeta raiz del proyecto:

```bash
./gradlew :app:run
```

En Windows:

```bat
gradlew.bat :app:run
```

Tambien se puede abrir el proyecto en Android Studio y ejecutar la funcion `main()` del archivo:

```text
app/src/main/kotlin/com/example/sistemapedidos/Main.kt
```

## Que datos maneja el sistema

El sistema trabaja con datos cargados manualmente dentro del programa:

- Clientes: id, nombre, correo y telefono.
- Productos: id, nombre, categoria y precio.
- Articulos del pedido: producto y cantidad.
- Pedidos: id, cliente, lista de articulos y estatus.

Los estatus usados son: `Pendiente`, `Enviado`, `Entregado` y `Cancelado`.

## Consultas de la Entrega 1

El programa muestra:

1. Todos los pedidos registrados.
2. Todos los productos disponibles.
3. Pedidos filtrados por estatus.
4. Productos filtrados por categoria.
5. Busqueda de productos por nombre.
6. Productos ordenados por precio de menor a mayor.
7. Pedidos ordenados por monto total.
8. Total vendido considerando solo pedidos entregados.
9. Cantidad de pedidos por estatus.
10. Productos con precio mayor a una cantidad definida.
11. Cliente asociado a cada pedido.
12. Lista resumida con cliente, total del pedido y estatus.

## Reportes de la Entrega 2

El programa genera:

1. Total vendido por categoria.
2. Total vendido por cliente.
3. Cliente con mayor monto de compras.
4. Producto mas vendido.
5. Categoria con mayor venta.
6. Ticket promedio de pedidos entregados.
7. Top 5 de productos mas vendidos.
8. Top 3 de clientes con mayor compra.
9. Pedidos agrupados por estatus.
10. Productos agrupados por categoria.
11. Unidades vendidas por producto.
12. Reporte general de ventas.
13. Reporte de pedidos cancelados.
14. Reporte de productos no vendidos.

## Funciones funcionales utilizadas

- `filter`: se usa para filtrar pedidos entregados, cancelados o por estatus, y productos por categoria o precio.
- `map`: se usa para transformar pedidos en resumenes y extraer datos especificos.
- `flatMap`: se usa para trabajar con todos los articulos de todos los pedidos.
- `groupBy`: se usa para agrupar ventas por categoria, cliente, producto o estatus.
- `mapValues`: se usa para transformar agrupaciones en totales o conteos.
- `sumOf`: se usa para calcular subtotales, totales vendidos y unidades vendidas.
- `count`: se usa para contar pedidos por estatus.
- `sortedBy` y `sortedByDescending`: se usan para ordenar productos, pedidos, clientes y productos vendidos.
- `take`: se usa para obtener el top 5 y top 3.
- `average`: se usa para calcular el ticket promedio.

## Comparacion entre Entrega 1 y Entrega 2

La Entrega 1 se enfoca en representar los datos principales del sistema y realizar consultas basicas con listas. La Entrega 2 reutiliza esa estructura y agrega reportes de analisis, agrupaciones, acumulaciones y estadisticas generales. En esta version se conservaron las clases de datos iniciales y se agregaron funciones propias para los reportes, evitando ciclos tradicionales en el procesamiento principal.
