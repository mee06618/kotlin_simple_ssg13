import java.io.File

fun main() {
    //testWriteFile();
    //testWriteFile2();
    //testWriteFile3();
    //testReadFile();
    testReadFile2();
}

data class TestArticle(
    val id: Int,
    val title: String,
    val body: String
) {
    fun toJson(): String {
        var jsonStr = ""

        jsonStr += "{"
        jsonStr += "\r\n"

        jsonStr += "\t" + """ "id":$id """.trim() + ","

        jsonStr += "\r\n"

        jsonStr += "\t" + """ "title":"$title" """.trim() + ","

        jsonStr += "\r\n"

        jsonStr += "\t" + """ "body":"$body" """.trim()

        jsonStr += "\r\n"

        jsonStr += "}"

        return jsonStr
    }
}

fun testReadFile2() {
    testWriteFile3();

    // fileContent 변수에 담기는 내용
    /*
    {
        "id":1,
        "title":"제목1",
        "body":"내용1"
    }
    */
    val fileContent = File("test/3.json").readText(Charsets.UTF_8)

    val testArticle = testArticleFromJson(fileContent)
    println(testArticle.id) // 출력 : 1
}

fun testArticleFromJson(jsonStr: String): TestArticle {
    val jsonMap = mapFromJson(jsonStr)

    val id = jsonMap["id"].toString().toInt()
    val title = jsonMap["title"].toString()
    val body = jsonMap["body"].toString()

    return TestArticle(id, title, body)
}

fun mapFromJson(jsonStr: String): Map<String, Any> {
    val map = mutableMapOf<String, Any>()

    var jsonStr = jsonStr.drop(1)
    jsonStr = jsonStr.dropLast(1)

    val jsonItems = jsonStr.split(",\r\n")

    for (jsonItem in jsonItems) {
        val jsonItemBits = jsonItem.trim().split(":", limit = 2)

        val key = jsonItemBits[0].trim().drop(1).dropLast(1)
        var value = jsonItemBits[1].trim()

        when {
            value == "true" -> {
                map[key] = true
            }
            value == "false" -> {
                map[key] = false
            }
            value.startsWith("\"") -> {
                map[key] = value.drop(1).dropLast(1)
            }
            value.contains(".") -> {
                map[key] = value.toDouble()
            }
            else -> {
                map[key] = value.toInt()
            }
        }
    }

    return map.toMap()
}

fun testReadFile() {
    testWriteFile()

    val fileContent = File("test/1.txt").readText(Charsets.UTF_8)
    println("test/1.txt의 내용 : $fileContent")
}

fun testWriteFile3() {
    val testArticle = TestArticle(8, "제목1", "내용1")
    File("test/3.json").writeText(testArticle.toJson())
}

fun testWriteFile2() {
    val id = 1
    val title = "안녕하세요, 저는,age=33"
    val body = "내용"

    var fileContent = ""
    fileContent += "{"
    fileContent += "\r\n"
    fileContent += "\t" + """ "id":$id, """.trim()
    fileContent += "\r\n"
    fileContent += "\t" + """ "title":"$title," """.trim()
    fileContent += "\r\n"
    fileContent += "\t" + """ "body":"$body" """.trim()
    fileContent += "\r\n"
    fileContent += "}"

    File("test/2.txt").writeText(fileContent)
}

fun testWriteFile() {
    File("test/1.txt").writeText("안녕하세요.")
}