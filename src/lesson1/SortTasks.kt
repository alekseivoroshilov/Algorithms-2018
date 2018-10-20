@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File
import java.io.IOException
import java.lang.StringBuilder
import java.util.Arrays

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
 * каждый на отдельной строке. Пример:
 *
 * 13:15:19
 * 07:26:57
 * 10:00:03
 * 19:56:14
 * 13:15:19
 * 00:40:31
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 00:40:31
 * 07:26:57
 * 10:00:03
 * 13:15:19
 * 13:15:19
 * 19:56:14
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */

fun sortTimes(inputName: String, outputName: String) {
    val intTimes = mutableListOf<Int>()
    for (line in File(inputName).readLines()) {
        if (lineToInt(line) == -1) {
            throw IOException()
        }
        intTimes.add(lineToInt(line))
    }
    val sortedTimes = intTimes.toIntArray()
    heapSort(sortedTimes) //значение не имеет, но это, вроде, самая быстрая сортировка в данном случае
    val writer = File(outputName).bufferedWriter()
    for (seconds in sortedTimes) {
        writer.write(String.format("%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60) + "\n")
    }
    writer.close()
}

fun lineToInt(str: String): Int {
    val strTime = str.split(":")
    var timeInSeconds = 0
    for (part in strTime) timeInSeconds = timeInSeconds * 60 + part.toInt()
    return timeInSeconds
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    val streets = mutableMapOf<String, MutableList<String>>()
    val format = Regex("^[А-я]+ [А-я]+ - [А-я, ]+ [0-9]+\$") //соблюдение формата

    for (line in File(inputName).readLines()) {
        if (!line.matches(format)) throw IOException()
        val streetAndName = line.split(" - ")
        val name = streetAndName[0]
        val street = streetAndName[1]
        if (street in streets) {
            val listOfNames = streets.getOrPut((street)) { mutableListOf() } //подсказали. пытался сделать с getOrDefault. Спросить на лекции
            listOfNames.add(name) //имя добавляется в значение ключа(улицы)
        } else streets.put(street, mutableListOf(name))

        val sortedMap = streets.toSortedMap() //сортированные по ключам улицы
        val writer = File(outputName).bufferedWriter()

        for (street in sortedMap) {
            street.value.sort()
            val sorted = street.value
            writer.write(street.key + " - " +
                    "$sorted".replace("[","").replace("]","") + "\n")
        }
        writer.close()
    }
}


/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */
fun sortTemperatures(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    TODO()
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    for (i in 0..first.size - 1) second[i] = first[i] //Не могу понять, как написать с одной строчки. Спросить на лекции
    Arrays.sort(second)
}


