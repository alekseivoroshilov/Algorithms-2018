@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson5

import lesson5.impl.GraphBuilder
import java.util.*

/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 */
fun Graph.findEulerLoop(): List<Graph.Edge> { //T = O(Vertices + Edges),
// R = O(Vertices(queue) + list(verticies) + result(Edges) = O(V + E))
    var cycleExist = true
    for (vertex in this.vertices) {
        val degree = (getNeighbors(vertex)).size
        if (degree % 2 == 1) cycleExist = false
    }
    if (!cycleExist) return emptyList()

    val start = vertices.elementAt(0)
    val queue = Stack<Graph.Vertex>()
    val untouchedEdges = mutableListOf<Graph.Edge>()

    for (edge in this.edges) untouchedEdges.add(edge)
    queue.add(start)

    val list = mutableListOf<Graph.Vertex>()
    while (!queue.isEmpty()) {
        val peek = queue.peek()
        loop@ for (vertex in getNeighbors(peek)) {
            val edge = getConnection(peek, vertex)
            if (edge in untouchedEdges) {
                queue.push(vertex)
                untouchedEdges.remove(edge)
                break@loop
            }
        }
        if (peek == queue.peek()) list.add(queue.pop())
    }
    val result = mutableListOf<Graph.Edge>()
    for (i in 1 until list.size) {
        result.add(getConnection(list.get(i - 1), list.get(i))!!)
    }
    return result.toList()
}


/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 */
fun Graph.minimumSpanningTree(): Graph {
    TODO()
}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Эта задача может быть зачтена за пятый и шестой урок одновременно
 */
//https://neerc.ifmo.ru/wiki/index.php?title=Обход_в_глубину,_цвета_вершин
//Depth-first search //T = O(Vertexes + Edges), R = O(Vertexes)
fun startDFS(graph: Graph, start: Graph.Vertex, setOfVertexLists: List<MutableSet<Graph.Vertex>>): Set<Graph.Vertex> {
    var counter = 0
    val visited = mutableSetOf<Graph.Vertex>() // массив посещённых вершин
    val result = setOfVertexLists
    fun dfs(start: Graph.Vertex): Set<Graph.Vertex> {
        val neighboursForVertex = graph.getNeighbors(start).filter { it !in visited } //поиск соседей, которых не посетили
        visited.add(start)
        neighboursForVertex.forEach {
            result[counter].add(it)
            counter = if (counter == 0) 1 else 0 //вершина пройдена
            dfs(it)
        }
        counter = if (counter == 0) 1 else 0
        visited.remove(start)
        return result.reversed().maxBy { it.size }?.toSet() ?: setOf()
    }
    return dfs(start)
}

fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> {
    val start = vertices.elementAt(0)
    val setOfVertexLists = listOf(mutableSetOf<Graph.Vertex>(), mutableSetOf())
    setOfVertexLists[1].add(start)
    return startDFS(this, start, setOfVertexLists)

}
// итого: T = O(V + E), R = O(2*V)

/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 */
fun Graph.longestSimplePath(): Path {
    TODO()
}