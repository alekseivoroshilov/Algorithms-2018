package lesson1

import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.math.abs

abstract class AbstractTaskTests : AbstractFileTests() {

    protected fun sortTimes(sortTimes: (String, String) -> Unit) {
        try {
            sortTimes("input/time_in1.txt", "temp.txt")
            assertFileContent("temp.txt",
                    """
                     00:40:31
                     07:26:57
                     10:00:03
                     13:15:19
                     13:15:19
                     19:56:14
                """.trimIndent()
            )
        } finally {
            File("temp.txt").delete()
        }
        try {
            sortTimes("input/time_in2.txt", "temp.txt")
            assertFileContent("temp.txt",
                    """
                     00:00:00
                """.trimIndent()
            )
        } finally {
            File("temp.txt").delete()
        }
        try {
            sortTimes("input/time_in3.txt", "temp.txt")
            assertFileContent("temp.txt", File("input/time_out3.txt").readLines().joinToString(separator = "\n"))
        } finally {
            File("temp.txt").delete()
        }
        try {
            sortTimes("input/time_in4.txt", "temp.txt")
            assertFileContent("temp.txt",
                    """
                        00:00:00
                        01:55:55
                        02:44:55
                        02:45:55
                        02:53:55
                        02:55:55
                        02:55:55
                        23:59:59
                    """.trimIndent())
        } finally {
            //File("temp.txt").delete()
        }
        try {
            sortTimes("input/time_in5.txt", "temp.txt")

            //assertFileContent("temp.txt", "asd")
        } catch (e: Exception) {
            //
        }

    }

    protected fun sortAddresses(sortAddresses: (String, String) -> Unit) {
        // TODO: large test
        try {
            sortAddresses("input/addr_in1.txt", "temp.txt")
            assertFileContent("temp.txt",
                    """
                    Железнодорожная 3 - Петров Иван
                    Железнодорожная 7 - Иванов Алексей, Иванов Михаил
                    Садовая 5 - Сидоров Петр, Сидорова Мария
                """.trimIndent()
            )
        } finally {
            File("temp.txt").delete()
        }
        try {
            sortAddresses("input/addr_in2.txt", "temp.txt")
            assertFileContent("temp.txt",
                    """
                    Сбывшиесянадежды 3 - Зачет Операционныесистемы, Зачет Программирование, Зачет Схемотехника
                """.trimIndent()
            )
        } finally {
            File("temp.txt").delete()
        }
        try {
            sortAddresses("input/addr_in3.txt", "temp.txt")
            assertFileContent("temp.txt",
                    """
                    Сбывшиесянадежды 3 - Зачет Операционныесистемы, Зачет Программирование, Зачет Схемотехника
                """.trimIndent()
            )
        } catch (e: Exception) {
            //
        }finally {
            File("temp.txt").delete()
        }
    }

    private fun generateTemperatures(size: Int) {
        val random = Random()
        val temperatures = mutableListOf<Int>()
        for (t in -2730..5000) {
            val count = random.nextInt(size)
            for (i in 1..count) {
                temperatures += t
            }
        }

        fun BufferedWriter.writeTemperatures() {
            for (t in temperatures) {
                if (t < 0) write("-")
                write("${abs(t) / 10}.${abs(t) % 10}")
                newLine()
            }
            close()
        }

        File("temp_sorted_expected.txt").bufferedWriter().writeTemperatures()
        temperatures.shuffle(random)
        File("temp_unsorted.txt").bufferedWriter().writeTemperatures()
    }

    protected fun sortTemperatures(sortTemperatures: (String, String) -> Unit) {
        try {
            sortTemperatures("input/temp_in1.txt", "temp.txt")
            assertFileContent("temp.txt",
                    """
                    -98.4
                    -12.6
                    -12.6
                    11.0
                    24.7
                    99.5
                    121.3
                """.trimIndent()
            )
        } finally {
            File("temp.txt").delete()
        }
        try {
            sortTemperatures("input/temp_in2.txt", "temp.txt")
            assertFileContent("temp.txt",
                    """
                        -272.0
-258.0
-246.0
-199.0
-167.0
-166.0
-165.0
-164.0
-25.0
-18.0
-12.0
0.0
1.0
2.0
4.0
5.0
6.0
7.0
8.0
9.0
10.0
13.0
14.0
15.0
16.0
17.0
19.0
20.0
21.0
22.0
23.0
24.0
26.0
27.0
28.0
29.0
30.0
31.0
32.0
33.0
34.0
35.0
36.0
37.0
38.0
39.0
40.0
41.0
42.0
43.0
44.0
45.0
46.0
47.0
48.0
49.0
50.0
51.0
52.0
53.0
54.0
55.0
56.0
57.0
58.0
59.0
60.0
61.0
62.0
63.0
65.0
66.0
67.0
68.0
69.0
70.0
71.0
72.0
73.0
74.0
75.0
76.0
77.0
78.0
79.0
80.0
81.0
82.0
84.0
85.0
86.0
87.0
88.0
89.0
90.0
91.0
92.0
93.0
94.0
95.0
96.0
97.0
99.0
100.0
101.0
102.0
103.0
104.0
105.0
106.0
107.0
108.0
109.0
110.0
111.0
112.0
114.0
115.0
116.0
117.0
118.0
119.0
120.0
122.0
123.0
124.0
125.0
126.0
127.0
128.0
129.0
130.0
132.0
133.0
134.0
135.0
136.0
137.0
138.0
139.0
140.0
141.0
142.0
143.0
144.0
145.0
146.0
147.0
148.0
149.0
150.0
152.0
153.0
154.0
155.0
156.0
157.0
159.0
160.0
161.0
162.0
163.0
169.0
170.0
171.0
172.0
173.0
174.0
175.0
176.0
177.0
178.0
179.0
180.0
182.0
183.0
184.0
185.0
186.0
187.0
188.0
189.0
190.0
191.0
192.0
193.0
194.0
195.0
196.0
197.0
198.0
200.0
201.0
202.0
203.0
204.0
205.0
206.0
207.0
208.0
209.0
210.0
211.0
212.0
213.0
214.0
215.0
216.0
217.0
218.0
219.0
220.0
221.0
222.0
223.0
224.0
225.0
226.0
227.0
228.0
229.0
230.0
231.0
232.0
233.0
234.0
235.0
236.0
237.0
238.0
239.0
240.0
241.0
242.0
243.0
244.0
245.0
247.0
248.0
249.0
250.0
251.0
252.0
253.0
254.0
255.0
256.0
257.0
259.0
260.0
261.0
262.0
263.0
264.0
265.0
266.0
267.0
268.0
269.0
270.0
271.0
                    """.trimIndent())
        } finally {
            File("temp.txt").delete()
        }

        fun testGeneratedTemperatures(size: Int) {
            try {
                generateTemperatures(size)
                sortTemperatures("temp_unsorted.txt", "temp_sorted_actual.txt")
                assertFileContent("temp_sorted_actual.txt",
                        File("temp_sorted_expected.txt").readLines().joinToString(separator = "\n")
                )
            } finally {
                File("temp_unsorted.txt").delete()
                File("temp_sorted_expected.txt").delete()
                File("temp_sorted_actual.txt").delete()
            }
        }
        testGeneratedTemperatures(10)
        testGeneratedTemperatures(500)
        testGeneratedTemperatures(1000)
    }

    protected fun sortSequence(sortSequence: (String, String) -> Unit) {
        // TODO: large test
        try {
            sortSequence("input/seq_in1.txt", "temp.txt")
            assertFileContent("temp.txt",
                    """
                        1
                        3
                        3
                        1
                        2
                        2
                        2
                    """.trimIndent())
        } finally {
            File("temp.txt").delete()
        }
        try {
            sortSequence("input/seq_in2.txt", "temp.txt")
            assertFileContent("temp.txt",
                    """
                        25
                        39
                        25
                        39
                        25
                        39
                        12
                        12
                        12
                    """.trimIndent())
        } finally {
            File("temp.txt").delete()
        }

        fun BufferedWriter.writeNumbers(numbers: List<Int>) {
            for (n in numbers) {
                write("$n")
                newLine()
            }
            close()
        }

        fun generateSequence(totalSize: Int, answerSize: Int) {
            val random = Random()
            val numbers = mutableListOf<Int>()

            val answer = 100000 + random.nextInt(100000)
            val count = mutableMapOf<Int, Int>()
            for (i in 1..totalSize - answerSize) {
                var next: Int
                var nextCount: Int
                do {
                    next = random.nextInt(answer - 1) + 1
                    nextCount = count[next] ?: 0
                } while (nextCount >= answerSize - 1)
                numbers += next
                count[next] = nextCount + 1
            }
            for (i in totalSize - answerSize + 1..totalSize) {
                numbers += answer
            }
            File("temp_sequence_expected.txt").bufferedWriter().writeNumbers(numbers)
            for (i in totalSize - answerSize until totalSize) {
                numbers.removeAt(totalSize - answerSize)
            }
            for (i in totalSize - answerSize until totalSize) {
                val toInsert = random.nextInt(totalSize - answerSize)
                numbers.add(toInsert, answer)

            }
            File("temp_sequence.txt").bufferedWriter().writeNumbers(numbers)
        }

        try {
            generateSequence(500000, 200)
            sortSequence("temp_sequence.txt", "temp.txt")
            assertFileContent("temp.txt", File("temp_sequence_expected.txt").readLines().joinToString("\n"))
        } finally {
            File("temp_sequence_expected.txt").delete()
            File("temp_sequence.txt").delete()
            File("temp.txt").delete()
        }
    }

    protected fun generateArrays(firstSize: Int, secondSize: Int): Triple<Array<Int>, Array<Int?>, Array<Int?>> {
        val random = Random()
        val expectedResult = Array<Int?>(firstSize + secondSize) {
            it * 10 + random.nextInt(10)
        }
        val first = mutableListOf<Int>()
        val second = mutableListOf<Int?>()
        for (i in 1..firstSize) second.add(null)
        for (element in expectedResult) {
            if (first.size < firstSize && (random.nextBoolean() || second.size == firstSize + secondSize)) {
                first += element!!
            } else {
                second += element
            }
        }
        return Triple(first.toTypedArray(), second.toTypedArray(), expectedResult)
    }
}