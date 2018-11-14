package lesson3

import java.lang.IllegalArgumentException
import java.util.SortedSet
import kotlin.NoSuchElementException

// Attention: comparable supported but comparator is not
class KtBinaryTree<T : Comparable<T>> : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    private var root: Node<T>? = null

    override var size = 0
        private set

    private class Node<T>(val value: T) {

        var left: Node<T>? = null

        var right: Node<T>? = null

    }

    override fun add(element: T): Boolean {
        val closest = find(element)
        val comparison = if (closest == null) -1 else element.compareTo(closest.value)
        if (comparison == 0) {
            return false
        }
        val newNode = Node(element)
        when {
            closest == null -> root = newNode
            comparison < 0 -> {
                assert(closest.left == null)
                closest.left = newNode
            }
            else -> {
                assert(closest.right == null)
                closest.right = newNode
            }
        }
        size++
        return true
    }

    override fun checkInvariant(): Boolean =
            root?.let { checkInvariant(it) } ?: true

    private fun checkInvariant(node: Node<T>): Boolean {
        val left = node.left
        if (left != null && (left.value >= node.value || !checkInvariant(left))) return false
        val right = node.right
        return right == null || right.value > node.value && checkInvariant(right)
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    override fun remove(element: T): Boolean { //T = O(N*logN) , R = O(N*logN)
        if (find(element) == null) return false
        var current = root ?: return false
        var parent = root!! //нам нужен родитель, потому что можно менять только значения ветвей(узлов)
        size--

        while (current.value != element) {
            parent = current
            current = if (current.value > element)
                current.left ?: return false
            else
                current.right ?: return false
        }

        when { //нод и узел - синонимы
            //у удаляемого нода нет двух узлов -> остаётся лишь просто установить его значение в null
            current.left == null && current.right == null -> setNode(current, parent, null)
            current.left == null -> setNode(current, parent, current.right) //нет левого узла
            current.right == null -> setNode(current, parent, current.left) //нет правого узла
            else -> { //если у удаляемого нода есть и левый, и правый узлы
                var minNode = current.right ?: return false
                var parentMinNode = current.right!!
                while (minNode.left != null) { // связано с алгоритмом удаления узла с двумя дочерними узлами
                    //левая цепочка правого нода у удаляемого узла ближе по значению к value удаляемого нода
                    //поэтому эта левая цепочка сдвигается, заполняя удаляемый нод
                    parentMinNode = minNode
                    val left = minNode.left ?: return false
                    minNode = left
                }
                when { //тоже рассматриваются несколько случаев
                    current == root && parentMinNode == minNode -> { //у минимального нода нет левой цепочки//del root
                        root = minNode
                        minNode.left = root!!.left
                    }
                    current == root && parentMinNode != minNode -> { //у минимального нода есть левая цепочка//del root
                        parentMinNode.left = minNode.right
                        root = minNode
                        minNode.left = current.left
                        minNode.right = current.right
                    }
                    parentMinNode == minNode -> setNode(current, parent, minNode) //у минимального нода нет левой цепочки
                    else -> { //если удаляется не root нод//есть левая и правая цепочки//
                        parentMinNode.left = minNode.right
                        minNode.right = current.right
                        minNode.left = current.left
                        setNode(current, parent, minNode)
                    }
                }
                minNode.left = current.left //в конце-концов, минимальный нод в левой цепочке правого узла
                // у удаляемого нода становится на место удаляемого нода
                //хорошо показано тут: https://neerc.ifmo.ru/wiki/images/thumb/d/dd/Bst_del3.png/900px-Bst_del3.png
            }
        }
        return true
    }


    private fun minElemSubtree(t: Node<T>): Node<T> {
        if (t.left != null)
            return minElemSubtree(t.left!!)
        return t
    }

    private fun maxElemSubtree(t: Node<T>): Node<T> {
        if (t.right != null)
            return maxElemSubtree(t.right!!)
        return t
    }

    private fun setNode(current: Node<T>, parent: Node<T>, set: Node<T>?) { // установить нод(текущий,
        // родитель текущего, новое значение нода)
        when (current) {
            root -> root = set //если текущий нод сфокусирован на root-нод,  установить на set
            parent.left -> parent.left = set //если текущий нод - левый узел его родителя -> смена значения левого узла
            parent.right -> parent.right = set
        }
    }

    override operator fun contains(element: T): Boolean {
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
    }

    private fun find(value: T): Node<T>? =
            root?.let { find(it, value) }

    private fun find(start: Node<T>, value: T): Node<T> {
        val comparison = value.compareTo(start.value)
        return when {
            comparison == 0 -> start
            comparison < 0 -> start.left?.let { find(it, value) } ?: start
            else -> start.right?.let { find(it, value) } ?: start
        }
    }

    inner class BinaryTreeIterator : MutableIterator<T> {

        private var current: Node<T>? = null

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private fun findNext(): Node<T>? { //без этой функции не проверить fun remove(element:T)
            if (root == null) return null
            if (current == null) return find(first())
            val point = current!! //стартуем с текущего нода, ставя маркер на него
            if (point.right != null) return minElemSubtree(point.right!!) //функция описана выше.
            // ищет минимальный элемент в цепочке
            else { //если от тек
                var searchPoint: Node<T>? = null
                var parent = root!!
                while (parent != point) {
                    val comparison = point.value.compareTo(parent.value);
                    if (comparison > 0) {
                        parent = parent.right!!
                    } else {
                        searchPoint = parent
                        parent = parent.left!!
                    }
                }
                return searchPoint
            }
        }//T = O(N*logN) , R = O(N*logN)

        override fun hasNext(): Boolean = findNext() != null

        override fun next(): T {
            current = findNext()
            return (current ?: throw NoSuchElementException()).value
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        override fun remove() {
            val point = current
            current = findNext()
            if (point != null) remove(point.value)
        }
    }

    override fun iterator(): MutableIterator<T> = BinaryTreeIterator()

    override fun comparator(): Comparator<in T>? = null

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    override fun headSet(toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    override fun tailSet(fromElement: T): SortedSet<T> {
        TODO()
    }

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }
}
